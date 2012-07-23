package celebrity.com;

import java.util.ArrayList;

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
import celebrity.com.task.CelebImagesTask;

public class ImagesFragment extends Fragment {
	private MainFragmentActivity context;
	private GridView gridImages;
	public static ArrayList<String> imageList;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.context = (MainFragmentActivity) this.getActivity();
		Log.v("onAttach----------------", "onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v("onCreate----------------", "onCreate");

		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.v("onCreateView----------------", "onCreateView");

		View v;

		v = inflater.inflate(R.layout.images_fragment, container, false);

		gridImages = (GridView) v.findViewById(R.id.gridview);

		if (imageList != null) {
			gridImages.setAdapter(new ImageAdapter(context, imageList));
		} else {

			String fb_is_on = MainFragmentActivity.appStatus.get("FB_ON");
			if (!(fb_is_on.equals(""))) {
				context.showDialog(0);
				new CelebImagesTask(context).execute();
			} else {
				Toast.makeText(context, "Facebook is OFF,make it ON",
						Toast.LENGTH_SHORT).show();
			}
		}
		return v;
	}

	public void onResume() {
		Log.v("onResume----------------", "onResume");
		super.onResume();
	}

	public void onResultOutput(ArrayList<String> result) {

		imageList = result;
		Log.v("onResultOutput----------------", "onResultOutput");

		gridImages.setAdapter(new ImageAdapter(context, imageList));

		gridImages
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long arg3) {
						// Toast.makeText(context, imageList.get(position),
						// Toast.LENGTH_SHORT).show();
					}
				});
		context.removeDialog(0);
	}
}