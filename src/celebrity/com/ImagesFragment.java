package celebrity.com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import celebrity.com.adapter.ImageAdapter;
import celebrity.com.parser.ParseResult;
import celebrity.com.parser.RestClient;

public class ImagesFragment extends Fragment {

	ImagesFragment imagesFragment;
	MainScreen context;
	GridView girGridView;
	public static ArrayList<String> imageList;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.context = (MainScreen) this.getActivity();
		Log.v("celeb", "In onAttach of ImagesFragment");
//		ArrayList<String> imgArr = getImagesUrls();
	}

	public void onResume() {
		Log.v("celeb", "In onResume of ImagesFragment");
		super.onResume();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v;

//		new CelebImagesTask(context).execute();
		
		v = inflater.inflate(R.layout.images_fragment, container, false);

		girGridView = (GridView) v.findViewById(R.id.gridview);
		girGridView.setAdapter(new ImageAdapter(context,getImagesUrls()));

		girGridView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long arg3) {
						Toast.makeText(context, imageList.get(position),
								Toast.LENGTH_SHORT).show();
					}
				});

		return v;
	}

//	public void getImages(ArrayList<String> result) {
//		imageList = result;
//	}
	
	public ArrayList<String> getImagesUrls(){
		RestClient restClient = new RestClient();
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
			imageList = ParseResult.INSTANCE.parseData(json);
			Log.i("json::::::::::", String.valueOf(json));
		} catch (ClientProtocolException e) {
			Log.i("ClientProtocolException::::::::::", String.valueOf(e));
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("IOException::::::::::", String.valueOf(e));
		}
		return imageList;
	}	
}