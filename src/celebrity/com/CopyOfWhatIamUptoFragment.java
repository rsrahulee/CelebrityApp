package celebrity.com;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import celebrity.com.adapter.FeedTweetAdapter;
import celebrity.com.dialog.TweetFeedDialog;
import celebrity.com.task.FeedTask;
import celebrity.com.task.TweetTask;

public class CopyOfWhatIamUptoFragment extends ListFragment {
	MainFragmentActivity context;
	public static ArrayList<String> feedList;
	public static ArrayList<String> tweetList;
	public static ArrayList<String> feed_tweet = new ArrayList<String>();

	private String fb_is_on;
	private String tw_is_on;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.context = (MainFragmentActivity) this.getActivity();
		Log.v("celeb", "In onAttach of what I am AUpto Fragment");
	}

	public void onResume() {
		Log.v("celeb", "In onResume of what I am AUpto Fragment");

		super.onResume();

		if (feedList != null && tweetList != null) {

			setListAdapter(new FeedTweetAdapter(context, feed_tweet));

		} else {

			// fb_is_on = MainFragmentActivity.appStatus.get("FB_ON");
			// tw_is_on = MainFragmentActivity.appStatus.get("TW_ON");

			fetchFbData();

			// if (!fb_is_on.equals("")) {
			// fetchFbData();
			// } else {
			// if (feedList != null) {
			// setListAdapter(new FeedTweetAdapter(context, feed_tweet));
			// } else {
			// Toast.makeText(context,
			// "Facebook is OFF,Please ON it to get feeds",
			// Toast.LENGTH_SHORT).show();
			// }
			// }
			//
			// if (!tw_is_on.equals("")) {
			// fetchTwData();
			// } else {
			// if (tweetList != null) {
			// setListAdapter(new FeedTweetAdapter(context, feed_tweet));
			// } else {
			// Toast.makeText(context,
			// "Twitter is OFF,Please ON it to get tweets",
			// Toast.LENGTH_SHORT).show();
			// }
			// }
		}
	}

	public void fetchFbData() {
		// if (!(fb_is_on.equals(""))) {

		if (feedList == null) {
			context.showDialog(0);
			new FeedTask(context).execute();
		} else {
			setListAdapter(new FeedTweetAdapter(context, feed_tweet));
		}

		// }
		// else {
		// if (feedList != null) {
		// setListAdapter(new FeedTweetAdapter(context, feed_tweet));
		// } else {
		// Toast.makeText(context, "Facebook is OFF,Please ON it to get feeds",
		// Toast.LENGTH_SHORT).show();
		// }
		// }
	}

	public void fetchTwData() {
		// if (!(tw_is_on.equals(""))) {
		if (tweetList == null) {
			context.showDialog(0);
			new TweetTask(context).execute();
		} else {
			setListAdapter(new FeedTweetAdapter(context, feed_tweet));
		}
		// }
		// else {
		// if (tweetList != null) {
		// setListAdapter(new FeedTweetAdapter(context, feed_tweet));
		// } else {
		// Toast.makeText(context, "Twitter is OFF,Please ON it to get tweets",
		// Toast.LENGTH_SHORT).show();
		// }
		// }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = null;

		v = inflater.inflate(R.layout.whatamupto_fragment, container, false);

		return v;
	}

	public void onFeedResultOutput(ArrayList<String> feedResult) {
		feedList = feedResult;

		if (feedList != null) {
			feed_tweet.addAll(feedList);
		} else {
			Toast.makeText(context, "Problem fetching feeds from Facebook", Toast.LENGTH_SHORT).show();
		}
		setListAdapter(new FeedTweetAdapter(context, feed_tweet));

		// fetchTwData();

		context.removeDialog(0);

		fetchTwData();
	}

	public void onTweetResultOutput(ArrayList<String> tweetResult) {
		tweetList = tweetResult;

		if (tweetList != null) {
			feed_tweet.addAll(tweetList);
		} else {
			Toast.makeText(context, "Problem fetching Tweets from Twitter", Toast.LENGTH_SHORT).show();
		}
		setListAdapter(new FeedTweetAdapter(context, feed_tweet));

		// fetchFbData();

		context.removeDialog(0);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		String message = feed_tweet.get(position);
		Log.i("onListItemClick", "onListItemClick");

		if (!message.contains("FB")) {

			tw_is_on = MainFragmentActivity.appStatus.get("TW_ON");
			if (!tw_is_on.equals("")) {
				TweetFeedDialog dialog = new TweetFeedDialog(context);
				dialog.showTweetFeedDialog("Tweet", "send tweet");
			} else {
				Toast.makeText(context, "Twitter is OFF,Please ON it to send tweets", Toast.LENGTH_SHORT).show();
			}

		} else {

			fb_is_on = MainFragmentActivity.appStatus.get("FB_ON");
			if (!fb_is_on.equals("")) {
				TweetFeedDialog dialog = new TweetFeedDialog(context);
				dialog.showTweetFeedDialog("Comment", "send comments");
			} else {
				Toast.makeText(context, "Facebook is OFF,Please ON it to send feeds", Toast.LENGTH_SHORT).show();
			}
		}
	}
}