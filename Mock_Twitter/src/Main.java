import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.w3c.dom.Element;
import org.w3c.dom.html.HTMLButtonElement;

import acsse.csc3a.twitter.Profile;

import com.jwetherell.algorithms.data_structures.Edge;
import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.TYPE;
import com.jwetherell.algorithms.data_structures.Vertex;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import java.net.URL;

public class Main extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	// Loading the webView
	WebView webView = new WebView();
	WebEngine webEngine = webView.getEngine();
	ScriptEngineManager manager = new ScriptEngineManager();
	ScriptEngine scriptEngine = manager.getEngineByName("Script/script.js");
	JSObject jsConnector = null;
	//List of verticies
	List<Vertex<Profile>> verticies = new ArrayList<Vertex<Profile>>();
	//List of edges
	List<Edge<Profile>> edges = new ArrayList<Edge<Profile>>();
	//Add the profile to the Graph vertex
	Vertex<Profile> vProfile;
	
	Graph<Profile> twitterGraph = new Graph<>(TYPE.UNDIRECTED, verticies, edges);
	
	//Method for logging in
	public void LogIn(String username, String password) {
		Profile newProfile = new Profile(username, password);
		//Add the profile to the Graph vertex
				vProfile = new Vertex<Profile>(newProfile);
				verticies.add(vProfile);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Twitter Mock");
		//Loading the html resources
		String home = "resources/index.html";
		File f = new File(home);
		String urlHome = f.toURI().toURL().toString();
		String login = "resources/login.html";
		File g = new File(login);
		String urlLogin = g.toURI().toURL().toString();
		
		webEngine.setJavaScriptEnabled(true);
		webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) ->{
			if(Worker.State.SUCCEEDED == newValue){
				System.out.println("It was clicked");
				jsConnector = (JSObject) webEngine.executeScript("window");
				jsConnector.setMember("main", new JavaApp());
			}
			else {
				System.out.println("Nothing to show here but a button is not clicked");
			}
		});
		
		if(vProfile == null) {
			webEngine.load(urlLogin);
		}
		else {
			webEngine.load(urlHome);
		}
		
		//HTMLButtonElement buttonElement = webEngine.getDocument().getElementByI
		// Setting the scene
		VBox vBox = new VBox(webView);
		Scene scene = new Scene(vBox, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	public class auth{
		
		public boolean login(String username) {
			System.out.println("Logging in");
			boolean check = false;
			for(Vertex<Profile> v: verticies) {
				if(v.getValue().getUsername() == username) {
					check = false;
				}
				else {
					check = true;
					LogIn(username, "madeup");
				}
			}
			return check;	
		}
	}
	public class JavaApp{
		public void exit() {
			System.out.println("Exiting the platform");
			Platform.exit();
		}
		//User A following User B
		public String followMethod(String userName) {
			
			System.out.println("Follow method launched");
			
			Vertex<Profile> currentProfile = null;
			int cost = 0;
			//Find the username
			for(Vertex<Profile> v: verticies) {
				if(v.getValue().getUsername() == userName) {
					//FIND OUT THE CONNECTION	
					twitterGraph = new Graph<>(twitterGraph);
					Edge<Profile> edge = v.getEdge(vProfile);
					
					if(edge != null) {
						cost = edge.getCost();
						if(cost < 1) {
							edge.setCost(cost+1);
							edges.add(edge);
						}
					} else {
						Edge<Profile> newEdge = new Edge<Profile>(1, vProfile, v);
						edges.add(newEdge);
					}
					currentProfile = v;
					//
				}
				//add a method to stop the method from going forward, as user doesn't exist
				
			}
			
			return null;
		}
		
		//Getting a user from the HTML
		
		//Showing unfollowed profiles
		public String[] unfollowedProfiles() {
			String[] unfollowed = null;
			unfollowed[0] = "No users present";
			for(Vertex<Profile> v : verticies) {
				int i =0;
				if(v.getWeight() == 0)
				{
					jsConnector.call("whoToFollow", v.getValue().getUsername());
					String whoToFollow = "<div class=\"who-to-follow__block\">\r\n"
							+ "                        <img class=\"who-to-follow__author-logo\" src=\"./images/profile-image-1.jpg\" />\r\n"
							+ "                        <div class=\"who-to-follow__content\">\r\n"
							+ "                            <div>\r\n"
							+ "                                <div class=\"who-to-follow__author-name\">\r\n"
							+ "                                    Becki (& Chris)\r\n"
							+ "                                </div>\r\n"
							+ "                                <div class=\"who-to-follow__author-slug\" id=\"userName\">\r\n"
							+ "                                    @"+ v.getValue().getUsername() +"\r\n"
							+ "                                </div>\r\n"
							+ "                            </div>\r\n"
							+ "                            <button class=\"who-to-follow__button\" onClick=\"FollowUser();\">Follow</button>\r\n"
							+ "                        </div>\r\n"
							+ "                    </div>";
					unfollowed[i] = whoToFollow;
				}
			}
			return unfollowed;
		}
		
		public String[] showTweets() {
			System.out.println("Trying to show tweets");
			String[] feed = null;
			feed[0] = "<div class='tweet__content' " +"> No content to show </div>";
			for(Vertex<Profile> v : verticies) {
				int i = 0;
				if(v.getWeight() == 2) {
					String tweets = """
							<div class="tweet" id="feed-tweet" method="post">
			                <img class="tweet__author-logo" src="./images/profile-image-1.jpg" />
			                <div class="tweet__main">
			                    <div class="tweet__header">
			                        <div class="tweet__author-name" id="author-name">
			                            Becki (& Chris)
			                        </div>
			                        <div class="tweet__author-slug" id="author-username"> @"""+
			                            vProfile.getValue().getUsername() +"""
			                        </div>
			                        <div class="tweet__publish-time" id="publish-time">"""+
			                             vProfile.getValue().getTweets().get(0).getTime() +"""
			                        </div>
			                    </div>
			                    <div class="tweet__content" id="tweet-body">"""+
			                        vProfile.getValue().getTweets().get(0).getTweet() + """
			                    </div>
			                </div>
			            </div>
							""";
					
					//return the string to the webView
					feed[i] = tweets;
					
				}
			}
			jsConnector.call("feed_tweets",(Object[]) feed);
			return feed;
		}
	}
}
