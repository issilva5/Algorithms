package graph;

import containers.stack.Stack;
import containers.stack.StackArray;

public abstract class SP {
	
	protected double[] distTo;
	protected DirectedEdge[] edgeTo;
	
	public SP(int s, int V) {
		
		this.edgeTo = new DirectedEdge[V];
		this.distTo = new double[V];
		
		for (int i = 0; i < V; i++)
			this.distTo[i] = Double.POSITIVE_INFINITY;
		
		this.distTo[s] = 0;
		
	}
	
	public double distTo(int v) {
		return this.distTo[v];
	}
	
	public Iterable<DirectedEdge> pathTo(int v) {
		
		Stack<DirectedEdge> path = new StackArray<>();
		
		for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
			path.push(e);
		
		return path;
		
	}
	
	public boolean hasPathTo(int v) {
		
		return Double.isFinite(distTo[v]);
		
	}
	
	protected void relax(DirectedEdge e) {
		
		int from = e.from(), to = e.to();
		if (distTo[to] > distTo[from] + e.weigth()) {
			
			distTo[to] = distTo[from] + e.weigth();
			edgeTo[to] = e;
			
		}
		
	}

}
