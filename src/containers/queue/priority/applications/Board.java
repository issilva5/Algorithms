package containers.queue.priority.applications;
import java.util.Arrays;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {

	private final int[][] tiles;
	private final int N;
	private Board twinB = null;

	// create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
    	
    	this.N = tiles.length;
    	this.tiles = new int[this.N][this.N];
    	for (int p = 0; p < this.N; p++)
            for (int q = 0; q < this.N; q++)
            	this.tiles[p][q] = tiles[p][q];
    	
    	
    	if (this.N > 0 && this.N != this.tiles[0].length)
    		throw new IllegalArgumentException();
    	
    }
                                           
    // string representation of this board
    public String toString() {
    	StringBuilder s = new StringBuilder();
        s.append(this.N + "\n");
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++) {
                s.append(String.format("%2d ", this.tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
    	return this.N;
    }

    // number of tiles out of place
    public int hamming() {
    	
    	int cont = 1;
    	int ham = 0;
    	for (int i = 0; i < this.N; i++)
            for (int j = 0; j < this.N; j++, cont++)
                if (this.tiles[i][j] != cont && this.tiles[i][j] != 0)
                	ham++;
    	
    	return ham;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
    	
    	int deltaX, deltaY, rowGoal, colGoal, man = 0;
    	for (int i = 0; i < this.N; i++)
            for (int j = 0; j < this.N; j++) {
            	
            	if (this.tiles[i][j] != 0) {
            	
	            	rowGoal = (this.tiles[i][j] - 1) / this.N;
	            	colGoal = (this.tiles[i][j] - 1) % this.N;
	            	
	            	deltaX = Math.abs(i - rowGoal);
	            	deltaY = Math.abs(j - colGoal);
	            	
	            	man += (deltaX + deltaY);
	            	
            	}
            	
            }
    	
    	return man;
    }

    // is this board the goal board?
    public boolean isGoal() {
    	return this.hamming() == 0;
    }

    // does this board equal y?
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (N != other.N)
			return false;
		if (!Arrays.deepEquals(tiles, other.tiles))
			return false;
		return true;
	}
	
	private enum Neighbour {
    	UPPER,
    	LOWER,
    	RIGHT,
    	LEFT
    }
	
	// all neighboring boards
    public Iterable<Board> neighbors() {
    	
    	Stack<Board> sb = new Stack<>();
    	int[] zero = this.findVoid();
    	
    	if (zero[0] > 0) {
    		sb.push(this.neighbour(zero[0], zero[1], Neighbour.UPPER));
    	}
    	
    	if (zero[0] < this.N - 1) {
    		sb.push(this.neighbour(zero[0], zero[1], Neighbour.LOWER));
    	}
    	
    	if (zero[1] > 0) {
    		sb.push(this.neighbour(zero[0], zero[1], Neighbour.LEFT));
    	}
    	
    	if (zero[1] < this.N - 1) {
    		sb.push(this.neighbour(zero[0], zero[1], Neighbour.RIGHT));
    	}
    	
    	return sb;
    }

    private Board neighbour(int i, int j, Neighbour id) {
		
    	int[][] copy = new int[this.N][this.N];
    	for (int p = 0; p < this.N; p++)
            for (int q = 0; q < this.N; q++)
            	copy[p][q] = this.tiles[p][q];
    	
    	if (id == Neighbour.UPPER) {
    		
			copy[i][j] = copy[i-1][j];
			copy[i-1][j] = 0;
    		
    	} else if (id == Neighbour.LOWER) {
    		
    		copy[i][j] = copy[i+1][j];
    		copy[i+1][j] = 0;
    		
    	} else if (id == Neighbour.LEFT) {
    		
    		copy[i][j] = copy[i][j-1];
    		copy[i][j-1] = 0;
    		
    	} else if (id == Neighbour.RIGHT) {
    		
    		copy[i][j] = copy[i][j+1];
    		copy[i][j+1] = 0;

    	}
    	
    	return new Board(copy);
		
	}

	private int[] findVoid() {
		
    	for (int i = 0; i < this.N; i++)
            for (int j = 0; j < this.N; j++)
            	if (this.tiles[i][j] == 0)
            		return new int[] {i, j};
        
        return null;
	}

	// a board that is obtained by exchanging any pair of tiles
    public Board twin() {
    	
    	if (this.twinB != null)
    		return this.twinB;
    	
    	int x, y;
    	do {
    		
    		x = StdRandom.uniform(this.N);
    		y = StdRandom.uniform(this.N);
    		
    	} while (this.tiles[x][y] == 0);
    	
    	int xc, yc;
    	
    	do {
    	
	    	if (StdRandom.uniform(2) % 2 == 1) {
	    		
	    		yc = y;
	    		
	    		do {
	    			
		    		xc = x + (StdRandom.uniform(2) == 0 ? 1 : -1);
		    		
	    		} while (xc < 0 || xc >= this.N);
	        	
	    	} else {
	    	
	    		xc = x;
	    		
		    	do {
		    		
		    		yc = y + (StdRandom.uniform(2) == 0 ? 1 : -1);
		    		
		    	} while (yc < 0 || yc >= this.N);
		    	
	    	}
    	
    	} while (this.tiles[xc][yc] == 0);
    	
    	int[][] copy = new int[this.N][this.N];
    	for (int p = 0; p < this.N; p++)
            for (int q = 0; q < this.N; q++)
            	copy[p][q] = this.tiles[p][q];
    	
    	int aux = copy[x][y];
    	copy[x][y] = copy[xc][yc];
    	copy[xc][yc] = aux;
    	
    	this.twinB = new Board(copy);
    	return this.twinB;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
    	
    	Board b = new Board(new int[][] {
    							{1, 0, 3},
    							{4, 2, 5},
    							{7, 8, 6}
    						});
    	
    	StdOut.println(b);
    	StdOut.println(b.manhattan());
    	StdOut.println(b.hamming());
    	
    	for (Board p : b.neighbors())
    		StdOut.println(p);
    	
    	StdOut.println(b.twin());
    	
    }
	
}
