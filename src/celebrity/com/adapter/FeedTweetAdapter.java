package celebrity.com.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import celebrity.com.R;

public class FeedTweetAdapter extends BaseAdapter {
	Context context;
	private final ArrayList<String> feed_tweets;

	public FeedTweetAdapter(Context context, ArrayList<String> feed_tweets) {
		// super(context);
		this.context = context;
		this.feed_tweets = feed_tweets;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.customlistitem, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);

		if (feed_tweets.get(position).contains("FB")) {
			String feedstweets = feed_tweets.get(position).substring(2);
			textView.setText(feedstweets);
			imageView.setImageResource(R.drawable.facebook_logo);
		} else {
			textView.setText(feed_tweets.get(position));
			imageView.setImageResource(R.drawable.twitter_logo);
		}
		return rowView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return feed_tweets.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
}
