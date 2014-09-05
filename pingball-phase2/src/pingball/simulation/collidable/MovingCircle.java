package pingball.simulation.collidable;

import physics.Circle;
import physics.Geometry;
import physics.Geometry.VectPair;
import physics.Vect;
import pingball.simulation.Ball;
import pingball.simulation.Constants;

/**
 * A mutable moving circle
 * 
 * MovingCircle object is a circle that represents a ball. It has 
 * the center, radius, and velocity. The density of moving circle is 1 unit mass per unit length^3
 * 
 * Rep-Invariant:
 *      -
 */
public class MovingCircle implements Collidable {
    
    private Circle circle;
    
    private Vect velocity;
    
    /**
     * Create a MovingCirle object
     * 
     * @param center a vector representing the center location of the MovingCircle
     *  
     * @param radius a double number representing the radius. Must be nonnegative
     * 
     * @param velocity a vector representing the velocity direction and magnitude
     */
    public MovingCircle(Vect center, double radius, Vect velocity) {
        this.circle = new Circle(center, radius);
        this.velocity = velocity;
    }
    
    /**
     * Return a Physics.Circle object referencing the position and radius of this MovingCircle
     * 
     * @return the Physics.Circle object that has the position and radius of this MovingCircle
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
        return Geometry.timeUntilBallBallCollision(circle, velocity, anotherCircle.getCircle(), ball.getVelocity());
    }
    
    /**
     * Collide this object with ball, changing the velocity of the ball and this object if this object.
     * This method assume the two objects are at the point of impact. Note that this method mutates
     * this object as well as the ball.
     * 
     * @param ball the ball that will collide with this object
     */
    @Override public void collide(Ball ball) {
        MovingCircle anotherCircle = ball.getCircle();
        VectPair newVelocity = Geometry.reflectBalls(circle.getCenter(), getMass(), velocity, anotherCircle.getCircle().getCenter(), anotherCircle.getMass(), anotherCircle.getVelocity());
        velocity = newVelocity.v1;
        ball.setVelocity(newVelocity.v2);
    }
    
    /**
     * Move this object for some time.
     * 
     * @param time a double representing the time that this object will be simulated to move
     */
    public void move(double time) {
        circle = new Circle(circle.getCenter().plus(velocity.times(time)), circle.getRadius());
    }
    
    /**
     * Return the mass of this MovingCircle. Note that the density is 1 unit mass per unit length^3
     * 
     * @return the mass of this object
     */
    public double getMass() {
        return 4.0 / 3.0 * Math.PI * Constants.BALL_DENSITY * circle.getRadius() * circle.getRadius() * circle.getRadius();
    }
    
    /**
     * Return the velocity of this object
     * 
     * @return the velocity of this object
     */
    public Vect getVelocity() {
        return velocity;
    }
    
    /**
     * Set the velocity of this object
     * 
     * @param velocity a vector representing the direction and magnitude of the new velocity for this object
     */
    public void setVelocity(Vect velocity) {
        this.velocity = velocity;
    }
    
    /**
     * Set the center location of this object
     * 
     * @param center the vector representing the new location of this object
     */
    public void setCenter(Vect center){
        this.circle = new Circle(center,this.circle.getRadius());
    }
    
    /**
     * Set the angular velocity of this object
     * 
     * @param angularVelocity a double representing the angular velocity in radian per second
     */
    @Override public void setRotating(double angularVelocity) {
        // Do nothing
    }
    
    /**
     * Rotate this object by a particular angle corresponding to the center of rotation
     * 
     * @param angle a double number representing the angle that this object will rotate
     */
    @Override public void rotate(double angle) {
        // Do nothing
    }
}
