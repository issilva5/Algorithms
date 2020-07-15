package graph;

public class BellmanFordSP extends SP {
	
	// Negative weigth without cycles
	
	public BellmanFordSP(EdgeWeigthedDigraph G, int s) {
		
		super(s, G.V());
		
		for (int i = 0; i < G.V(); i++)
			for (int v = 0; v < G.V(); v++)
				for (DirectedEdge e : G.adj(v))
					super.relax(e);
		
		
	}

}
