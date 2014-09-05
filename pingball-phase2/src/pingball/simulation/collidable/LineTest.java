package pingball.simulation.collidable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import physics.Vect;
import pingball.simulation.Ball;

/*
 * Testing Strategy:
 *
 * partitioning based on:
 * 1. the position and orientation of this line
 * 2. the reflection coefficient of this line 
 * 3. the angular velocity and direction of rotation
 * 4. the trajectory of the ball that collides with this object and whether the ball collides or not
 */
public class LineTest {
    
    @Test public void testCollisionNeverHappen() {
        Line ll = new Line(new Vect(5,5), new Vect(13.5,5), 1.0, new Vect(5.0,5.0));
        Ball b1 = new Ball(new Vect(2.0,4.0), new Vect(2.0,-0.4), "b1", 9.8, .20, .20); 
        Double t = ll.timeUntilCollision(b1);
        assertTrue( t.equals(Double.POSITIVE_INFINITY) );
    }
    
    @Test public void testTimeUntilCollision() {
        Line ll = new Line(new Vect(5,5), new Vect(13.5,5), 1.0, new Vect(5.0,5.0));
        Ball b1 = new Ball(new Vect(2.0,4.0), new Vect(2.0,0.4), "b1", 9.8, .20, .20); 
        Double t = ll.timeUntilCollision(b1);
        assertTrue( t < Double.POSITIVE_INFINITY );
        assertTrue( t > 0.0 );
    }
    
    @Test public void testCollideDifferentReflecCoeff() {
        Line ll = new Line(new Vect(10,10), new Vect(8,20), 0.7, new Vect(10,10));
        Line ll2 = new Line(new Vect(10,10), new Vect(8,20), 0.55, new Vect(10,10));
        Line ll3 = new Line(new Vect(10,10), new Vect(8,20), 0.2, new Vect(10,10));

        Ball b1 = new Ball(new Vect(13,10), new Vect(-1,1), "b1", 9.8, .20, .20); 
        Ball b2 = new Ball(new Vect(13,10), new Vect(-1,1), "b2", 9.8, .20, .20);
        Ball b3 = new Ball(new Vect(13,10), new Vect(-1,1), "b3", 9.8, .20, .20);
        
        ll.collide(b1);
        ll2.collide(b2);
        ll3.collide(b3);
        
        assertTrue( b1.getVelocity().x() > b2.getVelocity().x() );
        assertTrue( b2.getVelocity().x() > b3.getVelocity().x() );
        assertTrue( b1.getVelocity().y() > b2.getVelocity().y() );
        assertTrue( b2.getVelocity().y() > b3.getVelocity().y() );
    }
    
    @Test public void testCollideOneReflectCoeff() {
        Line ll = new Line(new Vect(5,5), new Vect(13.5,5), 1.0, new Vect(5.0,5.0));
        Ball b0 = new Ball(new Vect(8.6,4), new Vect(0,1), "b1", 9.8, .20, .20); 
        Ball b1 = new Ball(new Vect(8.6,4), new Vect(0,1), "b1", 9.8, .20, .20); 
        ll.collide(b1);
        
        assertEquals( b1.getVelocity().x(), b0.getVelocity().x(),0.001);
        assertEquals( b1.getVelocity().y(), - b0.getVelocity().y(),0.001 );
    }
    
    @Test public void testCollideZeroReflectCoeff() {
        Line ll = new Line(new Vect(5,5), new Vect(13.5,5), 0.0, new Vect(5.0,5.0));
        Ball b1 = new Ball(new Vect(8.6,4), new Vect(0,1), "b1", 9.8, .20, .20); 
        ll.collide(b1);
        
        assertEquals( b1.getVelocity().x() , 0.0,0.001 );
        assertEquals( b1.getVelocity().y() , 0.0,0.001 );
    }
    
    @Test public void testCollideWhenRotating() {
        Line ll = new Line(new Vect(10,10), new Vect(10,20), 1.0, new Vect(10,10));
        Ball b1 = new Ball(new Vect(9,15), new Vect(1,0), "b1", 9.8, .20, .20); 
        Ball b2 = new Ball(new Vect(9,15), new Vect(1,0), "b2", 9.8, .20, .20);
        Ball b3 = new Ball(new Vect(9,15), new Vect(1,0), "b3", 9.8, .20, .20);
        
        ll.collide(b1);             // line not rotating
        ll.setRotating(-0.5);  
        ll.collide(b2);             // line rotating counterclockwise 
        ll.setRotating(0.5);
        ll.collide(b3);             // line rotating clockwise thus 

        assertTrue( b1.getVelocity().x() < b2.getVelocity().x() );
        assertTrue( b1.getVelocity().x() > b3.getVelocity().x() );
        assertEquals( b1.getVelocity().y() , b2.getVelocity().y(),0.001 );
        assertEquals( b1.getVelocity().y() , b3.getVelocity().y(),0.001 );
    }
    
    @Test public void testRotateClockwise() {
        Line ll = new Line(new Vect(5,5), new Vect(13.5,5), 1.0, new Vect(5.0,5.0));
        Line llRotated = new Line(new Vect(5,5), new Vect(13.5,5), 1.0, new Vect(5.0,5.0));
        llRotated.rotate(1.0);
        
        Double t1 = ll.timeUntilCollision(new Ball(new Vect(6,2), new Vect(0,1), "b1", 9.8, .20, .20));
        Double t2 = llRotated.timeUntilCollision(new Ball(new Vect(6,2), new Vect(0,1), "b1", 9.8, .20, .20));
        assertFalse( t1.equals( Double.POSITIVE_INFINITY ));
        assertFalse( t2.equals( Double.POSITIVE_INFINITY ));
        assertTrue( t1 < t2 ); // After the clockwise rotate, it should take more time to reach the line
    }
    
    @Test public void testRotateCounterClockwise() {
        Line ll = new Line(new Vect(4.5,5), new Vect(13.5,5), 1.0, new Vect(4.5,5.0));
        Line llRotated = new Line(new Vect(4.5,5), new Vect(13.5,5), 1.0, new Vect(4.5,5.0));
        llRotated.rotate(-0.5);
        
        Double t1 = ll.timeUntilCollision(new Ball(new Vect(6,2), new Vect(0,1), "b1", 9.8, .20, .20));
        Double t2 = llRotated.timeUntilCollision(new Ball(new Vect(6,2), new Vect(0,1), "b1", 9.8, .20, .20));
        assertFalse( t1.equals( Double.POSITIVE_INFINITY ));
        assertFalse( t2.equals( Double.POSITIVE_INFINITY ));
        assertTrue( t1 > t2 ); // After the clockwise rotate, it should take less time to reach the line
    }
}
