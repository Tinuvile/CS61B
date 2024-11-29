package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] grid;  // 表示网格
    private final WeightedQuickUnionUF uf;  // 并查集结构
    private final WeightedQuickUnionUF ufFull;  // 用于处理满的节点
    private final int N;  //网格大小
    private int openSiteCount;  // 开放站点的数量


    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must bigger than 0");
        }
        this.N = N;
        grid = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N * N + 2);  // 包含虚拟的顶部和底部
        ufFull = new WeightedQuickUnionUF(N * N + 1);  // 处理满的站点（不包括底部）
        openSiteCount = 0;
    }

    // 验证索引范围
    private void validateIndices(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException("索引超出范围");
        }
    }

    // 将二维坐标转换为一维索引
    private int xyTo1D(int row, int col) {
        return row * N + col;
    }

    // 连接到相邻的开放站点并更新并查集结构
    private void connectToNeighbors(int row, int col, int index) {
        // 上
        if (row > 0 && isOpen(row - 1, col)) {
            uf.union(index, xyTo1D(row - 1, col));
            ufFull.union(index, xyTo1D(row - 1, col));
        }
        // 下
        if (row < N - 1 && isOpen(row + 1, col)) {
            uf.union(index, xyTo1D(row + 1, col));
            ufFull.union(index, xyTo1D(row + 1, col));
        }
        // 左
        if (col > 0 && isOpen(row, col - 1)) {
            uf.union(index, xyTo1D(row, col - 1));
            ufFull.union(index, xyTo1D(row, col - 1));
        }
        // 右
        if (col < N - 1 && isOpen(row, col + 1)) {
            uf.union(index, xyTo1D(row, col + 1));
            ufFull.union(index, xyTo1D(row, col + 1));
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateIndices(row, col);
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            openSiteCount++;
            int index = xyTo1D(row, col);

            // 如果在顶部行，则连接到虚拟的顶部站点
            if (row == 0) {
                uf.union(index, N * N);
                ufFull.union(index, N * N);
            }
            if (row == N - 1) {
                uf.union(index, N * N + 1);
            }
            connectToNeighbors(row, col, index);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateIndices(row, col);
        return ufFull.connected(xyTo1D(row, col), N * N);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSiteCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(N * N, N * N + 1);
    }

    // use for unit testing (not required)
    public static void main(String[] args) {

    }
}








