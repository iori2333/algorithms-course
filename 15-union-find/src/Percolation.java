import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[] grid;
    private final int size;
    private int opened;

    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufn;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        opened = 0;
        grid = new boolean[n * n + 2];
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufn = new WeightedQuickUnionUF(n * n + 1);
    }

    private int toIndex(int row, int col) {
        return (row - 1) * size + col;
    }

    private boolean isValid(int n) {
        return 1 > n || n > size;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isValid(row) || isValid(col)) {
            throw new IllegalArgumentException();
        }
        int pos = toIndex(row, col);
        if (!grid[pos]) {
            grid[pos] = true;
            opened++;
        } else {
            return;
        }

        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(toIndex(row - 1, col), pos);
            ufn.union(toIndex(row - 1, col), pos);
        }
        if (row < size && isOpen(row + 1, col)) {
            uf.union(toIndex(row + 1, col), pos);
            ufn.union(toIndex(row + 1, col), pos);
        }

        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(toIndex(row, col - 1), pos);
            ufn.union(toIndex(row, col - 1), pos);
        }
        if (col < size && isOpen(row, col + 1)) {
            uf.union(toIndex(row, col + 1), pos);
            ufn.union(toIndex(row, col + 1), pos);
        }

        if (row == 1) {
            uf.union(toIndex(row, col), 0);
            ufn.union(toIndex(row, col), 0);
        }
        if (row == size) {
            uf.union(toIndex(row, col), size * size + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (isValid(row) || isValid(col)) {
            throw new IllegalArgumentException();
        }
        int pos = toIndex(row, col);
        return grid[pos];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isValid(row) || isValid(col)) {
            throw new IllegalArgumentException();
        }
        int pos = toIndex(row, col);
        return ufn.find(0) == ufn.find(pos);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opened;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find(size * size + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        // do nothing
    }
}
