package celebrity.com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import celebrity.com.adapter.ImageAdapter;
import celebrity.com.parser.ParseResult;
import celebrity.com.parser.RestClient;
import celebrity.com.setting.ShareListAdapter;

public class ImagesFragment extends Fragment {

	ImagesFragment imagesFragment;
	MainFragmentActivity context;
	GridView girGridView;
	public static ArrayList<String> imageList;
	// private ProgressDialog loading;
	Handler mhandler;
	ShareListAdapter sharelist = new ShareListAdapter();

	// ImageAdapter adapter;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.context = (MainFragmentActivity) this.getActivity();

		 context.showDialog(0);

		// mhandler = new Handler();
		// loading = new ProgressDialog(context);
		// loading.setMessage("Please wait Loading...");
		// loading.setCancelable(true);

		Log.v("onAttach----------------", "onAttach");

		// ArrayList<String> imgArr = getImagesUrls();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v("onCreate----------------", "onCreate");

		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

//		context.showDialog(0);

		Log.v("onCreateView----------------", "onCreateView");

		View v;

		// new CelebImagesTask(context).execute();

		v = inflater.inflate(R.layout.images_fragment, container, false);
		// showLoading(true, "Please wait", "Fetching Images");
		// Thread t = new Thread(new Runnable() {
		// @Override
		// public void run() {
		// adapter = new ImageAdapter(context, getImagesUrls());
		// }
		// });
		// t.start();

		girGridView = (GridView) v.findViewById(R.id.gridview);			
		
		return v;
	}

	public void onResume() {

		// context.removeDialog(0);
		// showLoading(false, "", "");
		Log.v("onResume----------------", "onResume");
		super.onResume();
		
		if (imageList != null) {
			girGridView.setAdapter(new ImageAdapter(context, imageList));
		} else {
			String fb_is_on = MainFragmentActivity.appStatus.get("FB_ON");
			if (!(fb_is_on.equals(""))) {
				imageList = getImagesUrls();
				girGridView.setAdapter(new ImageAdapter(context, imageList));
			} else {
				Toast.makeText(context, "Facebook is OFF,make it ON",
						Toast.LENGTH_SHORT).show();
			}
		}

		girGridView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long arg3) {
//						Toast.makeText(context, imageList.get(position),
//								Toast.LENGTH_SHORT).show();
					}
				});
		context.removeDialog(0);
	}

	// public void getImages(ArrayList<String> result) {
	// imageList = result;
	// }

	public ArrayList<String> getImagesUrls() {
		Log.v("getImagesUrls----------------", "getImagesUrls");

		RestClient restClient = new RestClient();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs
				.add(new BasicNameValuePair(
						"access_token",
						"AAAEy3j8cizoBAOZAWyo1YgcgS6OcYTZAEBJVaBciDbbYRocn7DPmd2eMNsPVMA2HFUI2XfZBmrFvNnQ6ssV7XZCf6m36kOxBWZAZAKTZAmGlwZDZD"));

		nameValuePairs.add(new BasicNameValuePair("limit", "" + 10));
		try {
			String json = restClient.doNewApiCall(
					"https://graph.facebook.com/10150307510509807/photos",
					"GET", nameValuePairs);
			imageList = ParseResult.INSTANCE.parseData(json);
			Log.i("json::::::::::", String.valueOf(json));
		} catch (ClientProtocolException e) {
			Log.i("ClientProtocolException::::::::::", String.valueOf(e));
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("IOException::::::::::", String.valueOf(e));
		}
		return imageList;
	}

	// void showLoading(final boolean show, final String title, final String
	// msg) {
	// mhandler.post(new Runnable() {
	// @Override
	// public void run() {
	// if (show) {
	// if (loading != null) {
	// loading.setTitle(title);
	// loading.setMessage(msg);
	// loading.show();
	// }
	// } else {
	// loading.cancel();
	// loading.dismiss();
	// }
	// }
	// });
	// }
}