package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver {
    private final int moves; //从初始状态到目标状态所需最小步骤数
    private final Iterable<WorldState> solution; //保存从初始状态到目标状态的WorldState序列，表示解决方案路径

    /**
     * Constructor which solves the puzzle, computing everything
     * necessary for moves() and solution() to not have to solve
     * the problem again. Solves the puzzle using the A* algorithm.
     * Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        // 优先级队列
        MinPQ<SearchNode> pq = new MinPQ<>();
        // 已访问的状态集合
        Set<WorldState> visited = new HashSet<>();
        // 将初始节点添加到队列中
        SearchNode startNode = new SearchNode(initial, 0, initial.estimatedDistanceToGoal(), null);
        pq.insert(startNode);
        visited.add(initial);

        SearchNode solutionNode = null;

        while (!pq.isEmpty()) {
            SearchNode currentNode = pq.delMin();

            // 检查当前状态是否为目标状态
            if (currentNode.state.isGoal()) {
                solutionNode = currentNode;
                break;
            }

            // 遍历邻接状态
            for (WorldState neighbor : currentNode.state.neighbors()) {
                // 计算新的移动次数
                int newMoves = currentNode.moves + 1;

                // 仅在未访问且与父类不同的情况下插入新节点
                if (!visited.contains(neighbor) && (currentNode.parent == null || !currentNode.parent.state.equals(neighbor))) {
                    visited.add(neighbor);
                    SearchNode newNode = new SearchNode(neighbor, newMoves, newMoves + neighbor.estimatedDistanceToGoal(), currentNode);
                    pq.insert(newNode);
                }
            }
        }

        // 设置结果
        if (solutionNode != null) {
            this.moves = solutionNode.moves;
            this.solution = constructSolution(solutionNode);  // 从目标节点回溯生成路径
        } else {
            this.moves = -1;  // 表示无解的情况
            this.solution = null;
        }
    }

    /**
     * Returns the minimum number of moves to solve the puzzle
     * starting at the initial WorldState.
     */
    public int moves() {
        return moves;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     */
    public Iterable<WorldState> solution() {
        return solution;
    }

    // 根据解决节点的父节点回溯构建解决方案路径
    private Iterable<WorldState> constructSolution(SearchNode node) {
        List<WorldState> path = new LinkedList<>();
        while (node != null) {
            path.add(node.state);
            node = node.parent;  // 回溯到父节点
        }
        Collections.reverse(path);  // 反转路径以确保从起始到目标状态
        return path;
    }

    // 内部类，表示搜索状态的节点
    private class SearchNode implements Comparable<SearchNode> {
        private final WorldState state;
        private final int moves;
        private final int priority;  // f(n) = g(n) + h(n)
        private final SearchNode parent;  // 指向父节点的引用

        public SearchNode(WorldState state, int moves, int priority, SearchNode parent) {
            this.state = state;
            this.moves = moves;
            this.priority = priority; // 优先级
            this.parent = parent;
        }

        @Override
        public int compareTo(SearchNode other) {
            // 如果优先级相同，可能可以根据移动次数进行次要比较
            if (this.priority != other.priority) {
                return Integer.compare(this.priority, other.priority);
            }
            return Integer.compare(this.moves, other.moves); // 或根据其他条件。如果需要。
        }
    }
}