package hive.framework;

import hive.framework.user.UserDetailsDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void showUserDetailsDialog() {
		Intent mUserDetailsDialogIntent = new Intent(this,
				UserDetailsDialog.class);
		startActivity(mUserDetailsDialogIntent);
	}

	

}
