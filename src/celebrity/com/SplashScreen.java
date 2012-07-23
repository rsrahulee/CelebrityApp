package celebrity.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import celebrity.com.account.LoginActivity;

public class SplashScreen extends Activity {
	Handler mhandler;
	AppStatus appStatus;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		appStatus = AppStatus.getInstance(this);
		mhandler = new Handler();

		startApp();
	}

	void startApp() {

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				if (appStatus.isOnline()) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Log.v("SPLASH_SCREEN", "welcome");

					String logged_in = appStatus.get("logged_in");
					if (logged_in.equals("") || logged_in.equals(null)) {
						Intent intent_login = new Intent(SplashScreen.this,
								LoginActivity.class);
						startActivity(intent_login);
						finish();
					} else {
						Intent intent = new Intent(SplashScreen.this,
								MainFragmentActivity.class);
						startActivity(intent);
						finish();
					}

				} else {
					Intent intent = new Intent(SplashScreen.this,
							NoConnectivityScreen.class);
					startActivity(intent);
					Log.v("SPLASH_SCREEN", "You are not online!!!!");
//					finish();
				}
			}
		});
		t.start();
	}

	void message(String msg) {
		final String mesage = msg;
		mhandler.post(new Runnable() {
			@Override
			public void run() {
				Toast toast = Toast.makeText(SplashScreen.this, mesage, 8000);
				toast.show();
			}
		});
	}
}
