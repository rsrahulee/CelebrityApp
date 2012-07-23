package celebrity.com.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;
import celebrity.com.MainFragmentActivity;
import celebrity.com.WhatIamUptoFragment;
import celebrity.com.constants.Constant;
import celebrity.com.parser.ParseResult;
import celebrity.com.webservice.RestClient;

public class FeedTask extends AsyncTask<Double, Integer, ArrayList<String>> {

	ArrayList<String> feedList;
	MainFragmentActivity mainContext;

	public FeedTask(MainFragmentActivity context) {
		mainContext = context;
	}

	protected ArrayList<String> doInBackground(Double... create_params) {
		Log.v("getImagesUrls----------------", "getImagesUrls");

		RestClient restClient = new RestClient();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("access_token",
				Constant.access_token));

		try {
			String json = restClient.doNewApiCall(
					"https://graph.facebook.com/JohnnyDeppNewsPage/feed",
					"GET", nameValuePairs);
			feedList = ParseResult.INSTANCE.parseFbFeeds(json);
			Log.i("json--------------", String.valueOf(json));
		} catch (ClientProtocolException e) {
			Log.i("ClientProtocolException-------------", String.valueOf(e));
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("IOException--------------", String.valueOf(e));
		}
		return feedList;
	}

	protected void onPostExecute(ArrayList<String> result) {

		WhatIamUptoFragment uptoFragment = (WhatIamUptoFragment) mainContext
				.getSupportFragmentManager().findFragmentByTag("upto_tab");

		if (uptoFragment != null) {
			if (uptoFragment.getView() != null) {
				uptoFragment.onFeedResultOutput(result);
			}
		}
	}
}
