package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int N = oomages.size();

        if (N == 0) {
            return true;
        }

        int minBucketSize = N / 50;
        int maxBucketSize = (int) (N / 2.5);

        int[] buckets = new int[M];

        for (Oomage oomage : oomages) {
            int bucketNum = (oomage.hashCode() & 0x7FFFFFFF) % M;
            buckets[bucketNum]++;
        }

        for (int count : buckets) {
            if (count < minBucketSize || count > maxBucketSize) {
                return false;
            }
        }

        return true;
    }
}
