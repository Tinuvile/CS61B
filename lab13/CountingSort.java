import java.util.Arrays;

/**
 * Class with 2 ways of doing Counting sort, one naive way and one "better" way
 *
 * @author Akhil Batra, Alexander Hwang
 * @author Zhuhe Zhang
 *
 **/
public class CountingSort {
    /**
     * Counting sort on the given int array. Returns a sorted version of the array.
     * Does not touch original array (non-destructive method).
     * DISCLAIMER: this method does not always work, find a case where it fails
     *
     * @param arr int array that will be sorted
     * @return the sorted array
     */
    public static int[] naiveCountingSort(int[] arr) {
        // find max
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = max > i ? max : i;
        }

        // gather all the counts for each value
        int[] counts = new int[max + 1];
        for (int i : arr) {
            counts[i]++;
        }

        // when we're dealing with ints, we can just put each value
        // count number of times into the new array
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }

        // however, below is a more proper, generalized implementation of
        // counting sort that uses start position calculation
        int[] starts = new int[max + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        int[] sorted2 = new int[arr.length];
        for (int i = 0; i < arr.length; i += 1) {
            int item = arr[i];
            int place = starts[item];
            sorted2[place] = item;
            starts[item] += 1;
        }

        // return the sorted array
        return sorted;
    }

    /**
     * Counting sort on the given int array, must work even with negative numbers.
     * Note, this code does not need to work for ranges of numbers greater
     * than 2 billion.
     * Does not touch original array (non-destructive method).
     *
     * @param arr int array that will be sorted
     */
    public static int[] betterCountingSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }

        // 1. 创建保护性副本
        int[] workArr = Arrays.copyOf(arr, arr.length);

        // 2. 计算数值范围
        int min = workArr[0];
        int max = workArr[0];
        for (int num : workArr) {
            if (num < min) min = num;
            if (num > max) max = num;
        }

        // 3. 初始化计数数组
        int range = max - min + 1;
        int[] counts = new int[range];

        // 4. 统计元素出现次数
        for (int num : workArr) {
            counts[num - min]++;
        }

        // 5. 计算累计位置（稳定排序关键）
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }

        // 6. 生成排序结果
        int[] sorted = new int[workArr.length];
        for (int i = workArr.length - 1; i >= 0; i--) {
            int num = workArr[i];
            int pos = counts[num - min] - 1;
            sorted[pos] = num;
            counts[num - min]--;
        }

        return sorted;
    }
}
