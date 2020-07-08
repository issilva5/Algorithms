package graph;

public class KosarajuSCC {
	
	private int[] component;
	private int count;
	
	public KosarajuSCC(Digraph G) {
		
		Iterable<Integer> r = (new DeepFirstOrder(G.reverse())).reversePost();
		
		this.component = new int[G.V()];
		for (int i = 0; i < this.component.length; i++)
			this.component[i] = -1;
		
		for (int v : r)
			if (this.component[v] == -1) {
				this.dfs(G, v);
				this.count++;
			}
		
	}
	
	private void dfs(Digraph G, int v) {

		this.component[v] = this.count;
		for (int w : G.adj(v))
			if (this.component[w] == -1)
				this.dfs(G, w);

	}

	public boolean stronglyConnected(int v, int w) {

		return this.component[v] == this.component[w];
		
	}

	public int count() {
		
		return this.count;

	}

	public int id(int v) {
		
		return this.component[v];

	}	

}
