package containers.queue.priority.applications;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

@SuppressWarnings("unused")
public class Solver {

	private SearchNode finalNode = null;
	private boolean solvable = true;
	private final Board board;

	private class SearchNode implements Comparable<SearchNode> {
		
		Board board;
		int moves;
		SearchNode previous;
		int hamming;
		int manhattan;

		public SearchNode() {
			
			this.board = null;
			this.moves = 0;
			this.previous = null;
			this.hamming = 0;
			this.manhattan = 0;
			
		}
		
		public SearchNode(Board board) {
			
			this.board = board;
			this.moves = 0;
			this.previous = null;
			this.hamming = board.hamming();
			this.manhattan = board.manhattan();
			
		}
		
		public SearchNode(Board board, int moves, SearchNode previous) {
			
			this.board = board;
			this.moves = moves;
			this.previous = previous;
			this.hamming = board.hamming();
			this.manhattan = board.manhattan();
			
		}

		@Override
		public int compareTo(SearchNode that) {
			
			//could use hamming distance too
			int thisDist = this.manhattan + this.moves;
			int thatDist = that.manhattan + that.moves;
			
			return thisDist - thatDist;
		}
		
	}
	
	// find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    	
    	if (initial == null)
    		throw new IllegalArgumentException();
    	
    	this.board = initial;
    	
    	this.isSolvable();
    	
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
    	
    	if (this.finalNode != null)
    		return this.solvable;
    	
    	MinPQ<SearchNode> pq = new MinPQ<>(this.board.dimension() * this.board.dimension());
    	pq.insert(new SearchNode(this.board));
    	
    	MinPQ<SearchNode> pqt = new MinPQ<>(this.board.dimension() * this.board.dimension());
    	pqt.insert(new SearchNode(this.board.twin()));
    	
    	while (true) {
    		
    		if (!pq.isEmpty()) {
    		
	    		SearchNode onTurn = pq.delMin();
	    		if (onTurn.board.isGoal()) {
	    			this.finalNode = onTurn;
	    			this.solvable = true;
	    			break;
	    		}
	    		
	    		for (Board b : onTurn.board.neighbors()) {
	    			
	    			if (onTurn.previous != null) {
	    				if (!b.equals(onTurn.previous.board))
	    					pq.insert(new SearchNode(b, onTurn.moves + 1, onTurn));
	    			} else pq.insert(new SearchNode(b, onTurn.moves + 1, onTurn));
	    		}
	    		
    		}
    		
    		if (!pqt.isEmpty()) {
    			
    			SearchNode onTurn = pqt.delMin();
	    		if (onTurn.board.isGoal()) {
	    			this.solvable = false;
	    			break;
	    		}
	    		
	    		for (Board b : onTurn.board.neighbors()) {
	    			
	    			if (onTurn.previous != null) {
	    				if (!b.equals(onTurn.previous.board))
	    					pqt.insert(new SearchNode(b, onTurn.moves + 1, onTurn));
	    			} else pqt.insert(new SearchNode(b, onTurn.moves + 1, onTurn));
	    		}
    			
    		}
			
		}
    	
    	return this.solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
    	
    	if (!this.solvable)
    		return -1;
    	
    	return this.finalNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
    	
    	if (!this.solvable)
    		return null;
    	
    	Stack<Board> q = new Stack<>();
    	
    	SearchNode aux = new SearchNode();
    	aux.board = this.finalNode.board;
    	aux.previous = this.finalNode.previous;
    	
    	do {
    		
    		q.push(aux.board);
    		aux = aux.previous;
    		
    	} while (aux != null);
    	
    	return q;
    	
    }

    // test client (see below) 
    public static void main(String[] args) {
    	
    	// create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
        
    }
	
}
