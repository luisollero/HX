package com.highexplosive.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HxLogin extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		initButtons();
	}

	private void initButtons() {
		Button button = (Button) findViewById(R.id.loginButton);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				checkUser();
			}
		});
		
		TextView register = (TextView) findViewById(R.id.loginRegister);
		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HxLogin.this, HxRegister.class);
				startActivity(intent);
			}
		});
	}

	
	
	/**
	 * Check if the credentials are correct
	 */
	protected void checkUser() {
		boolean loginSuccess = true;

		//TODO: Call the webservice
		
		if (loginSuccess) {
			Intent intent = new Intent(this, HxActivity.class);
			startActivity(intent);
		}
	}

}
