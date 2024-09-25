public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == 1;
    }

    private boolean isPalindrome(String word) {
        int left = 0;
        int right = word.length() - 1;
        while (left < right) {
            if (!equalChars(word.charAt(left), word.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    private boolean isPalindrome(char[] word) {
        int left = 0;
        int right = word.length - 1;
        while (left < right) {
            if (!equalChars(word[left], word[right])) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    private boolean isPalindrome(String word, CharacterComparator cc) {
        int left = 0;
        int right = word.length() - 1;
        while (left < right) {
            if (!cc.equalChars(word.charAt(left), word.charAt(right))) {
                return false; // 不符合条件，返回 false
            }
            left++;
            right--;
        }
        return true; // 所有字符均符合条件，返回 true
    }
}
