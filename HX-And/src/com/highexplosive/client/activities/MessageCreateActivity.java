package com.highexplosive.client.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.highexplosive.client.HxConstants;
import com.highexplosive.client.R;

public class MessageCreateActivity extends Activity {

	private static final String TAG = MessageCreateActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_create);

		initializeButtons();
	}


	private void initializeButtons() {
		
		((Button) findViewById(R.id.messageDiscardInCreate)).setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
			
		});
		
		((Button) findViewById(R.id.messageSendInCreate)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Editable to = ((AutoCompleteTextView) findViewById(R.id.messageToInCreate)).getText();
				Editable subject = ((EditText) findViewById(R.id.messageSubjectInCreate)).getText();
				Editable content = ((EditText) findViewById(R.id.messageContentInCreate)).getText();
				if ((to != null) && (subject != null) && (content != null)) {
					if (HxConstants.ONLINE_MODE) {
						new SendMessage().execute(new String[] { to.toString(), subject.toString(), content.toString() });
					}
				}
			}
		});
	}

	/**
	 * Update the karma of the message
	 * @author Luis Ollero
	 *
	 */
	private class SendMessage extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... strings) {
			String response = "";

			if (HxConstants.ONLINE_MODE) {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost("url_to_upload_URL");
				try {
					
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
					nameValuePairs.add(new BasicNameValuePair("userid", String.valueOf(HxConstants.userId)));
					nameValuePairs.add(new BasicNameValuePair("to", strings[1]));
					nameValuePairs.add(new BasicNameValuePair("subject", strings[2]));
					nameValuePairs.add(new BasicNameValuePair("content", strings[3]));
					
					ResponseHandler<String> handler = new BasicResponseHandler();
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					response = client.execute(httpPost, handler);
					
				} catch (Exception e) {
					Log.e(TAG, e.getMessage());
				}
			}
			return response;
		}
	}
	
}
