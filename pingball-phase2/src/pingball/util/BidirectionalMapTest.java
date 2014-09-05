package pingball.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/*
 * Testing Strategy:
 *
 * Partition pairs using maps parameterized by the same and
 * different types, and with different orders in the types.
 * Test pairs by inserting mappings that are new, mappings that
 * overlap (and overwrite old mappings). Test getting mappings
 * and reverse mappings, and ensure that the mappings work both ways.
 */
public class BidirectionalMapTest {
    
    @Test public void emptySizeTest() {
        BidirectionalMap<String, Integer> map = new BidirectionalMap<>();
        assertEquals(map.size(), 0);
    }

    @Test public void nonEmptySizeTest() {
        BidirectionalMap<String, Integer> map = new BidirectionalMap<>();
        map.putForward("one", 1);
        map.putReverse(2, "two");
        assertEquals(map.size(), 2);
    }

    @Test public void overwriteTest() {
        BidirectionalMap<String, Integer> map = new BidirectionalMap<>();
        map.putForward("one", 2);
        map.putForward("one", 1);
        assertEquals(map.size(), 1);
        assertEquals(map.getForward("one"), (Integer) 1);
    }

    @Test public void overwriteBackwardsTest() {
        BidirectionalMap<String, Integer> map = new BidirectionalMap<>();
        map.putForward("one", 2);
        map.putForward("two", 2);
        assertEquals(map.size(), 1);
        assertEquals(map.getReverse(2), "two");
    }

    @Test public void overwriteReverseTest() {
        BidirectionalMap<String, Integer> map = new BidirectionalMap<>();
        map.putForward("one", 2);
        map.putReverse(2, "two");
        map.putForward("three", 3);
        assertEquals(map.size(), 2);
        assertEquals(map.getForward("two"), (Integer) 2);
        assertEquals(map.getForward("three"), (Integer) 3);
    }

    @Test public void bidirectionalTest() {
        BidirectionalMap<String, String> map = new BidirectionalMap<>();
        map.putForward("one", "ONE");
        assertEquals(map.size(), 1);
        assertEquals(map.getForward("one"), "ONE");
        assertEquals(map.getReverse("ONE"), "one");
    }

    @Test public void containsOverwriteTest() {
        BidirectionalMap<String, String> map = new BidirectionalMap<>();
        map.putForward("one", "ONE");
        map.putForward("two", "TWO");
        map.putReverse("THREE", "three");
        assertEquals(map.size(), 3);
        assertTrue(map.containsForward("one"));
        assertTrue(map.containsReverse("TWO"));
    }

    @Test public void removeTest() {
        BidirectionalMap<String, String> map = new BidirectionalMap<>();
        map.putForward("one", "ONE");
        map.putForward("two", "TWO");
        map.putReverse("THREE", "three");
        map.removeForward("one");
        map.removeReverse("TWO");
        assertEquals(map.size(), 1);
        assertTrue(map.containsReverse("THREE"));
    }

}
