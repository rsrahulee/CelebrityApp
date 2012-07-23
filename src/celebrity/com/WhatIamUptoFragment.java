package celebrity.com;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import celebrity.com.adapter.FeedTweetAdapter;
import celebrity.com.constants.Constant;
import celebrity.com.facebook.Facebook;
import celebrity.com.task.FeedTask;
import celebrity.com.task.TweetTask;
import celebrity.com.twitter.TwitterUtils;

public class WhatIamUptoFragment extends ListFragment {
	MainFragmentActivity context;
	public static ArrayList<String> feedList;
	public static ArrayList<String> tweetList;
	public static ArrayList<String> feed_tweet = new ArrayList<String>();

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.context = (MainFragmentActivity) this.getActivity();
		Log.v("celeb", "In onAttach of what I am AUpto Fragment");

	}

	public void onResume() {
		Log.v("celeb", "In onAttach of what I am AUpto Fragment");

		super.onResume();

		if (feedList != null && tweetList != null) {

			setListAdapter(new FeedTweetAdapter(context, feed_tweet));

		} else {

			String fb_is_on = MainFragmentActivity.appStatus.get("FB_ON");
			String tw_is_on = MainFragmentActivity.appStatus.get("TW_ON");

			if (!(fb_is_on.equals(""))) {
				if (feedList == null) {

					context.showDialog(0);
					new FeedTask(context).execute();
				} else {
					setListAdapter(new FeedTweetAdapter(context, feed_tweet));
				}

			} else {
				Toast.makeText(context, "Facebook is OFF,make it ON",
						Toast.LENGTH_SHORT).show();
			}
			if (!(tw_is_on.equals(""))) {
				if (tweetList == null) {
					context.showDialog(0);
					new TweetTask(context).execute();
				} else {
					setListAdapter(new FeedTweetAdapter(context, feed_tweet));
				}
			} else {
				Toast.makeText(context, "Twitter is OFF,make it ON",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = null;

		v = inflater.inflate(R.layout.whatamupto_fragment, container, false);

		return v;
	}

	public void onFeedResultOutput(ArrayList<String> feedResult) {
		feedList = feedResult;

		if (feedList != null) {
			feed_tweet.addAll(feedList);
		} else {
			Toast.makeText(context, "Problem fetching feeds from Facebook",
					Toast.LENGTH_SHORT).show();
		}
		setListAdapter(new FeedTweetAdapter(context, feed_tweet));

		context.removeDialog(0);
	}

	public void onTweetResultOutput(ArrayList<String> tweetResult) {
		tweetList = tweetResult;

		if (tweetList != null) {
			feed_tweet.addAll(tweetList);
		} else {
			Toast.makeText(context, "Problem fetching Tweets from Twitter",
					Toast.LENGTH_SHORT).show();
		}
		setListAdapter(new FeedTweetAdapter(context, feed_tweet));

		context.removeDialog(0);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		String message = feed_tweet.get(position);
		Log.i("onListItemClick", "onListItemClick");

		if (!message.contains("FB")) {
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(context);
			sendTweetFrom(prefs, message);
		} else {
			// send coments
			updateStatus(message);
		}
	}

	public void sendTweetFrom(final SharedPreferences prefs,
			final String message) {
		Thread t = new Thread() {
			public void run() {

				try {
					TwitterUtils.sendTweet(prefs, message);
					mTwitterHandler.post(mUpdateTwitterNotification);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		};
		t.start();
	}

	private final Handler mTwitterHandler = new Handler();

	final Runnable mUpdateTwitterNotification = new Runnable() {
		public void run() {
			Toast.makeText(context, "Tweet sent !", Toast.LENGTH_LONG).show();
		}
	};

	public void updateStatus(String message) {
		Facebook facebook = new Facebook("205134869806");
		try {
			Bundle bundle = new Bundle();
			bundle.putString("message", "Update from my Android Application: "+message);
			bundle.putString(Facebook.TOKEN, Constant.access_token);
			String response = facebook.request("me/feed", bundle, "POST");
			Log.d("UPDATE RESPONSE", "" + response);
		} catch (MalformedURLException e) {
			Log.e("MALFORMED URL", "" + e.getMessage());
		} catch (IOException e) {
			Log.e("IOEX", "" + e.getMessage());
		}
	}

}