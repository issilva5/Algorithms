package graph.applications;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

	private final Digraph G;

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		
		if (G == null)
			throw new IllegalArgumentException();

		this.G = new Digraph(G);

	}

	private int[] lca(int v, int w) {

		Queue<Integer> vi = new Queue<>();
		Queue<Integer> wi = new Queue<>();
		
		vi.enqueue(v);
		wi.enqueue(w);

		return this.lca(vi, wi);

	}

	private int[] lca(Iterable<Integer> v, Iterable<Integer> w) {
		
		this.validate(v, w);

		boolean[] markedV = new boolean[this.G.V()];
		boolean[] markedW = new boolean[this.G.V()];
		int[] distToW = new int[this.G.V()];
		int[] distToV = new int[this.G.V()];

		Queue<Integer> bfsV = new Queue<>();
		Queue<Integer> bfsW = new Queue<>();

		for (int s : v) {
			markedV[s] = true;
			distToV[s] = 0;
			bfsV.enqueue(s);
		}

		for (int s : w) {
			markedW[s] = true;
			distToW[s] = 0;
			bfsW.enqueue(s);
		}

		while (!bfsV.isEmpty()) {

			int p = bfsV.dequeue();
			for (int u : this.G.adj(p)) {

				if (!markedV[u]) {
										
					bfsV.enqueue(u);
					distToV[u] = distToV[p] + 1;
					markedV[u] = true;
				}

			}

		}
		
		while (!bfsW.isEmpty()) {

			int p = bfsW.dequeue();
			for (int u : this.G.adj(p)) {

				if (!markedW[u]) {
					
					bfsW.enqueue(u);
					distToW[u] = distToW[p] + 1;
					markedW[u] = true;
				}

			}

		}

		int shortest = -1;
		int length = Integer.MAX_VALUE;

		for (int i = 0; i < this.G.V(); i++) {
			
			if (markedV[i] && markedW[i]) {

				int l = distToV[i] + distToW[i];

				if (l < length) {

					shortest = i;
					length = l;

				}

			}

		}

		if (shortest == -1)
			length = -1;
		
		return new int[] { shortest, length };

	}

	private void validate(Iterable<Integer> v, Iterable<Integer> w) {
		
		if (v == null || w == null)
			throw new IllegalArgumentException();
		
		for (Integer i : v) {
			
			if (i == null)
				throw new IllegalArgumentException();
			
			if (i < 0 || i >= this.G.V())
				throw new IllegalArgumentException();
			
		}
			
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		return this.lca(v, w)[1];
	}

	// a common ancestor of v and w that participates in a shortest ancestral path;
	// -1 if no such path
	public int ancestor(int v, int w) {
		return this.lca(v, w)[0];
	}

	// length of shortest ancestral path between any vertex in v and any vertex in
	// w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		return this.lca(v, w)[1];
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no such
	// path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		return this.lca(v, w)[0];
	}

	// do unit testing of this class
	public static void main(String[] args) {

		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		
		Queue<Integer> vi = new Queue<>();
		Queue<Integer> wi = new Queue<>();
		
		vi.enqueue(13);
		vi.enqueue(23);
		vi.enqueue(24);
		
		wi.enqueue(6);
		wi.enqueue(16);
		wi.enqueue(17);
		
		int lengthi = sap.length(vi, wi);
		int ancestori = sap.ancestor(vi, wi);
		StdOut.printf("length = %d, ancestor = %d\n", lengthi, ancestori);
		
		while (!StdIn.isEmpty()) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			int length = sap.length(v, w);
			int ancestor = sap.ancestor(v, w);
			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		}

	}

}
