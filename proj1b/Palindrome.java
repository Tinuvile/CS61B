public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            deque.addLast(ch);
        }

        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);

        while(deque.size()>1){
            char first = deque.removeFirst();
            char last = deque.removeLast();
            if (first != last){
                return false;
            }
        }

        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        int left = 0;
        int right = word.length() - 1;
        while (left < right) {
            if (!cc.equalChars(word.charAt(left), word.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
