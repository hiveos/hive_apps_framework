package hive.framework.settings;

import hive.framework.R;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class MainSettingsFragment extends PreferenceFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences_launcher);
	}
}
