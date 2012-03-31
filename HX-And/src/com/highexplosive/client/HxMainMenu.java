package com.highexplosive.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.highexplosive.client.activities.ProfileActivity;

public class HxMainMenu extends Activity {

	@SuppressWarnings("unused")
	private static final String TAG = HxMainMenu.class.getCanonicalName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_menu);
		
		initButtons();
	}
	
	
	private void initButtons() {
		((Button)findViewById(R.id.mainMenuNews)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HxMainMenu.this, HxActivity.class);
				startActivity(intent);
			}
		});
		
		((Button)findViewById(R.id.mainMenuProfile)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HxMainMenu.this, ProfileActivity.class);
				startActivity(intent);
			}
		});

		((Button)findViewById(R.id.mainMenuEndOfTurn)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
	}
	

		
	

}
