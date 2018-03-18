package DataStuff;

import java.util.Random;
import java.util.StringTokenizer;

import opennlp.tools.tokenize.SimpleTokenizer;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.tokenizers.Tokenizer;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class GenerateClassifierGender {
	
	public GenerateClassifierGender() throws Exception {
		DataSource source = new DataSource("/Users/Lukas/Desktop/training_set.arff");
		Instances trainset = source.getDataSet();
		DataSource source2 = new DataSource("/Users/Lukas/Desktop/training_set.arff");
		Instances testset = source.getDataSet();
		
		
		
		// filter
		StringToWordVector stw = new StringToWordVector();
		stw.setAttributeIndices("first");
		stw.setOutputWordCounts(false);
		stw.setTokenizer(new WordTokenizer());
		 stw.setAttributeIndices("1");
		 stw.setAttributeIndices("2");
		 stw.setAttributeIndices("3");
		 // classifier
		 J48 j48 = new J48();
		 j48.setUnpruned(true);        // using an unpruned J48
		 // meta-classifier
		 FilteredClassifier fc = new FilteredClassifier();
		 fc.setFilter(stw);
		 fc.setClassifier(j48);
		 // train and make predictions
		 fc.buildClassifier(trainset);
		 for (int i = 0; i < testset.numInstances(); i++) {
		   double pred = fc.classifyInstance(testset.instance(i));
		   System.out.print("ID: " + testset.instance(i).value(0));
		   System.out.print(", actual: " + testset.classAttribute().value((int) testset.instance(i).classValue()));
		   System.out.println(", predicted: " + testset.classAttribute().value((int) pred));
		 }
		
	}
	
	/*public void buildClassifier() throws Exception {
		DataSource source = new DataSource("/Users/Lukas/Desktop/training_set.arff");
		Instances dataset = source.getDataSet();
		
		String[] options = new String[1];
		options[0] = "-U";            // unpruned tree
		J48 tree = new J48();         // new instance of tree
		tree.setOptions(options);     // set the options
		tree.buildClassifier(dataset);   // build classifier
		
		Evaluation eval = new Evaluation(dataset);
		eval.crossValidateModel(tree, dataset, 10, new Random(1));
		
	}*/
}
