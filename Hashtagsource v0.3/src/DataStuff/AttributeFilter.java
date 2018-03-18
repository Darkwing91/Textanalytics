package DataStuff;

import java.io.File;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class AttributeFilter {
	public static void main(String args[]) throws Exception{
		DataSource source = new DataSource("/Users/Lukas/Documents/test.arff");
		Instances dataset = source.getDataSet();
		
		String[] opts = new String[]{ "-R", "2"};
		Remove remove = new Remove();
		remove.setOptions(opts);
		remove.setInputFormat(dataset);
		Instances newData = Filter.useFilter(dataset,  remove);
		
		System.out.println(newData.toSummaryString());
		
		ArffSaver saver = new ArffSaver();
		saver.setInstances(newData);
		saver.setFile(new File("/Users/Lukas/Desktop/new.arff"));
		saver.writeBatch();
	}
}
