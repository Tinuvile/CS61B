import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 * @author Zhuhe Zhang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        String[] sorted = Arrays.copyOf(asciis, asciis.length);

        int maxLen = 0;
        for (String s : sorted) {
            if (s.length() > maxLen) {
                maxLen = s.length();
            }
        }

        for (int i = maxLen; i >= 0; i--) {
            sortHelperLSD(sorted, i);
        }

        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        final int RANGE = 256;
        int[] counts = new int[RANGE + 1];

        for (String s : asciis) {
            int c = (index < s.length()) ? s.charAt(index) : 0;
            counts[c + 1]++;
        }

        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }

        String[] temp = new String[asciis.length];
        for (int i = asciis.length - 1; i >= 0; i--) {
            String s = asciis[i];
            int c = (index < s.length()) ? s.charAt(index) : 0;
            temp[counts[c]] = s;
            counts[c]++;
        }

        System.arraycopy(temp, 0, asciis, 0, asciis.length);
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
