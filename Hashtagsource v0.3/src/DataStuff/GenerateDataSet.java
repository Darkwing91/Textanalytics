package DataStuff;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import MainStuff.GenerateUserSet;
import MainStuff.User;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ArffSaver;

public class GenerateDataSet {
	
	public GenerateDataSet() throws ParseException {
		
	}
	
	/**
	 * Generates an dataset with Attributes of the class User and fills it with the Data provided by the Arraylist
	 * @param users
	 */
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public Instances GetData(ArrayList<User> users, String relname) {
		//Create Variables
		FastVector<Attribute>	atts;
		Instances				data;
		double[]				vals;
				
		//Setup Attributes
		atts = new FastVector();
		atts.addElement(new Attribute("age"));
		atts.addElement(new Attribute("name", (FastVector) null));
		atts.addElement(new Attribute("sentence", (FastVector) null));
		atts.addElement(new Attribute("gender", (FastVector) null));
			 
		// 2. create Instances object
		data = new Instances("" + relname, atts, 0);
			 
		// 3. fill with data
		for(User user : users) {
				for(String msg : user.getNachichten()) {
					vals = new double[data.numAttributes()];
					//Age
					if(user.getAlter() != 0)
						vals[0] = user.getAlter();
					else
						vals[0] = Utils.missingValue();
					//Name
					vals[1] = data.attribute(1).addStringValue(user.getName());
					//Sentence
					vals[2] = data.attribute(2).addStringValue(msg);
					//Gender
					if(user.getGender() != null)
						vals[3] = data.attribute(3).addStringValue(user.getGender());
					else
						vals[3] = Utils.missingValue();
					//add
					data.add(new DenseInstance(1.0, vals));
				}
		}
			 
		// 4. output data
		System.out.println(data);
		
		return data;
	}
	
	public static void main(String args[]) throws Exception{
		ArrayList<User> users = new ArrayList<User>();
		GenerateUserSet ts = new GenerateUserSet();
		users.addAll(ts.getTrainingList());
		TestSave(new GenerateDataSet().GetData(users, "training_data"), "training_set");
	}
	
	public static void TestSave(Instances dataset, String filename) throws IOException {
		ArffSaver saver = new ArffSaver();
		saver.setInstances(dataset);
		saver.setFile(new File("/Users/Lukas/Desktop/" + filename + ".arff"));
		saver.writeBatch();
	}
}
