package hive.framework.user;

import hive.framework.DeviceAdminRequestActivity;
import hive.framework.R;
import hive.framework.receivers.DeviceAdmin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserDetailsDialog extends Activity {

	Context mContext;

	ArrayList<String> mUserInformation = new ArrayList<String>();

	DevicePolicyManager mDPM;
	ComponentName mAdminName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_user_details);

		mContext = this;

		readInformation();

		setValues();

		mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		mAdminName = new ComponentName(this, DeviceAdmin.class);

		Button mCancelButton = (Button) findViewById(R.id.dialog_cancel);
		Button mLogoutButton = (Button) findViewById(R.id.dialog_logout);

		mCancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();
			}
		});

		mLogoutButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (mDPM.isAdminActive(mAdminName)) {
					File mLoginStatusFile = new File(Environment
							.getExternalStorageDirectory()
							+ "/HIVE/User/logged");

					if (!mLoginStatusFile.exists()) {
						try {
							mLoginStatusFile.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {

						FileWriter Write;
						try {
							Write = new FileWriter(mLoginStatusFile);
							Write.write("false");
							Write.flush();
							Write.close();
							Write = null;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					Intent intent = new Intent();
					intent.setAction("hive.action.General");
					intent.putExtra("do", "logout");
					sendBroadcast(intent);

					mDPM.lockNow();

					finish();
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							mContext);
					builder.setMessage(R.string.error_admin_not_enabled)
							.setPositiveButton(
									R.string.dialog_selection_set_admin,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											Intent i = new Intent(
													mContext,
													DeviceAdminRequestActivity.class);
											i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
											mContext.startActivity(i);
										}
									})
							.setNegativeButton(R.string.cancel,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
										}
									});
					builder.create();
					builder.show();
				}
			}
		});
	}

	public void setValues() {

		ImageView UserAvatarView = (ImageView) findViewById(R.id.userAvatar);
		TextView UserNameView = (TextView) findViewById(R.id.UserName);
		TextView UserIdView = (TextView) findViewById(R.id.UserId);
		TextView UserClassView = (TextView) findViewById(R.id.UserClass);

		String mUserAvatarPath = Environment.getExternalStorageDirectory()
				+ "/HIVE/User/avatar.png";

		File mUserAvatarFile = new File(mUserAvatarPath);
		File mUserInfoFile = new File(Environment.getExternalStorageDirectory()
				+ "/HIVE/User/information");

		BitmapFactory.Options mBitmapOptions = new BitmapFactory.Options();
		mBitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap mUserAvatar = BitmapFactory.decodeFile(mUserAvatarPath,
				mBitmapOptions);

		if (mUserAvatarFile.exists() && mUserInfoFile.exists()) {
			UserAvatarView.setImageBitmap(mUserAvatar);
			UserNameView.setText(" " + mUserInformation.get(0).toUpperCase());
			UserIdView
					.setText(" "
							+ mUserInformation
									.get(2)
									.toUpperCase()
									.substring(
											mUserInformation.get(2)
													.indexOf("=") + 1));
			UserClassView.setText(" "
					+ mUserInformation.get(3).substring(
							mUserInformation.get(3).indexOf("=") + 1));

		} else {
			UserAvatarView.setImageResource(R.drawable.ic_launcher);
		}

	}

	public void readInformation() {
		File mUserInfoFile = new File(Environment.getExternalStorageDirectory()
				+ "/HIVE/User/information");
		try {
			BufferedReader mBufferReader = new BufferedReader(new FileReader(
					mUserInfoFile));
			String line;

			while ((line = mBufferReader.readLine()) != null) {
				mUserInformation.add(line);
			}
		} catch (IOException e) {
		}
	}

}