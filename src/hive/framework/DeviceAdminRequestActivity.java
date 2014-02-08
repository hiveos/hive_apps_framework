package hive.framework;

import hive.framework.receivers.DeviceAdmin;
import android.app.Activity;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DeviceAdminRequestActivity extends Activity {

	private static final int REQUEST_ENABLE = 0;

	DevicePolicyManager mDPM;
	ComponentName mAdminName;

	TextView mSetDeviceAdmin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_admin_request);

		mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		mAdminName = new ComponentName(this, DeviceAdmin.class);

		mSetDeviceAdmin = (TextView) findViewById(R.id.set_as_device_admin);

		mSetDeviceAdmin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				requestDeviceAdmin();
			}
		});

		applyWallpaper();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_ENABLE) {
			if (resultCode == Activity.RESULT_OK) {
				finish();
			}
		}

	}

	@Override
	public void onBackPressed() {
	}

	public void requestDeviceAdmin() {
		if (!mDPM.isAdminActive(mAdminName)) {
			Intent intent = new Intent(
					DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName);
			intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
					R.string.device_admin_desc);
			startActivityForResult(intent, REQUEST_ENABLE);
		}
	}

	public void applyWallpaper() {
		final WallpaperManager wallpaperManager = WallpaperManager
				.getInstance(this);
		final Drawable wallpaperDrawable = wallpaperManager.getFastDrawable();
		getWindow().setBackgroundDrawable(wallpaperDrawable);
	}

}
