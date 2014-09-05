package pingball.simulation.collidable;

import physics.Angle;
import physics.Circle;
import physics.Geometry;
import physics.Vect;
import pingball.simulation.Ball;

/**
 * A mutable fixed circle
 * 
 * FixedCircle object is a circle that represents a fixed circle at some position. It has 
 * the center, radius, reflection coefficient and optionally the center of rotation
 * and angular velocity. Even though it is called FixedCircle, the object can mutate by
 * rotating around the center of rotation. It, however, cannot move with linear velocity
 * 
 * Rep-Invariant:
 *      -
 */
public class FixedCircle implements Collidable {

    private Circle circle;
    
    private final double reflectionCoeff;
    
    private double angularVelocity;
    
    private final Vect centerOfRotation;
    
    /**
     * Create a FixedCircle object
     * 
     * @param center a vector representing the center location of the MovingCircle
     * 
     * @param radius a double number representing the radius. Must be nonnegative
     * 
     * @param reflectionCoeff a double number representing the reflection
     * 
     * @param centerOfRotation a vector representing the location of the center of rotation
     */
    public FixedCircle(Vect center, double radius, double reflectionCoeff, Vect centerOfRotation) {
        this.circle = new Circle(center, radius);
        this.reflectionCoeff = reflectionCoeff;
        this.centerOfRotation = centerOfRotation;
    }
    
    /**
     * Create a FixedCircle object when the object cannot rotate
     * 
     * @param center a vector representing the center location of the MovingCircle
     * 
     * @param radius a double number representing the radius. Must be nonnegative
     * 
     * @param reflectionCoeff a double number representing the reflection
     */
    public FixedCircle(Vect center, double radius, double reflectionCoeff) {
        this(center, radius, reflectionCoeff, center);
    }
    
    /**
     * Return a Physics.Circle object referencing the position and radius of this FixedCircle
     * 
     * @return the Physics.Circle object that has the position and radius of this FixedCircle
     */
    public Circle getCircle() {
        return this.circle;
    }
    
    /**
     * Return the it takes for this object to collide with the ball. If the collision will not
     * occur, this method return Double.POSITIVE_INFINITY.
     * 
     * @param ball the ball that will collide with this object
     * 
     * @return a double representing the time it takes for this object to collide with the ball
     */
    @Override public double timeUntilCollision(Ball ball) {
        MovingCircle anotherCircle = ball.getCircle();
        return Geometry.timeUntilRotatingCircleCollision(circle, centerOfRotation, angularVelocity, anotherCircle.getCircle(), ball.getVelocity());
        
    }

    /**
     * Collide this object with ball, changing the velocity of the ball. This method assume the
     * two objects are at the point of impact. Note that this method mutates the ball.
     * 
     * @param ball the ball that will collide with this object
     */
    @Override public void collide(Ball ball) {
        MovingCircle anotherCircle = ball.getCircle();
        Vect newVelocity = Geometry.reflectRotatingCircle(circle, centerOfRotation, angularVelocity, anotherCircle.getCircle(), ball.getVelocity(), reflectionCoeff);
        ball.setVelocity(newVelocity);
    }
    
    /**
     * Set the angular velocity of this object
     * 
     * @param angularVelocity a double representing the angular velocity in radian per second
     */
    @Override public void setRotating(double angularVelocity) {
        this.angularVelocity = angularVelocity;
    }
    
    /**
     * Rotate this object by a particular angle corresponding to the center of rotation
     * 
     * @param angle a double number representing the angle that this object will rotate
     */
    @Override public void rotate(double angle) {
        circle = Geometry.rotateAround(circle, centerOfRotation, new Angle(angle));
    }
}
