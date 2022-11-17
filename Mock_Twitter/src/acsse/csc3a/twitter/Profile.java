package acsse.csc3a.twitter;

import java.util.ArrayList;

public class Profile implements Comparable<Profile>{
	String username = "";
	String profilePicture = "";
	String password = "";
	enum Connection {
		NOTFOLLOWING,
		FOLLOWING,
		FRIENDS
	}
	
	
	// An array of tweets for a user.
	ArrayList<Tweet> tweets = new ArrayList<>();
	
	// Creating an instance of a profile.
	public Profile(String username, String Password) {
		this.username = username;
		this.password = Password;		
	}
	 public String getUsername() {
		 return username;
	 }
	 
	 public ArrayList<Tweet> getTweets() {
		 return tweets;
	 }


	@Override
	public int compareTo(Profile o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
