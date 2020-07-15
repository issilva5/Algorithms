package graph;

import containers.stack.Stack;
import containers.stack.StackArray;

public class DeepFirstOrder {
	
	// Topological Order for DAG
	
	private boolean[] marked;
	private Stack<Integer> reversePostOrder;

	public DeepFirstOrder(Digraph G) {

		this.marked = new boolean[G.V()];
		this.reversePostOrder = new StackArray<>();
		
		for (int i = 0; i < this.marked.length; i++)
			if (!this.marked[i])
				this.dfs(G, i);

	}
	
	public DeepFirstOrder(EdgeWeigthedDigraph G) {
		
		this.marked = new boolean[G.V()];
		this.reversePostOrder = new StackArray<>();
		
		for (int i = 0; i < this.marked.length; i++)
			if (!this.marked[i])
				this.dfs(G, i);
		
	}

	private void dfs(EdgeWeigthedDigraph G, int v) {
		
		this.marked[v] = true;
		for (DirectedEdge e : G.adj(v)) {
			
			int w = e.to();
			
			if (!this.marked[w])
				this.dfs(G, w);
			
		}
		
		this.reversePostOrder.push(v);
		
	}

	private void dfs(Graph G, int v) {

		this.marked[v] = true;
		for (int w : G.adj(v))
			if (!this.marked[w])
				this.dfs(G, w);
		
		this.reversePostOrder.push(v);

	}
	
	public Iterable<Integer> reversePost() {
		return this.reversePostOrder;
	}

}
