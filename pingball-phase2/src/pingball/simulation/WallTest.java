package pingball.simulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.BeforeClass;
import org.junit.Test;

import physics.Vect;
import pingball.simulation.collidable.Collidable;
import pingball.util.Pair;

/*
 * Testing Strategy:
 *
 * Partitioning the input space by the type of the wall the its state of connection.
 * We also partition the input space for the ball by the velocity of the ball (0 or nonzero), 
 * and by the fact that the ball collides this wall or not.
 */
public class WallTest {
    
    private static LinkedBlockingQueue<String> sendQueue, receiveQueue;
    private static Board board;
    private static Ball ball,ball2,ball3;
    private static File file;
    
    @BeforeClass public static void setUpBeforeClass() throws IOException {
        sendQueue = new LinkedBlockingQueue<String>();
        receiveQueue = new LinkedBlockingQueue<String>();
        board = new Board(sendQueue, receiveQueue, "testBoard", true, Constants.DEFAULT_GRAVITY, Constants.DEFAULT_FRICTION_MU1, Constants.DEFAULT_FRICTION_MU2);
    }
    
    @Test public void testConnectDisconnectWallHorizontal() {
        Wall w1 = new Wall(board, Wall.Side.BOTTOM, sendQueue);
        w1.connect("anotherBoard");
        w1.connect("anotherBoard");
        w1.disconnect();
        w1.disconnect();
        // The Rep Invariant will be checked in checkRep()
    }
    
    @Test public void testConnectDisconnectWallVertical() {
        Wall w1 = new Wall(board, Wall.Side.LEFT, sendQueue);
        w1.connect("anotherBoard");
        w1.connect("anotherBoard");
        w1.disconnect();
        w1.disconnect();
        // The Rep Invariant will be checked in checkRep()
    }
    
    @Test public void testGridLocationHorizontal() {
        Wall w1 = new Wall(board, Wall.Side.TOP, sendQueue);
        Wall w2 = new Wall(board, Wall.Side.BOTTOM, sendQueue);
        assertTrue( w1.getLocation().x() == -1 && w1.getLocation().y() == -1 );
        assertTrue( w2.getLocation().x() == -1 && w2.getLocation().y() == board.getHeight() );
    }
    
    @Test public void testGridLocationVertical() {
        Wall w1 = new Wall(board, Wall.Side.LEFT, sendQueue);
        Wall w2 = new Wall(board, Wall.Side.RIGHT, sendQueue);
        assertTrue( w1.getLocation().x() == -1 && w1.getLocation().y() == -1 );
        assertTrue( w2.getLocation().x() == board.getHeight() && w2.getLocation().y() == -1 );
    }
    
    @Test public void testGridRepresentationWithConnectionHorizontal() {
        Wall w1 = new Wall(board, Wall.Side.TOP, sendQueue);
        Wall w2 = new Wall(board, Wall.Side.BOTTOM, sendQueue);
        w1.connect("______");
        w2.connect("testBOARD123");
        List<String> w1r = w1.gridRepresentation();
        List<String> w2r = w2.gridRepresentation();
        assertTrue( w1r.size() == 1 && w2r.size() == 1 );
        assertEquals( w1r.get(0), "........______........");
        assertEquals( w2r.get(0), ".....testBOARD123.....");
    }

