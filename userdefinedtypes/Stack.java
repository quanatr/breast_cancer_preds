package userdefinedtypes;

import tools.Quick;

public class Stack {
	private Node[] array;
	private int elementN = 0;
	
	public Stack() {
		array = new Node[1];
	}
	
	public boolean isEmpty() {
		return elementN == 0;
	}
	
	public void push(Node item) {
		if (elementN == array.length) {
			resize(2*array.length);
		}
		
		//assign item to array[elementN] then increment elementN
		//now array[elementN] = null
		array[elementN++] = item;
	}
	
	public Node pop() {
		if (elementN > 0 && elementN == array.length/4) {
			resize(array.length/2);
		}
		
		//since array[elementN] = null after push, decrement so you get the latest non null
		//element
		
		Node item = array[--elementN];
		
		//after popping, you want to remove the popped element from the array
		//since int is a primitive type and not an object, you cant assign null
		//-1 takes the place of null here
		array[elementN] = null;
		
		return item;
	}
	
	public void resize(int capacity) {
		//make a new array
		Node[] copy = new Node[capacity];
		
		//copy all the elements in the original array into the new array
		for (int i = 0; i < elementN; i++) {
			copy[i] = array[i];
		}
		
		//now you have an array whose size has been changed
		array = copy;
	}
	
	private static void exch(Node[] a, int i, int j) {
		Node swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}
	
	public Node quickSelect(int k, int dimension) {
		Node[] copy = new Node[elementN];
		
		for (int i = 0; i < elementN; i++) {
			copy[i] = array[i];
		}
		
		Node n = Quick.select(copy, k, dimension);
		
		//put the selected element at the top of the stack
		exch(array, 0, k);
		
		return n;
	}
} 

