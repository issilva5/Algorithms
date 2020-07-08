package graph;

import containers.stack.Stack;
import containers.stack.StackArray;

public class DirectedCycleFinder {

	private boolean[] marked;
	private int[] edgeTo;
	private Stack<Integer> cycle; // vertices on a cycle (if one exists)
	private boolean[] onStack; // vertices on recursive call stack

	public DirectedCycleFinder(Digraph G) {
		
		this.onStack = new boolean[G.V()];
		this.edgeTo = new int[G.V()];
		this.marked = new boolean[G.V()];
		for (int v = 0; v < G.V(); v++)
			if (!this.marked[v])
				this.dfs(G, v);
		
	}

	private void dfs(Digraph G, int v) {
		
		this.onStack[v] = true;
		this.marked[v] = true;
		
		for (int w : G.adj(v))
			if (this.hasCycle()) return;
			else if (!this.marked[w]) {
				
				this.edgeTo[w] = v;
				this.dfs(G, w);
				
			} else if (this.onStack[w]) {
				
				this.cycle = new StackArray<>();
				for (int x = v; x != w; x = this.edgeTo[x])
					this.cycle.push(x);
				
				this.cycle.push(w);
				this.cycle.push(v);
				
			}
		
		this.onStack[v] = false;
		
	}

	public boolean hasCycle() {
		return this.cycle != null;
	}

	public Iterable<Integer> cycle() {
		return this.cycle;
	}

}