    @Test public void testGridRepresentationWithConnectionVertical() {
        Wall w1 = new Wall(board, Wall.Side.LEFT, sendQueue);
        Wall w2 = new Wall(board, Wall.Side.RIGHT, sendQueue);
        w1.connect("THISISANOTHERTEST");
        w2.connect("______________________");
        List<String> w1r = w1.gridRepresentation();
        List<String> w2r = w2.gridRepresentation();
        assertTrue( w1r.size() == 22 && w2r.size() == 22 );
        assertEquals( w1r.get(0), "."); assertEquals( w1r.get(1), "."); assertEquals( w1r.get(2), "T");
        assertEquals( w1r.get(3), "H"); assertEquals( w1r.get(4), "I"); assertEquals( w1r.get(5), "S");
        assertEquals( w1r.get(6), "I"); assertEquals( w1r.get(7), "S"); assertEquals( w1r.get(8), "A");
        assertEquals( w1r.get(9), "N"); assertEquals( w1r.get(10), "O"); assertEquals( w1r.get(11), "T");
        assertEquals( w1r.get(12), "H"); assertEquals( w1r.get(13), "E"); assertEquals( w1r.get(14), "R");
        assertEquals( w1r.get(15), "T"); assertEquals( w1r.get(16), "E"); assertEquals( w1r.get(17), "S");
        assertEquals( w1r.get(18), "T"); assertEquals( w1r.get(19), "."); assertEquals( w1r.get(20), "."); assertEquals( w1r.get(21), ".");
        
        assertEquals( w2r.get(0), "_"); assertEquals( w2r.get(1), "_"); assertEquals( w2r.get(2), "_");
        assertEquals( w2r.get(3), "_"); assertEquals( w2r.get(4), "_"); assertEquals( w2r.get(5), "_");
        assertEquals( w2r.get(6), "_"); assertEquals( w2r.get(7), "_"); assertEquals( w2r.get(8), "_");
        assertEquals( w2r.get(9), "_"); assertEquals( w2r.get(10), "_"); assertEquals( w2r.get(11), "_");
        assertEquals( w2r.get(12), "_"); assertEquals( w2r.get(13), "_"); assertEquals( w2r.get(14), "_");
        assertEquals( w2r.get(15), "_"); assertEquals( w2r.get(16), "_"); assertEquals( w2r.get(17), "_");
        assertEquals( w2r.get(18), "_"); assertEquals( w2r.get(19), "_"); assertEquals( w2r.get(20), "_"); assertEquals( w2r.get(21), "_");
    }
    
    @Test public void testGridRepresntationNoConnectionHorizontal() {
        Wall w1 = new Wall(board, Wall.Side.TOP, sendQueue);
        Wall w2 = new Wall(board, Wall.Side.BOTTOM, sendQueue);
        w1.connect("______");
        w1.disconnect();
        List<String> w1r = w1.gridRepresentation();
        List<String> w2r = w2.gridRepresentation();
        assertTrue( w1r.size() == 1 && w2r.size() == 1 );
        assertEquals( w1r.get(0), "......................");
        assertEquals( w2r.get(0), "......................");  
    }
    
    @Test public void testGridRepresntationNoConnectionVertical() {
        Wall w1 = new Wall(board, Wall.Side.LEFT, sendQueue);
        Wall w2 = new Wall(board, Wall.Side.RIGHT, sendQueue);
        w1.connect("______");
        w1.disconnect();
        List<String> w1r = w1.gridRepresentation();
        List<String> w2r = w2.gridRepresentation();
        assertTrue( w1r.size() == 22 && w2r.size() == 22 );
        for(String s: w1r) {
            assertEquals(s,".");
        }
        for(String s: w2r) {
            assertEquals(s,".");
        }
    }
    
    @Test public void testEvoleHorizontalVertical() {
        Wall w1 = new Wall(board, Wall.Side.TOP, sendQueue);
        w1.evolve(20.0);
        Wall w2 = new Wall(board, Wall.Side.RIGHT, sendQueue);
        w1.evolve(0.5);
        // The Rep Invariant will be checked in checkRep()
    }
    
    @Test public void testCollideBallNotMovingHorizontalVertical() {
        Wall w1 = new Wall(board, Wall.Side.TOP, sendQueue);
        Wall w2 = new Wall(board, Wall.Side.RIGHT, sendQueue);
        Pair<Double, Collidable> t = w1.timeUntilCollision(new Ball(new Vect(10,10), new Vect(0,0), "b1", 10.0, 0.2, 0.2));
        Pair<Double, Collidable> t2 = w2.timeUntilCollision(new Ball(new Vect(19.2,0.56), new Vect(0,0), "b1", 0, 0.2, 0.2));
        assertTrue( t.getFirst().equals(Double.POSITIVE_INFINITY));
        assertTrue( t2.getFirst().equals(Double.POSITIVE_INFINITY));
    }
    
