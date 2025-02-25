package lab11.graphs;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int x1 = maze.toX(v);
        int y1 = maze.toY(v);
        int x2 = maze.toX(t);
        int y2 = maze.toY(t);
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        /* You do not have to use this method. */
        int minVertex = -1;
        int minDistance = Integer.MAX_VALUE;

        for (int v = 0; v < maze.V(); v++) {
            if (!marked[v] && distTo[v] + h(v) < minDistance) {
                minDistance = distTo[v] + h(v);
                minVertex = v;
            }
        }
        return minVertex;
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(v -> distTo[v] + h(v)));

        distTo[s] = 0;
        pq.add(s);

        while (!pq.isEmpty() && !targetFound) {
            int v = pq.poll();
            if (v == t) {
                targetFound = true;
                break;
            }
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    int newDist = distTo[v] + 1;
                    if (newDist < distTo[w]) {
                        distTo[w] = newDist;
                        edgeTo[w] = v;
                        pq.add(w);
                    }
                }
            }
            marked[v] = true;
            announce();
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

