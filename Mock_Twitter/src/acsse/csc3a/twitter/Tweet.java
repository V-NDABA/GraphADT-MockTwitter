package acsse.csc3a.twitter;

import java.time.LocalDateTime;

public class Tweet {
	String tweet;
	LocalDateTime timePosted;
	
	public Tweet(String tweet, LocalDateTime timePosted) {
		this.tweet = tweet;
		this.timePosted = timePosted;
	}
	
	public String getTweet() {
		return tweet;
	}
	
	public LocalDateTime getTime() {
		return timePosted;
	}
}
