package userdefinedtypes;

public class MaxPQ {
	private Node[] pq;
	private int N; //size
	
	private boolean less(int i, int j) {
		return pq[i].getDistance() < pq[j].getDistance();
	}
	
	private void exch(int i, int j) {
		Node swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
	}
	
	private void swim (int k) {
		//as long as k is not the root and the parent node (k/2) of k is less than k
		//then this continues to run
		while (k > 0 && less(k/2, k)) {
			//promote k 
			exch(k, k/2);
			
			//give k his parent's position to continue on to the next loop
			k = k/2;
		}
	}
	
	private void sink (int k) {
		//as long as the child of k is not larger than the size of the heap 
		while (2*k < N) {
			int j = 2*k;
			
			//compare the 2 children of k
			//pick the larger one out of the 2 to promote
			//if all the conditions are met, this means that the right child is the larger one
			if (j < N-1 && less(j, j+1)) j++;
			
			//if parent (k) is not smaller than his child (2*k or 2*k+1) then heap is ordered
			if (!less(k, j)) break;
			
			//executed only us parent (k) is smaller than one of his children
			//demote k
			exch(k, j);
			
			//give k his child's position to continue on to the next loop
			k = j;
		}
	}
	
	private void resize(int capacity) {
		//make a new array
		Node[] copy = new Node[capacity];
		
		//copy all the elements in the original array into the new array
		for (int i = 0; i < N; i++) {
			copy[i] = pq[i];
		}
		
		//now you have an array whose size has been changed
		pq = copy;
	}
		
	//constructor
	public MaxPQ() {
		pq = new Node[1];
	}
	
	public boolean isEmpty() {
		return N == 0;
	}
	
	public int size() {
		return N;
	}
	
	public void insert(Node item) {
		if (N == pq.length) {
			resize(2*pq.length);
		}
		
		pq[N++] = item;
		
		//to ensure max order heap
		swim(N-1);
	}
	
	public Node delMax() {
		if (N > 0 && N == pq.length/4) {
			resize(pq.length/2);
		}
		
		//since its max order heap, the first element of the heap is max
		Node max = pq[0];
		
		//swap max with the lowest level element
		//now max is at the end of the heap
		exch(0, --N);
		
		//reorder the heap
		sink(0);
		
		//prevent loitering
		pq[N] = null;
		
		return max;
	}
	
	public Node getMax() {
		return pq[0];
	}
	
	public int getSize() {
		return N;
	}
}
