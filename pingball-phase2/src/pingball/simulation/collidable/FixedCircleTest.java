package pingball.simulation.collidable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import physics.Circle;
import physics.Vect;
import pingball.simulation.Ball;

/*
 * Testing Strategy:
 *
 * partitioning based on:
 * 1. the position of this fixed circle
 * 2. the reflection coefficient of this fixed circle 
 * 3. the angular velocity and direction of rotation
 * 4. the trajectory of the ball that collides with this object and whether the ball collides or not
 */
public class FixedCircleTest {
    
    private static FixedCircle fc1 = new FixedCircle(new Vect(2.0,3.0), 0, 1.0);
    private static FixedCircle fc2 = new FixedCircle(new Vect(15.5, 15.5), 0, 0.9, new Vect(15.5,15.5));
    private static FixedCircle fc3 = new FixedCircle(new Vect(10,10), 0, 1, new Vect(0,0));
    
    @Test public void testGetCircle() {
        assertEquals(fc1.getCircle(), new Circle(new Vect(2.0,3.0),0));
        assertEquals(fc2.getCircle(), new Circle(new Vect(15.5, 15.5),0));
    }
    
    @Test public void testNotCollidingAtAll() {
        Ball b = new Ball(new Vect(19,19), new Vect(0,0), "b", 0, 0, 0);
        assertTrue(Double.POSITIVE_INFINITY == fc1.timeUntilCollision(b));
        
        Ball b2 = new Ball(new Vect(15,15), new Vect(1,1), "c", 0, 0, 0);
        assertTrue(Double.POSITIVE_INFINITY == fc1.timeUntilCollision(b2));
    }
    
    @Test public void testReflectionCoeff1() {
        FixedCircle fc32 = new FixedCircle(new Vect(10,10), 0, 0, new Vect(0,0));
        Ball b1 = new Ball(new Vect(15,15), new Vect(-1,-1), "c", 0, 0, 0);
        Double t = fc32.timeUntilCollision(b1);
        assertTrue( t >= 0 && t < Double.POSITIVE_INFINITY );
        fc32.collide(b1);
        assertEquals( b1.getVelocity(), new Vect(0,0));
    }
    
    @Test public void testReflectionCoeffLessThan1() {
        FixedCircle fc32 = new FixedCircle(new Vect(10,10), 0, 0.9, new Vect(0,0));
        Ball b1 = new Ball(new Vect(15,15), new Vect(-1,-1), "c", 0, 0, 0);
        Double t = fc32.timeUntilCollision(b1);
        assertTrue( t >= 0 && t < Double.POSITIVE_INFINITY );
        fc32.collide(b1);
        assertFalse( b1.getVelocity().equals(new Vect(0,0)) || b1.getVelocity().equals(new Vect(1,1)) );
    }
    
    @Test public void testRlectionCoeffZero() {
        FixedCircle fc32 = new FixedCircle(new Vect(10,10), 0, 0, new Vect(0,0));
        Ball b1 = new Ball(new Vect(15,15), new Vect(-1,-1), "c", 0, 0, 0);
        Double t = fc32.timeUntilCollision(b1);
        assertTrue( t >= 0 && t < Double.POSITIVE_INFINITY );
        fc32.collide(b1);
        assertEquals( b1.getVelocity(), new Vect(0,0));
    }
    
    @Test public void testCollideWhenRotating() {
        fc3.setRotating(1);
        Ball b1 = new Ball(new Vect(9,11), new Vect(0,0), "b", 0, 0, 0);
        Ball b2 = new Ball(new Vect(11,11), new Vect(0,0), "c", 0, 0, 0);
        
        Double t1 = fc3.timeUntilCollision(b1);
        Double t2 = fc3.timeUntilCollision(b2);
        
        assertTrue( t1 > 0 && t1 < Double.POSITIVE_INFINITY );
        assertTrue( t2.equals(Double.POSITIVE_INFINITY) );
        
        fc3.collide(b1);
        assertFalse( b1.getVelocity().equals(new Vect(0,0)));
    }
}
