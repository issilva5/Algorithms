package graph;

import containers.queue.Queue;
import containers.queue.QueueArray;

public class Degrees {
	
	private int[] indg;
	private int[] outdg;
	private Queue<Integer> sources;
	private Queue<Integer> sinks;
	private boolean map;
	
	Degrees(Digraph G) {
		
		this.indg = new int[G.V()];
		this.outdg = new int[G.V()];
		this.sources = new QueueArray<>();
		this.sinks = new QueueArray<>();
		this.map = true;
		
		for (int v = 0; v < G.V(); v++)
			for (int w : G.adj(v)) {
				
				this.indg[w]++;
				this.outdg[v]++;
				
			}
		
		for (int v = 0; v < G.V(); v++) {
			if (this.indg[v] == 0)
				this.sources.enqueue(v);
			
			if (this.outdg[v] == 0)
				this.sinks.enqueue(v);
		}
		
		for (int d : this.outdg)
			if (d != 1)
				this.map = false;
		
	}
	
	int indegree(int v) {
		return this.indg[v];
	}
	
	int outdegree(int v) {
		return this.outdg[v];
	}
	
	Iterable<Integer> sources() {
		return this.sources;
	}
	
	Iterable<Integer> sinks() {
		return this.sinks;
	}
	
	boolean isMap() {
		return this.map;
	}

}
