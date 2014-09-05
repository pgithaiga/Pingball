package pingball.simulation.gadget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.BeforeClass;
import org.junit.Test;

import physics.Vect;
import pingball.simulation.Ball;
import pingball.simulation.Board;
import pingball.simulation.Constants;
import pingball.simulation.GridLocation;
import pingball.simulation.collidable.Collidable;
import pingball.simulation.gadget.Flipper.FlipDirection;
import pingball.simulation.gadget.Flipper.FlipperType;
import pingball.simulation.gadget.Gadget.Orientation;
import pingball.util.Pair;

/*
 * Testing Strategy:
 *
 * Partitioning based on 
 * 1. Location of flipper on board
 * 2. Its orientation.
 * 3. Its type
 * 3. Triggering each possible collidable in it.
 * 4. Colliding zero, one or multiple balls
 * 
 * We check the working of the following methods for all types of flippers :
 * 1. The constructor
 * 2. The action method: when its not colliding with any ball, colliding with one or more balls, being triggered etc.
 * 3. The gridRepresentation
 * 4. The getLocation method
 * 5. The timeUntil collision method
 * 6. The collide method.
 */
public class FlipperTest {
    
    private static Vect center = new Vect(4,4);
    private static Vect velocity = new Vect(-1,-1);
    
    private static List<Flipper> flippers = new ArrayList<Flipper>();
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
    
    private void reInit() {
        flippers = new ArrayList<Flipper>();
        flippers.add(new Flipper(board, "TestFlipper", new GridLocation(2,2), FlipperType.LEFT, Orientation.ANGLE_0));
        flippers.add(new Flipper(board, "TestFlipper", new GridLocation(4,2), FlipperType.LEFT, Orientation.ANGLE_90));
        flippers.add(new Flipper(board, "TestFlipper", new GridLocation(6,2), FlipperType.LEFT, Orientation.ANGLE_180));
        flippers.add(new Flipper(board, "TestFlipper", new GridLocation(8,2), FlipperType.LEFT, Orientation.ANGLE_270));
        flippers.add(new Flipper(board, "TestFlipper", new GridLocation(2,12), FlipperType.RIGHT, Orientation.ANGLE_0));
        flippers.add(new Flipper(board, "TestFlipper", new GridLocation(2,14), FlipperType.RIGHT, Orientation.ANGLE_90));
        flippers.add(new Flipper(board, "TestFlipper", new GridLocation(2,16), FlipperType.RIGHT, Orientation.ANGLE_180));
        flippers.add(new Flipper(board, "TestFlipper", new GridLocation(2,18), FlipperType.RIGHT, Orientation.ANGLE_270));
    
    }
    
    @Test public void testConstructor(){
        Flipper flipper;
        flipper = new Flipper(board, "TestFlipper", new GridLocation(2,2), FlipperType.LEFT, Orientation.ANGLE_0);
        flipper = new Flipper(board, "TestFlipper", new GridLocation(2,2), FlipperType.LEFT, Orientation.ANGLE_90);
        flipper = new Flipper(board, "TestFlipper", new GridLocation(2,2), FlipperType.LEFT, Orientation.ANGLE_180);
        flipper = new Flipper(board, "TestFlipper", new GridLocation(2,2), FlipperType.LEFT, Orientation.ANGLE_270);
        flipper = new Flipper(board, "TestFlipper", new GridLocation(2,2), FlipperType.RIGHT, Orientation.ANGLE_0);
        flipper = new Flipper(board, "TestFlipper", new GridLocation(2,2), FlipperType.RIGHT, Orientation.ANGLE_90);
        flipper = new Flipper(board, "TestFlipper", new GridLocation(2,2), FlipperType.RIGHT, Orientation.ANGLE_180);
        flipper = new Flipper(board, "TestFlipper", new GridLocation(2,2), FlipperType.RIGHT, Orientation.ANGLE_270);
        //assertTrue(checkRep()) would be called inside classes
    }
    
    @Test public void testAction(){
        reInit();
        for (Flipper flipper: flippers){
            FlipDirection original = flipper.getDirection();
            flipper.action();
            assertFalse(original.equals(flipper.getDirection()));
            //assertTrue(checkRep()) would be called inside classes
        }
    }
    
