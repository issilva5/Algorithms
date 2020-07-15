package graph;

import java.util.Set;
import java.util.TreeSet;

import containers.Bag;

public class EdgeWeigthedDigraph {
	
	private final int V;
	private final Bag<DirectedEdge>[] ladj;
	private int E;
	
	@SuppressWarnings("unchecked")
	public EdgeWeigthedDigraph(int V) {
		this.E = 0;
		this.V = V;
		this.ladj = (Bag<DirectedEdge>[]) new Object[this.V];
		
		for (int i = 0; i < this.V; i++)
			this.ladj[i] = new Bag<>();
	}
	
	public void addEdge(DirectedEdge e) {
		
		this.ladj[e.from()].add(e);
		
		this.E++;
		
	}
	
	public void addEdge(int v, int w, double weigth) {
		
		DirectedEdge e = new DirectedEdge(v, w, weigth);
		this.ladj[v].add(e);
		
		this.E++;
		
	}

	public Iterable<DirectedEdge> adj(int v) {
		return this.ladj[v];
	}
	
	public Iterable<DirectedEdge> edges() {
		Set<DirectedEdge> set = new TreeSet<>();
		
		for (int i = 0; i < this.V; i++)
			for (DirectedEdge e : this.adj(i))
				set.add(e);
		
		return set;
	}

	public int V() {
		return this.V;
	}

	public int E() {
		return this.E;
	}

}
