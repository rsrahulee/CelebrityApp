package celebrity.com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import celebrity.com.parser.ParseResult;
import celebrity.com.parser.RestClient;
import celebrity.com.setting.ShareListAdapter;

public class WhatIamUptoFragment extends ListFragment {

	WhatIamUptoFragment mBusinessListFragment;
	MainScreen context;
	public static ArrayList<String> feedList;
	public static ArrayList<String> tweetList;
	public static ArrayList<String> feed_tweet;
	ShareListAdapter sharelist;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.context = (MainScreen) this.getActivity();
		Log.v("celeb", "In onAttach of what I am AUpto Fragment");

		feed_tweet = new ArrayList<String>();
		context.showDialog(0);
	}

	public void onResume() {
		Log.v("celeb", "In onAttach of what I am AUpto Fragment");

		super.onResume();

//		feed_tweet = new ArrayList<String>();

		if (!(feed_tweet.isEmpty())) {//(!feedList.isEmpty()) || (!tweetList.isEmpty())
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
					android.R.layout.simple_list_item_1, feed_tweet);
			setListAdapter(adapter);
		} else {
			String fb_is_on = MainScreen.appStatus.get("FB_ON");
			String tw_is_on = MainScreen.appStatus.get("TW_ON");

			if (!fb_is_on.equals("")) {
				feedList = getFeedsUrls();
				if(!(feedList.isEmpty())){
					feed_tweet.addAll(feedList);
				}

			} else {
				Toast.makeText(context, "Facebook is OFF,make it ON",
						Toast.LENGTH_SHORT).show();
			}
			if (!tw_is_on.equals("")) {
				tweetList = getTweetsUrls();
				if(!(tweetList.isEmpty())){
					feed_tweet.addAll(tweetList);
				}				
			} else {
				Toast.makeText(context, "Twitter is OFF,make it ON",
						Toast.LENGTH_SHORT).show();
			}

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
					android.R.layout.simple_list_item_1, feed_tweet);
			setListAdapter(adapter);
		}
		context.removeDialog(0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v;

		v = inflater.inflate(R.layout.whatamupto_fragment, container, false);

		return v;
	}

	public ArrayList<String> getFeedsUrls() {
		Log.v("getImagesUrls----------------", "getImagesUrls");

		RestClient restClient = new RestClient();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs
				.add(new BasicNameValuePair(
						"access_token",
						"AAAEy3j8cizoBAOZAWyo1YgcgS6OcYTZAEBJVaBciDbbYRocn7DPmd2eMNsPVMA2HFUI2XfZBmrFvNnQ6ssV7XZCf6m36kOxBWZAZAKTZAmGlwZDZD"));

		try {
			String json = restClient.doNewApiCall(
					"https://graph.facebook.com/JohnnyDeppNewsPage/feed",
					"GET", nameValuePairs);
			feedList = ParseResult.INSTANCE.parseFbFeeds(json);
			Log.i("json::::::::::", String.valueOf(json));
		} catch (ClientProtocolException e) {
			Log.i("ClientProtocolException::::::::::", String.valueOf(e));
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("IOException::::::::::", String.valueOf(e));
		}
		return feedList;
	}

	public ArrayList<String> getTweetsUrls() {
		RestClient restClient = new RestClient();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("q", "JohnnyDeppNews"));
		nameValuePairs.add(new BasicNameValuePair("result_type", "mixed"));
		try {
			String json = restClient.doApiCall("search.json", "GET",
					nameValuePairs);
			Log.i("json::::::::::", String.valueOf(json));

			tweetList = ParseResult.INSTANCE.parseTweets(json);

		} catch (ClientProtocolException e) {
			Log.i("ClientProtocolException::::::::::", String.valueOf(e));
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("IOException::::::::::", String.valueOf(e));
		}
		return tweetList;
	}
}