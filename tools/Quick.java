package tools;

import userdefinedtypes.Node;

public class Quick {
	private static int partition(Node[] a, int lo, int hi, int dimension) {
		int i = lo; int j = hi + 1;
		
		while (true) {
			while (less(a[++i], a[lo], dimension))
				if (i == hi) break;

			while (less(a[lo], a[--j], dimension))
				if (j == lo) break;
			
			//check if pointers cross
			if (i >= j) break;
			
			//swap a[i] and a[j]
			//at this point a[j] < a[lo] < a[i]
			exch(a, i, j);
		}
		
		//swapping with the pivot element, by doing this, pivot is now at j position, and a[j] is at old pivot's position
		//you swap with j because a[j] is smaller than pivot so it has to be on the left of pivot 
		exch(a, lo, j);
		
		//return index of item that is in the right place
		return j;
	}
	
	private static boolean less(Node v, Node w, int dimension) {
		//System.out.println("hello");
		return v.getVal(dimension) < w.getVal(dimension);
	}
	
	private static void exch(Node[] a, int i, int j) {
		Node swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}
	
	//k is which pos in the array
	public static Node select(Node[] a, int k, int dimension) {
		StdRandom.shuffle(a);
		
		int lo = 0, hi = a.length - 1;
		
		while (hi > lo) {
			int j = partition(a, lo, hi, dimension);
			
			if 		(j < k) lo = j + 1;
			else if (j > k) hi = j - 1;
			else			break;
		}
		
		//now the point that were looking for is at its right position
		return a[k];
	}
}
