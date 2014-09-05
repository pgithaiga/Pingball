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
import pingball.simulation.collidable.Collidable;
import pingball.simulation.gadget.Gadget.Orientation;
import pingball.util.Pair;

/*
 * Testing Strategy:
 *
 * Partitioning based on 
 * 1. Location of bumper on board
 * 2. Its orientation.
 * 3. Triggering each possible collidable in it.
 * 4. Colliding zero, one or multiple balls
 *
 * We check the working of the following methods for all types of triangle bumpers :
 * 1. The constructor
 * 2. The action
 * 3. The gridRepresentation
 * 4. The getLocation method
 * 5. The timeUntil collision method
 * 6. The collide method.
 */
public class TriangleBumperTest {
    private static Vect center = new Vect(4,4);
    private static Vect velocity = new Vect(-1,-1);
    
    private static TriangleBumper triangleBumper1;
    private static TriangleBumper triangleBumper2;
    private static TriangleBumper triangleBumper3;
    private static TriangleBumper triangleBumper4;
    
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

        triangleBumper1 = new TriangleBumper(board, "TestTriangleBumper", new GridLocation(2,2), Orientation.ANGLE_0);
        triangleBumper2 = new TriangleBumper(board, "TestTriangleBumper", new GridLocation(10,2), Orientation.ANGLE_90);
        triangleBumper3 = new TriangleBumper(board, "TestTriangleBumper", new GridLocation(2,12), Orientation.ANGLE_180);
        triangleBumper4 = new TriangleBumper(board, "TestTriangleBumper", new GridLocation(3,5), Orientation.ANGLE_270);
    }
    
    @Test public void testConstructor(){
        TriangleBumper triangleBumper;
        triangleBumper = new TriangleBumper(board, "TestTriangleBumper", new GridLocation(5,6), Orientation.ANGLE_0);
        triangleBumper = new TriangleBumper(board, "TestTriangleBumper", new GridLocation(6,7), Orientation.ANGLE_90);
        triangleBumper = new TriangleBumper(board, "TestTriangleBumper", new GridLocation(7,8), Orientation.ANGLE_180);
        triangleBumper = new TriangleBumper(board, "TestTriangleBumper", new GridLocation(8,9), Orientation.ANGLE_270);
        //assertTrue(checkRep()) would be called inside classes
    }
    
    @Test public void testAction(){
        TriangleBumper triangleBumper = new TriangleBumper(board, "TestTriangleBumper", new GridLocation(2,2), Orientation.ANGLE_0);
        triangleBumper.action();
        //action does nothing
        //assertTrue(checkRep()) would be called inside classes
    }
    @Test public void testGridRepresentation(){
        assertEquals("/",triangleBumper1.gridRepresentation().get(0));
        assertEquals("\\",triangleBumper2.gridRepresentation().get(0));
        assertEquals("/",triangleBumper3.gridRepresentation().get(0));
        assertEquals("\\",triangleBumper4.gridRepresentation().get(0));
        //assertTrue(checkRep()) would be called inside classes
    }
    
    @Test public void testGetLocation() {
        assertEquals(new GridLocation(2,2), triangleBumper1.getLocation());
        assertEquals(new GridLocation(10,2), triangleBumper2.getLocation());
        assertEquals(new GridLocation(2,12), triangleBumper3.getLocation());
        assertEquals(new GridLocation(3,5), triangleBumper4.getLocation());
    }
    
    @Test public void testTimeUntilCollision(){
        Ball ball = new Ball(new Vect(5,3), new Vect(-1,0), "TestBall", 10, 1, 1);
        TriangleBumper triangleBumper = new TriangleBumper(board, "TestTriangleBumper", new GridLocation(2,2), Orientation.ANGLE_0);
        Pair<Double, Collidable> p1 = triangleBumper.timeUntilCollision(ball);
        assertEquals(p1.getFirst(),2.65,0.1);
    }
    
    @Test public void testCollide(){
        Ball ball = new Ball(new Vect(5,3), new Vect(-1,0), "TestBall", 10, 0, 0);
        TriangleBumper triangleBumper = new TriangleBumper(board, "TestTriangleBumper", new GridLocation(2,2), Orientation.ANGLE_0);
        Pair<Double, Collidable> p1 = triangleBumper.timeUntilCollision(ball);
        triangleBumper.collide(ball, p1.getSecond());
        assertTrue(ball.getVelocity().y()>0);
    }

    @Test public void testNoCollide(){
        Ball ball = new Ball(new Vect(5,3), new Vect(1,0), "TestBall", 10, 0, 0);
        TriangleBumper triangleBumper = new TriangleBumper(board, "TestTriangleBumper", new GridLocation(2,2), Orientation.ANGLE_0);
        Pair<Double, Collidable> p1 = triangleBumper.timeUntilCollision(ball);
        assertTrue(p1.getSecond()==null);
    }
    
    @Test public void testCollideMultipleBallsDifferentCollidables(){
        Ball ball1 = new Ball(new Vect(5,3), new Vect(-1,0), "TestBall", 10, 0, 0);
        Ball ball2 = new Ball(new Vect(1,3), new Vect(1,0), "TestBall", 10, 0, 0);
        TriangleBumper triangleBumper = new TriangleBumper(board, "TestTriangleBumper", new GridLocation(2,2), Orientation.ANGLE_0);
        Pair<Double, Collidable> p1 = triangleBumper.timeUntilCollision(ball1);
        triangleBumper.collide(ball1, p1.getSecond());
        assertTrue(ball1.getVelocity().y()>0);
        Pair<Double, Collidable> p2 = triangleBumper.timeUntilCollision(ball2);
        triangleBumper.collide(ball2, p2.getSecond());
        assertTrue(ball2.getVelocity().x()<0);
    }
}
