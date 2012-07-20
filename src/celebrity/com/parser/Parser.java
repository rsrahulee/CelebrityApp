package celebrity.com.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import celebrity.com.R;

public class Parser {

	RestClient restClient = new RestClient();

	public Parser() {

//		ImageView imageView;
//		imageView.setImageDrawable(drawable);
//
//		for (int i = 0; i < 5; i++) {
//
//			LoadImage("");
//		}

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs
				.add(new BasicNameValuePair(
						"access_token",
						"AAAEy3j8cizoBAOZAWyo1YgcgS6OcYTZAEBJVaBciDbbYRocn7DPmd2eMNsPVMA2HFUI2XfZBmrFvNnQ6ssV7XZCf6m36kOxBWZAZAKTZAmGlwZDZD"));

		nameValuePairs.add(new BasicNameValuePair("limit", "" + 20));
		try {
			String json = restClient.doNewApiCall(
					"https://graph.facebook.com/10150307510509807/photos",
					"GET", nameValuePairs);
			ParseResult.INSTANCE.parseData(json);
			Log.i("json::::::::::", String.valueOf(json));
		} catch (ClientProtocolException e) {
			Log.i("ClientProtocolException::::::::::", String.valueOf(e));
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("IOException::::::::::", String.valueOf(e));
		}
	}

//	public Drawable LoadImage(String url) {
//		Drawable d;
//		try {
//			InputStream is = (InputStream) new URL(url).getContent();
//			d = Drawable.createFromStream(is, "src name");
//			return d;
//		} catch (NullPointerException e) {
//			d = getResources().getDrawable(R.drawable.icon);
//			return d;
//		} catch (Exception e) {
//			d = getResources().getDrawable(R.drawable.icon);
//			return d;
//		}
//	}

//	private Bitmap downloadFile(String fileUrl) {
//
//		Bitmap bmImg = null;
//		URL myFileUrl = null;
//
//		try {
//
//			myFileUrl = new URL(fileUrl);
//
//			HttpURLConnection conn = (HttpURLConnection) myFileUrl
//					.openConnection();
//			conn.setDoInput(true);
//			conn.connect();
//			InputStream is = conn.getInputStream();
//
//			bmImg = BitmapFactory.decodeStream(is);
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return bmImg;
//	}
}
