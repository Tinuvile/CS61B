import org.junit.Test;

import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    //Uncomment this class
    // once you've created your CharacterComparator interface and OffByOne class.
    @Test
    public void testequalChars() {
        OffByOne obo = new OffByOne();
        assertTrue(obo.equalChars('a', 'b'));
        assertTrue(obo.equalChars('b', 'a'));
        assertTrue(obo.equalChars('A', 'B'));
        assertTrue(obo.equalChars('r', 'q'));
        assertTrue(obo.equalChars('&', '%'));
        assertFalse(obo.equalChars('a', 'e'));
        assertFalse(obo.equalChars('z', 'a'));
        assertFalse(obo.equalChars('a', 'a'));
        assertFalse(obo.equalChars('A', 'b'));
        assertFalse(obo.equalChars('a', '#'));
    }
}
