package userdefinedtypes;

import java.util.Comparator;

//class Node (point)
public class Node {
	//constant
    public static final int K = 10; 		 //dimension (how many attributes used for diagnosis?)
	
	public static final Comparator<Node> BY_NEARNESS = new ByNearness();
	
	private int id;           //id
	private char diagnosis;   //diagnosis
	private float[] vals;     //attributes used for diagnosis
	private float distance;   //distance from some test point
	private Node left, right;
	
	private static class ByNearness implements Comparator<Node> {
		public int compare(Node v, Node w) {
			if 		(v.distance < w.distance) return -1;
			else if (v.distance > w.distance) return 1;
			else							  return 0;
		}
	}
		
	public Node() {
		this.id = -1;
		this.diagnosis = ' ';
		this.vals = new float[K];
	}	
	
	public Node(int id, char diagnosis, float[] vals) {
		this.id = id;
		this.diagnosis = diagnosis;
		this.vals = vals;
	}	
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setDiagnosis(char diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	public char getDiagnosis() {
		return this.diagnosis;
	}
	
	public void setVals(float[] vals) {
		this.vals = vals;
	}
	
	public float[] getVals() {
		return this.vals;
	}
	
	public void setVal(float val, int dimension) {
		this.vals[dimension] = val;
	}
	
	public float getVal(int i) {
		return this.vals[i];
	}
	
	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	public float getDistance() {
		return this.distance;
	}
	
	public void setLeft(Node left) {
		this.left = left;
	}
	
	public Node getLeft() {
		return this.left;
	}
	
	public void setRight(Node right) {
		this.right = right;
	}
	
	public Node getRight() {
		return this.right;
	}
}