    @Test public void testEvolve(){
        reInit();
        for(Flipper flipper: flippers){
            flipper.evolve(0.01);
            Double expectedAngle;
            if (flipper.getType().equals(FlipperType.LEFT)) {
               expectedAngle = 0.0;
            }
            else {
                expectedAngle = Math.PI/2;
            }
            assertEquals(expectedAngle, flipper.getCurrentAngle(), 0.001);
            //assertTrue(checkRep()) would be called inside classes
        }
    }
    
    @Test public void testActionandEvolve(){
        reInit();
        for(Flipper flipper: flippers){
            FlipDirection original = flipper.getDirection();
            flipper.action();
            assertFalse(original.equals(flipper.getDirection()));
            flipper.evolve(0.01);
            Double expectedAngle;
            if (flipper.getType().equals(FlipperType.LEFT)) {
               expectedAngle = (10.8*(2*Math.PI)/360.0);
            }
            else {
                expectedAngle = ((90-10.8)*(2*Math.PI)/360.0);
            }
            assertEquals(expectedAngle, flipper.getCurrentAngle(), 0.001);            //assertTrue(checkRep()) would be called inside classes
        }
    }
    
    @Test public void testGridRepresentationInitialState(){
        reInit();
        assertTrue(Arrays.asList("| ","| ").equals(flippers.get(0).gridRepresentation()));
        assertTrue(Arrays.asList("--","  ").equals(flippers.get(1).gridRepresentation()));
        assertTrue(Arrays.asList(" |"," |").equals(flippers.get(2).gridRepresentation()));
        assertTrue(Arrays.asList("  ","--").equals(flippers.get(3).gridRepresentation()));
        assertTrue(Arrays.asList(" |"," |").equals(flippers.get(4).gridRepresentation()));
        assertTrue(Arrays.asList("  ","--").equals(flippers.get(5).gridRepresentation()));
        assertTrue(Arrays.asList("| ","| ").equals(flippers.get(6).gridRepresentation()));
        assertTrue(Arrays.asList("--","  ").equals(flippers.get(7).gridRepresentation()));
        //assertTrue(checkRep()) would be called inside classes
    }
    
    @Test public void testTimeUntilCollision(){
        Ball ball = new Ball(new Vect(5,3), new Vect(-1,0), "TestBall", 10, 1, 1);
        Flipper flipper = new Flipper(board, "TestFlipper", new GridLocation(2,2), FlipperType.LEFT, Orientation.ANGLE_0);
        Pair<Double, Collidable> p1 = flipper.timeUntilCollision(ball);
        assertEquals(p1.getFirst(),2.75,0.001);
    }
    
    @Test public void testCollide(){
        Ball ball = new Ball(new Vect(5,3), new Vect(-1,0), "TestBall", 10, 0, 0);
        Flipper flipper = new Flipper(board, "TestFlipper", new GridLocation(2,2), FlipperType.LEFT, Orientation.ANGLE_0);
        Pair<Double, Collidable> p1 = flipper.timeUntilCollision(ball);
        flipper.collide(ball, p1.getSecond());
        assertTrue(ball.getVelocity().x()>0);
    }
    
    @Test public void testNoCollide(){
        Ball ball = new Ball(new Vect(5,3), new Vect(1,0), "TestBall", 10, 0, 0);
        Flipper flipper = new Flipper(board, "TestFlipper", new GridLocation(2,2), FlipperType.LEFT, Orientation.ANGLE_0);
        Pair<Double, Collidable> p1 = flipper.timeUntilCollision(ball);
        assertTrue(p1.getSecond()==null);
    }
    
    @Test public void testCollideMultipleBallsDifferentCollidables(){
        Ball ball1 = new Ball(new Vect(5,3), new Vect(-1,0), "TestBall", 10, 0, 0);
        Ball ball2 = new Ball(new Vect(1,3), new Vect(1,0), "TestBall", 10, 0, 0);
        Flipper flipper = new Flipper(board, "TestFlipper", new GridLocation(2,2), FlipperType.LEFT, Orientation.ANGLE_0);
        Pair<Double, Collidable> p1 = flipper.timeUntilCollision(ball1);
        flipper.collide(ball1, p1.getSecond());
        assertTrue(ball1.getVelocity().x()>0);
        Pair<Double, Collidable> p2 = flipper.timeUntilCollision(ball2);
        flipper.collide(ball2, p2.getSecond());
        assertTrue(ball2.getVelocity().x()<0);
    }
}
