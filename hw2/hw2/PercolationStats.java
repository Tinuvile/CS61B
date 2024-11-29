package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private final double[] fractions; // 存储每次实验中开放站点的比例
    private final int T; // 实验次数
    private final int N; // 网格大小

    // 在 N x N 网格上进行 T 次独立实验
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N 和 T 必须大于 0");
        }

        this.N = N;
        this.T = T;
        this.fractions = new double[T];

        for (int t = 0; t < T; t++) {
            Percolation percolation = pf.make(N); // 使用 PercolationFactory 创建新实例
            // 进行实验直到系统渗透
            while (!percolation.percolates()) {
                // 随机选择一个被阻塞的站点
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                // 打开该站点
                percolation.open(row, col);
            }
            // 记录当前实验的开放站点的比例
            fractions[t] = (double) percolation.numberOfOpenSites() / (N * N);
        }
    }

    // 渗透阈值的样本均值
    public double mean() {
        return StdStats.mean(fractions);
    }

    // 渗透阈值的样本标准差
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    // 95% 置信区间的低端点
    public double confidenceLow() {
        double mean = mean();
        double stddev = stddev();
        return mean - 1.96 * stddev / Math.sqrt(T);
    }

    // 95% 置信区间的高端点
    public double confidenceHigh() {
        double mean = mean();
        double stddev = stddev();
        return mean + 1.96 * stddev / Math.sqrt(T);
    }

    // 测试 PercolationStats 数据类型（本示例中未实现）
    /*
    public static void main(String[] args) {
        // 这里可以添加测试代码
    }
     */
}
