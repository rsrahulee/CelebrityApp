package celebrity.com.parser;

import java.util.ArrayList;

import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONObject;

public class ParseResult {

	public static ParseResult INSTANCE = new ParseResult();
	ArrayList<String> imgLinks;

	public ParseResult() {

	}

	public ArrayList<String> parseData(String strJsonReponse) {

		try {
			imgLinks = new ArrayList<String>();

			System.out.println("--------------DB and DB tables created");

			JSONObject metaJsonObject = new JSONObject(strJsonReponse);

			String strData = metaJsonObject.getString("data");
			JSONArray mJsonArray = new JSONArray(strData);

			for (int i = 0; i < mJsonArray.length(); i++) {

				imgLinks.add(mJsonArray.getJSONObject(i).getString("picture"));
			}

		} catch (Exception e) {

		}
		return imgLinks;
	}
	
	public ArrayList<String> parseFbFeeds(String strJsonReponse) {

		try {
			imgLinks = new ArrayList<String>();

			System.out.println("--------------DB and DB tables created");

			JSONObject metaJsonObject = new JSONObject(strJsonReponse);

			String strData = metaJsonObject.getString("data");
			JSONArray mJsonArray = new JSONArray(strData);

			for (int i = 0; i < mJsonArray.length(); i++) {

				imgLinks.add(mJsonArray.getJSONObject(i).getString("name"));
			}

		} catch (Exception e) {

		}
		return imgLinks;
	}
	
	public ArrayList<String> parseTweets(String strJsonReponse) {

		try {
			imgLinks = new ArrayList<String>();

			System.out.println("--------------DB and DB tables created");

			JSONObject metaJsonObject = new JSONObject(strJsonReponse);

			String strData = metaJsonObject.getString("results");
			JSONArray mJsonArray = new JSONArray(strData);

			for (int i = 0; i < mJsonArray.length(); i++) {

				imgLinks.add(mJsonArray.getJSONObject(i).getString("text"));
			}

		} catch (Exception e) {

		}
		return imgLinks;
	}
}
