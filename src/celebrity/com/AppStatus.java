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
	public final String TW_FIRST_NAME = "tw_first_name";
	public final String TW_LAST_NAME = "tw_last_name";
	public final String TW_UID = "tw_uid";
	public final String TWITTER_ON = "twitter_on";
	public final String FACEBOOK_ON = "facebook_on";

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

	public Boolean getSharedBoolValue(String key) {
		SharedPreferences sp = context.getSharedPreferences("FILE_NAME", 0);
		Boolean value = sp.getBoolean(key, false);
		return value;
	}

	public boolean saveSharedBoolValue(String key, Boolean value) {
		SharedPreferences sp = context.getSharedPreferences("FILE_NAME", 0);
		Editor edit = sp.edit();
		edit.putBoolean(key, value);
		return edit.commit();
	}

	public void saveFBValue(String key, String value) {
		SharedPreferences sp = context.getSharedPreferences("FILE_NAME", 0);
		Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}

	public String getFBValue(String key) {
		SharedPreferences sp = context.getSharedPreferences("FILE_NAME", 0);
		String value = sp.getString(key, "");
		return value;
	}

	public boolean saveTWValue(String key, Boolean value) {
		SharedPreferences sp = context.getSharedPreferences("FILE_NAME", 0);
		Editor edit = sp.edit();
		edit.putBoolean(key, value);
		return edit.commit();
	}

	public Boolean getTWValue(String key) {
		SharedPreferences sp = context.getSharedPreferences("FILE_NAME", 0);
		Boolean value = sp.getBoolean(key, false);
		return value;
	}

	public Boolean removeTWValue(String key) {
		SharedPreferences sp = context.getSharedPreferences("FILE_NAME", 0);
		Boolean value = sp.getBoolean(key, false);
		return value;
	}

	public Boolean removeFBValue(String key) {
		SharedPreferences sp = context.getSharedPreferences("FILE_NAME", 0);
		Boolean value = sp.getBoolean(key, false);
		return value;
	}

	public boolean clearSharedData() {
		SharedPreferences sp = context.getSharedPreferences("FILE_NAME", 0);
		Editor edit = sp.edit();
		edit.clear();
		return edit.commit();
	}

	public void clearSharedDataWithKey(String key) {
		SharedPreferences sp = context.getSharedPreferences("FILE_NAME", 0);
		Editor edit = sp.edit();
		edit.remove(key);
		edit.commit();
	}

	public void save(String key, String value) {
		SharedPreferences prefs = context.getSharedPreferences("Share",
				Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String get(String key) {
		SharedPreferences prefs = context.getSharedPreferences("Share",
				Context.MODE_PRIVATE);
		String Str = prefs.getString(key, "");
		return Str;
	}

	public void remove(String key) {
		SharedPreferences prefs = context.getSharedPreferences("Share",
				Context.MODE_PRIVATE);
		Editor edit = prefs.edit();
		edit.remove(key);
		edit.commit();
	}
}
