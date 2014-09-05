package pingball.simulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import physics.Vect;

/*
 * Testing Strategy:
 *
 * Partition grid location by the sign of each value and whether both values are the same
 * We also partition the input space for add method based on the same strategy, and the
 * test case of the method that input list based on the list length 0, 1, and >1.
 */
public class GridLocationTest {
    
    @Test public void testGetGridLocationPositivePositive() {
        GridLocation x = new GridLocation(20,30);
        assertTrue(x.getFirst() == 20);
        assertTrue(x.getSecond() == 30);
        assertEquals(x.toVect(), new Vect(20.0, 30.0));
    }

    @Test public void testGetGridLocationPositiveZero() {
        GridLocation x = new GridLocation(20,0);
        assertTrue(x.getFirst() == 20);
        assertTrue(x.getSecond() == 0);
        assertEquals(x.toVect(), new Vect(20.0, 0.0));      
    }
    
    @Test public void testGetGridLocationPositiveNegative() {
        GridLocation x = new GridLocation(5,-10);
        assertTrue(x.getFirst() == 5);
        assertTrue(x.getSecond() == -10);
        assertEquals(x.toVect(), new Vect(5.0, -10.0));
    }
    
    @Test public void testGetGridLocationZeroZero() {
        GridLocation x = new GridLocation(0,0);
        assertTrue(x.getFirst() == 0);
        assertTrue(x.getSecond() == 0);
        assertEquals(x.toVect(), new Vect(0, 0.0));
        
    }

    @Test public void testGetGridLocationZeroNegative() {
        GridLocation x = new GridLocation(0,Integer.MIN_VALUE);
        assertTrue(x.getFirst() == 0);
        assertTrue(x.getSecond() == Integer.MIN_VALUE);
        assertEquals(x.toVect(), new Vect(0, (double)Integer.MIN_VALUE));
        
    }
    
    @Test public void testGetGridLocationNegativeNegative() {
        GridLocation x = new GridLocation(-4,-1);
        assertTrue(x.getFirst() == -4);
        assertTrue(x.getSecond() == -1);
        assertEquals(x.toVect(), new Vect(-4.0, -1.0));
        
    }
    
    @Test public void testAddGridLocationWithPositivePositive() {
        GridLocation x = new GridLocation(0,-10);
        x = x.add(Integer.MAX_VALUE, 20);
        assertTrue(x.getFirst() == Integer.MAX_VALUE);
        assertTrue(x.getSecond() == 10);
        assertEquals(x.toVect(), new Vect((double) Integer.MAX_VALUE, 10));
        
    }
    
    @Test public void testAddGridLocationWithZero() {
        GridLocation x = new GridLocation(0,0);
        GridLocation y = x.add(0, 0);
        assertTrue(y.getFirst() == 0);
        assertTrue(y.getSecond() == 0);
        assertEquals(y.toVect(), new Vect(0.0, 0.0));      
    }
        
    @Test public void testAddGridLocationWithNegativeNegative() {
        GridLocation x = new GridLocation(0,0);
        GridLocation y = x.add(-50, -100);
        assertTrue(y.getFirst() == -50);
        assertTrue(y.getSecond() == -100);
        assertEquals(y.toVect(), new Vect(-50.0, -100.0));      
        
    }
    
    @Test public void testAddGridLocationWithArrayAllPossibilities() {
        GridLocation x = new GridLocation(20,30);
        x = x.add(new int[]{1,-2});
        x = x.add(new int[]{0,3});
        x = x.add(new int[]{-4,0});
        x = x.add(new int[]{2,2});
        assertTrue(x.getFirst() == 19);
        assertTrue(x.getSecond() == 33);
        assertEquals(x.toVect(), new Vect(19.0, 33.0));
        
    }
}
