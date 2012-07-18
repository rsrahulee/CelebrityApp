package celebrity.com.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import celebrity.com.ImagesFragment;

public class ImageAdapter extends BaseAdapter implements ListAdapter {
	private static Context context;

	 ArrayList<String> imageList;

	// public static ImageAdapter INSTANCE = new ImageAdapter(context);

	public ImageAdapter(Context context, ArrayList<String> arrayList) {
		super();
		this.context = context;
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
			imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);

		} else {
			imageView = (ImageView) convertView;
		}
		imageView.setImageDrawable(downloadImage(imageList.get(position)));
		return imageView;
	}

	private Drawable LoadImageFromURL(String url) {
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src");
			return d;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	private Drawable downloadImage(String fileUrl) {

//		Bitmap bmImg = null;
		URL myFileUrl = null;
		Drawable d = null;

		try {

			myFileUrl = new URL(fileUrl);

			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();

			// bmImg = BitmapFactory.decodeStream(is);
			d = Drawable.createFromStream(is, "src");
			return d;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}

	// public void getImages(ArrayList<String> result) {
	// imageList = result;
	// }

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
}
