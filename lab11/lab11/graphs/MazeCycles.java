package lab11.graphs;


import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int[] parentOf;
    private Stack<Integer> pathStack;
    private boolean hasCycle;

    public MazeCycles(Maze m) {
        super(m);
        parentOf = new int[maze.V()];
        pathStack = new Stack<>();
        hasCycle = false;
    }

    @Override
    public void solve() {
        dfs(0, -1);
    }

    // Helper methods go here
    private void dfs(int v, int parent) {
        marked[v] = true;
        pathStack.push(v);

        announce();

        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                parentOf[w] = v;
                dfs(w, v);
                if (hasCycle) {
                    return;
                } else if (w != parent) {
                    hasCycle = true;
                    findCycle(w);
                    return;
                }
            }
        }
        pathStack.pop();
    }

    private void findCycle(int start) {
        Stack<Integer> cycle = new Stack<>();
        cycle.push(start);

        while (!pathStack.isEmpty()) {
            int node = pathStack.pop();
            cycle.push(node);
            if (node == start) break;
        }

        int first = cycle.pop();
        while (!cycle.isEmpty()) {
            int next = cycle.pop();
            edgeTo[next] = first;
            announce();
            first = next;
        }

        edgeTo[start] = first;
        announce();
    }
}

