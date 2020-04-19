/* *****************************************************************************
 *  Name: Denis Petek
 *  Date: 4/18/2020
 *  Description: Algorithms I - Week 1 Programming Assignment
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Collections;

public class Percolation {
    private static int[][] NEIGHBOURS = new int[][] {
            { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }
    };

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.gridStates = new ArrayList<Integer>(Collections.nCopies(n * n + 2, -1));
        this.N = n;
        this.sourceCell = n * n;
        this.sinkCell = n * n + 1;
        this.numOpen = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        int i = flattern(row, col);
        gridStates.set(i, i);
        for (int[] neigbour: NEIGHBOURS) {
            int nRow = row + neigbour[0];
            int nCol = col + neigbour[1];
            if (isOpen(nRow, nCol)) {
                union(flattern(row, col), flattern(nRow, nCol));
            }
        }
        if (row == 0) {
            union (this.sourceCell, flattern(row, col));
        }
        if (row == this.N - 1) {
            union(this.sinkCell, flattern(row, col));
        }
        ++this.numOpen;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return gridStates.get(flattern(row, col)) != -1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        System.out.printf("isFull called %d %d\n", row, col);
        return gridStates.get(flattern(row, col)) == -1;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return find(this.sourceCell, this.sinkCell);
    }

    private int flattern(int row, int col) {
        return this.N * row + col;
    }

    private void union(int p, int q) {
        if (root(p) == root(q)) {
            return;
        }
        int rp = root(p);
        int rq = root(q);
        gridStates.set(rp, rq);
    }

    private boolean find(int p, int q) {
        return root(p) == root(q);
    }

    private int root(int i) {
        while (this.gridStates.get(i) != i) {
            i = this.gridStates.get(i);
        }
        return i;
    }

    private ArrayList<Integer> gridStates;
    private int N;
    private int sourceCell;
    private int sinkCell;
    private int numOpen;

    // test client (optional)
    //public static void main(String[] args)
}
