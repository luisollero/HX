package com.highexplosive.client.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class HxUtil {
	
	private static final int TIMEOUT = 30000;
	private static String TAG = HxUtil.class.getSimpleName();

	/**
	 * Utility method to retrieve from a URL the JSON that is in it
	 */
	public static InputStream retrieveInputStreamFromURL(String uri) {
		InputStream xml = null;
		try {
			
			URL url = new URL(uri);
			HttpURLConnection connection =
			    (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			connection.setUseCaches(true);
			connection.setConnectTimeout(TIMEOUT);
			connection.setReadTimeout(TIMEOUT);

			xml = connection.getInputStream();
			
		} catch (Exception e) {
			Log.e(TAG, "Connection problem trying to access to: " + uri);
			if (e.getMessage() != null) {
				Log.e(TAG, e.getMessage());
			}
		}

		return xml;
	}

}
