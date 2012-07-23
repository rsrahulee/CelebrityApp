package celebrity.com.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;
import celebrity.com.ImagesFragment;
import celebrity.com.MainFragmentActivity;
import celebrity.com.constants.Constant;
import celebrity.com.parser.ParseResult;
import celebrity.com.webservice.RestClient;

public class CelebImagesTask extends
		AsyncTask<Double, Integer, ArrayList<String>> {

	ArrayList<String> celebImgLinks;
	MainFragmentActivity mainContext;

	public CelebImagesTask(MainFragmentActivity context) {
		mainContext = context;
	}

	protected ArrayList<String> doInBackground(Double... create_params) {

		RestClient restClient = new RestClient();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("access_token",
				Constant.access_token));

		nameValuePairs.add(new BasicNameValuePair("limit", "" + 10));
		try {
			String json = restClient.doNewApiCall(
					"https://graph.facebook.com/10150307510509807/photos",
					"GET", nameValuePairs);
			celebImgLinks = ParseResult.INSTANCE.parseData(json);
			Log.i("json---------", String.valueOf(json));
		} catch (ClientProtocolException e) {
			Log.i("ClientProtocolException----------", String.valueOf(e));
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("IOException---------------", String.valueOf(e));
		}

		return celebImgLinks;
	}

	protected void onPostExecute(ArrayList<String> result) {

		ImagesFragment imageFragment = (ImagesFragment) mainContext
				.getSupportFragmentManager().findFragmentByTag("image_tab");

		if (imageFragment != null) {
			if (imageFragment.getView() != null) {
				imageFragment.onResultOutput(result);
			}
		}
	}
}
