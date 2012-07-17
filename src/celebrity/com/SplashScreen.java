package celebrity.com;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import celebrity.com.account.LoginActivity;

public class SplashScreen extends Activity {
//	private ProgressDialog loading;
	Handler mhandler;
	AppStatus appStatus;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		appStatus = AppStatus.getInstance(this);
		mhandler = new Handler();
//		loading = new ProgressDialog(this);
//		loading.setMessage("Loading...");
//		loading.setCancelable(true);

		startApp();
	}

	void startApp() {
//		showLoading(true, "Loading", "In Process please wait...");

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
					Intent intent_login = new Intent(SplashScreen.this,
							LoginActivity.class);
					startActivity(intent_login);
					finish();
				} else {
					Intent intent = new Intent(SplashScreen.this,
							NoConnectivityScreen.class);
					startActivity(intent);
					Log.v("SPLASH_SCREEN", "You are not online!!!!");
					finish();
				}
//				showLoading(false, "", "");
			}
		});
		t.start();
	}

//	void showLoading(final boolean show, final String title, final String msg) {
//		mhandler.post(new Runnable() {
//			@Override
//			public void run() {
//				if (show) {
//					if (loading != null) {
//						loading.setTitle(title);
//						loading.setMessage(msg);
//						loading.show();
//					}
//				} else {
//					loading.cancel();
//					loading.dismiss();
//				}
//
//			}
//		});
//	}

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
