import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private final double[] trialsResults;

	// perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
    	
    	if (n <= 0 || trials <= 0)
    		throw new IllegalArgumentException();
    	
    	this.trialsResults = new double[trials];
    	
    	for (int i = 0; i < trials; i++) {
    		
    		Percolation percolation = new Percolation(n);
    		
    		do {
    		
	    		int row = StdRandom.uniform(n) + 1;
	    		int col = StdRandom.uniform(n) + 1;
	    		
	    		percolation.open(row, col);
    		
    		} while (!percolation.percolates());
    		
    		this.trialsResults[i] = percolation.numberOfOpenSites()/(n*n*1.0);
    		
    		
    	}
    	
    }

    // sample mean of percolation threshold
    public double mean() {
		return StdStats.mean(trialsResults);
    	
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
		return StdStats.stddev(this.trialsResults);
    	
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
		return this.mean() - (1.96 * this.stddev())/Math.sqrt(this.trialsResults.length);
		
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
		return this.mean() + (1.96 * this.stddev())/Math.sqrt(this.trialsResults.length);
    	
    }

   // test client (see below)
   public static void main(String[] args) {
	   
	   int n = Integer.parseInt(args[0]);
	   int t = Integer.parseInt(args[1]);
	   
	   PercolationStats ps = new PercolationStats(n, t);
	   StdOut.println("mean                    = " + ps.mean());
	   StdOut.println("stddev                  = " + ps.stddev());
	   StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
	   
   }
	
}
