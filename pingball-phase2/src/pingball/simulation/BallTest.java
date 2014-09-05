package pingball.simulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import physics.Circle;
import physics.Vect;
import pingball.simulation.collidable.Collidable;
import pingball.util.Pair;

/*
 * Testing Strategy:
 *
 * Partition inputs to operations on ball by testing negative,
 * zero, and positive values for physical measurements (where appropriate).
 * We will also verify that balls with different constants such as gravity
 * and friction behave appropriately (for example, a ball with no friction
 * and no gravity should move smoothly, and this behavior can be tested).
 */
public class BallTest {
    
    Ball b1 = new Ball(new Vect(3.0,3.0), new Vect(0.0,0.0), "b1", 0.0, 0.0, 0.0);
    Ball b2 = new Ball(new Vect(12.40,2.8), new Vect(-2.2,0.0), "b2", 0.0, 0.30, 0.0);
    Ball b3 = new Ball(new Vect(10.5,4.2), new Vect(5.5,3.0), "b3", 9.8, 0.0, 0.0);;
    Ball b4 = new Ball(new Vect(18.2,6.1), new Vect(-4.6,0.12), "b4", 9.8, 0.25, 0.30);
    
    @Test public void testBallGetCircle() {
        assertEquals(b1.getCircle().getCircle(), new Circle(new Vect(3.0,3.0), 0.25));
        assertEquals(b3.getCircle().getCircle(), new Circle(new Vect(10.5,4.2), 0.25));
    }
    
    @Test public void testBallSetPosition() {
        Ball b32 = new Ball(new Vect(10.5,4.2), new Vect(5.5,3.0), "b3", 9.8, 0.0, 0.0);;
        Ball b42 = new Ball(new Vect(18.2,6.1), new Vect(-4.6,0.12), "b4", 9.8, 0.25, 0.30);
        b32.setCenter(new Vect(5.5,5.5));
        b42.setCenter(new Vect(18.2,6.1));
        assertEquals(b32.getCircle().getCircle(), new Circle(new Vect(5.5,5.5), 0.25));
        assertEquals(b42.getCircle().getCircle(), new Circle(new Vect(18.2,6.1), 0.25));
    }
    
    @Test public void testBallGetSetVelocity() {
        Ball b22 = new Ball(new Vect(12.40,2.8), new Vect(-2.2,0.0), "b2", 0.0, 0.30, 0.0);
        assertEquals(b22.getVelocity(), new Vect(-2.2,0.0));
        b22.setVelocity(new Vect(1.1,-3.2));
        assertEquals(b22.getVelocity(), new Vect(1.1,-3.2));
    }
    
    @Test public void testBallGridLocationInteger() {
        Ball b22 = new Ball(new Vect(12,2), new Vect(-2.2,0.0), "b2", 0.0, 0.30, 0.0);
        GridLocation g = b22.getLocation();
        assertTrue(g.x() == 12);
        assertTrue(g.y() == 2);
    }
    
    @Test public void testBallGridLocationFloating() {
        Ball b22 = new Ball(new Vect(12.40,2.8), new Vect(-2.2,0.0), "b2", 0.0, 0.30, 0.0);
        GridLocation g = b22.getLocation();
        assertTrue(g.x() == 12);
        assertTrue(g.y() == 2);
    }
    
    @Test public void testBallGridRepresentation() {
        List<String> g = b1.gridRepresentation();
        assertEquals( g.size(), 1);
        assertEquals( g.get(0), "*");
    }
    
    @Test public void testBallEvoleNotMoving() {
        b1.evolve(10.0);
        assertEquals(b1.getCircle().getCircle(), new Circle(new Vect(3.0,3.0), 0.25));
    }
    
    @Test public void testBallEvolePositiveVelocity() {
        Ball b32 = new Ball(new Vect(1.5,4.2), new Vect(5.5,3.0), "b3", 0.0, 0.0, 0.0);;
        b32.evolve(2.0);
        assertFalse(b32.getCircle().getCircle().equals(new Circle(new Vect(1.5,4.2), 0.25)));
    }
    
    @Test public void testBallEvoleNegativeVelocity() {
        Ball b32 = new Ball(new Vect(11.5,14.2), new Vect(-0.2,-0.5), "b3", 0.0, 0.0, 0.0);;
        b32.evolve(0.5);
        assertFalse(b32.getCircle().getCircle().equals(new Circle(new Vect(11.5,14.2), 0.25)));   
    }
    
    @Test public void testBallEvoleWithGravity() {
        Ball b33 = new Ball(new Vect(10.5,4.2), new Vect(5.5,3.0), "b3", 9.8, 0.0, 0.0);;
        b33.evolve(0.5);
        assertFalse(b33.getVelocity().equals( new Vect(5.5, 3.0)));
    }
    
    @Test public void testBallEvoleWithFriction() {
        Ball b22 = new Ball(new Vect(12.40,2.8), new Vect(-2.2,0.0), "b2", 0.0, 0.30, 0.0);
        b22.evolve(0.25);
        assertFalse( b22.getVelocity().equals(new Vect(-2.2,0.0)));
    }
    
    @Test public void testBallCollisionNeverHappen() {
        Ball b12 = new Ball(new Vect(3.0,3.0), new Vect(-4.2,0.0), "b1", 0.0, 0.0, 0.0);
        Ball b22 = new Ball(new Vect(12.40,2.8), new Vect(2.2,0.0), "b2", 0.0, 0.30, 0.0);
        Pair<Double,Collidable> t = b12.timeUntilCollision(b22);
        assertTrue( t.getFirst().equals( Double.POSITIVE_INFINITY));
        
    }
    
    @Test public void testBallCollisionDifferentDirectionVelocity() {
        Ball b12 = new Ball(new Vect(3.0,3.0), new Vect(2.2,0.0), "b1", 0.0, 0.0, 0.0);
        Ball b22 = new Ball(new Vect(12.40,2.8), new Vect(-2.2,0.0), "b2", 0.0, 0.30, 0.0);
        Pair<Double,Collidable> t = b12.timeUntilCollision(b22);
        assertFalse( t.getFirst().equals( Double.POSITIVE_INFINITY));
        b12.collide(b22, t.getSecond());
    }
    
    @Test public void testBallCollsidionSameDirectionVelocityDifferentMagnitude() {
        Ball b12 = new Ball(new Vect(3.0,3.0), new Vect(4.2,0.0), "b1", 0.0, 0.0, 0.0);
        Ball b22 = new Ball(new Vect(12.40,2.8), new Vect(2.2,0.0), "b2", 0.0, 0.30, 0.0);
        Pair<Double,Collidable> t = b12.timeUntilCollision(b22);
        assertFalse( t.getFirst().equals( Double.POSITIVE_INFINITY));
        b12.collide(b22, t.getSecond());
        
    }
}
