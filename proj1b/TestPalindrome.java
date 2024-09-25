import org.junit.Test;

import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    } //Uncomment this class once you've created your Palindrome class.

    @Test
    public void testisPalindrome() {
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertTrue(palindrome.isPalindrome("m"));
        assertFalse(palindrome.isPalindrome("horse"));
        assertFalse(palindrome.isPalindrome("rancor"));
        assertFalse(palindrome.isPalindrome("aaaaab"));
        assertFalse(palindrome.isPalindrome("abcbA"));
    }

    @Test
    public void testIsPalindrome() {
        Palindrome palindrome = new Palindrome();
        CharacterComparator obo = new OffByOne();
        CharacterComparator ob5 = new OffByN(5);

        assertFalse(palindrome.isPalindrome("aba", obo)); // 使用 OffByOne 进行检查
        assertTrue(palindrome.isPalindrome("abcba", obo)); // 使用 OffByOne 进行检查
        assertFalse(palindrome.isPalindrome("noon", obo)); // 不应该是 true
        assertTrue(palindrome.isPalindrome("detrude", obo)); // 应该是 true
        assertTrue(palindrome.isPalindrome("bibi", ob5)); // 使用 OffByN 进行检查
    }
}
