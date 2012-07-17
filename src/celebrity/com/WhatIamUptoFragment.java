package celebrity.com;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class WhatIamUptoFragment extends Fragment {

	WhatIamUptoFragment mBusinessListFragment;
	MainScreen context;
	private Button allButton;
	private Button supportButton;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.context = (MainScreen) this.getActivity();
		Log.v("celeb", "In onAttach of what I am AUpto Fragment");
	}

	public void onResume() {
		Log.v("celeb", "In onAttach of what I am AUpto Fragment");

		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v;

		v = inflater.inflate(R.layout.whatamupto_fragment, container, false);

		return v;
	}
}