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
import celebrity.com.model.AlbumModel;
import celebrity.com.parser.ParseResult;
import celebrity.com.webservice.RestClient;

public class CelebAlbumsTask extends AsyncTask<Double, Integer, ArrayList<AlbumModel>> {

	private ArrayList<AlbumModel> celebAlbumsLinks;
	MainFragmentActivity mainContext;

	public CelebAlbumsTask(MainFragmentActivity context) {
		mainContext = context;
	}

	protected ArrayList<AlbumModel> doInBackground(Double... create_params) {

		RestClient restClient = new RestClient();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("access_token", Constant.access_token));
		try {
			String json = restClient.doNewApiCall(Constant.fbAlbumLink, "GET", nameValuePairs);
			celebAlbumsLinks = ParseResult.INSTANCE.parseAlbums(json);
			Log.i("json---------", String.valueOf(json));
		} catch (ClientProtocolException e) {
			Log.i("ClientProtocolException----------", String.valueOf(e));
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("IOException---------------", String.valueOf(e));
		}

		return celebAlbumsLinks;
	}

	protected void onPostExecute(ArrayList<AlbumModel> result) {

		ImagesFragment imageFragment = (ImagesFragment) mainContext.getSupportFragmentManager().findFragmentByTag(
				"image_tab");

		if (imageFragment != null) {
			if (imageFragment.getView() != null) {
				imageFragment.onAlbumsOutput(result);
			}
		}
	}
}
