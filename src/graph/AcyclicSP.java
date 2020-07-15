package graph;

public class AcyclicSP extends SP {
	
	//By negating the weight of the edges, this algorithm gives the longest path
	
	public AcyclicSP(EdgeWeigthedDigraph G, int s) {
		
		super(s, G.V());
		
		DeepFirstOrder topological = new DeepFirstOrder(G);
		for (int v : topological.reversePost())
			for (DirectedEdge e : G.adj(v))
				super.relax(e);
		
	}

}
