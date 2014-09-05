package pingball.simulation.gadget;

import java.util.HashSet;
import java.util.Set;

import pingball.simulation.Ball;
import pingball.simulation.Board;
import pingball.simulation.GameObject;
import pingball.simulation.GridLocation;
import pingball.simulation.collidable.Collidable;
import pingball.util.DrawObject;

/**
 * 
 * A gadget is a triggerable game object.
 * 
 * A gadget can be a Circular Bumper, a Triangular Bumper, a Square Bumper, 
 * a Flipper or an Absorber.
 *
 */
public abstract class Gadget extends GameObject {
    
    /**
     * An enum to represent the orientation of a flipper or a trianglular bumper.
     *
     */
    public enum Orientation {
        ANGLE_0,
        ANGLE_90,
        ANGLE_180,
        ANGLE_270;
        public static Orientation of(String s) {
            return of(Integer.parseInt(s));
        }
        public static Orientation of(int i) {
            switch(i) {
                case 0:
                    return ANGLE_0;
                case 90:
                    return ANGLE_90;
                case 180:
                    return ANGLE_180;
                case 270:
                    return ANGLE_270;
                default:
                    throw new IllegalArgumentException("invalid orientation");
            }
        }
    }

    protected final Set<Gadget> linkedGadgets = new HashSet<>();

    protected final GridLocation location;

    protected final double reflectionCoeff;

    protected final Board board;

    protected final String name;
    
    /**
     * Creates a Gadget.
     * 
     * @param board The Board object on which this gadget is.
     * @param name The name of this gadget.
     * @param location The Grid Location of the top left corner of the gadget.
     * @param reflectionCoeff The reflection coefficient of the gadget.
     */
    public Gadget(Board board, String name, GridLocation location, double reflectionCoeff) {
        this.board = board;
        this.name = name;
        this.location = location;
        this.reflectionCoeff = reflectionCoeff; 
    }

    /**
     * Returns the grid location of the top left corner of the gadget.
     * 
     * @return The grid location.
     */
    @Override public final GridLocation getLocation() {
        return location;
    }

    /**
     * Perform the action of this gadget.
     */
    public abstract void action();

    /**
     * Adds a gadget to the list of gadgets whose action is performed when
     * this gadget is triggered.
     * 
     * @param gadget The gadget to be linked to this gadget
     * @return true of the addition was successful, false otherwise
     */
    public final boolean linkGadget(Gadget gadget) {
        return linkedGadgets.add(gadget);
    }


    /**
     * Removes a gadget from the list of gadgets whose action is performed when
     * this gadget is triggered.
     * 
     * @param gadget The gadget to be unlinked from this gadget
     * @return true if the removal was successful, false otherwise
     */
    public final boolean unlinkGadget(Gadget gadget) {
        return linkedGadgets.remove(gadget);
    }
    
    /**
     * Collides the ball with a particular collidable in this gadget.
     * 
     * The collidable must be a part of this gadget.
     *  
     * @param ball The ball to collide
     * @param collidable The collidable with which the ball collides
     */
    @Override public void collide(Ball ball, Collidable collidable) {
        super.collide(ball, collidable);
        this.trigger();
    }
    
    /**
     * Triggers this gadget
     */
    public void trigger() {
        for (Gadget linkedGadget: linkedGadgets) {
            linkedGadget.action();
        }
    }

    /**
     * Returns the name of this gadget.
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
        return new HashSet<>();        
    }
}
