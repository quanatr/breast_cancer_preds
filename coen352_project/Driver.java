**Project Main File*/

package coen352_project;

import java.util.*;

import tools.DataPreprocessor;
import tools.Stopwatch;
import tree.BallTree;
import tree.KDTree;
import userdefinedtypes.Node;

public class Driver {
	public static void main(String[] args) {
		//total entries
		final int M = 569;  
		
		//entries used for training
		int N = 450;        
		
		//entries used for testing
		int T = N/4;        
		
		//how many nearest neighbors
		int k = 7;          
	
		//process file, shuffle the entries and store info in list
		Node[] list = new Node[M];
		list = DataPreprocessor.dataProcess("C:\\Users\\Mr Quan\\Downloads\\Concordia Classes\\SUMMER 2023\\COEN352\\coen352_project\\data.csv", M);
		
		
		/***********************************************************************************************************/
		//build a kd tree with train data
		KDTree kd = new KDTree(Arrays.copyOfRange(list, 0, N));

		//test
		char[] preds = new char[T];
		
		Stopwatch stopwatch = new Stopwatch();
		for (int i = N; i < N+T; i++) {
			char pred = kd.classify(list[i].getId(), list[i].getVals(), k);
			if (pred == list[i].getDiagnosis()) preds[i-N] = 'C';
			else								preds[i-N] = 'I';	
		}
		double time_elapsed = stopwatch.elapsedTime();
		
		System.out.println("Kd tree");
		displayResults(preds, time_elapsed);
		
		
		/***********************************************************************************************************/
		//build a ball tree with train data
		BallTree bt = new BallTree(Arrays.copyOfRange(list, 0, N));
		
		//test
		char[] preds2 = new char[T];
		
		Stopwatch stopwatch2 = new Stopwatch();
		for (int i = N; i < N+T; i++) {
			char pred2 = bt.classify(list[i].getId(), list[i].getVals(), k);
			if (pred2 == list[i].getDiagnosis()) preds2[i-N] = 'C';
			else								preds2[i-N] = 'I';	
		}
		double time_elapsed2 = stopwatch2.elapsedTime();
		
		System.out.println("Ball tree");
		displayResults(preds2, time_elapsed2);
	}
	
	public static void displayResults(char[] preds, double time_elapsed) {
		int freq_correct = 0;
		int freq_incorrect = 0;
		
		for (int i = 0; i < preds.length; i++) {
			if (preds[i] == 'C') freq_correct++;
			else 			     freq_incorrect++;
		}
		
		double accuracy = (double) freq_correct/(freq_correct + freq_incorrect)*100;
		
		System.out.println("Correct predictions: " + freq_correct + " times");
		System.out.println("Incorrect predictions: " + freq_incorrect + " times");
		System.out.println("Accuracy: " + (float) accuracy + "%");
		System.out.println("Runtime: " + time_elapsed + " ms\n");
	}
}


