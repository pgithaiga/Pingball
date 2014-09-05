package pingball.simulation.gadget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.BeforeClass;
import org.junit.Test;

import physics.Vect;
import pingball.simulation.Ball;
import pingball.simulation.Board;
import pingball.simulation.Constants;
import pingball.simulation.GridLocation;
import pingball.simulation.collidable.Collidable;
import pingball.util.Pair;

/*
 * Testing Strategy:
 *
 * Partitioning based on 
 * 1. Location of portal on board
 * 2. Triggering each possible collidable in it.
 * 3. Colliding zero, one or multiple balls
 * 
 * We check the working of:
 * 1. The constructor
 * 2. The action
 * 3. The gridRepresentation
 * 4. The getMiddlePosition method
 * 5. The timeUntil collision method
 * 6. The collide method. 
 */
public class PortalTest {
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
        Portal portal = new Portal(board, "TestPortal", "otherBoard", "otherPortal", new GridLocation(2,2), sendQueue, receiveQueue);
        //assertTrue(checkRep()) would be called inside classes
    }
    
    @Test public void testMiddlePosition() {
        Portal portal1 = new Portal(board, "testPortal1", "otherBoard", "otherPortal", new GridLocation(5,10), sendQueue, receiveQueue);
        Portal portal2 = new Portal(board, "testPortal2", null, "otherPortal", new GridLocation(19,19), sendQueue, receiveQueue);

        assertEquals(portal1.getMiddlePosition(), new Vect(5.5,10.5));
        assertEquals(portal2.getMiddlePosition(), new Vect(19.5,19.5));
    }
    
    @Test public void testGridRepresentation() {
        Portal portal = new Portal(board, "testPortal1", "otherBoard", "otherPortal", new GridLocation(17,8), sendQueue, receiveQueue);
        assertEquals(portal.gridRepresentation(), Arrays.asList("X"));
    }
    
    @Test public void testAction() {
        Portal portal = new Portal(board, "testPortal1", "otherBoard", "otherPortal", new GridLocation(5,10), sendQueue, receiveQueue);
        portal.action();
        //checkrep will be called
    }
    
    @Test public void testTimeUntilCollisionLine() {
        Portal portal = new Portal(board, "testPortal1", "otherBoard", "otherPortal", new GridLocation(5,10), sendQueue, receiveQueue);
        Ball ball = new Ball(new Vect(9,10), new Vect(-1,0), "TestBall", 10, 1, 1);
        Pair<Double, Collidable> p1 = portal.timeUntilCollision(ball);
        assertEquals(p1.getFirst(),3,0.1);
    }
    
    @Test public void testTimeUntilCollisionDiagonal() {
        Portal portal = new Portal(board, null, "otherBoard", "otherPortal", new GridLocation(19,19), sendQueue, receiveQueue);
        Ball ball = new Ball(new Vect(19,10), new Vect(0.05,1), "TestBall", 10, 1, 1);
        Pair<Double, Collidable> p1 = portal.timeUntilCollision(ball);
        assertEquals(p1.getFirst(),8.75,0.1);
    }
    
    @Test public void testTimeUntilCollisionNeverCollide() {
        Portal portal = new Portal(board, "testPortal1", "otherBoard", "otherPortal", new GridLocation(11,10), sendQueue, receiveQueue);
        Ball ball = new Ball(new Vect(4,3), new Vect(-2.2,-1.2), "TestBall", 10, 1, 1);
        Pair<Double, Collidable> p1 = portal.timeUntilCollision(ball);
        assertTrue(p1.getFirst().equals(Double.POSITIVE_INFINITY));
    }
    
    @Test public void testCollideLocalPortalNotFound() {
        assertEquals(receiveQueue.size(), 0);
        Board boardx = new Board(sendQueue, receiveQueue, "testBoard", true, Constants.DEFAULT_GRAVITY, Constants.DEFAULT_FRICTION_MU1, Constants.DEFAULT_FRICTION_MU2);      
        Portal portal1 = new Portal(boardx, "testPortal1", null, "testPortal2", new GridLocation(1,10), sendQueue, receiveQueue);
        Ball ball = new Ball(new Vect(8,10), new Vect(20,-1.5), "TestBall", 10, 1, 1);
        portal1.collide(ball, null);
        assertEquals(receiveQueue.size(), 0);        
    }
    
    @Test public void testCollideLocalPortal() {
        assertEquals(receiveQueue.size(), 0);
        Board boardx = new Board(sendQueue, receiveQueue, "testBoard", true, Constants.DEFAULT_GRAVITY, Constants.DEFAULT_FRICTION_MU1, Constants.DEFAULT_FRICTION_MU2);      
        Portal portal1 = new Portal(boardx, "testPortal1", null, "testPortal2", new GridLocation(1,10), sendQueue, receiveQueue);
        Ball ball = new Ball(new Vect(4,3), new Vect(-2.2,-1.2), "TestBall", 10, 1, 1);
        boardx.addPortal("testPortal2", 9, 9, null, "otherPortal");
        portal1.collide(ball, null);
        assertEquals(receiveQueue.size(), 1);        
    }
    
    @Test public void testCollideConnectedPortal() {
        Portal portal1 = new Portal(board, "testPortal1", "otherPortal", "testPortal2", new GridLocation(1,10), sendQueue, receiveQueue);
        Ball ball = new Ball(new Vect(12.2,12.2), new Vect(2.46,0), "TestBall", 10, 1, 1);
        int z = sendQueue.size();
        portal1.collide(ball, null);
        assertEquals(sendQueue.size(), z+1);        
    }
}
