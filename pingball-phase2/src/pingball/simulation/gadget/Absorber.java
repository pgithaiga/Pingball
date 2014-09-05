package pingball.simulation.gadget;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import physics.Vect;
import pingball.simulation.Ball;
import pingball.simulation.Board;
import pingball.simulation.Constants;
import pingball.simulation.GameObjectType;
import pingball.simulation.GridLocation;
import pingball.simulation.collidable.Collidable;
import pingball.simulation.collidable.FixedCircle;
import pingball.simulation.collidable.Line;
import pingball.ui.UIConstants;
import pingball.util.DrawObject;
import pingball.util.DrawObject.Layer;
import pingball.util.Pair;
import pingball.util.StringUtils;

/**
 * Represents an absorber. The location of this object is relative to the board. It will
 * absorb any ball that collides with it, removing it from the board and stop its motion.
 * When its action was called, it shoots the least recent stored ball vertically.
 * 
 * Rep-Invariant:
 *      LastShotBall can be null, but other variables cant
 *      The width and height must be nonnegative
 *      The gridlocation must be in the way that the absorber does not exceed the board when combing with the width/height
 */
public class Absorber extends Gadget {
    
    private final List<String> representation;
    private final int width;
    private final int height;
    private final Vect bottomRight;
    private final Vect shootVelocity;
    private final List<Ball> heldBalls = new ArrayList<>();
    private final Layer layer;
    
    private Ball lastShotBall = null;
    
    /**
     * Creates an absorber
     * @param board The Board on which the absorber is.
     * @param name The name of this absorber.
     * @param location The grid location of the top left corner of the absorber.
     * @param width The width of the absorber.
     * @param height The height of the absorber.
     */
    public Absorber(Board board, String name, GridLocation location, int width,int height) {
        super(board, name, location, Constants.ABSORBER_REFLECTION_COEFF);
        this.width = width;
        this.height = height;
        
        int[][] displaceBy = new int[][] {{0,0},{0,height},{width,0},{width,height}};
  
        bottomRight = this.location.add(displaceBy[3]).toVect();
        
        for(int[] displacement: displaceBy){
            collidables.add(new FixedCircle(location.add(displacement).toVect(), 0, this.reflectionCoeff));
        }
        
        collidables.add(new Line(location.toVect(), location.add(displaceBy[1]).toVect(), reflectionCoeff));
        collidables.add(new Line(location.toVect(), location.add(displaceBy[2]).toVect(), reflectionCoeff));
        collidables.add(new Line(location.add(displaceBy[1]).toVect(), bottomRight, reflectionCoeff));
        collidables.add(new Line(location.add(displaceBy[2]).toVect(), bottomRight, reflectionCoeff));
        
        List<String> rows = new ArrayList<>();
        for(int j=0;j<this.height;j++){
            rows.add(StringUtils.repeat("=", this.width));
        }
        
        representation = Collections.unmodifiableList(rows);
        layer = Layer.GADGET_BG;
        
        shootVelocity = new Vect(0, -Constants.ABSORBER_SHOOT_VELOCITY);
        assert(checkRep());
    }
    
    /**
     * Check the rep-invariant of this object
     * @return whether the rep is ok
     */
    public boolean checkRep() {
        return width > 0 && height > 0 && location.x() >= 0 && location.x() + width <= board.getWidth() && 
                location.y() >= 0 && location.y() + height <= board.getHeight(); 
    }

    /**
     * Performs the action of this absorber.
     * 
     * If there is a ball in the absorber and no ball 
     * earlier shot is currently still inside the absorber, 
     * it shoots a ball toward the top.
     */
    @Override public void action() {
        if( !heldBalls.isEmpty() && (lastShotBall == null || !isBallInside(lastShotBall)) ) {
            Ball shotBall = heldBalls.remove(0);
            shotBall.setVelocity(shootVelocity);
            board.addBall(shotBall);
            lastShotBall = shotBall;
        }
        assert(checkRep());
    }
    
    /**
     * Returns a list of strings for the string representation of the absorber.
     * 
     * Each string in the list "=" symbol repeated for width number of times, where width
     * is the width of the absorber. 
     * The number of such strings in the list is equal to the height of the absorber.
     * 
     * @return The grid representation.
     */
    @Override public List<String> gridRepresentation() {
        assert(checkRep());
        return representation;
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
        if( !isBallInside(ball) ) {
            assert(checkRep());
            return super.timeUntilCollision(ball);
        } else {
            assert(checkRep());
            return Pair.of(Double.POSITIVE_INFINITY, null);
        }
    }

    /**
     * Collides the ball with a particular collidable in this absorber.
     * 
     * For an absorber, this means that the ball is absorbed into the absorber.
     * 
     * @param ball The ball to collide
     * @param collidable The collidable with which the ball collides
     */
    @Override public void collide(Ball ball, Collidable collidable) {
        Vect center = bottomRight.plus(new Vect(-Constants.BALL_RADIUS,-Constants.BALL_RADIUS));
        ball.setCenter(center);
        ball.setVelocity(new Vect(0,0));
        
        board.removeBall(ball);
        heldBalls.add(ball);
        
        if( ball == lastShotBall ) {
            lastShotBall = null;
        }
        assert(checkRep());
        trigger();
    }
    
    /**
     * Returns whether ball is inside the absorber
     * @param ball The ball to check
     * @return true if ball is inside, false otherwise (false only if whole ball has left the absorber)
     */
    private boolean isBallInside(Ball ball) {
        Vect ballCenter = ball.getCircle().getCircle().getCenter();
        Double ballX = ballCenter.x();
        Double ballY = ballCenter.y();
        Double radius = ball.getCircle().getCircle().getRadius();
        Integer topLeftX = location.getFirst();
        Integer topLeftY = location.getSecond();
        assert(checkRep());
        return (topLeftX - radius < ballX) && (ballX < topLeftX + width + radius) && (topLeftY - radius < ballY) && (ballY < topLeftY + height + radius) ;
        
    }

    /**
     * Returns a set of DrawObject representation of this instance 
     * 
     * @param the ratio of the representation in pixel/L
     * 
     * @return the set of DrawObject representation
     */
    @Override public Set<DrawObject> uiRepresentation(double ratio) {
        Shape uiRepresentation = new Rectangle2D.Double((location.x())*ratio, (location.y())*ratio, width*ratio, height*ratio);
        Set<DrawObject> result = new HashSet<>(Arrays.asList(new DrawObject(layer, uiRepresentation, GameObjectType.ABSORBER, 1.0)));
        
        // create text representing number of balls
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();

        try {
          GlyphVector vect = UIConstants.SYLFAEN_20.createGlyphVector(g2.getFontRenderContext(), String.valueOf(heldBalls.size()));
          result.add(new DrawObject(Layer.GADGET_FG, vect.getOutline((float) ((location.x() + width - 1)*ratio), (float) ((location.y() + height - 1)*ratio-vect.getVisualBounds().getY())), GameObjectType.BALL, 1.0));
        } finally {
          g2.dispose();
        }
        assert(checkRep());
        return result;        
    }
}
