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
import pingball.util.Pair;

/*
 * Testing Strategy:
 *
 * Partitioning based on 
 * 1. Location of bumper on board
 * 2. Triggering each possible collidable in it.
 * 3. Colliding zero, one or multiple balls
 * 
 * We check the working of:
 * 1. The constructor
 * 2. The action
 * 3. The gridRepresentation
 * 4. The getLocation method
 * 5. The timeUntil collision method
 * 6. The collide method. 
 * 
 */
public class SquareBumperTest {
    private static Vect center = new Vect(4,4);
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
        SquareBumper squareBumper = new SquareBumper(board, "TestSquareBumper", new GridLocation(2,2));
        //assertTrue(checkRep()) would be called inside classes
    }
    
    @Test public void testAction(){
        SquareBumper squareBumper = new SquareBumper(board, "TestSquareBumper", new GridLocation(2,2));
        squareBumper.action();
        //action does nothing
        //assertTrue(checkRep()) would be called inside classes
    }
    @Test public void testGridRepresentation(){
        SquareBumper squareBumper = new SquareBumper(board, "TestSquareBumper", new GridLocation(2,2));
        assertEquals("#",squareBumper.gridRepresentation().get(0));
        //assertTrue(checkRep()) would be called inside classes
    }
    
    @Test public void testGetLocation(){
        SquareBumper squareBumper = new SquareBumper(board, "TestSquareBumper", new GridLocation(2,2));
        assertEquals(new GridLocation(2,2),squareBumper.getLocation());
    }
    
    @Test public void testTimeUntilCollision(){
        Ball ball = new Ball(new Vect(5,3), new Vect(-1,0), "TestBall", 10, 1, 1);
        SquareBumper squareBumper = new SquareBumper(board, "TestSquareBumper", new GridLocation(1,3));
        Pair<Double, Collidable> p1 = squareBumper.timeUntilCollision(ball);
        assertEquals(p1.getFirst(),2.75,0.001);
    }
    
    @Test public void testCollide(){
        Ball ball = new Ball(new Vect(5,3), new Vect(-1,0), "TestBall", 10, 0, 0);
        SquareBumper squareBumper = new SquareBumper(board, "TestSquareBumper", new GridLocation(1,3));
        Pair<Double, Collidable> p1 = squareBumper.timeUntilCollision(ball);
        squareBumper.collide(ball, p1.getSecond());
        assertTrue(ball.getVelocity().x()>0);
    }
    

    @Test public void testCollideMultipleBallsDifferentCollidables(){
        Ball ball1 = new Ball(new Vect(6,3), new Vect(-1,0), "TestBall", 10, 0, 0);
        Ball ball2 = new Ball(new Vect(1,3), new Vect(1,0), "TestBall", 10, 0, 0);
        SquareBumper squareBumper = new SquareBumper(board, "TestSquareBumper", new GridLocation(2,3));
        Pair<Double, Collidable> p1 = squareBumper.timeUntilCollision(ball1);
        squareBumper.collide(ball1, p1.getSecond());
        assertTrue(ball1.getVelocity().x()>0);
        Pair<Double, Collidable> p2 = squareBumper.timeUntilCollision(ball2);
        squareBumper.collide(ball2, p2.getSecond());
        assertTrue(ball2.getVelocity().x()<0);
    }
    
    @Test public void testNoCollide(){
        Ball ball = new Ball(new Vect(5,3), new Vect(1,0), "TestBall", 10, 0, 0);
        SquareBumper squareBumper = new SquareBumper(board, "TestSquareBumper", new GridLocation(1,3));
        Pair<Double, Collidable> p1 = squareBumper.timeUntilCollision(ball);
        assertTrue(p1.getSecond()==null);
    }
    
}
