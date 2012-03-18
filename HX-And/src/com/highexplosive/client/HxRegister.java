package com.highexplosive.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Register user activity
 * 
 * @author Luis Ollero
 * 
 */
public class HxRegister extends Activity {

	private static final int DIALOG_WARNING = 0;
	protected static final String TAG = HxRegister.class.getSimpleName();
	private String factionSelected;
	private String roleSelected;
	private String user;
	private String motivation;
	private String mail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		initButtons();
	}

	/**
	 * Init buttons and spinners
	 */
	private void initButtons() {

		Spinner spinner = (Spinner) findViewById(R.id.registerFaction);
		ArrayAdapter<CharSequence> adapterFaction = ArrayAdapter
				.createFromResource(this, R.array.register_factions_array,
						android.R.layout.simple_spinner_item);
		adapterFaction
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapterFaction);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				factionSelected = (String) ((TextView) arg1).getText();
				initRoleSpinner();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// Do nothing
			}

		});

		initRoleSpinner();

		Button registerButton = (Button) findViewById(R.id.registerButton);
		registerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_WARNING);
			}
		});

	}

	/**
	 * Spinner for the Role
	 */
	private void initRoleSpinner() {
		Spinner roleSpinner = (Spinner) findViewById(R.id.registerRole);
		ArrayAdapter<CharSequence> adapterRole = ArrayAdapter
				.createFromResource(
						this,
						"Comstar".equals(factionSelected) ? R.array.register_comroles_array
								: R.array.register_isroles_array,
						android.R.layout.simple_spinner_item);
		adapterRole
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		roleSpinner.setAdapter(adapterRole);
	}

	/**
	 * 
	 */
	protected Dialog onCreateDialog(int id) {
		AlertDialog alert = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (id) {
		case DIALOG_WARNING:
			builder.setMessage(getString(R.string.register_popup))
					.setCancelable(false)
					.setPositiveButton(getString(R.string.register_ok),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									if (validate()) {
										registerUser();
										HxRegister.this.finish();
									} else {
										Toast.makeText(HxRegister.this, getString(R.string.register_fill_all_fields), Toast.LENGTH_LONG);
									}
								}
							})
					.setNegativeButton(R.string.register_cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});

			alert = builder.create();

			break;
		default:
			alert = null;
		}
		return alert;
	}
	
	protected boolean validate() {
		if ((user != null && user.length() != 0) && 
				(mail != null && mail.length() != 0) &&
				(factionSelected != null && factionSelected.length() != 0) &&
				(roleSelected != null && roleSelected.length() != 0) &&
				(motivation != null && motivation.length() != 0))
			return true;
		return false;
	}

	protected void registerUser() {
		if (HxConstants.ONLINE_MODE) {
			RegisterUserTask task = new RegisterUserTask();
			task.execute(new String[] { "http://localhost:8080/Hx-Rest/user/" });
		}
	}
	
	/**
	 * Register an user within the game
	 * 
	 * @author Luis Ollero
	 *
	 */
	private class RegisterUserTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			String response = "";
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost request = new HttpPost(urls[0]);
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
			nameValuePairs.add(new BasicNameValuePair("user", user));
			nameValuePairs.add(new BasicNameValuePair("mail", mail));
			nameValuePairs.add(new BasicNameValuePair("faction", factionSelected));
			nameValuePairs.add(new BasicNameValuePair("role", roleSelected));
			nameValuePairs.add(new BasicNameValuePair("motivation", motivation));
			
			ResponseHandler<String> handler = new BasicResponseHandler();
			try {
				request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				response = httpclient.execute(request, handler);
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
			
			return response;
		}

	}

}
