package graph;

public class DirectedEdge implements Comparable<DirectedEdge> {
	
	private final int from;
	private final int to;
	private final double weigth;
	
	public DirectedEdge(int v, int w, double weigth) {
		this.from = v;
		this.weigth = weigth;
		this.to = w;
	}
	
	public int from() {
		return this.from;
		
	}
	
	public int to() {
		return this.to;
		
	}
	
	@Override
	public int compareTo(DirectedEdge that) {
		
		if (this.weigth < that.weigth) return -1;
		if (this.weigth > that.weigth) return +1;
		return 0;
		
	}
	
	public double weigth() {
		return this.weigth;
	}
	
	@Override
	public String toString() {
		return this.from + " --" + this.weigth + "--> " + this.to;
	}

}
