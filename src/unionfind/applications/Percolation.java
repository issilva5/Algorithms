package unionfind.applications;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private final WeightedQuickUnionUF uf;
	private final boolean[][] grid;
	private final int size;
	private int opened;
	private boolean percolated;

	// creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
    	
    	if (n <= 0)
    		throw new IllegalArgumentException();
    	
    	this.uf = new WeightedQuickUnionUF(n*n + 2);
    	this.grid = new boolean[n+1][n+1];
    	this.size = n;
    	this.opened = 0;
    	this.percolated = false;
    	
    	for (int i = 0; i < n + 1; i++)
    		for (int j = 0; j < n + 1; j++)
    			this.grid[i][j] = false;
    			
    	
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
    	
    	if (row <= 0 || row > this.size || col <= 0 || col > this.size)
    		throw new IllegalArgumentException();
    	
    	if (this.isOpen(row, col))
    		return;
    	
    	this.grid[row][col] = true;
    	this.opened++;
    	int p = col + (row - 1) * this.size;
    	
    	this.lowerUnion(row, col, p);
    	this.upperUnion(row, col, p);
    	this.rightUnion(row, col, p);
    	this.leftUnion(row, col, p);
    	
    }

	private void leftUnion(int row, int col, int p) {
		
		if (col - 1 > 0 && this.isOpen(row, col-1)) {
			
			int q = (col - 1) + (row - 1) * this.size;
			this.uf.union(p, q);
			
		}
		
	}

	private void rightUnion(int row, int col, int p) {
		
		if (col + 1 <= this.size && this.isOpen(row, col+1)) {
			
			int q = (col + 1) + (row - 1) * this.size;
			this.uf.union(p, q);
			
		}
		
	}

	private void upperUnion(int row, int col, int p) {
		
		int q;
		if (row - 1 > 0 && this.isOpen(row-1, col)) {
    		
    		q = col + (row - 2) * this.size;
    		this.uf.union(p, q);
    		
    	} 
		
		if (row - 1 == 0) {
    		
    		q = 0;
    		
    		this.uf.union(p, q);
    		
    	}
		
	}

	private void lowerUnion(int row, int col, int p) {
		
		int q;
		if (row + 1 <= this.size && this.isOpen(row+1, col)) {
    		
    		
    		q = col + row * this.size;
    		
    		this.uf.union(p, q);
    		
    	} 
		
		if (row + 1 == this.size + 1) {
    		
    		q = this.size*this.size + 1;
    		
    		this.uf.union(p, q);
    		
    	}
		
	}

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    	
    	if (row <= 0 || row > this.size || col <= 0 || col > this.size)
    		throw new IllegalArgumentException();
    	
		return this.grid[row][col];
    	
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	
    	if (row <= 0 || row > this.size || col <= 0 || col > this.size)
    		throw new IllegalArgumentException();
    	
    	int p = col + (row - 1) * this.size;
    	
		return this.uf.find(0) == this.uf.find(p);
    	
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
		return this.opened;
    	
    }

    // does the system percolate?
    public boolean percolates() {
    	
    	if(!this.percolated)
    		this.percolated = this.uf.find(0) == this.uf.find(this.size * this.size + 1);
		
		return this.percolated;
    	
    }
    
//    public static void main(String[] args) {
//		
//    	Percolation p = new Percolation(5);
//    	p.open(5,1);
//    	System.out.println(p.uf.count());
//    	p.open(1, 2);
//    	System.out.println(p.uf.count());
//    	assert p.isFull(1, 2);
//    	p.open(2, 3);
//    	System.out.println(p.uf.count());
//    	assert !p.isFull(2, 3);
//    	p.open(2, 2);
//    	System.out.println(p.uf.count());
//    	assert p.isFull(2, 2);
//    	assert p.isFull(2, 3);
//    	p.open(4, 3);
//    	System.out.println(p.uf.count());
//    	assert !p.isFull(4, 3);
//    	p.open(3,2);
//    	System.out.println(p.uf.count());
//    	assert !p.isFull(4, 3);
//    	assert p.isFull(3, 2);
//    	p.open(3,3);
//    	System.out.println(p.uf.count());
//    	assert p.isFull(4, 3);
//    	p.open(5,3);
//    	System.out.println(p.uf.count() + " Per");
//    	assert p.percolates();
//    	p.open(1, 5);
//    	System.out.println(p.uf.count());
//    	assert p.isFull(1, 5);
//    	p.open(3, 5);
//    	System.out.println(p.uf.count());
//    	assert !p.isFull(3, 5);
//    	System.out.println(p.isFull(5, 1));
//    	
//	}
	
}
