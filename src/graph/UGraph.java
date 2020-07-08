package graph;

import containers.Bag;

public class UGraph implements Graph {

	private final int V;
	private Bag<Integer>[] ladj;
	private int E;

	@SuppressWarnings("unchecked")
	public UGraph(int V) {

		this.V = V;
		this.ladj = (Bag<Integer>[]) new Bag[this.V];
		for (int i = 0; i < this.V; i++)
			this.ladj[i] = new Bag<>();

	}

	public void addEdge(int v, int w) {

		this.ladj[v].add(w);
		this.ladj[w].add(v);
		this.E++;

	}

	public Iterable<Integer> adj(int v) {
		return this.ladj[v];
	}

	public int V() {
		return this.V;
	}

	public int E() {
		return this.E;
	}

	public String toString() {

		String g = "";
		g += "V: " + this.V + " E: " + this.E + System.lineSeparator();
		for (int i = 0; i < this.V; i++)
			for (int w : this.ladj[i])
				g += i + " - " + w + System.lineSeparator();

		return g;

	}

	@SuppressWarnings("unused")
	public static int degree(UGraph G, int v) {
		int degree = 0;
		for (int w : G.adj(v))
			degree++;
		return degree;
	}

	public static int maxDegree(UGraph G) {
		int max = 0;
		for (int v = 0; v < G.V(); v++)
			if (degree(G, v) > max)
				max = degree(G, v);
		return max;
	}

	public static double averageDegree(UGraph G) {
		return 2.0 * G.E() / G.V();
	}

	public static int numberOfSelfLoops(UGraph G) {
		int count = 0;
		for (int v = 0; v < G.V(); v++)
			for (int w : G.adj(v))
				if (v == w)
					count++;
		return count / 2; // each edge counted twice
	}

}
