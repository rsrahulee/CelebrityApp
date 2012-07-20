package celebrity.com;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import celebrity.com.account.LoginActivity;
import celebrity.com.twitter.PrepareRequestTokenActivity;
import celebrity.com.twitter.TwitterUtils;

public class TwitterShare extends Activity {
	private SharedPreferences prefs;

	// private final Handler mTwitterHandler = new Handler();
	 String msgToTweet;

	// private boolean bIsFromSocialLogin;
	private static AppStatus appStatus;
	private String token;
	private String secret;

	// final Runnable mUpdateTwitterNotification = new Runnable() {
	// public void run() {
	// isFromTwitterShare = true;
	// finish();
	// }
	// };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
//		appStatus = AppStatus.getInstance(this);
//
//		token = appStatus.getSharedStringValue(appStatus.TWITTER_TOKEN);
//		if (token == null)
//			token = "";
//
//		secret = appStatus.getSharedStringValue(appStatus.TWITTER_SECRET);

		if (TwitterUtils.isAuthenticated(prefs)) {
			Log.i("---------------", "TwitterUtils.isAuthenticated(prefs)");
			// sendTweet();
			Intent intent = new Intent(getApplicationContext(),MainFragmentActivity.class);
			startActivity(intent);
		} else {
			Intent i = new Intent(getApplicationContext(),
					PrepareRequestTokenActivity.class);
			startActivity(i);
			finish();
		}
	}

	@Override
//	protected void onResume() {
//		super.onResume();
//		Intent i = new Intent(getApplicationContext(), MainScreen.class);
//		startActivity(i);
//	}

//	private String getTweetMsg() {
//		return msgToTweet;
//	}

	// public void sendTweet() {
	// Thread t = new Thread() {
	// public void run() {
	//
	// try {
	// // TwitterUtils.sendTweet(getTweetMsg());
	// mTwitterHandler.post(mUpdateTwitterNotification);
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// }
	//
	// };
	// t.start();
	// }

//	private void clearCredentials() {
//		// edit.remove("twitter_token");
//		// edit.remove("twitter_secret");
//	}

//	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}