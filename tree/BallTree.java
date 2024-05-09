package tree;

import tools.Calculator;
import tools.Quick;
import userdefinedtypes.MaxPQ;
import userdefinedtypes.Node;

public class BallTree {
	private class BallNode {
		private Node[] points;
		private Node centroid = new Node();
		private float radius = 0;
		
		//children 
		private BallNode left;
		private BallNode right;
		
		public BallNode(Node[] points) {
			this.points = new Node[points.length];
			this.points = points;
		}
		
		public void setCentroid (Node centroid) {
			this.centroid = centroid;
		}
		
		public void setRadius (float radius) {
			this.radius = radius;
		}
	}
	
	private BallNode root = null;

	private int findGreatestSpreadDimension(Node[] points) {
		int greatest_d = 0;
		
		int num_of_dimensions = Node.K;
		int num_of_points = points.length;
		
		float max_var = 0;
		
		for (int d = 0; d < num_of_dimensions; d++) {
			float[] vals = new float[num_of_points];
			
			for (int p = 0; p < num_of_points; p++) {
				vals[p] = points[p].getVal(d);
			}
			
			float mean = Calculator.findMean(vals);
			float var = Calculator.findVar(vals, mean);
			
			if (max_var < var) {
				max_var = var;
				greatest_d = d;
			}
		}
		
		return greatest_d;
	}

	private Node findCentroid(int dimension, Node[] points) {
		int num_of_points = points.length;
		
		return Quick.select(points, num_of_points / 2, dimension);
	}
	
	private float findRadius(Node centroid, Node[] points) {
		float max_dist = 0;
		
		//find point farthest away from a given point and calculate distance
		for (int i = 0; i < points.length; i++) {
			float dist = Calculator.euclidean(points[i].getVals(), centroid.getVals());
			
			if (max_dist < dist) {
				max_dist = dist;
			}
		}
		
		return max_dist;
	}
	
	private BallNode put(BallNode x, Node[] points) {
		int num_of_points = points.length;
		
		if (num_of_points < 1) return null;
		
		if (num_of_points == 1) return new BallNode(points);
		
		int dimension = findGreatestSpreadDimension(points);
		//find the median point of that dimension
		Node centroid = findCentroid(dimension, points);
		
		//find index of centroid
		int centroid_i = 0;
 		for (int i = 0; i < num_of_points; i++)
			centroid_i = (points[i].getId() == centroid.getId() ? i : centroid_i);
 		
 		//build left data set
 		Node[] left_points = new Node[centroid_i];
 		for (int i = 0; i < left_points.length; i++)
			left_points[i] = points[i];
 		
 		//build right data set
 		Node[] right_points = new Node[num_of_points - centroid_i - 1];
 		for (int i = 0; i < right_points.length; i++)
			right_points[i] = points[i + centroid_i + 1];
 		
		//create parent ball node
 		x = new BallNode(points);
 		
 		//set centroid of ball node
 		x.setCentroid(centroid);
 		
 		//set radius of ball node
 		float radius = findRadius(centroid, points);
 		x.setRadius(radius);
 		
 		//set left and right ball nodes
 		x.left = put(x.left, left_points);
 		x.right = put(x.right, right_points);
 		
 		return x;
	}
	
	private MaxPQ classify(int t_id, float[] t_vals, int num_of_neighbors, MaxPQ Q, BallNode x) {
		//balls that are too far away so ignore
		if (Calculator.euclidean(t_vals, x.centroid.getVals()) - x.radius >= Calculator.euclidean(t_vals, Q.getMax().getVals()))
			return Q;
		
		//leaf ball
		else if (x.left == null || x.right == null) {
			//go through every point in the current ball
			for (int i = 0; i < x.points.length; i++) {
				if(Calculator.euclidean(t_vals, x.points[i].getVals()) < Calculator.euclidean(t_vals, Q.getMax().getVals())) {
					Q.insert(x.points[i]);
					x.points[i].setDistance(Calculator.euclidean(t_vals, x.points[i].getVals()));
					
					//if the pq is full then remove max
					if (Q.getSize() > num_of_neighbors) Q.delMax();
				}
			}
		}
		
		//internal ball
		else {
			if (x.left == null && x.right == null) 
				return Q;
			
			if (x.left == null) 
				Q = classify(t_id, t_vals, num_of_neighbors, Q, x.right);
			else
				Q = classify(t_id, t_vals, num_of_neighbors, Q, x.left);
			
			if (x.right == null) 
				Q = classify(t_id, t_vals, num_of_neighbors, Q, x.left);
			else
				Q = classify(t_id, t_vals, num_of_neighbors, Q, x.right);
		}
		
		return Q;
	}
	
	/**********************************************************************************/
	
	public BallTree(Node[] points) {
		put(points);
	}
	
	public void put(Node[] points) {
		root = put(root, points);
	}
	
	public char classify(int t_id, float[] t_vals, int num_of_neighbors) {
		MaxPQ neighbors = new MaxPQ();
		
		//insert smth in pq and let it be max
		neighbors.insert(root.points[0]);
		
		neighbors = classify(t_id, t_vals, num_of_neighbors, neighbors, root);
		
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
