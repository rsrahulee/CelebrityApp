package celebrity.com;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FbWebPage extends Activity {

	private WebView webView;
	private String url;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fb_webpage);

		String fb_or_tw = getIntent().getExtras().getString("like FB_TW");
		if (fb_or_tw.equals("like TW")) {
			url = "https://mobile.twitter.com/JohnnyDeppNews";
		} else {			
			url = "http://www.facebook.com/JohnnyDeppNewsPage";
		}

		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);

		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView View, int progress) {
				FbWebPage.this.setProgress(progress * 100);
			}
		});
		
		webView.getSettings().setDomStorageEnabled(true);
		webView.loadUrl(url);

		webView.setWebViewClient(new AuthClient());

		webView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				WebView.HitTestResult hr = ((WebView) v).getHitTestResult();
				Log.i("******", "getExtra = " + hr.getExtra() + "\t\t Type=" + hr.getType());

				return false;
			}
		});
		webView.setInitialScale(1);
		Log.i("-----------", "-----------");
	}

	private class AuthClient extends WebViewClient {
		@Override
		public void onPageFinished(WebView v, String url) {
			v.clearHistory();
			Log.d("clear web view", "************");
		}
	}

	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	//
	// if (keyCode == KeyEvent.KEYCODE_BACK) {
	//
	// Log.i("Backup Button", "Pressed");
	//
	// // Intent i = new Intent(FbWebPage.this, LoginInActivity.class);
	// // i.putExtra("username", name);
	// // startActivity(i);
	// // finish();
	// }
	// // super.onKeyDown(keyCode, event)
	// return true;
	// }
}