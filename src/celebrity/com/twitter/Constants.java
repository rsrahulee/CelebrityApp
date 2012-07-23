package celebrity.com.twitter;

public class Constants {
	
	public static final String CONSUMER_KEY = "1oJyS6KaeiEbVaSwQ3jIQ"; //Dm6LdgazBR9QjapsCt6kUA
	public static final String CONSUMER_SECRET= "d51vpj8kPuheZFPoYnKsMsGi7NmZqqCx5s6Tl5s4";	//Hy8JsePBvMZUp3WIRM2nIgnaTsx0QAjqMuHB7kmxk

	
	public static final String REQUEST_URL = "http://api.twitter.com/oauth/request_token";
	public static final String ACCESS_URL = "http://api.twitter.com/oauth/access_token";
	public static final String AUTHORIZE_URL = "http://api.twitter.com/oauth/authorize";
	
	public static final String	OAUTH_CALLBACK_SCHEME	= "x-oauthflow-twitter";
	public static final String	OAUTH_CALLBACK_HOST		= "callback";
	public static final String	OAUTH_CALLBACK_URL		= OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;

}