    @Test public void testCollideNeverHappenHorizontal() {
        Wall w1 = new Wall(board, Wall.Side.BOTTOM, sendQueue);
        Pair<Double, Collidable> t = w1.timeUntilCollision(new Ball(new Vect(8.2,14.7), new Vect(2.2,-2.5), "b1", 10.0, 0.2, 0.2));
        assertTrue( t.getFirst().equals(Double.POSITIVE_INFINITY));        
    }

    @Test public void testCollideNeverHappenVertical() {
        Wall w1 = new Wall(board, Wall.Side.LEFT, sendQueue);
        Pair<Double, Collidable> t = w1.timeUntilCollision(new Ball(new Vect(8.2,14.7), new Vect(0.25, 1.2), "b1", 0.12, 0.0, 2));
        assertTrue( t.getFirst().equals(Double.POSITIVE_INFINITY));        
    }
    
    @Test public void testCollideWithConnectionHorizontal() throws IOException {
        LinkedBlockingQueue<String> sendQueue2 = new LinkedBlockingQueue<String>();
        LinkedBlockingQueue<String> receiveQueue2 = new LinkedBlockingQueue<String>();
        Board board2 = new Board(sendQueue2, receiveQueue2, "testBoard", true, Constants.DEFAULT_GRAVITY, Constants.DEFAULT_FRICTION_MU1, Constants.DEFAULT_FRICTION_MU2);
        Wall w1 = new Wall(board2, Wall.Side.TOP, sendQueue2);
        Ball b1 = new Ball(new Vect(4.7,2.13), new Vect(-0.2,-5), "b1", 10.0, 0.2, 0.2);
        board2.addBall(b1);
        board2.addGameObject(w1);
        Pair<Double, Collidable> t = w1.timeUntilCollision(b1);
        assertFalse( t.getFirst().equals(Double.POSITIVE_INFINITY));
        w1.collide(b1, t.getSecond());
        // The Rep Invariant will be checked in checkRep()
    }
    
    @Test public void testCollideWithConnectionVertical() throws IOException {
        LinkedBlockingQueue<String> sendQueue2 = new LinkedBlockingQueue<String>();
        LinkedBlockingQueue<String> receiveQueue2 = new LinkedBlockingQueue<String>();
        Board board2 = new Board(sendQueue2, receiveQueue2, "testBoard", true, Constants.DEFAULT_GRAVITY, Constants.DEFAULT_FRICTION_MU1, Constants.DEFAULT_FRICTION_MU2);
        Wall w1 = new Wall(board2, Wall.Side.RIGHT, sendQueue2);
        Ball b1 = new Ball(new Vect(4.7,2.13), new Vect(10.12,2.4), "b1", 9.8, 1.45, 2);
        board2.addBall(b1);
        board2.addGameObject(w1);
        Pair<Double, Collidable> t = w1.timeUntilCollision(b1);
        assertFalse( t.getFirst().equals(Double.POSITIVE_INFINITY));
        w1.collide(b1, t.getSecond());
        // The Rep Invariant will be checked in checkRep()
    }
    
    @Test public void testCollideWithOutConnectionHorizontal() {
        Wall w1 = new Wall(board, Wall.Side.BOTTOM, sendQueue);
        Ball b1 = new Ball(new Vect(8.2,14.7), new Vect(2.2,2.5), "b1", 20.0, 0.0, 0.11);
        Pair<Double, Collidable> t = w1.timeUntilCollision(b1);
        assertFalse( t.getFirst().equals(Double.POSITIVE_INFINITY));
        w1.collide(b1, t.getSecond());
        assertFalse( b1.getVelocity().equals(new Vect(2.2,2.5))); 
    }
    
    @Test public void testCollideWithOutConnectionVertical() {
        Wall w1 = new Wall(board, Wall.Side.LEFT, sendQueue);
        Ball b1 = new Ball(new Vect(8.2,14.7), new Vect(-1.30,0), "b1", 10.0, 0.2, 0.2);
        Pair<Double, Collidable> t = w1.timeUntilCollision(b1);
        assertFalse( t.getFirst().equals(Double.POSITIVE_INFINITY));
        w1.collide(b1, t.getSecond());
        assertFalse( b1.getVelocity().equals( new Vect(-1.30,0))); 
    }
}
