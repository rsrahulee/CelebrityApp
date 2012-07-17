package celebrity.com;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutFragment extends Fragment {

	AboutFragment mBusinessListFragment;
	MainScreen context;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.context = (MainScreen) this.getActivity();
		Log.v("celeb", "In onAttach of AboutFragment");
	}

	public void onResume() {
		Log.v("celeb", "In onResume of AboutFragment");

		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v;

		v = inflater.inflate(R.layout.about_fragment, container, false);

		return v;
	}
}