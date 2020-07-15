package graph;

import containers.Bag;
import containers.queue.priority.MinPQ;
import unionfind.UF;
import unionfind.WeigthedPathCompressionQU;

public class Kruskall implements MST {
	
	private Bag<Edge> mst = new Bag<>();
	private double weigth = 0;
	
	public Kruskall(EdgeWeightedGraph G) {
		
		MinPQ<Edge> pq = new MinPQ<Edge>(G.E());
		for (Edge e : G.edges())
			pq.insert(e);
		
		UF uf = new WeigthedPathCompressionQU(G.V());
		while (!pq.isEmpty() && mst.size() < G.V() - 1) {
			
			Edge e = pq.deleteMin();
			int v = e.either(), w = e.other(v);
			if (!uf.connected(v, w)) {
				mst.add(e);
				uf.union(v, w);
				weigth += e.weigth();
			}
			
		}
		
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
