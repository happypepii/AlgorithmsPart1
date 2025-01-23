import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF QU;
    private int[][] grid;
    private int n;
    private int numOfOpens;
    // virtual node for Union-Find
    private final int virtualTop;
    private final int virtualBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if (n <= 0) throw new IllegalArgumentException("n must be > 0");

        this.n = n;
        this.QU = new WeightedQuickUnionUF(n*n+2); // +2 for virtual top & bottom
        this.virtualTop = n * n;
        this.virtualBottom = n * n + 1;
        grid = new int[n][n]; // 0 : block
        this.numOfOpens = 0;
    }

    private void validateIndices(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException("Index out of bounds");
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateIndices(row, col);
        row--; col--;  // convert to 0-based indices

        if (grid[row][col] == 0) {  // if the site is not open
            grid[row][col] = 1;  // open the site
            numOfOpens++;

            if (row == 0) {  // connect to virtual top
                QU.union(virtualTop, row * n + col);
            }
            if (row == n-1) {  // connect to virtual bottom
                QU.union(virtualBottom, row * n + col);
            }
            // Connect with the neighboring open sites
            // using 1-based indices for isOpen
            if (row > 0 && isOpen(row - 1 + 1, col + 1)) {
                QU.union((row - 1) * n + col, row * n + col);  // up
            }
            if (row < n - 1 && isOpen(row + 1 + 1, col + 1)) {
                QU.union((row + 1) * n + col, row * n + col);  // down
            }
            if (col > 0 && isOpen(row + 1, col - 1 + 1)) {
                QU.union(row * n + (col - 1), row * n + col);  // left
            }
            if (col < n - 1 && isOpen(row + 1, col + 1 + 1)) {
                QU.union(row * n + (col + 1), row * n + col);  // right
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        validateIndices(row, col);
        return grid[row-1][col-1] == 1; // convert to 0-based indices
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        validateIndices(row, col);
        return isOpen(row, col) && 
               QU.find((row-1) * n + (col-1)) == QU.find(virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return numOfOpens;
    }

    // does the system percolate?
    public boolean percolates(){
        return QU.find(virtualTop) == QU.find(virtualBottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        // Test 1: 2x2 grid
        Percolation perc = new Percolation(2);
        System.out.println("Initially percolates? " + perc.percolates()); // should be false
        
        perc.open(1, 1);
        System.out.println("Open (1,1) percolates? " + perc.percolates()); // should be false
        System.out.println("Is (1,1) full? " + perc.isFull(1, 1)); // should be true
        
        perc.open(2, 1);
        System.out.println("Open (2,1) percolates? " + perc.percolates()); // should be true
        
        // Test 2: Invalid inputs
        try {
            perc.open(0, 1); // should throw exception
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly caught invalid row");
        }
        
        try {
            perc.open(1, 3); // should throw exception
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly caught invalid column");
        }
        
        // Test 3: Number of open sites
        System.out.println("Number of open sites: " + perc.numberOfOpenSites()); // should be 2
    }
}