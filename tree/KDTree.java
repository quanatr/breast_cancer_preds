package tree;

import tools.*;
import userdefinedtypes.MaxPQ;
import userdefinedtypes.Node;
import userdefinedtypes.Stack;

public class KDTree {
	//root of BST
	private Node root = null;
	
	private Node put(Node x, int id, char diagnosis, float[] vals, int depth) {
		//if no node is found, make a new node
		if (x == null) {
			return new Node(id, diagnosis, vals); 
		}
		
		//dimension goes from 0 - 9 then repeats
		int current_d = depth % Node.K;
		
		//under the current dimension, if
		//search val is smaller than or equal to current val, put search node to the left of current node
		if (vals[current_d] <= x.getVal(current_d)) 
			x.setLeft(put(x.getLeft(), id, diagnosis, vals, depth+1));
		//search val is larger than current val, put search node to the right of current node
		else if (vals[current_d] > x.getVal(current_d)) 
			x.setRight(put(x.getRight(), id, diagnosis, vals, depth+1));
	
		return x;
	}
	
	/************************************************************/
	
	public KDTree(Node[] list) {
		int num_of_points = list.length;
		
		Stack stack = new Stack();
		
		//build stack
		for (int i = 0; i < num_of_points; i++) {
			stack.push(list[i]);
		}
		
		//build tree while trying to balance it
		for (int i = 0; i < num_of_points; i++) {
			//dimension goes from 0 - 9 then repeats
			int current_d = i % Node.K;
			
			stack.quickSelect((num_of_points - i) / 2, current_d);
			
			Node n = stack.pop();
			
			put(n.getId(), n.getDiagnosis(), n.getVals());
		}
	}
	
	public void put(int id, char diagnosis, float[] vals) {
		root = put(root, id, diagnosis, vals, 0);
	}
	
	public char classify(int id, float[] vals, int num_of_neighbors) {
		Node x = root;
		
		MaxPQ neighbors = new MaxPQ();
		neighbors.insert(x);
		
		int depth = 0;
		while (x != null) {
			//dimension goes from 0 - 9 then repeats
			int current_d = depth % Node.K;
			
			//find the distance between given node and current node as you go along
			float distance = Calculator.euclidean(vals, x.getVals());
			x.setDistance(distance);
			
			depth++;
			
			if (x.getDistance() < neighbors.getMax().getDistance()) {	
				neighbors.insert(x);
				
				//if the pq is full then remove max
				if (neighbors.getSize() > num_of_neighbors) {
					neighbors.delMax();
				}
			}
			
			if 		(vals[current_d] < x.getVal(current_d)) x = x.getLeft();
			else if (vals[current_d] > x.getVal(current_d)) x = x.getRight();
			else {
				if (x.getLeft() == null) x = x.getRight();
				else					 x = x.getLeft();
			}
		}
		
		//compare the number of Ms to the number of Bs near test point
		int freq_M = 0;
		int freq_B = 0;
		
		int nloops = (neighbors.size() >= num_of_neighbors ? num_of_neighbors : neighbors.size());
		
		for (int i = 0; i < nloops; i++) {
			Node current_neighbor = neighbors.delMax();
			
			if 		(current_neighbor.getDiagnosis() == 'M') freq_M++;
			else if (current_neighbor.getDiagnosis() == 'B') freq_B++;
		}
		
		return (freq_M > freq_B ? 'M' : 'B');
	}
}
