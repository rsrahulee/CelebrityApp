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
import celebrity.com.adapter.ImageAdapter;
import celebrity.com.parser.ParseResult;
import celebrity.com.parser.RestClient;

public class CelebImagesTask extends AsyncTask<Double, Integer, ArrayList<String>> {
	
	ArrayList<String> celebImgLinks;
	ImagesFragment imageFragmentContext;

	public CelebImagesTask(MainFragmentActivity context) {
		
	}

	protected ArrayList<String> doInBackground(Double... create_params) {

		RestClient restClient = new RestClient();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs
				.add(new BasicNameValuePair(
						"access_token",
						"AAAEy3j8cizoBAOZAWyo1YgcgS6OcYTZAEBJVaBciDbbYRocn7DPmd2eMNsPVMA2HFUI2XfZBmrFvNnQ6ssV7XZCf6m36kOxBWZAZAKTZAmGlwZDZD"));

		nameValuePairs.add(new BasicNameValuePair("limit", "" + 5));
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
		
		return celebImgLinks;
	}

	protected void onPostExecute(ArrayList<String> result) {
//		ImageAdapter.INSTANCE.getImages(result);
		ImagesFragment.imageList = result;
	}
}
