package celebrity.com.dialog;

import java.io.IOException;
import java.net.MalformedURLException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import celebrity.com.FbWebPage;
import celebrity.com.MainFragmentActivity;
import celebrity.com.R;
import celebrity.com.constants.Constant;
import celebrity.com.facebook.Facebook;
import celebrity.com.twitter.TwitterUtils;

public class TweetFeedDialog {
	MainFragmentActivity main_context;
	private final Handler mTwitterFacebookHandler = new Handler();

	public TweetFeedDialog(Context context) {
		main_context = (MainFragmentActivity) context;
	}

	public void showTweetFeedDialog(final String title, String message) {
		AlertDialog.Builder alert = new AlertDialog.Builder(main_context);

		alert.setTitle(title);
		alert.setCancelable(true);
		alert.setMessage(message);
		if (title.equals("Tweet")) {
			alert.setIcon(R.drawable.twitter_logo);
		} else {
			alert.setIcon(R.drawable.facebook_logo);
		}

		// Set an EditText view to get user input
		final EditText input = new EditText(main_context);
		input.setText("@johnnyDeppNews: ");
		input.setLines(1);
		input.setHeight(130);

		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				if (title.equals("Tweet")) {

					int maxLength = 140;
					InputFilter[] FilterArray = new InputFilter[1];
					FilterArray[0] = new InputFilter.LengthFilter(maxLength);
					input.setFilters(FilterArray);

					String value = input.getText().toString();
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(main_context);
					sendTweets(prefs, value);
				} else {
					String value = input.getText().toString();
					updateStatus(value);
				}
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
			}
		});

		if (title.equals("Tweet")) {
			alert.setNeutralButton("Follow", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {

					dialog.dismiss();

					Intent i = new Intent(main_context, FbWebPage.class);
					i.putExtra("like FB_TW", "like TW");
					main_context.startActivity(i);

				}
			});
		} else {

			alert.setNeutralButton("Like", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {

					dialog.dismiss();

					Intent i = new Intent(main_context, FbWebPage.class);
					i.putExtra("like FB_TW", "like FB");
					main_context.startActivity(i);

				}
			});
		}

		alert.show();
	}

	public void sendTweets(final SharedPreferences prefs, final String message) {
		Thread t = new Thread() {
			public void run() {

				try {
					TwitterUtils.sendTweet(prefs, message);
					mTwitterFacebookHandler.post(mUpdateTwitterNotification);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		};
		t.start();
	}

	final Runnable mUpdateTwitterNotification = new Runnable() {
		public void run() {
			Toast.makeText(main_context, "Tweet sent !", Toast.LENGTH_SHORT).show();
		}
	};

	public void updateStatus(String message) {
		Facebook facebook = new Facebook("337405099674426");
		try {
			Bundle bundle = new Bundle();
			bundle.putString("message", message);
			bundle.putString(Facebook.TOKEN, Constant.access_token);
			String response = facebook.request("me/feed", bundle, "POST");
			// String response = facebook.request("205134869806"+ "/feed",
			// bundle, "POST");
			Log.d("UPDATE RESPONSE", "" + response);
			mTwitterFacebookHandler.post(mUpdateFacebookNotification);

		} catch (MalformedURLException e) {
			Log.e("MALFORMED URL", "" + e.getMessage());
		} catch (IOException e) {
			Log.e("IOEX", "" + e.getMessage());
		}
	}

	final Runnable mUpdateFacebookNotification = new Runnable() {
		public void run() {
			Toast.makeText(main_context, "comment sent !", Toast.LENGTH_SHORT).show();
		}
	};
}
