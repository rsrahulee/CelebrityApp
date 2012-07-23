package celebrity.com.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class RestClient {

	
	public static String base_url = "http://search.twitter.com/";
	public static String getBase_url() {
		return base_url;
	}

	public static void setBase_url(String base_url) {
		RestClient.base_url = base_url;
	}

	static RestClient instance = new RestClient();
	static Context context;

	public static RestClient getInstance(Context ctx) {
		context = ctx;
		return instance;
	}

	public String doApiCall(String url, String type,
			List<NameValuePair> nameValuePairs) throws ClientProtocolException,
			IOException {

		String result = "null";

		HttpClient httpclient = new DefaultHttpClient();

		if (type.equalsIgnoreCase("POST")) {

			HttpPost httpPost = new HttpPost(base_url + url);
			Log.v("RestClient", "#############POST URL: " + base_url + url);
			httpPost.setHeader("accept", "text/mobile");

			if (nameValuePairs != null && !nameValuePairs.isEmpty()) {

				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			}

			HttpResponse response = httpclient.execute(httpPost);
			result = EntityUtils.toString(response.getEntity());
			return result;

		} else if (type.equalsIgnoreCase("GET")) {

			if (nameValuePairs != null && !nameValuePairs.isEmpty()) {

				url += "?";

				Iterator<NameValuePair> iter = nameValuePairs.iterator();
				while (iter.hasNext()) {

					NameValuePair nvp = iter.next();
					url += nvp.getName();
					url += "=";
					url += nvp.getValue();
					if (iter.hasNext())
						url += "&";

				}
			}

			Log.v("REstClient", "####url GET: " + base_url + url);
			HttpGet httpGet = new HttpGet(base_url + url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			httpGet.setHeader("accept", "text/mobile");
			result = httpclient.execute(httpGet, responseHandler);
			return result;

		}

		return result;
	}

	public String doNewApiCall(String url, String type,
			List<NameValuePair> nameValuePairs) throws ClientProtocolException,
			IOException {

		String result = "null";

		HttpClient httpclient = new DefaultHttpClient();

		if (type.equalsIgnoreCase("POST")) {

			HttpPost httpPost = new HttpPost(url);
			Log.v("RestClient", "#############POST URL: " + url);
			httpPost.setHeader("accept", "text/mobile");

			if (nameValuePairs != null && !nameValuePairs.isEmpty()) {

				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			}

			HttpResponse response = httpclient.execute(httpPost);
			result = EntityUtils.toString(response.getEntity());
			return result;

		} else if (type.equalsIgnoreCase("GET")) {

			if (nameValuePairs != null && !nameValuePairs.isEmpty()) {

				url += "?";

				Iterator<NameValuePair> iter = nameValuePairs.iterator();
				while (iter.hasNext()) {

					NameValuePair nvp = iter.next();
					url += nvp.getName();
					url += "=";
					url += nvp.getValue();
					if (iter.hasNext())
						url += "&";

				}
			}

			Log.v("REstClient", "####url GET: " + url);
			HttpGet httpGet = new HttpGet(url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			httpGet.setHeader("accept", "text/mobile");
			result = httpclient.execute(httpGet, responseHandler);
			return result;

		}

		return result;
	}


	public JSONObject getJSONFromUrl(String url) {

		InputStream is = null;
		JSONObject jObj = null;
		String json = "";
		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();           

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		// return JSON String
		return jObj;
	}
}
