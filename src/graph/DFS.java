package graph;

import containers.stack.Stack;
import containers.stack.StackArray;

public class DFS extends Paths {

	private boolean[] marked;
	private int[] edgeTo;
	private int source;

	public DFS(Graph G, int s) {

		this.marked = new boolean[G.V()];
		this.edgeTo = new int[G.V()];
		this.source = s;

		this.dfs(G, s);

	}

	private void dfs(Graph G, int v) {

		this.marked[v] = true;
		for (int w : G.adj(v))
			if (!this.marked[w]) {
				this.dfs(G, w);
				this.edgeTo[w] = v;
			}

	}

	@Override
	public boolean hasPathTo(int v) {
		
		return this.marked[v];
		
	}

	@Override
	public Iterable<Integer> pathTo(int v) {

		if (!this.hasPathTo(v))
			return null;
		
		Stack<Integer> path = new StackArray<>();
		for (int x = v; x != this.source; x = this.edgeTo[x])
			path.push(x);

		path.push(this.source);

		return path;
		
	}

}
