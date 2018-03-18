package MainStuff;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterStuff {
	
	Twitter twitter;
	TwitterFactory tf;
	
	public TwitterStuff(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("*********************")
		  .setOAuthConsumerSecret("******************************************")
		  .setOAuthAccessToken("**************************************************")
		  .setOAuthAccessTokenSecret("******************************************");
		
		tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
		System.out.println("Twitter Stuff set!");
	}
	
	public ArrayList<User> getTweets(String qr){
		
		ArrayList<User> users = new ArrayList<User>();
		try {
			long numTweets = 0;
			int i = 0;
            Query query = new Query(qr);
            System.out.println("Query: " + query.getQuery());
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    //System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                    users.add(new User(tweet.getUser().getScreenName()));
                }
                numTweets = numTweets + tweets.size();
                i++;
            } while (((query = result.nextQuery()) != null) && i < 3);
            //System.out.println("Anzahl: " + numTweets);
            //System.out.println("Users: " + users.size());
            //System.out.println("Name: " + users.get(0).getName());
            return users;
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
		return null;
	}

	public ArrayList<String> getMsgs(String name){
		ArrayList<String> msgs = new ArrayList<String>();
		try {
			int i = 0;
			Query query = new Query(name);
			//System.out.println("Name: " + query.getQuery());
			QueryResult result;
			do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {
					//System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
					msgs.add(new String(tweet.getText()));
				}
				i++;
			} while (((query = result.nextQuery()) != null) && i < 1);
			return msgs;
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
			System.exit(-1);
		}
		return null;
	}
}
