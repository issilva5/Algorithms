package graph;

public class CC {

	private int[] component;
	private int count;

	public CC(UGraph G) {
		
		this.component = new int[G.V()];
		for (int i = 0; i < this.component.length; i++)
			this.component[i] = -1;
		
		for (int i = 0; i < this.component.length; i++) {
			
			if (this.component[i] == -1) {
				this.dfs(G, i);
				this.count++;
			}
			
		}
		
		
	}
	
	private void dfs(UGraph G, int v) {

		this.component[v] = this.count;
		for (int w : G.adj(v))
			if (this.component[w] == -1)
				this.dfs(G, w);

	}

	public boolean connected(int v, int w) {

		return this.component[v] == this.component[w];
		
	}

	public int count() {
		
		return this.count;

	}

	public int id(int v) {
		
		return this.component[v];

	}

}
