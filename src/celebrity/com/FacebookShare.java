package celebrity.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import celebrity.com.account.LoginActivity;
import celebrity.com.constants.Constant;
import celebrity.com.facebook.DialogError;
import celebrity.com.facebook.Facebook;
import celebrity.com.facebook.Facebook.DialogListener;
import celebrity.com.facebook.FacebookError;

public class FacebookShare extends Activity {

	Facebook facebook = new Facebook(Constant.fb_id);
	String access_token;

	private static AppStatus appStatus;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		appStatus = AppStatus.getInstance(this);

		access_token = appStatus.getSharedStringValue(appStatus.FACEBOOK_TOKEN);

		if (access_token != null) {
			facebook.setAccessToken(access_token);
		}

		if (!facebook.isSessionValid()) {

			facebook.authorize(this, new String[] { "publish_stream",
					"read_stream", "offline_access", "user_photos", "email" },
					Facebook.FORCE_DIALOG_AUTH, new DialogListener() {
						@Override
						public void onComplete(Bundle values) {
							appStatus.saveSharedStringValue(appStatus.FACEBOOK_TOKEN, facebook.getAccessToken());

							// getFBId();
							Intent intent = new Intent(FacebookShare.this,
									MainFragmentActivity.class);
							intent.putExtra("fromFB", true);
							startActivity(intent);
							finish();
						}

						@Override
						public void onCancel() {
							// TODO Auto-generated method stub
							Log.i("####checkin", "on cancel");
							Intent intent = new Intent(FacebookShare.this,LoginActivity.class);
							startActivity(intent);
							finish();
						}

						@Override
						public void onError(DialogError e) {
							// TODO Auto-generated method stub
							Log.i("####checkin", "on error");
							Intent intent = new Intent(FacebookShare.this,LoginActivity.class);
							startActivity(intent);
							Toast.makeText(FacebookShare.this, "Unable to open the Facebook", Toast.LENGTH_SHORT).show();
							finish();
						}

						@Override
						public void onFacebookError(FacebookError e) {
							// TODO Auto-generated method stub
							Log.i("####checkin", "on FB error");
							Intent intent = new Intent(FacebookShare.this,LoginActivity.class);
							startActivity(intent);
							finish();
						}
					});
		} else {
			// getFBId();
			Intent intent = new Intent(FacebookShare.this,
					MainFragmentActivity.class);
			startActivity(intent);
			finish();
		}
	}

	// public void getFBId() {
	// Bundle bundle = new Bundle();
	// try {
	// String response = facebook.request("me", bundle);
	// Log.i("friends response", response);
	// JSONObject json = Util.parseJson(response);
	// String facebookID = json.getString("id");
	//
	// appStatus.saveSharedStringValue(appStatus.FB_UID, facebookID);
	// } catch (MalformedURLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (FacebookError e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		facebook.authorizeCallback(requestCode, resultCode, data);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onAuthenticationResult(Boolean success) {
		if (success) {
			this.removeDialog(0);
			DisplayToast("Facebook preferences posted!");
		} else {
			DisplayToast("Fail to post facebook preferences!");
		}
	}

	private void DisplayToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		this.removeDialog(0);
	}
}
