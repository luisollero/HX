package com.highexplosive.client.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.highexplosive.client.R;
import com.highexplosive.client.model.WarReport;

public class WarReportDetailActivity extends Activity {

	private static final String TAG = WarReportDetailActivity.class.getSimpleName();
	private WarReport declaration;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_detail);

		retrieveFullReport(0);
		
		initializeButtons();
	}


	/**
	 * Fill the declaration popup
	 * @param declarationId
	 */
	private void retrieveFullReport(int declarationId) {
//		declaration = HxJsonUtils.getDeclarationDetail(this,
//				declarationId);
//		Log.v(TAG, declaration.toString());
//
		((TextView) findViewById(R.id.reportResultInDetail)).setText(getString(R.string.dummy_text));
		((TextView) findViewById(R.id.reportResumeInDetail)).setText(getString(R.string.dummy_text_long));
//		((TextView) findViewById(R.id.declarationSubjectInDetail)).setText(declaration.getSubject());
//		((TextView) findViewById(R.id.declarationBody)).setText(declaration.getBody());
//		
//		// Favorited
//		((ToggleButton) findViewById(R.id.plusOneInDetail)).setChecked(declaration.isFavorited());
//
//		// Time
//		((TextView) findViewById(R.id.declarationTime)).setText(DateUtils
//				.formatDateTime(this, declaration.getTime(),
//						DateUtils.FORMAT_24HOUR));
	}

	private void initializeButtons() {
		
		((Button) findViewById(R.id.backInReportDetail)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

}
