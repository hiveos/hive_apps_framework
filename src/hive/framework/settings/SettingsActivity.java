package hive.framework.settings;

import hive.framework.R;

import java.util.List;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {

	@Override
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.preference_headers, target);
	}

	@Override
	protected boolean isValidFragment(String fragmentName) {
		if (LauncherSettingsFragment.class.getName().equals(fragmentName)) {
			return true;
		}
		return false;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
	}

}
