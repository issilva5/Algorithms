package graph.applications;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.Bag;

/**
 *  The {@code FlowNetwork} class represents a capacitated network
 *  with vertices named 0 through <em>V</em> - 1, where each directed
 *  edge is of type {@link FlowEdge} and has a real-valued capacity
 *  and flow.
 *  It supports the following two primary operations: add an edge to the network,
 *  iterate over all of the edges incident to or from a vertex. It also provides
 *  methods for returning the number of vertices <em>V</em> and the number
 *  of edges <em>E</em>. Parallel edges and self-loops are permitted.
 *  <p>
 *  This implementation uses an adjacency-lists representation, which 
 *  is a vertex-indexed array of {@link Bag} objects.
 *  All operations take constant time (in the worst case) except
 *  iterating over the edges incident to a given vertex, which takes
 *  time proportional to the number of such edges.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/64maxflow">Section 6.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class BaseballFlowNetwork {
    private static final String NEWLINE = "\n";

    private Map<BaseballVertex, Bag<BaseballEdge>> adj;
    private int E;
    
    /**
     * Initializes an empty flow network with {@code V} vertices and 0 edges.
     * @param V the number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    public BaseballFlowNetwork() {
        
        adj = new HashMap<>();
        E = 0;
        
    }

    /**
     * Returns the number of vertices in the edge-weighted graph.
     * @return the number of vertices in the edge-weighted graph
     */
    public int V() {
        return adj.size();
    }
    
    public Set<BaseballVertex> vertex() {
    	return adj.keySet();
    }

    /**
     * Adds the edge {@code e} to the network.
     * @param e the edge
     * @throws IllegalArgumentException unless endpoints of edge are between
     *         {@code 0} and {@code V-1}
     */
    public void addEdge(BaseballEdge e) {
    	
        BaseballVertex v = e.from();
        BaseballVertex w = e.to();
        
        if (adj.containsKey(v)) {
        	adj.get(v).add(e);
        } else {
        	adj.put(v, new Bag<>());
        	adj.get(v).add(e);
        }
        
        if (adj.containsKey(w)) {
        	adj.get(w).add(e);
        } else {
        	adj.put(w, new Bag<>());
        	adj.get(w).add(e);
        }
        
        this.E++;
        
    }

    /**
     * Returns the edges incident on vertex {@code v} (includes both edges pointing to
     * and from {@code v}).
     * @param v the vertex
     * @return the edges incident on vertex {@code v} as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<BaseballEdge> adj(BaseballVertex v) {
    	
    	if (adj.containsKey(v))
    		return adj.get(v);
    	
    	return null;
    	
    }

//    // return list of all edges - excludes self loops
//    public Iterable<FlowEdge> edges() {
//        Bag<FlowEdge> list = new Bag<FlowEdge>();
//        for (int v = 0; v < V; v++)
//            for (FlowEdge e : adj(v)) {
//                if (e.to() != v)
//                    list.add(e);
//            }
//        return list;
//    }


    /**
     * Returns a string representation of the flow network.
     * This method takes time proportional to <em>E</em> + <em>V</em>.
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,  
     *    followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.V() + " " + this.E + NEWLINE);
        for (BaseballVertex v : adj.keySet()) {
            s.append(v + ":  ");
            for (BaseballEdge e : adj.get(v)) {
                if (!e.to().equals(v)) s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

}