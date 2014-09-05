package pingball.simulation.gadget;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import physics.Vect;
import pingball.simulation.Ball;
import pingball.simulation.Board;
import pingball.simulation.Constants;
import pingball.simulation.GameObjectType;
import pingball.simulation.GridLocation;
import pingball.simulation.collidable.Collidable;
import pingball.simulation.collidable.FixedCircle;
import pingball.util.DrawObject;
import pingball.util.DrawObject.Layer;
import pingball.util.Pair;

/**
 * Represents a portal of size 1x1 grid. The location of this object is relative to the board.
 * 
 * Rep-Invariant:
 *      The gridlocation must be in the way that the circlebumper does not exceed the board when combing with the width/height
 *      otherBoard can be null, but not other variables
 */
public class Portal extends Gadget {
    
    private final List<String> representation;
    
    private final String otherBoard, otherPortal;
    
    private final double middleX, middleY;
    
    private final BlockingQueue<String> sendQueue, receieveQueue;
    
    private final Layer layer;
    
    /**
     * Creates a portal
     * 
     * @param board The board where the portal is on
     * @param name The name of this portal
     * @param otherBoard The other board that this portal connects to. Can be null if it is a local portal
     * @param otherPortal The other portal that this portal connects to
     * @param location The gridlocation of this portal
     * @param sendQueue The sendQueue of this client
     * @param receiveQueue The receiveQueue of this client
     */
    public Portal(Board board, String name, String otherBoard, String otherPortal, GridLocation location, 
            BlockingQueue<String> sendQueue, BlockingQueue<String> receiveQueue) {
        super(board, name, location, Constants.PORTAL_REFLECTION_COEFF);
        this.sendQueue = sendQueue;
        this.receieveQueue = receiveQueue;
        this.otherBoard = otherBoard;
        this.otherPortal = otherPortal;
        collidables.add(new FixedCircle(location.toVect().plus(new Vect(0.5,0.5)), 0.5, reflectionCoeff));
        representation = Collections.unmodifiableList(Arrays.asList("X"));   
        middleX = location.getFirst() + Constants.PORTAL_RADIUS;
        middleY = location.getSecond() + Constants.PORTAL_RADIUS;
        layer = Layer.GADGET_FG;
        
        registerPortal();
        
        assert(checkRep());
    }
    
    /**
     * Check the rep-invariant of this object
     * @return whether the rep is ok
     */
    public boolean checkRep() {
        return location.x() >= 0 && location.x() < board.getWidth() && 
               location.y() >= 0 && location.y() < board.getHeight();         
    }
    
    /**
     * Sends Register message to the sendQueue, which will pass it to the server, so the server knows there is this portal
     * in the universe
     */
    private void registerPortal() {
        try {
            if( otherBoard == null ) {
                sendQueue.put(String.format("portal-register-local %s", name));
            } else {
                sendQueue.put(String.format("portal-register-connected %s %s %s", name, otherBoard, otherPortal));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Return the middle location of this portal in the unit of L
     * 
     * @return the middle location of this portal
     */
    public Vect getMiddlePosition() {        
        assert(checkRep());
        return new Vect(middleX, middleY);
    }
    
    /**
     * Performs the action of this portal.
     * 
     * Portals have no action, so this does nothing.
     */
    @Override public void action() {        
        assert(checkRep());
        // Do nothing
    }
    
    /**
     * Returns the minimum time after which the ball will collide with 
     * some collidable in this game object, and also returns the collidable 
     * with which it will collide. 
     * 
     * @param ball The ball that will collide
     * @return The pair of the minimum time and the collidable with which 
     *          the ball will collide after that time. 
     */
    @Override public Pair<Double, Collidable> timeUntilCollision(Ball ball) {        
        assert(checkRep());
        if( !isBallInside(ball) ) {
            return super.timeUntilCollision(ball);
        } else {
            return Pair.of(Double.POSITIVE_INFINITY, null);
        }
    }
    
    /**
     * Collides the ball with a particular collidable in this portal.
     * 
     * For an absorber, this means that the ball might be teleported or might pass through the portal, depending on 
     * the avaliability of the connection
     * 
     * @param ball The ball to collide
     * @param collidable The collidable with which the ball collides
     */
    @Override public void collide(Ball ball, Collidable collidable) {
        if(otherBoard == null && board.containsPortal(otherPortal)) {
            try {
                Vect velocity = ball.getVelocity();
                board.removeBall(ball);
                receieveQueue.put(String.format("warp %s %s %f %f",
                    otherPortal, ball.getName(), velocity.x(), velocity.y()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }   
        } else if( otherBoard != null ) {
            try {
                Vect velocity = ball.getVelocity();
                board.removeBall(ball);
                sendQueue.put(String.format("warp %s %s %f %f",
                    name, ball.getName(), velocity.x(), velocity.y()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }     
        }
        trigger();        
        assert(checkRep());
    }
    
    /**
     * Returns a list of strings for the string representation of the portal.
     * 
     * Grid representation of the portal is "X".
     * 
     * @return the grid representation
     */
    @Override public List<String> gridRepresentation() {        
        assert(checkRep());
        return representation;
    }
    
    private boolean isBallInside(Ball ball) {
        Vect ballCenter = ball.getCircle().getCircle().getCenter();
        Double ballX = ballCenter.x();
        Double ballY = ballCenter.y();
        Double radius = ball.getCircle().getCircle().getRadius();
        
        assert(checkRep());
        return (ballX - middleX)*(ballX - middleX) + (ballY - middleY)*(ballY - middleY) < (radius + Constants.PORTAL_RADIUS)*(radius + Constants.PORTAL_RADIUS) + Constants.THRESHOLD;
    }
    
    /**
     * Returns a set of DrawObject representation of this instance 
     * 
     * @param the ratio of the representation in pixel/L
     * 
     * @return the set of DrawObject representation
     */
    @Override public Set<DrawObject> uiRepresentation(double ratio) {
        Shape uiRepresentation = new Ellipse2D.Double((location.x())*ratio, (location.y())*ratio, ratio, ratio);        
        assert(checkRep());
        return new HashSet<>(Arrays.asList(new DrawObject(layer, uiRepresentation, GameObjectType.PORTAL, 1.0)));        
    }
}
