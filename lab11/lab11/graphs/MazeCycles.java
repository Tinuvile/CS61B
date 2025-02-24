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
    private Stack<Integer> stack = new Stack<Integer>();

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
    }

    @Override
    public void solve() {
        // TODO: Your code here!
    }

    // Helper methods go here
}

