package graph;

import containers.queue.Queue;
import containers.queue.QueueArray;
import containers.stack.Stack;
import containers.stack.StackArray;

public class BFS extends Paths {

	private int[] distTo;
	private int[] edgeTo;
	private int source;
	
	public BFS(Graph G, int s) {
		
		this.distTo = new int[G.V()];
		for (int i = 0; i < this.distTo.length; i++)
			this.distTo[i] = -1;
		
		this.edgeTo = new int[G.V()];
		this.source = s;
		
		this.bfs(G, s);
		
	}
	
	private void bfs(Graph G, int s) {
		
		this.distTo[s] = 0;
		Queue<Integer> q = new QueueArray<>();
		q.enqueue(s);
		
		while (!q.isEmpty()) {
			
			int v = q.dequeue();
			for (int w : G.adj(v))
				if (this.distTo[w] == -1) {
					this.distTo[w] = this.distTo[v] + 1;
					this.edgeTo[w] = v;
					q.enqueue(w);
				}
			
		}
		
	}

	@Override
	public boolean hasPathTo(int v) {
		
		return this.distTo[v] != -1;
		
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
