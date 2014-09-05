package pingball.simulation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pingball.simulation.collidable.Collidable;
import pingball.util.DrawObject;
import pingball.util.Pair;

/**
 * An object that appears on the board. 
 * 
 * The game objects can be a ball, a bounding wall or a gadget.
 */
public abstract class GameObject {

    protected final Set<Collidable> collidables = new HashSet<>();
    
    protected double brightLevel = 1.0;
    
    protected boolean flashing = false;
    
    /**
     * Returns the grid location of the top left corner of the game object.
     * 
     * @return The grid location.
     */
    public abstract GridLocation getLocation();
    
    /**
     * Returns a list of strings for the string representation of the game object.
     * 
     * Each string in the list represents a row of the bounding square of the object,
     * Number of strings is equal to the height of the bounding square of the object 
     * The rows are aligned with respect to the top left of the object.
     * 
     * @return The grid representation
     */
    public abstract List<String> gridRepresentation();
    
    /**
     * Returns a set of DrawObject representation of this instance 
     * 
     * @param the ratio of the representation in pixel/L
     * 
     * @return the set of DrawObject representation
     */
    public abstract Set<DrawObject> uiRepresentation(double ratio);
    
    /**
     * Evolve the game object for the specified period of time.
     * @param time The time period to evolve the object for.
     */
    public void evolve(double time) {
        if( flashing ) {
            brightLevel = brightLevel - time / Constants.FLASH_TIME;
        } else {
            brightLevel = brightLevel + time / Constants.FLASH_TIME;
        }
        
        if( brightLevel > 1.0 ) {
            brightLevel = 1.0;
        } else if( brightLevel < 0.0 ) {
            brightLevel = 0.0;
            flashing = false;
        }
    }

    /**
     * Collide the ball with a particular collidable in the game object. 
     * 
     * If this game object is a ball, then mutate itself also.
     * 
     * @param ball The ball that collides.
     * @param collidable The collidable that the ball collides with.
     */
    public void collide(Ball ball, Collidable collidable) {
        flashing = true;
        collidable.collide(ball);
    }
    
    /**
     * Returns the minimum time after which the ball will collide with 
     * some collidable in this game object, and also returns the collidable 
     * with which it will collide. 
     * 
     * Assumes ball moves with constant velocity for the specified time period. So, 
     * returned value is accurate only for a time period < Simulation timestep.
     * 
     * @param ball The ball that will collide
     * @return The pair of the minimum time and the collidable with which 
     *          the ball will collide after that time. 
     */
    public Pair<Double, Collidable> timeUntilCollision(Ball ball) {
        double minTime = Double.POSITIVE_INFINITY;
        Collidable collidesWith = null;
        for(Collidable collidable: collidables){
            double collideTime = collidable.timeUntilCollision(ball);
            if (collideTime < minTime) {
                minTime = collideTime;
                collidesWith = collidable;
            }
        }
        return Pair.of(minTime,collidesWith);
    }

}
