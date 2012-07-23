package celebrity.com.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import celebrity.com.webservice.RestClient;

import android.util.Log;

public class Parser {

	RestClient restClient = new RestClient();

	public Parser() {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs
				.add(new BasicNameValuePair(
						"access_token",
						"AAAEy3j8cizoBAOZAWyo1YgcgS6OcYTZAEBJVaBciDbbYRocn7DPmd2eMNsPVMA2HFUI2XfZBmrFvNnQ6ssV7XZCf6m36kOxBWZAZAKTZAmGlwZDZD"));

		nameValuePairs.add(new BasicNameValuePair("limit", "" + 20));
		try {
			String json = restClient.doNewApiCall(
					"https://graph.facebook.com/10150307510509807/photos",
					"GET", nameValuePairs);
			ParseResult.INSTANCE.parseData(json);
			Log.i("json::::::::::", String.valueOf(json));
		} catch (ClientProtocolException e) {
			Log.i("ClientProtocolException::::::::::", String.valueOf(e));
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("IOException::::::::::", String.valueOf(e));
		}
	}
}
