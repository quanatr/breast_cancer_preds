package tools;

import userdefinedtypes.Node;

public class Calculator {
	public static float euclidean(float[] a, float[] b) {
		float total = 0;
		
		for(int i = 0; i < Node.K; i++) {
			total += (a[i] - b[i])*(a[i] - b[i]);
		}
		
		return (float) Math.sqrt(total);
	}
	
	public static float[] makeVector(float[] a, float[] b) {
		int num_of_dimensions = Node.K;
		float[] vector = new float[num_of_dimensions];
		
		for (int d = 0; d < num_of_dimensions; d++) 
			vector[d] = (b[d] - a[d]);
		
		return vector;
	}
	
	public static float dotProduct(float[] a, float[] b) {
		int num_of_dimensions = Node.K;
		float total = 0;
		
		for (int d = 0; d < num_of_dimensions; d++) 
			total += (a[d] * b[d]);
		
		return total;
	}
	
	public static float vectorMag(float[] a) {
		int num_of_dimensions = Node.K;
		float total = 0;
		
		for (int d = 0; d < num_of_dimensions; d++) 
			total += (a[d])*(a[d]);
		
		return (float) Math.sqrt(total);
	}
	
	//projection's component of b on a
	public static float component(float[] a, float[] b) {
		float nominator = dotProduct(a, b);
		float denominator = vectorMag(a);
		
		return Math.abs(nominator / denominator);
	}
	
	public static float findMean(float[] values) {
		int n = values.length;
		float total = 0;
				
		for (int i = 0; i < n; i++)
			total += values[i];
		
		return total / n;
	}
	
	public static float findVar(float[] values, float mean) {
		float nominator = 0;
		float denominator = values.length;
		
		for (int i = 0; i < values.length; i++) {
			nominator += ((values[i] - mean)*(values[i] - mean));
		}
		
		return (nominator/denominator);
	}
}
