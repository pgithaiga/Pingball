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
 * 1. the position of this moving circle
 * 2. the velocity of this moving circle
 * 3. the trajectory of the ball that collides with this object and whether the ball collides or not
 */
public class MovingCircleTest {
    
    private static MovingCircle mc1 = new MovingCircle(new Vect(2.5,2.5), 0.25, new Vect(2.2,0));
    private static MovingCircle mc2 = new MovingCircle(new Vect(5.4,6), 0.25, new Vect(5.5,3.1));
    private static MovingCircle mc3 = new MovingCircle(new Vect(10.6,10.8), 0.25, new Vect(0,0));
    private static MovingCircle mc4 = new MovingCircle(new Vect(19.0,4.4), 0.25, new Vect(2.2,0));
    
    @Test public void testGetCirle() {
        assertEquals(mc1.getCircle(), new Circle(new Vect(2.5,2.5), 0.25));
        assertEquals(mc2.getCircle(), new Circle(new Vect(5.4,6.0), 0.25));
    }
    
    @Test public void testGetVelocity() {
        assertEquals(mc3.getVelocity(), new Vect(0.0,0.0));
        assertEquals(mc4.getVelocity(), new Vect(2.2,0.0));
    }
    
    @Test public void testGetMass() {
        assertTrue(new Double(mc2.getMass()).equals(4.0 / 3.0 * Math.PI * 0.25 * 0.25 * 0.25));
        assertTrue(new Double(mc3.getMass()).equals(4.0 / 3.0 * Math.PI * 0.25 * 0.25 * 0.25));
    }
    
    @Test public void testMoveBallByZeroTimeOrVelocity() {
        MovingCircle mc11 = new MovingCircle(new Vect(2.5,2.5), 0.25, new Vect(2.2,0));
        mc11.move(0.0);
        assertEquals(mc11.getCircle(), new Circle(new Vect(2.5,2.5), 0.25));

        MovingCircle mc31 = new MovingCircle(new Vect(10.6,10.8), 0.25, new Vect(0,0));
        mc31.move(10.0);
        assertEquals(mc31.getCircle(), new Circle(new Vect(10.6,10.8), 0.25));
    }
    
    @Test public void testSetCenter() {
        MovingCircle mc11 = new MovingCircle(new Vect(2.5,2.5), 0.25, new Vect(2.2,0));
        mc11.setCenter(new Vect(5,5));
        assertEquals(mc11.getCircle().getCenter(), new Vect(5,5));

        MovingCircle mc31 = new MovingCircle(new Vect(10.6,10.8), 0.25, new Vect(0,0));
        mc31.setCenter(new Vect(10.6,10.8));
        assertEquals(mc31.getCircle().getCenter(), new Vect(10.6,10.8));
    }
    
    @Test public void testSetVelocity() {
        MovingCircle mc21 = new MovingCircle(new Vect(5.4,6), 0.25, new Vect(5.5,3.1));
        mc21.setVelocity(new Vect(1,2));
        assertEquals(mc21.getVelocity(), new Vect(1,2));
    }
    
    @Test public void testCollisionWithStoppingBall() {
        MovingCircle mc11 = new MovingCircle(new Vect(2.5,2.5), 0.25, new Vect(2.2,0));
        Ball b = new Ball(new Vect(10,2.5), new Vect(0,0), "ball1", 0, 0, 0);
        Double t = mc11.timeUntilCollision(b);
        assertTrue( t >= 0.0 && t <= Double.POSITIVE_INFINITY );
        mc11.collide(b);
        assertEquals( b.getVelocity(), new Vect(2.2,0));
        assertEquals( mc11.getVelocity(), new Vect(0,0));
    }
    
    @Test public void testCollisionNeverHappen() {
        Ball b = new Ball(new Vect(2,2), new Vect(-1,-2), "ball2", 0, 0, 0);
        Double t = mc2.timeUntilCollision(b);
        assertTrue( t.equals(Double.POSITIVE_INFINITY) );
    }
    
    @Test public void testCollisionWithSomeVelocity() {
        MovingCircle mc11 = new MovingCircle(new Vect(2.5,2.5), 0.25, new Vect(2.2,0));
        Ball b = new Ball(new Vect(10,2.2), new Vect(-3.0,0.0001), "ball1", 0, 0, 0);
        Double t = mc11.timeUntilCollision(b);
        assertTrue( t >= 0.0 && t <= Double.POSITIVE_INFINITY );
        mc11.collide(b);
        assertFalse( b.getVelocity().equals(new Vect(2.2,0)) );
        assertFalse( mc11.getVelocity().equals(new Vect(0,0)));
    }
    
}
