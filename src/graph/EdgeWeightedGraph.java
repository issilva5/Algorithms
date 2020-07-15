package graph;

import java.util.Set;
import java.util.TreeSet;

import containers.Bag;

public class EdgeWeightedGraph{

	private final int V;
	private final Bag<Edge>[] ladj;
	private int E;
	
	@SuppressWarnings("unchecked")
	public EdgeWeightedGraph(int V) {
		this.E = 0;
		this.V = V;
		this.ladj = (Bag<Edge>[]) new Object[this.V];
		
		for (int i = 0; i < this.V; i++)
			this.ladj[i] = new Bag<>();
	}
	
	public void addEdge(Edge e) {
		
		int v = e.either(), w = e.other(v);
		this.ladj[v].add(e);
		this.ladj[w].add(e);
		
		this.E++;
		
	}
	
	public void addEdge(int v, int w, double weigth) {
		
		Edge e = new Edge(v, w, weigth);
		this.ladj[v].add(e);
		this.ladj[w].add(e);
		
		this.E++;
		
	}

	public Iterable<Edge> adj(int v) {
		return this.ladj[v];
	}
	
	public Iterable<Edge> edges() {
		Set<Edge> set = new TreeSet<>();
		
		for (int i = 0; i < this.V; i++)
			for (Edge e : this.adj(i))
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
