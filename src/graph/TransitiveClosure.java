package graph;

public class TransitiveClosure {
	
	private DFS[] all;

	TransitiveClosure(Digraph G) {
		
		this.all = new DFS[G.V()];
		for (int v = 0; v < G.V(); v++)
			this.all[v] = new DFS(G, v);
		
	}

	boolean reachable(int v, int w) {
		
		return this.all[v].hasPathTo(w);
		
	}
	
}
