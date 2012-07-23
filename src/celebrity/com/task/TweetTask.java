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
import celebrity.com.parser.ParseResult;
import celebrity.com.webservice.RestClient;

public class TweetTask extends AsyncTask<Double, Integer, ArrayList<String>> {

	ArrayList<String> tweetList;
	MainFragmentActivity mainContext;

	public TweetTask(MainFragmentActivity context) {
		mainContext = context;
	}

	protected ArrayList<String> doInBackground(Double... create_params) {
		RestClient restClient = new RestClient();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("q", "JohnnyDeppNews"));
		nameValuePairs.add(new BasicNameValuePair("result_type", "mixed"));
		try {
			String json = restClient.doApiCall("search.json", "GET",
					nameValuePairs);
			Log.i("json------------", String.valueOf(json));

			tweetList = ParseResult.INSTANCE.parseTweets(json);

		} catch (ClientProtocolException e) {
			Log.i("ClientProtocolException---------------", String.valueOf(e));
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("IOException------------------", String.valueOf(e));
		}
		return tweetList;
	}

	protected void onPostExecute(ArrayList<String> result) {

		WhatIamUptoFragment uptoFragment = (WhatIamUptoFragment) mainContext
				.getSupportFragmentManager().findFragmentByTag("upto_tab");

		if (uptoFragment != null) {
			if (uptoFragment.getView() != null) {
				uptoFragment.onTweetResultOutput(result);
			}
		}
	}
}
