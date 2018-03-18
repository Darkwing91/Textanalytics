package MainStuff;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import DataStuff.GenerateClassifierGender;
import DataStuff.GenerateDataSet;
import DataStuff.SimpleClassifier;
import StatisticStuff.View;

public class Model {

	ArrayList<User> users = new ArrayList<User>();
	
	public static void main(String[] args) throws Exception {
		//TwitterStuff twitterAcc = new TwitterStuff();
		//List<User> users = twitterAcc.getTweets("#MeToo");
		//SaveData(new GenerateDataSet().GetData(new Model().getTwitterData(), "classifiable_data"), "classifiable_set");
		new Model();
	}
	
	public static void SaveData(Instances dataset, String filename) throws IOException {
		ArffSaver saver = new ArffSaver();
		saver.setInstances(dataset);
		saver.setFile(new File("/Users/Lukas/Desktop/" + filename + ".arff"));
		saver.writeBatch();
	}

	public Model(){
		SimpleClassifier sc = new SimpleClassifier();
		sc.classifierTest();
		/*TwitterStuff twitterAcc = new TwitterStuff();
		users = twitterAcc.getTweets("#gg");
		for(User user : users){
			System.out.println(user.getName());
			user.SetNachichten(twitterAcc.getMsgs(user.getName()));
			//System.out.println(user.getFirstMsg());
			/*for(String msg : user.getNachichten()){
				System.out.println(msg);
			}
			//System.out.println(user.getGender());
			//System.out.println(user.getAlter()); 
		}
		for(User user : users) {
			user.SetGender(sc.classifyUserGender(user));
			user.SetAlter(sc.classifyUserAge(user));
			System.out.println(user.getName());
			System.out.println(user.getGender());
			System.out.println("" + user.getAlter());
		}
		System.out.println(sc.retriveCategorys());*/
	}
	
	public ArrayList<User> getTwitterData() {
		return users;
	}
}
