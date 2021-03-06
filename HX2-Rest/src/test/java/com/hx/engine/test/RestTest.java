package com.hx.engine.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.HttpResponse;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.NameValuePair;
import org.apache.http.RequestLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import com.hx.model.dto.Role;

public class RestTest extends TestBase {
	
	
	private static String MAIN_SERVER = "http://localhost:8081/HX2-Rest/";
	
	@Test
	public void init() {
//		postUserTest();
		postCheckUserTest();
//		postPersonalityTest();
//		postCommunicationTest();
//		getCommunicationTest();
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

	private void postCommunicationTest() {
		String result = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost request = new HttpPost(
				MAIN_SERVER + "communication");
		ResponseHandler<String> handler = new BasicResponseHandler();
		
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

	/**
	 * 
	 */
	private void getCommunicationTest() {
		HttpResponse result = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpUriRequest request = new HttpGet(MAIN_SERVER + "communication/1");
		
		try {
			result = httpclient.execute(request);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		
		if (result != null)
			System.out.println(result);
	}
	
	private void postUserTest() {
		String result = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost request = new HttpPost(
				MAIN_SERVER + "user");
		ResponseHandler<String> handler = new BasicResponseHandler();
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
		nameValuePairs.add(new BasicNameValuePair("name", "Uno Quepasaba"));
		nameValuePairs.add(new BasicNameValuePair("motivation", "I want to join this wonderful game. Foo."));
		nameValuePairs.add(new BasicNameValuePair("mail", "test@highexplosive.net"));
		nameValuePairs.add(new BasicNameValuePair("role", "PRIMUS"));
		nameValuePairs.add(new BasicNameValuePair("faction", "COMSTAR"));
		
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

	/**
	 * 
	 */
	private void postCheckUserTest() {
		String result = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost request = new HttpPost(
				MAIN_SERVER + "user/0/check");
		ResponseHandler<String> handler = new BasicResponseHandler();
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("userName", "luisollero@gmail.com"));
		nameValuePairs.add(new BasicNameValuePair("userPass", "whatever"));
		
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

	private void postPersonalityTest() {
		String result = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost request = new HttpPost(
				MAIN_SERVER + "personality");
		ResponseHandler<String> handler = new BasicResponseHandler();
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
		nameValuePairs.add(new BasicNameValuePair("name", "Katrina"));
		nameValuePairs.add(new BasicNameValuePair("completeName", "Katrina Steiner"));
		nameValuePairs.add(new BasicNameValuePair("userId", "1"));
		nameValuePairs.add(new BasicNameValuePair("role", Role.DUKE.name()));
		nameValuePairs.add(new BasicNameValuePair("houseId", "STEINER"));
		nameValuePairs.add(new BasicNameValuePair("homeSectorId", "10"));
		
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