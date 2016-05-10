package SocialUtils.Twitter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAPI {
	private Twitter twitter;
	private User user;
	private ConfigurationBuilder builder;
	private RequestToken requestToken;
	private AccessToken accessToken;
	private String ConsumerKey = "3cpe6PPsbmJrenouFHW8t7rQM";
	private String ConsumerSecret = "APkPmOTLgg0t1GMFuKvrdMnDwdQxhBVWClD3ZHBwn4NySAYoYL";
	
	private boolean logged;
	
	public TwitterAPI(){
		twitter.setOAuthConsumer(ConsumerKey, ConsumerSecret);
		builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(ConsumerKey);
		builder.setOAuthConsumerSecret(ConsumerSecret);
		builder.setOAuthAccessToken(accessToken.getToken());
		builder.setOAuthAccessTokenSecret(accessToken.getTokenSecret()); 
	    try {
			requestToken = twitter.getOAuthRequestToken();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    accessToken = null;
	    logged = false;
	}
	
	public String getAuthURL(){
		return requestToken.getAuthorizationURL();
	}
	
	public void authAPI(String PIN){
		try{
	         if(PIN.length() > 0){
	           accessToken = twitter.getOAuthAccessToken(requestToken, PIN);
	           logged = true;
	           user = twitter.verifyCredentials();
	         }else{
	           accessToken = twitter.getOAuthAccessToken();
	         }
	      } catch (TwitterException te) {
	        if(401 == te.getStatusCode()){
	          System.out.println("Unable to get the access token.");
	        }else{
	          te.printStackTrace();
	        }
	      }
	}
	
	public void renewAccessToken(){
		try {
			requestToken = twitter.getOAuthRequestToken();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    accessToken = null;
	    logged = false;
	}
	
	public void persistAccessToken(){
		try {
			storeAccessToken(twitter.verifyCredentials().getId() , accessToken);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isUserLogged(){
		return logged;
	}
	
	public long getUserId(){
		try {
			return twitter.getId();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public String getUserDisplayName(){
		try {
			return twitter.getScreenName();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getUserName(){
		return user.getName();
	}
	
	public String getUserProfileImgURL(){
		return user.getOriginalProfileImageURL();
	}
	
	public String getUserLocation(){
		return user.getLocation();
	}
	
	/*public static void main(String args[]) throws Exception{
	    Twitter twitter = TwitterFactory.getSingleton();
	    twitter.setOAuthConsumer("3cpe6PPsbmJrenouFHW8t7rQM", "APkPmOTLgg0t1GMFuKvrdMnDwdQxhBVWClD3ZHBwn4NySAYoYL");
	    RequestToken requestToken = twitter.getOAuthRequestToken();
	    AccessToken accessToken = null;
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    while (null == accessToken) {
	      System.out.println("Open the following URL and grant access to your account:");
	      System.out.println(requestToken.getAuthorizationURL());
	      System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
	      String pin = br.readLine();
	      try{
	         if(pin.length() > 0){
	           accessToken = twitter.getOAuthAccessToken(requestToken, pin);
	         }else{
	           accessToken = twitter.getOAuthAccessToken();
	         }
	      } catch (TwitterException te) {
	        if(401 == te.getStatusCode()){
	          System.out.println("Unable to get the access token.");
	        }else{
	          te.printStackTrace();
	        }
	      }
	    }
	    //persist to the accessToken for future reference.
	    storeAccessToken(twitter.verifyCredentials().getId() , accessToken);
	    //Status status = twitter.updateStatus("Esto es una prueba");
	    //System.out.println("Successfully updated the status to [" + status.getText() + "].");
	    System.out.println("Tus datos:");
	    System.out.println(" Id: "+twitter.getId());
	    System.out.println(" Nombre: "+twitter.getScreenName());
	    System.exit(0);
	  }*/
	
	  private static void storeAccessToken(long l, AccessToken accessToken){
	    //store accessToken.getToken()
	    //store accessToken.getTokenSecret()
	  }
}
