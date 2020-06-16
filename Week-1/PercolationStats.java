import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	
	private final double[] fractionOfOpenSites;
	private final int trials;

	 // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
    	if (n <= 0 || trials <= 0) {
    		throw new IllegalArgumentException();
    	}
    	
    	this.trials = trials;
    	fractionOfOpenSites = new double[trials];
    	
    	
    	for (int i = 0; i < trials; i++) {
    		Percolation percolation = new Percolation(n);
    		while(!percolation.percolates()) {
    			int row = StdRandom.uniform(1, n+1);
    			int col = StdRandom.uniform(1, n+1);
    			percolation.open(row, col);
    		}
    		fractionOfOpenSites[i] = (double) percolation.numberOfOpenSites() / (n * n); 
    	}
    }

    // sample mean of percolation threshold
    public double mean() {
    	
    	return StdStats.mean(fractionOfOpenSites);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
    	
    	return StdStats.stddev(fractionOfOpenSites);
    }
    
    private double findConstant() {
    	double constant = 1.96 * stddev();
    	constant = constant / Math.sqrt(trials);
    	
    	return constant;
    	
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
    	double constant = findConstant();
    	
    	return mean() - constant;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	double constant = findConstant();
    	
    	return mean() + constant;
    	
    }

   // test client
   public static void main(String[] args) {
	   
	   int n = Integer.parseInt(args[0]);
	   int trials = Integer.parseInt(args[1]);
	   
	   PercolationStats stats = new PercolationStats(n, trials);
	   
	   StdOut.println("mean = " + stats.mean());
	   StdOut.println("stddev = " + stats.stddev());
	   StdOut.println("95% confidence level = [" + stats.confidenceLo() + ", "
			   + stats.confidenceHi() + "]");
   }
}
