package tools;

public class Stopwatch {
	private final long start;
	
	public Stopwatch() { 
		start = System.currentTimeMillis(); 
	}
	
	//result given in millisecs
	public double elapsedTime() {	
		long now = System.currentTimeMillis();
		return (now - start);
	}
}
