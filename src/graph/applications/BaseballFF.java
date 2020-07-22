package graph.applications;
import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.Queue;

/**
 *  The {@code FordFulkerson} class represents a data type for computing a
 *  <em>maximum st-flow</em> and <em>minimum st-cut</em> in a flow
 *  network.
 *  <p>
 *  This implementation uses the <em>Ford-Fulkerson</em> algorithm with
 *  the <em>shortest augmenting path</em> heuristic.
 *  The constructor takes <em>O</em>(<em>E V</em> (<em>E</em> + <em>V</em>))
 *  time, where <em>V</em> is the number of vertices and <em>E</em> is
 *  the number of edges. In practice, the algorithm will run much faster.
 *  The {@code inCut()} and {@code value()} methods take &Theta;(1) time.
 *  It uses &Theta;(<em>V</em>) extra space (not including the network).
 *  <p>
 *  If the capacities and initial flow values are all integers, then this
 *  implementation guarantees to compute an integer-valued maximum flow.
 *  If the capacities are floating-point numbers, then floating-point
 *  roundoff error can accumulate.
 *  <p>
 *  For additional documentation, see
 *  <a href="https://algs4.cs.princeton.edu/64maxflow">Section 6.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class BaseballFF {
    private static final double FLOATING_POINT_EPSILON = 1E-11;

    private Map<BaseballVertex, Boolean> marked;     // marked[v] = true iff s->v path in residual graph
    private Map<BaseballVertex, BaseballEdge> edgeTo;    // edgeTo[v] = last edge on shortest residual s->v path
    private double value;         // current value of max flow
  
    /**
     * Compute a maximum flow and minimum cut in the network {@code G}
     * from vertex {@code s} to vertex {@code t}.
     *
     * @param  G the flow network
     * @param  s the source vertex
     * @param  t the sink vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     * @throws IllegalArgumentException unless {@code 0 <= t < V}
     * @throws IllegalArgumentException if {@code s == t}
     * @throws IllegalArgumentException if initial flow is infeasible
     */
    public BaseballFF(BaseballFlowNetwork G, BaseballVertex s, BaseballVertex t) {
    	
        if (s.equals(t)) throw new IllegalArgumentException("Source equals sink");
        if (!isFeasible(G, s, t)) throw new IllegalArgumentException("Initial flow is infeasible");

        // while there exists an augmenting path, use it
        value = excess(G, t);
        while (hasAugmentingPath(G, s, t)) {

            // compute bottleneck capacity
            double bottle = Double.POSITIVE_INFINITY;
            for (BaseballVertex v = t; !v.equals(s); v = edgeTo.get(v).other(v)) {
                bottle = Math.min(bottle, edgeTo.get(v).residualCapacityTo(v));
            }

            // augment flow
            for (BaseballVertex v = t; !v.equals(s); v = edgeTo.get(v).other(v)) {
                edgeTo.get(v).addResidualFlowTo(v, bottle); 
            }

            value += bottle;
        }

        // check optimality conditions
        assert check(G, s, t);
    }

    /**
     * Returns the value of the maximum flow.
     *
     * @return the value of the maximum flow
     */
    public double value()  {
        return value;
    }

    /**
     * Returns true if the specified vertex is on the {@code s} side of the mincut.
     *
     * @param  s vertex
     * @return {@code true} if vertex {@code v} is on the {@code s} side of the micut;
     *         {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean inCut(BaseballVertex s)  {
        return marked.containsKey(s);
    }


    // is there an augmenting path? 
    // if so, upon termination edgeTo[] will contain a parent-link representation of such a path
    // this implementation finds a shortest augmenting path (fewest number of edges),
    // which performs well both in theory and in practice
    private boolean hasAugmentingPath(BaseballFlowNetwork g, BaseballVertex s, BaseballVertex t) {
        edgeTo = new HashMap<>();
        marked = new HashMap<>();

        // breadth-first search
        Queue<BaseballVertex> queue = new Queue<>();
        queue.enqueue(s);
        marked.put(s, true);        
        while (!queue.isEmpty() && !marked.containsKey(t)) {
            BaseballVertex v = queue.dequeue();

            for (BaseballEdge e : g.adj(v)) {
                BaseballVertex w = e.other(v);

                // if residual capacity from v to w
                if (e.residualCapacityTo(w) > 0) {
                    if (!marked.containsKey(w)) {
                    	edgeTo.put(w, e);
                    	marked.put(w, true);
                        queue.enqueue(w);
                    }
                }
            }
        }

        // is there an augmenting path?
        return marked.containsKey(t);
    }



    // return excess flow at vertex v
    private double excess(BaseballFlowNetwork g, BaseballVertex s) {
        double excess = 0.0;
        for (BaseballEdge e : g.adj(s)) {
            if (s == e.from()) excess -= e.flow();
            else               excess += e.flow();
        }
        return excess;
    }

    // return excess flow at vertex v
    private boolean isFeasible(BaseballFlowNetwork g, BaseballVertex s, BaseballVertex t) {

        // check that capacity constraints are satisfied
        for (BaseballVertex v : g.vertex()) {
            for (BaseballEdge e : g.adj(v)) {
                if (e.flow() < -FLOATING_POINT_EPSILON || e.flow() > e.capacity() + FLOATING_POINT_EPSILON) {
                    System.err.println("Edge does not satisfy capacity constraints: " + e);
                    return false;
                }
            }
        }

        // check that net flow into a vertex equals zero, except at source and sink
        if (Math.abs(value + excess(g, s)) > FLOATING_POINT_EPSILON) {
            System.err.println("Excess at source = " + excess(g, s));
            System.err.println("Max flow         = " + value);
            return false;
        }
        if (Math.abs(value - excess(g, t)) > FLOATING_POINT_EPSILON) {
            System.err.println("Excess at sink   = " + excess(g, t));
            System.err.println("Max flow         = " + value);
            return false;
        }
        for (BaseballVertex v : g.vertex()) {
            if (v.equals(s) || v.equals(t)) continue;
            else if (Math.abs(excess(g, v)) > FLOATING_POINT_EPSILON) {
                System.err.println("Net flow out of " + v + " doesn't equal zero");
                return false;
            }
        }
        return true;
    }



    // check optimality conditions
    private boolean check(BaseballFlowNetwork g, BaseballVertex s, BaseballVertex t) {

        // check that flow is feasible
        if (!isFeasible(g, s, t)) {
            System.err.println("Flow is infeasible");
            return false;
        }

        // check that s is on the source side of min cut and that t is not on source side
        if (!inCut(s)) {
            System.err.println("source " + s + " is not on source side of min cut");
            return false;
        }
        if (inCut(t)) {
            System.err.println("sink " + t + " is on source side of min cut");
            return false;
        }

        // check that value of min cut = value of max flow
        double mincutValue = 0.0;
        for (BaseballVertex v : g.vertex()) {
            for (BaseballEdge e : g.adj(v)) {
                if ((v == e.from()) && inCut(e.from()) && !inCut(e.to()))
                    mincutValue += e.capacity();
            }
        }

        if (Math.abs(mincutValue - value) > FLOATING_POINT_EPSILON) {
            System.err.println("Max flow value = " + value + ", min cut value = " + mincutValue);
            return false;
        }

        return true;
    }

}