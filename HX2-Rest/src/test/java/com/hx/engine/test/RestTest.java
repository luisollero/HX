package com.hx.engine.test;

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
import org.junit.Test;

public class RestTest extends TestBase {
	
	
	@Test
	public void init() {
		postTest();
//		putTest();
//		deleteTest();
	}

	
//	private void putTest() {
//		try {
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
	
//	private void deleteTest() {
//		try {
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}

	private void postTest() {
		String result = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost request = new HttpPost(
				"http://localhost:8080/HX2-Rest/communication");
		ResponseHandler<String> handler = new BasicResponseHandler();

		/*
		 * communication.setCommunicationId(1);
		communication.setFromId(1);
		communication.setFromName("from name");
		communication.setSubject("subject");
		communication.setPublishedIn("Tallin mensk");
		communication.setKarma(2);
		communication.setBody("Body");
		communication.setFavorited(true);
		 */
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		nameValuePairs.add(new BasicNameValuePair("fromId", "1"));
		nameValuePairs.add(new BasicNameValuePair("subject", "subject"));
		nameValuePairs.add(new BasicNameValuePair("published", "Tallin mensk"));
		nameValuePairs.add(new BasicNameValuePair("body", "Whatever but a thousand times " +
				"Whatever but a thousand times " +
				"Whatever but a thousand times " +
				"Whatever but a thousand times " +
				"Whatever but a thousand times "));
		
		try {
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			result = httpclient.execute(request, handler);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		if (result != null)
			System.out.println(result);
	}

}