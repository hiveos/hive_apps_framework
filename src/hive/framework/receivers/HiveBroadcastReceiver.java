package hive.framework.receivers;

import hive.framework.DeviceAdminRequestActivity;
import hive.framework.NoNetworkActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class HiveBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getStringExtra("do").equals("REQUEST_DEVICE_ADMIN")) {
			SharedPreferences configuration = context.getSharedPreferences(
					"Configuration", 0);
			boolean adminMode = configuration.getBoolean("adminMode", false);
			if (!adminMode) {
				Intent i = new Intent(context, DeviceAdminRequestActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
			}
		}
		if (intent.getStringExtra("do").equals("ERROR_NO_CONNECTION")) {
			Intent i = new Intent(context, NoNetworkActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}
	}
}
