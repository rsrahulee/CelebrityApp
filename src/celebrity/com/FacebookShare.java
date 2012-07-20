package celebrity.com;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import celebrity.com.facebook.DialogError;
import celebrity.com.facebook.Facebook;
import celebrity.com.facebook.Facebook.DialogListener;
import celebrity.com.facebook.FacebookError;
import celebrity.com.facebook.Util;

public class FacebookShare extends Activity {

	Facebook facebook = new Facebook("358310527573301");// 337405099674426
//	public ProgressDialog mProgressDialog;
	String access_token;

	private static AppStatus appStatus;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		appStatus = AppStatus.getInstance(this);

		access_token = appStatus.getSharedStringValue(appStatus.FACEBOOK_TOKEN);

		if (access_token != null) {
			facebook.setAccessToken(access_token);
		}

		if (!facebook.isSessionValid()) {

			facebook.authorize(this, new String[] {"publish_stream",
					"read_stream", "offline_access" ,"user_photos","email"}, new DialogListener() {
				@Override
				public void onComplete(Bundle values) {
					appStatus.saveSharedStringValue(appStatus.FACEBOOK_TOKEN,
							facebook.getAccessToken());
					
//					appStatus.saveSharedBoolValue(
//							appStatus.FACEBOOK_ON, true);

//					getFBId();
					Intent intent = new Intent(FacebookShare.this,MainFragmentActivity.class);					
					startActivity(intent);
					finish();
				}

				@Override
				public void onCancel() {
					// TODO Auto-generated method stub
					Log.i("####checkin", "on cancel");
					finish();
				}

				@Override
				public void onError(DialogError e) {
					// TODO Auto-generated method stub
					Log.i("####checkin", "on error");
					finish();
				}

				@Override
				public void onFacebookError(FacebookError e) {
					// TODO Auto-generated method stub
					Log.i("####checkin", "on FB error");
					finish();
				}
			});
		} else {
//			appStatus.saveSharedBoolValue(appStatus.FACEBOOK_ON, true);
//			getFBId();
			Intent intent = new Intent(FacebookShare.this,MainFragmentActivity.class);
			startActivity(intent);
			finish();
		}
	}

//	public void getFBId() {
//		Bundle bundle = new Bundle();
//		try {
//			String response = facebook.request("me", bundle);
//			Log.i("friends response", response);
//			JSONObject json = Util.parseJson(response);
//			String facebookID = json.getString("id");
//
//			appStatus.saveSharedStringValue(appStatus.FB_UID, facebookID);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (FacebookError e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		facebook.authorizeCallback(requestCode, resultCode, data);
	}

//	@Override
//	public void onResume() {
//		super.onResume();
//	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onAuthenticationResult(Boolean success) {
		if (success) {
			this.removeDialog(0);
			DisplayToast("Facebook preferences posted!");
		} else {
			DisplayToast("Fail to post facebook preferences!");
		}
	}

	private void DisplayToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		this.removeDialog(0);
	}

//	@Override
//	protected Dialog onCreateDialog(int id, Bundle args) {
//		final ProgressDialog dialog = new ProgressDialog(this);
//		dialog.setTitle("Please Wait...");
//		dialog.setIndeterminate(true);
//		dialog.setCancelable(true);
//		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//			@Override
//			public void onCancel(DialogInterface dialog) {
//				Log.i("CHECKINFORGOOD", "user cancelling authentication");
//
//			}
//		});
//		mProgressDialog = dialog;
//		return dialog;
//	}

//	@Override
//	public void onPause() {
//		super.onPause();
//	}
}
