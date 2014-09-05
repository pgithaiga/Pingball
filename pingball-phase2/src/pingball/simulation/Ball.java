package pingball.simulation;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import physics.Vect;
import pingball.simulation.collidable.Collidable;
import pingball.simulation.collidable.MovingCircle;
import pingball.util.DrawObject;
import pingball.util.DrawObject.Layer;

/**
 * A ball that will be on the board. Represent by a moving circle, plus the gravity and mu1/mu2 that applies on it
 * Rep Invariant: 
 *  The gridlocation must be such that the ball does not exceed the board after accounting for its radius.
 */
public class Ball extends GameObject{
    
    private final List<String> representation;
    
    private MovingCircle circle;
    
    private final double gravity;
    
    private final double mu1;
    
    private final double mu2;
    
    private final String name;
    
    private final Layer layer;
    
    /**
     * Create a ball
     * 
     * @param center a vector representing the location of the center of the ball
     * 
     * @param velocity a vector representing the direction and magnitude of the ball
     * 
     * @param name a string representing the name of the ball
     * 
     * @param gravity a double number representing the gravity that applies to the ball
     * 
     * @param mu1 a double number representing the first friction coefficient that applies to the ball
     * 
     * @param mu2 a double number representing the second friction coefficient that applies to the ball
     */
    public Ball(Vect center, Vect velocity, String name, double gravity, double mu1, double mu2) {
        this.circle = new MovingCircle(center, Constants.BALL_RADIUS, velocity);
        this.name = name;
        this.gravity = gravity;
        this.mu1 = mu1;
        this.mu2 = mu2;
        this.collidables.add(this.circle);
        this.representation = Collections.unmodifiableList(Arrays.asList("*"));
        this.layer = DrawObject.Layer.BALL;
        assert(checkRep());
    }
    
    /**
     * check the rep-invariant of this object
     * @return whether the rep is ok or not
     */
    private boolean checkRep() {
        Vect center = this.getCircle().getCircle().getCenter();
        return center.x() >= 0 && center.x() < Constants.BOARD_WIDTH && 
                center.y() >= 0 && center.y() < Constants.BOARD_HEIGHT;
    }
    /**
     * Returns the grid location of the top left corner of the ball.
     * 
     * @return The grid location.
     */
    @Override public final GridLocation getLocation() {
        Vect center = circle.getCircle().getCenter();
        assert(checkRep());
        return new GridLocation((int) center.x(), (int) center.y());
    }
    
    /**
     * Collide this object with ball, changing the velocity of the ball and this object if this object.
     * This method assume the two objects are at the point of impact. Note that this method mutates
     * this object as well as the ball.
     * 
     * @param ball the ball that will collide with this ball object
     * 
     * @param collidable the Collidable object that actually collide with the ball
     */
    @Override public void collide(Ball ball, Collidable collidable) {
        assert(checkRep());
        super.collide(ball, collidable);
    }
    
    /**
     * Return the MovingCircle representation of this object
     * 
     * @return a MovingCircle of object of this ball 
     */
    public MovingCircle getCircle(){
        return circle;
    }

    /**
     * Return the velocity of this object
     * 
     * @return a vector representing the magnitude and direction of this object's velocity
     */
    public Vect getVelocity() {
        return circle.getVelocity();
    }
    
    /**
     * Set the velocity of this object
     * 
     * @param velocity a vector representing the magnitude and direction of this object's velocity
     */
    public void setVelocity(Vect velocity) {
        circle.setVelocity(velocity);
        assert(checkRep());
    }
    
    /**
     * Evolve the ball for a particular amount of time. This method mutate the ball to some different location
     * and change the ball velocity according to the friction and gravitation. This method assume 
     * that time is small enough that it is possible to simulate those forces with linear equation. 
     * 
     * @param time a double number representing the time for which the ball evolves
     */
    @Override public void evolve(double time) {
        Vect velocity = getVelocity();
        circle.move(time);
        velocity = velocity.plus(new Vect(0, time*gravity));
        velocity = velocity.times(1 - mu1*time - mu2*velocity.length()*time);
        setVelocity(velocity);
        assert(checkRep());
    }
    
    /**
     * Set the location of the center of this ball object
     * 
     * @param center a vector representing the location of the new center
     */
    public void setCenter(Vect center){
        this.circle.setCenter(center);
        assert(checkRep());
    }

    /**
     * Return the list of string representing the grid representation of this ball
     * 
     * @return the list of string representing the grid representation of this ball
     */
    @Override public List<String> gridRepresentation() {
        return representation;
    }

    /**
     * Returns the name of this ball.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns a set of DrawObject representation of this instance 
     * 
     * @param the ratio of the representation in pixel/L
     * 
     * @return the set of DrawObject representation
     */
    @Override public Set<DrawObject> uiRepresentation(double ratio) {
        Shape s =  new Ellipse2D.Double((circle.getCircle().getCenter().x()-Constants.BALL_RADIUS)*ratio, (circle.getCircle().getCenter().y()-Constants.BALL_RADIUS)*ratio, Constants.BALL_RADIUS*ratio*2, Constants.BALL_RADIUS*ratio*2);
        assert(checkRep());
        return new HashSet<>(Arrays.asList(new DrawObject(layer, s, GameObjectType.BALL, 1.0)));        
    }
}
