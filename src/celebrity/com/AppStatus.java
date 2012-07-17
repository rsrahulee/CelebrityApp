package celebrity.com;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class AppStatus {

	public final String FACEBOOK_TOKEN = "facebook_token";
	public final String FB_UID = "fb_uid";
	public final String TWITTER_TOKEN = "twitter_token";
	public final String TWITTER_SECRET = "twitter_secret";
	public final String TW_FIRST_NAME  = "tw_first_name";
	public final String TW_LAST_NAME   = "tw_last_name";
	public final String TW_UID         = "tw_uid";

	private static AppStatus instance = new AppStatus();
	ConnectivityManager connectivityManager;
	static Context context;
	boolean connected = false;

	public static AppStatus getInstance(Context ctx) {
		context = ctx;
		return instance;
	}

	public Boolean isOnline() {

		try {
			connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();
			connected = networkInfo != null && networkInfo.isAvailable()
					&& networkInfo.isConnected();
			return connected;

		} catch (Exception e) {
			Log.v("AppStatus", e.toString());
		}
		return connected;
	}

	public String getSharedStringValue(String key) {
		SharedPreferences sp = context.getSharedPreferences("FILE_NAME", 0);
		String value = sp.getString(key, null);
		return value;
	}

	public boolean saveSharedStringValue(String key, String value) {
		SharedPreferences sp = context.getSharedPreferences("FILE_NAME", 0);
		Editor edit = sp.edit();
		edit.putString(key, value);
		return edit.commit();
	}
}
