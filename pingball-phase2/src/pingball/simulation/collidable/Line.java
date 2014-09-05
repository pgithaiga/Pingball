package pingball.simulation.collidable;

import physics.Angle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;
import pingball.simulation.Ball;

/**
 * A line segment. Represent a line that can possibly be rotating. Any ball can collide with this line.
 * 
 * Rep Invariant:
 *      -
 */
public class Line implements Collidable {

    private LineSegment lineSegment;

    private final double reflectionCoeff;

    private final Vect centerOfRotation;

    private double angularVelocity;

    /**
     * Make a Line.
     *
     * @param start The start position of the line segment.
     *
     * @param end The end position of the line segment.
     *
     * @param reflectionCoeff The coefficient of reflection of the line segment.
     *
     * @param centerOfRotation The center of rotation of the line segment.
     */
    public Line(Vect start, Vect end, double reflectionCoeff, Vect centerOfRotation) {
        this.lineSegment = new LineSegment(start, end);
        this.reflectionCoeff = reflectionCoeff;
        this.centerOfRotation = centerOfRotation;
    }

    /**
     * Make a Line.
     *
     * @param start The start position of the line segment.
     *
     * @param end The end position of the line segment.
     *
     * @param reflectionCoeff The coefficient of reflection of the line segment.
     */
    public Line(Vect start, Vect end, double reflectionCoeff) {
        this(start, end, reflectionCoeff, start);
    }

    /**
     * Returns the time until collision with a ball.
     *
     * @param ball The ball that is colliding with this.
     *
     * @return The time until collision.
     */
    @Override public double timeUntilCollision(Ball ball) {
        MovingCircle anotherCircle = ball.getCircle();
        return Geometry.timeUntilRotatingWallCollision(lineSegment, centerOfRotation, angularVelocity,
            anotherCircle.getCircle(), ball.getVelocity());
    }

    /**
     * Perform a collision with a ball.
     *
     * Updates the velocity of this ball according to how it would behave
     * if it hit this wall.
     *
     * @param ball The ball that is colliding with this.
     */
    @Override public void collide(Ball ball) {
        MovingCircle anotherCircle = ball.getCircle();
        Vect newVelocity = Geometry.reflectRotatingWall(lineSegment, centerOfRotation, angularVelocity,
            anotherCircle.getCircle(), ball.getVelocity(), reflectionCoeff);
        ball.setVelocity(newVelocity);
    }

    /**
     * Set the rotating state for this line.
     *
     * This method does not actually move the line.
     *
     * @param angularVelocity The angular velocity (in radians per second)
     * that this is rotating.
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
        lineSegment = Geometry.rotateAround(lineSegment, centerOfRotation, new Angle(angle));
    }

}
