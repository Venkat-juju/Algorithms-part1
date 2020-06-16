import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private final int num;
	private final WeightedQuickUnionUF uf;
	private boolean[] blocked;
	private int numOfOpenSites;
	
	// creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
    	
    	if (n <= 0) {
    		throw new IllegalArgumentException();
    	}
    	
    	num = n;
    	
    	uf = new WeightedQuickUnionUF(n*n + 2);
    	blocked = new boolean[n*n+2];
    	
    	for (int i = 1; i <= n*n; i++) {
    		blocked[i] = true;
    	}
    	
    	numOfOpenSites = 0;
    	
    	// connecting all the top row nodes with the virtual upper node
    	for (int i = 1; i <= n; i++) {
    		uf.union(0, i);
    	}
    	
    	
    	// connecting all the bottom row nodes with the virtual lower node
    	for (int i = n*n; i > n*n - n; i--) {
    		uf.union(n*n + 1, i);
    	}
    }
    
    // to return the index in the array with respect to the grid
    private int index(int row, int col) {
    	
    	if (row < 1 || row > num  || col < 1 || col > num) {
    		throw new IndexOutOfBoundsException();
    	}
    	
    	return ((row - 1) * num + col);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
    	
    	if (row < 1 || row > num || col < 1 || col > num) {
    		throw new IllegalArgumentException();
    	}
    	
    	int id = index(row, col);
    	
    	// to check whether the block is already opened or not
    	if(!blocked[id]) {
    		return;
    	}
    	
    	// opening the respective node(block)
    	blocked[id] = false;
    	
    	// check whether the upper block is open. if so.. connecting with it
    	if (row > 1 && isOpen(row - 1, col)) {
    		int id1 = index(row - 1, col);
    		
    		uf.union(id, id1);
    	}
    	
    	
    	// check whether the lower block is open. if so.. connecting with it
    	if (row < num && isOpen(row + 1, col)) {
    		int id1 = index(row + 1, col);
    		
    		uf.union(id, id1);
    	}
    	
    	// check whether the left block is open. if so.. connecting with it
    	if (col > 1 && isOpen(row, col - 1)) {
    		int id1 = index(row, col - 1);
    		
    		uf.union(id, id1);
    	}
    	
    	// check whether the right block is open. if so.. connecting with it
    	if (col < num && isOpen(row, col + 1)) {
    		int id1 = index(row, col + 1);
    		
    		uf.union(id, id1);
    	}
    	
    	numOfOpenSites++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    	
    	if (row < 1 || row > num || col < 1 || col > num) {
    		throw new IllegalArgumentException();
    	}
    	
    	return !blocked[index(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	
    	if (row < 1 || row > num || col < 1 || col > num) {
    		throw new IllegalArgumentException();
    	}
    	
    	int id = index(row, col);
    	
    	return (isOpen(row, col) && uf.connected(0, id));
    	
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
    	
    	return numOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
    	if(num == 1) {
    		return isOpen(1, 1);
    	}
    	
    	return uf.connected(0, num*num+1);
    }

    // test client (optional)
    public static void main(String[] args) {
    	
    	Percolation pc = new Percolation(2);

    	StdOut.println("percolates : " + pc.percolates());

    	StdOut.println("Open(1, 1) ");
    	pc.open(1, 1);
    	StdOut.println("isOpen (1, 1) : " + pc.isOpen(1, 1));
    	StdOut.println("percolates : " + pc.percolates());
    	StdOut.println("number of open sites : " + pc.numberOfOpenSites());
    	StdOut.println("iFull (1, 1) : " + pc.isFull(1, 1));
    	StdOut.println("Open(1, 2) ");
    	pc.open(1, 2);
    	StdOut.println("isOpen (1, 2) : " + pc.isOpen(1, 2));
    	StdOut.println("percolates : " + pc.percolates());
    	StdOut.println("number of open sites : " + pc.numberOfOpenSites());
    	StdOut.println("iFull (1, 2) : " + pc.isFull(1, 2));
    	StdOut.println("Open(2, 2) ");
    	pc.open(2, 2);
    	StdOut.println("isOpen (2, 2) : " + pc.isOpen(2, 2));
    	StdOut.println("percolates : " + pc.percolates());
    	StdOut.println("number of open sites : " + pc.numberOfOpenSites());
    	StdOut.println("iFull (2, 2) : " + pc.isFull(2, 2));
    	
    	
//    	pc.open(2, 1);
//    	pc.open(2, 2);
//    	StdOut.println(pc.numberOfOpenSites());
//    	StdOut.println(pc.percolates());
//    	StdOut.println(pc.isFull(2, 2));
//    	pc.open(3, 2);
//    	pc.open(3, 3);
//    	pc.open(4, 3);
//    	pc.open(4, 4);
//    	pc.open(5, 4);
//    	pc.open(5, 5);
//    	
//    	StdOut.println(pc.numberOfOpenSites());
//    	
//    	StdOut.println(pc.percolates());
    }
}
