package tools;

public class StdRandom {
	//pick integer array index between 0 and i uniformly at random
	private static double uniform(int range) {
		//Math.random() returns a double type between 0.0 and 1.0 
		return Math.random() * range;
	}
	
	private static void exch(Object[] a, int i, int j) {
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}
	
	public static void shuffle(Object[] a) {
		int n = a.length;
		
		//O(N) time
		for (int i = 0; i < n; i++) {
			//i + 1 bc if let's say you want to pick a random number between
			//0 and 5, then there are 6 elements to choose from in total and not 5. 
			//i + 1 is the range which, in the case, is 6
			int r = (int) StdRandom.uniform(i + 1);
			//System.out.println(r);
			exch(a, i, r);
		}
	}
}
