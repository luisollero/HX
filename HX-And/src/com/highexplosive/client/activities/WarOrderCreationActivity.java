package com.highexplosive.client.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.highexplosive.client.HxConstants;
import com.highexplosive.client.R;

public class WarOrderCreationActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.war_order_creation);
		
		initializeSpinners();
		initializeButtons();
		
	}

	
	private void initializeSpinners() {
		Spinner availableRegiments = (Spinner) findViewById(R.id.availableRegiments);
		Spinner adjacentSectors = (Spinner) findViewById(R.id.adjacentSectors);
		Spinner regimentOrder = (Spinner) findViewById(R.id.regimentOrder);

		if (HxConstants.ONLINE_MODE) {
			//TODO Online order creation spinners
		} else {
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
					  this, R.array.dummy_spinner, android.R.layout.simple_spinner_item );
			adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
			
			availableRegiments.setAdapter(adapter);
			adjacentSectors.setAdapter(adapter);
			regimentOrder.setAdapter(adapter);
		}
	}


	private void initializeButtons() {
		
		((Button) findViewById(R.id.cancelInOrderCreation)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		((Button) findViewById(R.id.warOrderCreationSave)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (HxConstants.ONLINE_MODE) {
					//TODO Online mode in order creation
				} else {
					// Dummy
					onBackPressed();
				}
			}
		});
		
		
	}

	
}
