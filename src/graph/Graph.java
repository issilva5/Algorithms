package graph;

public interface Graph {
	
	public void addEdge(int v, int w);
	
	public Iterable<Integer> adj(int v);
	
	public int V();
	
	public int E();
	
	public String toString();

}
