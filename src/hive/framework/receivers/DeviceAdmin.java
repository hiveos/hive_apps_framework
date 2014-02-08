package hive.framework.receivers;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class DeviceAdmin extends DeviceAdminReceiver {

	@Override
	public CharSequence onDisableRequested(Context context, Intent intent) {
		return super.onDisableRequested(context, intent);
	}

	@Override
	public void onEnabled(Context context, Intent intent) {
		super.onEnabled(context, intent);
		SharedPreferences configuration = context.getSharedPreferences(
				"Configuration", 0);
		SharedPreferences.Editor editor = configuration.edit();
		editor.putBoolean("adminMode", true);
		editor.commit();
	}

	@Override
	public void onDisabled(Context context, Intent intent) {
		super.onDisabled(context, intent);

		SharedPreferences configuration = context.getSharedPreferences(
				"Configuration", 0);
		SharedPreferences.Editor editor = configuration.edit();
		editor.putBoolean("adminMode", false);
		editor.commit();
	}

}
