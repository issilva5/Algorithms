package graph;

import containers.Bag;
import containers.queue.priority.MinPQ;

public class LazyPrim implements MST {
	
	private boolean[] marked;
	private Bag<Edge> mst;
	private MinPQ<Edge> pq;
	private double weigth = 0;
	
	public LazyPrim(EdgeWeightedGraph G) {
		
		marked = new boolean[G.V()];
		mst = new Bag<>();
		pq = new MinPQ<Edge>(G.E());
		
		visit(G, 0);
		
		while (!pq.isEmpty()) {
			
			Edge e = pq.deleteMin();
			int v = e.either(), w = e.other(v);
			if (marked[v] && marked[w]) continue;
			mst.add(e);
			weigth += e.weigth();
			if (!marked[v]) visit(G, v);
			if (!marked[w]) visit(G, w);
			
		}
		
	}

	private void visit(EdgeWeightedGraph G, int v) {
		
		marked[v] = true;
		for (Edge e : G.adj(v))
			if (!marked[e.other(v)])
				pq.insert(e);
		
	}

	@Override
	public Iterable<Edge> edges() {
		return this.mst;
	}

	@Override
	public double weigth() {
		return this.weigth;
	}

}
