package celebrity.com.parser;

import java.util.ArrayList;

import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONObject;
import celebrity.com.model.AlbumModel;

public class ParseResult {

	public static ParseResult INSTANCE = new ParseResult();
	ArrayList<String> imgLinks;
	ArrayList<AlbumModel> albumsList;

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

				imgLinks.add("FB " + mJsonArray.getJSONObject(i).getString("name"));
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

	public ArrayList<AlbumModel> parseAlbums(String strJsonReponse) {

		try {
			albumsList = new ArrayList<AlbumModel>();

			System.out.println("--------------DB and DB tables created");

			JSONObject metaJsonObject = new JSONObject(strJsonReponse);

			String strData = metaJsonObject.getString("data");
			JSONArray mJsonArray = new JSONArray(strData);

			for (int i = 0; i < mJsonArray.length(); i++) {
				AlbumModel model = new AlbumModel();
				model.setName(mJsonArray.getJSONObject(i).getString("name"));
				model.setCount(mJsonArray.getJSONObject(i).getString("count"));
				albumsList.add(model);
			}

		} catch (Exception e) {

		}
		return albumsList;
	}
}
