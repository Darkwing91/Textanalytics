package DataStuff;

import java.util.ArrayList;
import java.util.Arrays;

import MainStuff.GenerateUserSet;
import MainStuff.User;
import de.daslaboratorium.machinelearning.classifier.Classifier;
import de.daslaboratorium.machinelearning.classifier.bayes.BayesClassifier;

public class SimpleClassifier {
	Classifier<String,String> 	bayesGender 	= new BayesClassifier<String,String>();
	Classifier<String, String> 	bayesAge		= new BayesClassifier<String, String>();
	
	public SimpleClassifier() {
		initialize();
	}
	
	private void initialize(){
		ArrayList<User> users = new GenerateUserSet().getTestList();
		//users.addAll(new GenerateUserSet().getTrainingList());
		bayesGender.setMemoryCapacity(2000);
		for(User user : users) {
			bayesGender.learn(user.getGender(), Arrays.asList(user.getName().split("\\s")));
			for(String msg : user.getNachichten()){
				if(user.getGender().equals("male"))
					bayesGender.learn("male", Arrays.asList(msg.split("\\s")));
				else if (user.getGender().equals("female"))
					bayesGender.learn("female", Arrays.asList(msg.split("\\s")));
				else if (user.getGender().equals("channel"))
					bayesGender.learn("channel", Arrays.asList(msg.split("\\s")));
			}
			//bayesAge.learn(new Integer(user.getAlter()).toString(), Arrays.asList(user.getName().split("\\s")));
			if(user.getAlter() > 0)
			bayesAge.learn(new Integer(user.getAlter()).toString(), Arrays.asList(user.getFirstMsg().split("\\s")));
		}
	}
	
	public String classifyUserGender(User u){
		/*String resp = ""; //Response
		int i = 0, j = 0, k = 0;//i = males, j = females, k = bots
		for(String msg : u.getNachichten()){
			if(bayesGender.classify(Arrays.asList(msg.split("\\s"))).getCategory().equals("male"))
				i++;
			else if(bayesGender.classify(Arrays.asList(msg.split("\\s"))).getCategory().equals("female"))
				j++;
			else
				k++;
		}
		if(i>j && i>k)
			resp = "male";
		if(j>i && j>k)
			resp = "female";
		if(k>i && k>j)
			resp = "channel";*/
		return bayesGender.classify(Arrays.asList(u.getFirstMsg().split("\\s"))).getCategory();
	}
	
	public int classifyUserAge(User u){
		int i = 0;
		for(String msg : u.getNachichten())
			i = new Integer(bayesAge.classify(Arrays.asList(msg.split("\\s"))).getCategory()).intValue();
		return i;
	}
	
	public String retriveCategorys(){
		return new String("Age Categorys: " + bayesAge.getCategories().toString() + " Gender Categorys: " + bayesGender.getCategories().toString());
	}
	
	public void classifierTest() {
		ArrayList<User> users = new GenerateUserSet().getTrainingList();
		double geacc = 0.0, ageacc = 0.0;
		int i = 0, j = 0, p = 0;
		for(User user : users) {
			p++;
			String cG = classifyUserGender(user);
			String cA = new Integer(classifyUserAge(user)).toString();
			System.out.println("Classified Gender: " + cG + " real Gender: " + user.getGender());
			System.out.println("Classified Age: " + cA + " real Age: " + user.getAlter());
			if(user.getGender().equals(cG)){
				/*bayesGender.learn(user.getGender(), Arrays.asList(user.getName().split("\\s")));
				for(String msg : user.getNachichten()){
					if(user.getGender().equals("male"))
						bayesGender.learn("male", Arrays.asList(msg.split("\\s")));
					else if (user.getGender().equals("female"))
						bayesGender.learn("female", Arrays.asList(msg.split("\\s")));
					else if (user.getGender().equals("channel"))
						bayesGender.learn("channel", Arrays.asList(msg.split("\\s")));
				}
				//bayesAge.learn(new Integer(user.getAlter()).toString(), Arrays.asList(user.getName().split("\\s")));*/
				i++;
			}
			int k = new Integer(cA).intValue();
			if(user.getAlter() > 0 ){
				if((user.getAlter()-10) < k && (user.getAlter()+10) > k)
					j++;
			}
				
		}
		geacc = ((double)i/(double)p);
		ageacc = ((double)j/(double)p);
		System.out.println("Gender Accuracy: " + geacc + ", Hits: " + i + ", Tests: " + p);
		System.out.println("Age Accuracy: " + ageacc + ", Hits: " + j + ", Tests: " + p);
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		testClassifier();
	}
	
	public static void testClassifier(){
		Classifier<String,String> bayes = new BayesClassifier<String,String>();
		
		//Beispiele
		String[] positiveText = "I love sunny days".split("\\s");
		String[] negativeText = "I hate rain".split("\\s");
		
		//Lernen
		bayes.learn("positive", Arrays.asList(positiveText));
		bayes.learn("negative", Arrays.asList(negativeText));
		
		//Sentences to classify
		String[] unknownText1 = "today is a sunny day".split("\\s");
		String[] unknownText2 = "there will be rain".split("\\s");
		
		System.out.println(bayes.classify(Arrays.asList(unknownText1)).getCategory());
		System.out.println(bayes.classify(Arrays.asList(unknownText2)).getCategory());
		
		((BayesClassifier<String,String>) bayes).classifyDetailed(Arrays.asList(unknownText1));
		
		bayes.setMemoryCapacity(500);
	}
}
