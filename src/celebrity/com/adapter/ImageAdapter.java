package celebrity.com.adapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

public class ImageAdapter extends BaseAdapter implements ListAdapter {
	private static Context context;

	private ArrayList<String> imageList;

	public ImageAdapter(Context context, ArrayList<String> arrayList) {
		super();
		ImageAdapter.context = context;
		this.imageList = arrayList;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(context);
			imageView.setLayoutParams(new GridView.LayoutParams(110, 110));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);

		} else {
			imageView = (ImageView) convertView;
		}

		Bitmap bitmapImage = BitmapFactory.decodeFile("/sdcard/" + ""
				+ position + ".PNG");

		if (bitmapImage != null) {
			imageView.setImageBitmap(bitmapImage);
		} else {
			downloadImage(imageList.get(position), position);
			Bitmap bitmapImageSD = BitmapFactory.decodeFile("/sdcard/" + ""
					+ position + ".PNG");
			imageView.setImageBitmap(bitmapImageSD);
		}
		return imageView;
	}

	private void downloadImage(String fileUrl, int position) {

		Bitmap bmImg = null;
		URL myFileUrl = null;

		try {

			myFileUrl = new URL(fileUrl);

			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();

			bmImg = BitmapFactory.decodeStream(is);

			saveImageToSD(bmImg, position);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return imageList.get(arg0);
	}

	public void saveImageToSD(Bitmap bitmap, int position) {

		String extStorageDirectory = Environment.getExternalStorageDirectory()
				.toString();
		
		String root = Environment.getExternalStorageDirectory().toString();
		new File(root + "/mvc/mvc").mkdirs();

		
		OutputStream outStream = null;

		File file = new File(extStorageDirectory, "" + position + ".PNG");
		try {
			outStream = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
			outStream.flush();
			outStream.close();

			Toast.makeText(context, "Saved" + "" + position + ".PNG",
					Toast.LENGTH_LONG).show();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Bitmap getImageSD(String img) {
		Bitmap bitmap = BitmapFactory.decodeFile(img);
		return bitmap;
	}
}
