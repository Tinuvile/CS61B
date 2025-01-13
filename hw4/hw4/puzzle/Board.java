package hw4.puzzle;

import java.util.ArrayList;
import java.util.List;

public class Board implements WorldState {
    private final int[][] tiles; // 瓦片的二维数组
    private final int N;          // 棋盘大小 N x N

    /**
     * Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j
     */
    public Board(int[][] tiles) {
        N = tiles.length; // 棋盘的大小 N
        this.tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, N);
        }
    }

    /**
     * Returns value of tile at row i, column j (or 0 if blank)
     */
    public int tileAt(int i, int j) {
        if (i < 0 || i >= N || j < 0 || j >= N) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return tiles[i][j];
    }

    /**
     * Returns the board size N
     */
    public int size() {
        return N;
    }

    /**
     * Returns the neighbors of the current board
     */
    public Iterable<WorldState> neighbors() {
        List<WorldState> neighbors = new ArrayList<>();
        int zeroRow = -1, zeroCol = -1;

        // 查找空白瓦片（值为 0）
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                    break;
                }
            }
        }

        // 将空白瓦片的邻居进行交换并添加到邻居列表中
        int[] dir = { -1, 0, 1, 0, -1 }; // 上、右、下、左方向的增量
        for (int d = 0; d < 4; d++) {
            int newRow = zeroRow + dir[d];
            int newCol = zeroCol + dir[d + 1];
            if (newRow >= 0 && newRow < N && newCol >= 0 && newCol < N) {
                // 创建新的局面
                int[][] newTiles = new int[N][N];
                for (int i = 0; i < N; i++) {
                    System.arraycopy(tiles[i], 0, newTiles[i], 0, N);
                }
                // 交换空白瓦片与邻居瓦片
                newTiles[zeroRow][zeroCol] = tiles[newRow][newCol];
                newTiles[newRow][newCol] = 0;
                neighbors.add(new Board(newTiles));
            }
        }
        return neighbors;
    }

    /**
     * Returns the Hamming estimate
     */
    public int hamming() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 计算汉明距离：计算所有不在正确位置的瓦片数量
                if (tiles[i][j] != 0 && tiles[i][j] != (i * N + j + 1)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Returns the Manhattan estimate.
     */
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int tile = tiles[i][j];
                if (tile != 0) {
                    int targetX = (tile - 1) / N; // 目标行
                    int targetY = (tile - 1) % N; // 目标列
                    distance += Math.abs(i - targetX) + Math.abs(j - targetY); // 计算曼哈顿距离
                }
            }
        }
        return distance;
    }

    /**
     * Estimated distance to goal. This method should
     * simply return the results of Manhattan when submitted to Gradescope.
     */
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /**
     * Returns true if this board's tile values are the same
     * position as y's
     */
    @Override
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;

        Board board = (Board) y;
        if (this.size() != board.size()) return false;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.tiles[i][j] != board.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N).append("\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}