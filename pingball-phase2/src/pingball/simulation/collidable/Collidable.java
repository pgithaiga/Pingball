package pingball.simulation.collidable;

import pingball.simulation.Ball;

/**
 * Interface for any collidable object. This object is mutable and can be either a line or a circle.
 */
public interface Collidable {
    
    /**
     * Return the it takes for this object to collide with the ball. If the collision will not
     * occur, this method return Double.POSITIVE_INFINITY.
     * 
     * @param ball the ball that will collide with this object
     * 
     * @return a double representing the time it takes for this object to collide with the ball
     */
    public double timeUntilCollision(Ball ball);
    
    /**
     * Collide this object with ball, changing the velocity of the ball and this object if this object
     * is a MovingCircle. This method assume the two objects are at the point of impact.
     * Note that this method mutates this object as well as the ball.
     * 
     * @param ball the ball that will collide with this object
     */
    public void collide(Ball ball);
    
    /**
     * Set the angular velocity of this object. Do nothing if this object is a movingcircle
     * 
     * @param angularVelocity a double representing the angular velocity in radian per second
     */
    public void setRotating(double angularVelocity);
    
    /**
     * Rotate this object by a particular angle corresponding to the center of rotation. Do nothing if this object is a movingcircle
     * 
     * @param angle a double number representing the angle that this object will rotate
     */
    public void rotate(double angle);
}
