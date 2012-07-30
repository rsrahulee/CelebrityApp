package celebrity.com;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

public class ImagesFragment extends Fragment implements MediaScannerConnectionClient {
	private MainFragmentActivity context;
	private GridView gridImages;
	public static ArrayList<String> imageList;

	private String[] allFiles;
	private String SCAN_PATH;
	private static final String FILE_TYPE = "*/*";
	private MediaScannerConnection conn;

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Log.v("onCreateView----------------", "onCreateView");

		View v;

		v = inflater.inflate(R.layout.images_fragment, container, false);

		gridImages = (GridView) v.findViewById(R.id.gridview);

		onImageClick();

		if (imageList != null) {
			gridImages.setAdapter(new ImageAdapter(context, imageList));
		} else {

			String fb_is_on = MainFragmentActivity.appStatus.get("FB_ON");
			if (!(fb_is_on.equals(""))) {
				context.showDialog(0);
				File folder = new File("/sdcard/CelebApp/Images/");
				if (folder.exists()) {
					allFiles = folder.list();
					if (allFiles != null) {
						gridImages.setAdapter(new ImageAdapter(context, imageList));
						context.removeDialog(0);
					}
				} else {
					new CelebImagesTask(context).execute();
				}
			} else {
				
				File folder = new File("/sdcard/CelebApp/Images/");
				if (folder.exists()) {
					allFiles = folder.list();
					if (allFiles != null) {
						gridImages.setAdapter(new ImageAdapter(context, imageList));
					}
				}else{
					Toast.makeText(context, "Sorry! No Images found", Toast.LENGTH_SHORT).show();
				}				
			}
		}
		return v;
	}

	public void onResume() {
		Log.v("onResume----------------", "onResume");
		super.onResume();
		
		if(allFiles!=null){
			for (int i = 0; i < allFiles.length; i++) {
				Log.d("all file path" + i, allFiles[i] + allFiles.length);
			}
			SCAN_PATH = Environment.getExternalStorageDirectory().toString() + "/CelebApp/Images/" + allFiles[0];
		}
	}

	public void onResultOutput(ArrayList<String> result) {

		if (result != null) {
			imageList = result;
			Log.v("onResultOutput----------------", "onResultOutput");

			gridImages.setAdapter(new ImageAdapter(context, imageList));
		} else {
			Toast.makeText(context, "Error loading images", Toast.LENGTH_SHORT).show();
		}

		onImageClick();

		context.removeDialog(0);
	}

	public void onImageClick() {
		gridImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				startScan();
			}
		});
	}

	private void startScan() {
		Log.d("Connected", "success" + conn);
		if (conn != null) {
			conn.disconnect();
		}
		conn = new MediaScannerConnection(context, this);
		conn.connect();
	}

	@Override
	public void onMediaScannerConnected() {
		// TODO Auto-generated method stub
		conn.scanFile(SCAN_PATH, FILE_TYPE);
	}

	@Override
	public void onScanCompleted(String path, Uri uri) {
		try {
			Log.d("onScanCompleted", uri + "success" + conn);
			if (uri != null) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(uri);
				startActivity(intent);
			}
		} finally {
			conn.disconnect();
			conn = null;
		}
	}
}