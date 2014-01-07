package hive.framework.user;

import hive.framework.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_user_details);

//		String mAvatarUrl = "http://hive.xiprox.org/student/" + mUserInformation.get(1) + "/avatar.png";

		readInformation();

		setValues();

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
			UserIdView.setText(" " + mUserInformation.get(1).toUpperCase());
			UserClassView.setText(" " + mUserInformation.get(2));

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