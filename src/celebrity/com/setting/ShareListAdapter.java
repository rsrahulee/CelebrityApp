package celebrity.com.setting;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import celebrity.com.FacebookShare;
import celebrity.com.MainFragmentActivity;
import celebrity.com.NoConnectivityScreen;
import celebrity.com.R;
import celebrity.com.twitter.PrepareRequestTokenActivity;
import celebrity.com.twitter.TwitterUtils;

public class ShareListAdapter extends BaseAdapter {

	Context context;
	private ArrayList<String> shareItems;
	MainFragmentActivity mContext;

	private static int FACEBOOK = 0;
	private static int TWITTER = 1;
	private ToggleButton tb;
	private Handler mhandler;

	public ShareListAdapter() {

	}

	public ShareListAdapter(Context context, ArrayList<String> shareItems, MainFragmentActivity mainContext) {
		this.context = context;
		this.shareItems = shareItems;
		this.mContext = mainContext;
	}

	@Override
	public int getCount() {
		if (shareItems != null)
			return shareItems.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int index) {
		return shareItems.get(index);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.share_list_item, parent, false);
		TextView tv = (TextView) v.findViewById(R.id.testViewFacebook);
		tv.setText(shareItems.get(position));

		RelativeLayout rlt = (RelativeLayout) v.findViewById(R.id.relativeLayoutshareItem);
		rlt.setId(position);

		tb = (ToggleButton) v.findViewById(R.id.toggleShare);
		tb.setId(position);

		mhandler = new Handler();

		if (position == 0) {
			String facebook_on = MainFragmentActivity.appStatus.get("FB_ON");
			if (facebook_on.equals("") || facebook_on.equals(null)) {
				tb.setChecked(false);
			} else {
				tb.setChecked(true);
			}
		} else if (position == 1) {
			String twitter_on = MainFragmentActivity.appStatus.get("TW_ON");

			if (twitter_on.equals("")) {
				tb.setChecked(false);
			} else {
				tb.setChecked(true);
			}
		}

		tb.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (((ToggleButton) v).isChecked()) {
					Log.i("button name----------", "" + v.getId());
					Log.i("ShareListAdapter", "Toggle button is On");
					if (CheckAndchangeStatus(v.getId(), true)) {
						if (v.getId() == FACEBOOK) {
							onFacebookClick(v);
						} else if (v.getId() == TWITTER) {
							onTwitterClick(v);
						}
					}
				} else {
					Log.i("ShareListAdapter", "Toggle button is Off");
					CheckAndchangeStatus(v.getId(), false);
				}
			}
		});

		rlt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		return v;
	}

	// on FacebookStatusChanged store status to preferences
	public void onFacebookClick(View v) {

		String access_token = MainFragmentActivity.appStatus
				.getSharedStringValue(MainFragmentActivity.appStatus.FACEBOOK_TOKEN);
		if (access_token.equals(null)) {
			if (MainFragmentActivity.appStatus.isOnline()) {
				Intent intent_ShareFB = new Intent(context, FacebookShare.class);
				context.startActivity(intent_ShareFB);
			} else {
				Log.v("LoginActivity", "App is not online!");
				Intent intent = new Intent(context, NoConnectivityScreen.class);
				context.startActivity(intent);
				// mContext.finish();
			}
		} else {
			Toast.makeText(context, "Already Logged in to Facebook", Toast.LENGTH_SHORT).show();
		}
	}

	// on TwitterStatusChanged store status to preferences
	public void onTwitterClick(View v) {
		if (MainFragmentActivity.appStatus.isOnline()) {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			Log.e("onFacebookClick", "Facebook Clicked ");

			if (TwitterUtils.isAuthenticated(prefs)) {

				Log.i("---------------", "TwitterUtils.isAuthenticated(prefs)");
				Toast.makeText(context, "Already Logged in to Twitter", Toast.LENGTH_SHORT).show();
			} else {

				Intent i = new Intent(context, PrepareRequestTokenActivity.class);
				context.startActivity(i);
				// mContext.finish();
			}
		} else {
			Log.v("ShareListAdapter", "App is not online!");
			Intent intent = new Intent(context, NoConnectivityScreen.class);
			context.startActivity(intent);
			// mContext.finish();
		}
	}

	private boolean CheckAndchangeStatus(int isFacebookOrTwitter, boolean bIsChecked) {
		boolean bReturnStatus = false;
		if (isFacebookOrTwitter == FACEBOOK) {
			if (bIsChecked) {
				MainFragmentActivity.appStatus.save("FB_ON", "FB_ON");
				bReturnStatus = true;
			} else {
				MainFragmentActivity.appStatus.remove("FB_ON");
			}
		} else if (isFacebookOrTwitter == TWITTER) {
			if (bIsChecked) {
				tb.setChecked(true);
				MainFragmentActivity.appStatus.save("TW_ON", "TW_ON");
				bReturnStatus = true;
			} else {

				tb.setChecked(false);
				MainFragmentActivity.appStatus.remove("TW_ON");
			}
		}
		return bReturnStatus;
	}

	public void onAuthenticationResult(Boolean success) {
		if (success) {
			message("Social preferences updated!");
		} else {
			message("Fail to update social preferences!");
		}
	}

	public void message(String msg) {
		final String mesage = msg;
		mhandler.post(new Runnable() {
			@Override
			public void run() {
				Toast toast = Toast.makeText(context, mesage, 8000);
				toast.show();
			}
		});
	}
}
