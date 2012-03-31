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
import com.highexplosive.client.model.Message;

public class MessageDetailActivity extends Activity {

	private static final String TAG = MessageDetailActivity.class.getSimpleName();
	private Message message;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_detail);

		int messageId = getIntent().getExtras().getInt(
				Message.MESSAGE_ID);

		retrieveFullDeclaration(messageId);
		
		initializeButtons();
	}


	/**
	 * Fill the message popup
	 * @param messageId
	 */
	private void retrieveFullDeclaration(int messageId) {
		message = HxJsonUtils.getMessageDetail(this,
				messageId);

		((TextView) findViewById(R.id.messageFromInDetail)).setText(message.getFromName());
		((TextView) findViewById(R.id.messageSubjectInDetail)).setText(message.getSubject());
		((TextView) findViewById(R.id.messageContentInDetail)).setText(message.getBody());
		
		// Favorite
		((ToggleButton) findViewById(R.id.favoriteInDetail)).setChecked(message.isFavorited());

		// Time
		((TextView) findViewById(R.id.messageTime)).setText(DateUtils
				.formatDateTime(this, message.getTime(),
						DateUtils.FORMAT_24HOUR));
	}

	private void initializeButtons() {
		
		((ToggleButton) findViewById(R.id.favoriteInDetail)).setOnClickListener(new ToggleButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				new MarkAsFavorite().execute(new Integer[] { ((ToggleButton)v).isChecked() ? 1 : 0, message.getMessageId() });
			}
			
		});
		
		((Button) findViewById(R.id.messageBackInDetail)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	/**
	 * Update the karma of the message
	 * @author Luis Ollero
	 *
	 */
	private class MarkAsFavorite extends AsyncTask<Integer, Void, String> {
		@Override
		protected String doInBackground(Integer... integers) {
			String response = "";

			if (HxConstants.ONLINE_MODE) {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpPut httpGet = new HttpPut("url_to_upload_URL" + "messageId" + integers[0]
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
