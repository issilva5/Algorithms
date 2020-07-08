package graph;

public class CycleFinder {

	public boolean[] marked;
	private boolean hasCycle;

	public CycleFinder(UGraph G) {

		this.marked = new boolean[G.V()];
		this.hasCycle = false;

		for (int v = 0; v < G.V(); v++)
			if (!this.marked[v])
				this.dfs(G, v, v);

	}

	private void dfs(UGraph g, int v, int u) {

		this.marked[v] = true;
		for (int w : g.adj(v))
			if (!this.marked[w])
				this.dfs(g, w, v);
			else if (w != u) {
				this.hasCycle = true;
				return;
			}

	}

	public boolean hasCycle() {
		return this.hasCycle;
	}

}
