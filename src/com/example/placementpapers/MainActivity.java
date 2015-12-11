package com.example.placementpapers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	Button SignUp;
	Button SCompanies;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.bsignup:
			Intent i = new Intent("android.intent.action.STU_REGISTER");
			startActivity(i);
			finish();
			break;
		case R.id.bcompanies:
			Intent j = new Intent("android.intent.action.RANDOMCOMPANIES");
			startActivity(j);
			finish();
			break;

		}
	}

}
