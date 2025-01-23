import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
    private final double[] thresholds;
    private final int trials;
    private static final double CONFIDENCE_95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be positive");
        }
        
        this.trials = trials;
        this.thresholds = new double[trials];
        
        // Perform trials
        for (int t = 0; t < trials; t++) {
            Percolation perc = new Percolation(n);
            int openSites = 0;
            
            // Keep opening random sites until the system percolates
            while (!perc.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    openSites++;
                }
            }
            
            // Calculate and store the threshold for this trial
            thresholds[t] = (double) openSites / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (trials == 1) return Double.NaN;
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(trials));
    }

    // test client
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Expected 2 arguments: n and trials");
        }
        
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        
        PercolationStats stats = new PercolationStats(n, trials);
        
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
