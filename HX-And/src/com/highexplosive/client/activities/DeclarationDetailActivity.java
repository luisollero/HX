package com.highexplosive.client.activities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.highexplosive.client.HxConstants;
import com.highexplosive.client.HxJsonUtils;
import com.highexplosive.client.R;
import com.highexplosive.client.model.Declaration;

public class DeclarationDetailActivity extends Activity {

	private static final String TAG = DeclarationDetailActivity.class.getSimpleName();
	private Declaration declaration;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.declaration_detail);

		int declarationId = getIntent().getExtras().getInt(
				Declaration.DECLARATION_ID);

		retrieveFullDeclaration(declarationId);
		
		initializeButtons();
	}


	/**
	 * Fill the declaration popup
	 * @param declarationId
	 */
	private void retrieveFullDeclaration(int declarationId) {
		declaration = HxJsonUtils.getDeclarationDetail(this,
				declarationId);
		Log.v(TAG, declaration.toString());

		((TextView) findViewById(R.id.declarationFromInDetail)).setText(declaration.getFromName());
		((TextView) findViewById(R.id.declarationPublished)).setText(declaration.getPublished());
		((TextView) findViewById(R.id.declarationSubjectInDetail)).setText(declaration.getSubject());
		((TextView) findViewById(R.id.declarationBody)).setText(declaration.getBody());
		
		// Favorited
		((ToggleButton) findViewById(R.id.plusOneInDetail)).setChecked(declaration.isFavorited());

		// Time
		((TextView) findViewById(R.id.declarationTime)).setText(DateUtils
				.formatDateTime(this, declaration.getTime(),
						DateUtils.FORMAT_24HOUR));
	}

	private void initializeButtons() {
		
		((ToggleButton) findViewById(R.id.plusOneInDetail)).setOnClickListener(new ToggleButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				new UpdateKarma().execute(new Integer[] { ((ToggleButton)v).isChecked() ? 1 : 0, declaration.getDeclarationId() });
			}
			
		});
		
		((Button) findViewById(R.id.backInDetail)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	/**
	 * Update the karma of the declaration
	 * @author Luis Ollero
	 *
	 */
	private class UpdateKarma extends AsyncTask<Integer, Void, String> {
		@Override
		protected String doInBackground(Integer... integers) {
			String response = "";

			if (HxConstants.ONLINE_MODE) {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpPut httpGet = new HttpPut("url_to_upload_URL" + "declarationId" + integers[0]
						+ "upvote" +  integers[1]);
				try {
					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();
					
					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(content));
					String s = "";
					while ((s = buffer.readLine()) != null) {
						response += s;
					}
				} catch (Exception e) {
					Log.e(TAG, e.getMessage());
				}
			}
			return response;
		}
	}
	
}
