package celebrity.com.setting;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import celebrity.com.MainFragmentActivity;
import celebrity.com.R;

public class SettingFragment extends ListFragment {
	MainFragmentActivity context;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.context = (MainFragmentActivity) this.getActivity();
		Log.v("celeb", "In onAttach of SettingFragment");
	}

	public void onResume() {
		super.onResume();

		ArrayList<String> lst = new ArrayList<String>();
		lst.add("facebook");
		lst.add("twitter");

		ShareListAdapter adapter = new ShareListAdapter(this.getActivity(), lst, context);
		this.setListAdapter((ListAdapter) adapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v;

		v = inflater.inflate(R.layout.setting_fragment, container, false);

		return v;
	}
}