package pingball.simulation.gadget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.BeforeClass;
import org.junit.Test;

import physics.Vect;
import pingball.simulation.Ball;
import pingball.simulation.Board;
import pingball.simulation.Constants;
import pingball.simulation.GridLocation;
import pingball.util.StringUtils;

/*
 * Testing Strategy:
 *
 * Partition based on
 * 1. Check constructor
 * 2. Test action with 
 *    1. No ball inside 
 *    2. One ball inside 
 *    3. Two balls inside
 *    4. First shot ball hasn't left absorber
 *    5. First shot ball has left absorber 
 * 3. Checking if shot ball doesn't hit the top
 * 4. Check grid representation
 * 5. Check getLocation
 *  
 */
public class AbsorberTest {
    private static Vect center = new Vect(14,14);
    private static Vect velocity = new Vect(-1,-1);
    
    private static LinkedBlockingQueue<String> sendQueue, receiveQueue;
    private static Board board;
    private static Ball ball,ball2,ball3;
    private static File file;
    
    @BeforeClass public static void setUpBeforeClass() throws IOException {
        sendQueue = new LinkedBlockingQueue<String>();
        receiveQueue = new LinkedBlockingQueue<String>();
        board = new Board(sendQueue, receiveQueue, "testBoard", true, Constants.DEFAULT_GRAVITY, Constants.DEFAULT_FRICTION_MU1, Constants.DEFAULT_FRICTION_MU2);
        ball = new Ball(center, velocity, "TestBall", 10, 1, 1);
        ball2 = new Ball(center, velocity, "TestBall2", 10, 1, 1);
        ball3 = new Ball(center, velocity, "TestBall3", 10, 1, 1);
    }
    
    @Test public void testConstructor(){
        Absorber absorber = new Absorber(board, "TestAbsorber", new GridLocation(2,2), 10, 4);
        //assertTrue(absorber.checkRep());
    }
    
    @Test public void testActionNoBallInside() {
        Absorber absorber = new Absorber(board, "TestAbsorber", new GridLocation(2,2), 10, 4);
        absorber.action();
        //assertTrue(checkRep())
    }
    
    @Test public void testActionOneBallInside(){
        Absorber absorber = new Absorber(board, "TestAbsorber", new GridLocation(2,2), 10, 4);
        absorber.collide(ball, absorber.timeUntilCollision(ball).getSecond());
        assertEquals(new Vect(11.75,5.75),ball.getCircle().getCircle().getCenter());
        absorber.action();
        assertEquals(new Vect(0,-Constants.ABSORBER_SHOOT_VELOCITY),ball.getVelocity());
        //action does nothing
        //assertTrue(checkRep()) would be called inside classes
    }
    
    @Test public void testActionTwoBallsInside(){
        Absorber absorber = new Absorber(board, "TestAbsorber", new GridLocation(2,2), 10, 4);
        absorber.collide(ball, absorber.timeUntilCollision(ball).getSecond());
        assertEquals(new Vect(11.75,5.75),ball.getCircle().getCircle().getCenter());
        absorber.collide(ball2, absorber.timeUntilCollision(ball2).getSecond());
        assertEquals(new Vect(11.75,5.75),ball2.getCircle().getCircle().getCenter());
        absorber.action();
        assertEquals(new Vect(0,-Constants.ABSORBER_SHOOT_VELOCITY),ball.getVelocity());
        assertEquals(new Vect(0,0),ball2.getVelocity());
        //action does nothing
        //assertTrue(checkRep()) would be called inside classes
    }
    
    @Test public void testActionTwoBallsFirstBallHasntLeft(){
        Absorber absorber = new Absorber(board, "TestAbsorber", new GridLocation(2,2), 10, 4);
        absorber.collide(ball, absorber.timeUntilCollision(ball).getSecond());
        assertEquals(new Vect(11.75,5.75),ball.getCircle().getCircle().getCenter());
        absorber.collide(ball2, absorber.timeUntilCollision(ball2).getSecond());
        assertEquals(new Vect(11.75,5.75),ball2.getCircle().getCircle().getCenter());
        absorber.action();
        assertEquals(new Vect(0,-Constants.ABSORBER_SHOOT_VELOCITY),ball.getVelocity());
        absorber.action();
        assertEquals(new Vect(0,0),ball2.getVelocity());
        //action does nothing
        //assertTrue(checkRep()) would be called inside classes
    }
    
    @Test public void testShotBallDoesntCollideWithTop() {
        Absorber absorber = new Absorber(board, "TestAbsorber", new GridLocation(2,2), 10, 4);
        absorber.collide(ball, absorber.timeUntilCollision(ball).getSecond());
        assertEquals(new Vect(11.75,5.75),ball.getCircle().getCircle().getCenter());
        absorber.action();
        assertEquals(new Vect(0,-Constants.ABSORBER_SHOOT_VELOCITY),ball.getVelocity());
        assertTrue(absorber.timeUntilCollision(ball).getFirst().equals(Double.POSITIVE_INFINITY));
    }
    
    @Test public void testActionTwoBallsFirstBallHasLeft() {
        Absorber absorber = new Absorber(board, "TestAbsorber", new GridLocation(2,2), 10, 4);
        absorber.collide(ball, absorber.timeUntilCollision(ball).getSecond());
        assertEquals(new Vect(11.75,5.75),ball.getCircle().getCircle().getCenter());
        absorber.collide(ball2, absorber.timeUntilCollision(ball2).getSecond());
        assertEquals(new Vect(11.75,5.75),ball2.getCircle().getCircle().getCenter());
        absorber.action();
        assertEquals(new Vect(0,-Constants.ABSORBER_SHOOT_VELOCITY),ball.getVelocity());
        ball.evolve(0.1);
        absorber.action();
        assertEquals(new Vect(0,-Constants.ABSORBER_SHOOT_VELOCITY),ball2.getVelocity());
    }
    
    @Test public void testGridRepresentation(){
        Absorber absorber = new Absorber(board, "TestAbsorber", new GridLocation(2,2), 10, 4);
        assertEquals(StringUtils.repeat("=", 10),absorber.gridRepresentation().get(0));
        //assertTrue(checkRep()) would be called inside classes
    }
    
    @Test public void testGetLocation() {
        Absorber absorber = new Absorber(board, "TestAbsorber", new GridLocation(2,2), 10, 4);
        assertEquals(new GridLocation(2,2),absorber.getLocation());
    }
    
    
}
