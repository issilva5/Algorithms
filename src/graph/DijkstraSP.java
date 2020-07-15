package graph;

import edu.princeton.cs.algs4.IndexMinPQ;

/**
 * Always get the closest vertex.
 * @author itallo
 *
 */
public class DijkstraSP extends SP {
	
	private IndexMinPQ<Double> pq;
	
	public DijkstraSP(EdgeWeigthedDigraph G, int s) {
		
		super(s, G.V());
		
		this.pq = new IndexMinPQ<Double>(G.V());
		
		pq.insert(s, distTo[s]);
		while (!pq.isEmpty()) {
			
			int v = pq.delMin();
			for (DirectedEdge e : G.adj(v))
				this.relax(e);
			
		}
		
	}
	
	@Override
	protected void relax(DirectedEdge e) {
		
		int from = e.from(), to = e.to();
		if (distTo[to] > distTo[from] + e.weigth()) {
			
			distTo[to] = distTo[from] + e.weigth();
			edgeTo[to] = e;
			
			if (pq.contains(to)) pq.decreaseKey(to, distTo[to]);
			else pq.insert(to, distTo[to]);
			
		}
		
	}

}
