package celebrity.com.account;

import android.accounts.AccountAuthenticatorActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import celebrity.com.AppStatus;
import celebrity.com.FacebookShare;
import celebrity.com.MainScreen;
import celebrity.com.NoConnectivityScreen;
import celebrity.com.R;
import celebrity.com.twitter.PrepareRequestTokenActivity;
import celebrity.com.twitter.TwitterUtils;

public class LoginActivity extends AccountAuthenticatorActivity {

	private Button btnFacebookLogin, btnTwitterLogin;
	public static AppStatus appStatus;
	private SharedPreferences prefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);

		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		appStatus = AppStatus.getInstance(this);

		btnFacebookLogin = (Button) findViewById(R.id.facebookLogin);
		btnTwitterLogin = (Button) findViewById(R.id.twitterLogin);

		btnFacebookLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
//				 Intent intent= new
//				 Intent(LoginActivity.this,MainScreen.class);
//				 startActivity(intent);
				if (appStatus.isOnline()) {
					Log.e("LoginActivity", "Facebook Clicked ");
					Intent intent_ShareFB = new Intent(LoginActivity.this,
							FacebookShare.class);
					startActivity(intent_ShareFB);
				} else {
					Log.v("LoginActivity", "App is not online!");
					Intent intent = new Intent(LoginActivity.this,
							NoConnectivityScreen.class);
					startActivity(intent);
					finish();
				}
			}
		});

		btnTwitterLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (appStatus.isOnline()) {
					// Intent intent_ShareTW = new Intent(LoginActivity.this,
					// TwitterShare.class);
					// startActivity(intent_ShareTW);

					if (TwitterUtils.isAuthenticated(prefs)) {
						Log.i("---------------",
								"TwitterUtils.isAuthenticated(prefs)");
						// sendTweet();
						Intent intent = new Intent(getApplicationContext(),
								MainScreen.class);
						startActivity(intent);
					} else {
						Intent i = new Intent(getApplicationContext(),
								PrepareRequestTokenActivity.class);
						startActivity(i);
						finish();
					}
					Log.e("LoginActivity", "Twitter Clicked ");
				} else {
					Log.v("LoginActivity", "App is not online!");
					Intent intent = new Intent(LoginActivity.this,
							NoConnectivityScreen.class);
					startActivity(intent);
					finish();
				}
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ShowMessageBox();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void ShowMessageBox() {
		AlertDialog exitAlert = new AlertDialog.Builder(this).create();
		exitAlert.setTitle("Exit Application");
		exitAlert.setMessage("Are you sure you want to leave Celebrity App?");

		exitAlert.setButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();
			}
		});
		exitAlert.setButton2("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		exitAlert.show();
	}
}
