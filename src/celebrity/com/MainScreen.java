package celebrity.com;

import java.util.HashMap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.TextView;
import celebrity.com.setting.SettingFragment;

public class MainScreen extends FragmentActivity {

	Bundle instanceState;
	public static AppStatus appStatus;
	public TabHost mTabHost;
	public TabManager mTabManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		instanceState = savedInstanceState;

		setupTabsScreen();
	}

	private void setupTabsScreen() {
		setContentView(R.layout.mainscreen);
		setupTabs(instanceState);
	}

	private void setupTabs(Bundle savedInstanceState) {

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		mTabManager = new TabManager(this, mTabHost, R.id.linearLayout2);

		Resources res = getResources();

		mTabManager.addTab(
				mTabHost.newTabSpec("about_tab").setIndicator("About",
						res.getDrawable(R.drawable.tabbarbusiness)),
				AboutFragment.class, null);
		mTabManager.addTab(
				mTabHost.newTabSpec("image_tab").setIndicator("Images",
						res.getDrawable(R.drawable.tabbarcauses)),
				ImagesFragment.class, null);
		mTabManager.addTab(
				mTabHost.newTabSpec("upto_tab").setIndicator("What I am Upto",
						res.getDrawable(R.drawable.tabbarsettings)),
				WhatIamUptoFragment.class, null);
		mTabManager.addTab(
				mTabHost.newTabSpec("setting_tab").setIndicator("Settings",
						res.getDrawable(R.drawable.tabbarsettings)),
				SettingFragment.class, null);

		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(new Integer(savedInstanceState
					.getInt("tab")).toString());
		}
	}

	public static class TabManager implements TabHost.OnTabChangeListener {
		private final FragmentActivity mActivity;
		private final TabHost mTabHost;
		private final int mContainerId;
		private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
		TabInfo mLastTab;

		static final class TabInfo {
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;
			private Fragment fragment;

			TabInfo(String _tag, Class<?> _class, Bundle _args) {
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		static class DummyTabFactory implements TabHost.TabContentFactory {
			private final Context mContext;

			public DummyTabFactory(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		public TabManager(FragmentActivity activity, TabHost tabHost,
				int containerId) {
			mActivity = activity;
			mTabHost = tabHost;
			mContainerId = containerId;
			mTabHost.setOnTabChangedListener(this);
		}

		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
			tabSpec.setContent(new DummyTabFactory(mActivity));
			String tag = tabSpec.getTag();

			TabInfo info = new TabInfo(tag, clss, args);

			// Check to see if we already have a fragment for this tab, probably
			// from a previously saved state. If so, deactivate it, because our
			// initial state is that a tab isn't shown.
			info.fragment = mActivity.getSupportFragmentManager()
					.findFragmentByTag(tag);
			if (info.fragment != null && !info.fragment.isDetached()) {
				FragmentTransaction ft = mActivity.getSupportFragmentManager()
						.beginTransaction();
				ft.detach(info.fragment);
				ft.commit();
			}

			mTabs.put(tag, info);
			mTabHost.addTab(tabSpec);
			// Set tab background
			mTabHost.getTabWidget().getChildAt((mTabs.size() - 1));
			// Set tab text color
			TextView tv = (TextView) mTabHost.getTabWidget()
					.getChildAt((mTabs.size() - 1))
					.findViewById(android.R.id.title);
			tv.setTextColor(Color.WHITE);
			setClickHandler(tag);
		}

		private void setClickHandler(String tag) {
			final String finalTag = tag;
			final Integer finalTabIndex = mTabs.size() - 1;

			mTabHost.getTabWidget().getChildAt((mTabs.size() - 1))
					.setOnClickListener((new OnClickListener() {
						@Override
						public void onClick(View v) {
							Log.i("MainScreen", "inside Tab onClick");
							if (mTabHost.getCurrentTabTag().equals(finalTag)) {
								Integer altTabIndex;

								altTabIndex = (finalTabIndex == 2) ? 1 : 2;
								Log.v("CHECKINFORGOOD", "ALT INDEX: "
										+ altTabIndex.toString() + " INDEX "
										+ finalTabIndex.toString());
								// Tab refresh hack. Meh. We could touch
								// TabHost.mCurrentTab through reflection or
								// even
								// extend TabHost, since TabHost.mCurrentTab is
								// protected, but both feel even hackier than
								// this.
								mTabHost.setCurrentTab(altTabIndex);
								mTabHost.setCurrentTab(finalTabIndex);
							} else {
								mTabHost.setCurrentTab(finalTabIndex);
							}
						}
					}));
		}

		@Override
		public void onTabChanged(String tabId) {
			Log.v("CHECKINFORGOD", "All up in my tabchange");
			TabInfo newTab = mTabs.get(tabId);
			// if (mLastTab != newTab)
			{
				FragmentTransaction ft = mActivity.getSupportFragmentManager()
						.beginTransaction();
				if (mLastTab != null) {
					if (mLastTab.fragment != null) {
						Log.e("CHECKINFORGOOD", "Detaching tab for fragment.  "
								+ mLastTab.fragment.getClass().getName());
						ft.detach(mLastTab.fragment);
					}
				}
				if (newTab != null) {
					if (newTab.fragment == null) {
						newTab.fragment = Fragment.instantiate(mActivity,
								newTab.clss.getName(), newTab.args);
						ft.add(mContainerId, newTab.fragment, newTab.tag);
						Log.e("CHECKINFORGOOD", "Adding tab for fragment.  "
								+ newTab.fragment.getClass().getName());
					} else {
						ft.detach(newTab.fragment);
						newTab.fragment = Fragment.instantiate(mActivity,
								newTab.clss.getName(), newTab.args);
						ft.add(mContainerId, newTab.fragment, newTab.tag);
						Log.e("CHECKINFORGOOD", "Attaching tab for fragment.  "
								+ newTab.fragment.getClass().getName());
					}
				}
				mLastTab = newTab;
				ft.commit();
				// clear backstatck on root fragment clicked
				for (int i = 0; i < mActivity.getSupportFragmentManager()
						.getBackStackEntryCount(); ++i) {
					mActivity.getSupportFragmentManager().popBackStack();
				}
				mActivity.getSupportFragmentManager()
						.executePendingTransactions();
			}
		}

	}

//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			int iFragCnt = this.getSupportFragmentManager()
//					.getBackStackEntryCount();
//
//			if (iFragCnt > 0) {
//				FragmentManager.BackStackEntry frg_last = this
//						.getSupportFragmentManager().getBackStackEntryAt(
//								iFragCnt - 1);
//				Fragment lastFragment = this.getSupportFragmentManager()
//						.findFragmentByTag(frg_last.getName());
//				this.getSupportFragmentManager().popBackStackImmediate();
//				iFragCnt = this.getSupportFragmentManager()
//						.getBackStackEntryCount();
//
//				if (iFragCnt == 0) {
//					this.mTabManager.onTabChanged(this.mTabHost
//							.getCurrentTabTag());
//				} else {
//					FragmentManager.BackStackEntry frg = this
//							.getSupportFragmentManager().getBackStackEntryAt(
//									iFragCnt - 1);
//					Fragment currFrag = this.getSupportFragmentManager()
//							.findFragmentByTag(frg.getName());
//					FragmentTransaction ft = getSupportFragmentManager()
//							.beginTransaction();
//					this.attachFragment(currFrag);
//					ft.detach(lastFragment);
//					ft.commit();
//				}
//			} else {
////				ShowMessageBox();
//				Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
//				startActivity(intent);
//				finish();
//			}
//
//			return true;
//		}
//
//		return super.onKeyDown(keyCode, event);
//	}

//	public void ShowMessageBox() {
//		AlertDialog exitAlert = new AlertDialog.Builder(this).create();
//		exitAlert.setTitle("Exit Application");
//		exitAlert.setMessage("Are you sure you want to leave Celebrity App?");
//
//		exitAlert.setButton("Yes", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//				finish();
//			}
//		});
//		exitAlert.setButton2("No", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//			}
//		});
//		exitAlert.show();
//	}
	
//	public void attachFragment(Fragment fragment) {
//		FragmentTransaction ft = this.getSupportFragmentManager()
//				.beginTransaction();
//
//		if (mTabManager.mLastTab.fragment != null) {
//			Fragment oldFragment = mTabManager.mLastTab.fragment;
//			ft.detach(oldFragment);
//		}
//
//		ft.attach(fragment);
//		mTabManager.mLastTab.fragment = fragment;
//		ft.commit();
//	}
}
