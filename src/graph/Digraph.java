package graph;

import containers.Bag;

public class Digraph implements Graph {

	private final int V;
	private Bag<Integer>[] ladj;
	private int E;
	
	@SuppressWarnings("unchecked")
	public Digraph(int V) {
		
		this.V = V;
		this.ladj = (Bag<Integer>[]) new Bag[this.V];
		for (int i = 0; i < this.V; i++)
			this.ladj[i] = new Bag<>();
		
	}
	
	@Override
	public void addEdge(int v, int w) {
		
		this.ladj[v].add(w);
		this.E++;
		
	}

	@Override
	public Iterable<Integer> adj(int v) {
		return this.ladj[v];
	}

	@Override
	public int V() {
		return this.V;
	}

	@Override
	public int E() {
		return this.E;
	}
	
	public Digraph reverse() {
		
		Digraph r = new Digraph(this.V);
		for (int v = 0; v < this.V; v++)
			for (int w : this.adj(v))
				r.addEdge(w, v);
		
		return r;
	}

}
