package graph;

public class Edge implements Comparable<Edge> {

	private final int v;
	private final int w;
	private final double weigth;
	
	public Edge(int v, int w, double weigth) {
		this.v = v;
		this.weigth = weigth;
		this.w = w;
	}
	
	public int either() {
		return this.v;
		
	}
	
	public int other(int vertex) {
		return vertex == this.v ? this.w : this.v;
		
	}
	
	@Override
	public int compareTo(Edge that) {
		
		if (this.weigth < that.weigth) return -1;
		if (this.weigth > that.weigth) return +1;
		return 0;
		
	}
	
	public double weigth() {
		return this.weigth;
	}
	
	@Override
	public String toString() {
		return this.v + " --" + this.weigth + "-- " + this.w;
	}

}
