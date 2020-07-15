package graph;

import containers.stack.Stack;
import containers.stack.StackArray;

public class NegativeCycle extends SP {
	
	private int V;
	private boolean negativeC;
	private int negativeV;
	
	public NegativeCycle(EdgeWeigthedDigraph G, int s) {
		
		super(s, G.V());
		this.V = G.V();
		this.negativeC = false;
		this.negativeV = -1;
		
		for (int i = 0; i < G.V(); i++)
			for (int v = 0; v < G.V(); v++)
				for (DirectedEdge e : G.adj(v))
					this.relax(e, i);
		
	}
	
	public boolean hasNegativeCycle() {
		return this.negativeC;
	}
	
	public Iterable<DirectedEdge> negativeCycle() {
		
		Stack<DirectedEdge> path = new StackArray<>();
		
		for (DirectedEdge e = edgeTo[this.negativeV]; e.from() != this.negativeV; e = edgeTo[e.from()])
			path.push(e);
		
		return path;
		
	}
	
	private void relax(DirectedEdge e, int round) {
		
		int from = e.from(), to = e.to();
		if (distTo[to] > distTo[from] + e.weigth()) {
			
			distTo[to] = distTo[from] + e.weigth();
			edgeTo[to] = e;
			
			if (round == V) {
				this.negativeC = true;
				this.negativeV = from;
			}
			
		}
		
	}

}
