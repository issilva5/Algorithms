package graph;

import containers.queue.Queue;
import containers.queue.QueueArray;

public class FordFulkerson {
	private boolean[] marked; // true if s->v path in residual network
	private FlowEdge[] edgeTo; // last edge on s->v path
	private double value; // value of flow

	public FordFulkerson(FlowNetwork G, int s, int t) {
		value = 0.0;
		while (hasAugmentingPath(G, s, t)) {
			double bottle = Double.POSITIVE_INFINITY;
			for (int v = t; v != s; v = edgeTo[v].other(v))
				bottle = Math.min(bottle, edgeTo[v].residualCapacity(v));
			for (int v = t; v != s; v = edgeTo[v].other(v))
				edgeTo[v].addResidualFlowTo(v, bottle);
			value += bottle;
		}
	}

	private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {

		edgeTo = new FlowEdge[G.V()];
		marked = new boolean[G.V()];
		Queue<Integer> queue = new QueueArray<Integer>();
		queue.enqueue(s);
		marked[s] = true;
		
		while (!queue.isEmpty()) {
			int v = queue.dequeue();
			for (FlowEdge e : G.adj(v)) {
				int w = e.other(v);
				if (e.residualCapacity(w) > 0 && !marked[w]) {
					edgeTo[w] = e;
					marked[w] = true;
					queue.enqueue(w);
				}
			}
		}
		
		return marked[t];

	}

	public double value() {
		return value;
	}

	public boolean inCut(int v) {
		return marked[v];
	}
}