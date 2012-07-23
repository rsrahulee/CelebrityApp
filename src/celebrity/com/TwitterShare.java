package celebrity.com;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import celebrity.com.twitter.PrepareRequestTokenActivity;
import celebrity.com.twitter.TwitterUtils;

public class TwitterShare extends Activity {
	private SharedPreferences prefs;
	String msgToTweet;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);

		if (TwitterUtils.isAuthenticated(prefs)) {
			Log.i("---------------", "TwitterUtils.isAuthenticated(prefs)");
			// sendTweet();
			Intent intent = new Intent(getApplicationContext(),
					MainFragmentActivity.class);
			startActivity(intent);
		} else {
			Intent i = new Intent(getApplicationContext(),
					PrepareRequestTokenActivity.class);
			startActivity(i);
			finish();
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}