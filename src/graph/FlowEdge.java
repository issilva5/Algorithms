package graph;

public class FlowEdge {
	
	private final int v;
	private final int w;
	private final double capacity;
	private double flow;
	
	public FlowEdge(int v, int w, double capacity) {
		this.capacity = capacity;
		this.w = w;
		this.v = v;
		this.flow = 0;
	}
	
	public int from() {
		return v;
	}
	
	public int to() {
		return w;
	}
	
	public int other(int x) {
		return x == v ? w : v;
	}
	
	public double capacity() {
		return capacity;
	}
	
	public double flow() {
		return flow;
	}
	
	public double residualCapacity(int vertex) {
		
		if(vertex == w)
			return this.capacity - this.flow;
		else if (vertex == v)
			return this.flow;
		else
			throw new IllegalArgumentException();
		
	}
	
	public void addResidualFlowTo(int vertex, double delta) {
		
		if (vertex == w)
			this.flow += delta;
		else if (vertex == v)
			this.flow -= delta;
		else
			throw new IllegalArgumentException();
		
	}
	
	@Override
	public String toString() {
		
		String e = "";
		return e + v + " --" + flow + "/" + capacity + " --> " + w;
		
	}

}
