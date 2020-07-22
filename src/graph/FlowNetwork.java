package graph;

import containers.Bag;

public class FlowNetwork {
	
	private final int V;
	private Bag<FlowEdge>[] ladj;
	private int E;
	
	@SuppressWarnings("unchecked")
	public FlowNetwork(int V) {
		
		this.V = V;
		this.ladj = (Bag<FlowEdge>[]) new Object[V];
		for (int i = 0; i < V; i++)
			this.ladj[i] = new Bag<>();
		
	}
	
	public void addEdge(FlowEdge e) {
		
		int v = e.from();
		int w = e.to();
		this.ladj[v].add(e);
		this.ladj[w].add(e);
		this.E++;
		
	}
	
	public Iterable<FlowEdge> adj(int vertex) {
		return this.ladj[vertex];
	}
	
	public Iterable<FlowEdge> edges() {
		return null;
	}
	
	public int V() {
		return V;
	}
	
	public int E() {
		return E;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

}
