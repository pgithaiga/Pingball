package pingball.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/*
 * Testing Strategy:
 *
 * Partition pairs using pairs parameterized by the same and
 * different types, and with different orders in the types.
 * Test pairs containing identical first and second elements,
 * and pairs with different elements
 */
public class PairTest {
    
    private static Pair<Integer, Integer> pair1 = new Pair<>(-1, 2);
    private static Pair<Integer, Integer> pair2 = new Pair<>(2, -1);
    private static Pair<Integer, String> pair3 = new Pair<>(2, "fish");
    private static Pair<String, Integer> pair4 = new Pair<>("fish", 2);
    private static Pair<String, String> pair5 = new Pair<>("first", "second");

    @Test public void testGetFirstIntegers() {
        assertEquals(pair1.getFirst(), (Integer) (-1));
    }

    @Test public void testGetFirstStrings() {
        assertEquals(pair5.getFirst(), "first");
    }

    @Test public void testGetSecondIntegers() {
        assertEquals(pair1.getSecond(), (Integer) 2);
    }

    @Test public void testGetSecondStrings() {
        assertEquals(pair5.getSecond(), "second");
    }

    @Test public void testEqualsLiteral() {
        assertTrue(pair1.equals(Pair.of(-1, 2)));
    }

    @Test public void testNotEqualsOrdered() {
        assertFalse(pair1.equals(pair2));
    }

    @Test public void testNotEqualsDifferentTypes() {
        assertFalse(pair1.equals(pair5));
    }

    @Test public void testNotEqualsDifferentTypesOrdered() {
        assertFalse(pair3.equals(pair4));
    }

    @Test public void toStringIntegers() {
        assertEquals(pair1.toString(), "(-1,2)");
    }

    @Test public void toStringIntegerString() {
        assertEquals(pair3.toString(), "(2,fish)");
    }

    @Test public void toStringStrings() {
        assertEquals(pair5.toString(), "(first,second)");
    }

}
