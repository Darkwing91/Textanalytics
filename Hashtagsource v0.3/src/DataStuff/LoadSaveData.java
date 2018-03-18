package DataStuff;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class LoadSaveData {
	public static void main(String args[]) throws Exception{
		DataSource source = new DataSource("/Users/Lukas/Documents/test.arff");
		Instances dataset = source.getDataSet();
		//Instances dataset = new Instances(new BufferedReader(new FileReader("/Users/Lukas/Documents/test.arff")));
		
		System.out.println(dataset.toSummaryString());
		
		ArffSaver saver = new ArffSaver();
		saver.setInstances(dataset);
		saver.setFile(new File("/Users/Lukas/Desktop/new.arff"));
		saver.writeBatch();
	}
}
