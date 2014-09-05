package pingball.simulation.gadget;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pingball.simulation.Board;
import pingball.simulation.Constants;
import pingball.simulation.GameObjectType;
import pingball.simulation.GridLocation;
import pingball.simulation.collidable.FixedCircle;
import pingball.simulation.collidable.Line;
import pingball.util.DrawObject;
import pingball.util.DrawObject.Layer;

/**
 * Represents a squarebumper of size 1x1. The location of this object is relative to the board. 
 * It will bounce any object that collides with it.
 * 
 * Rep-Invariant:
 *      The gridlocation must be in the way that the squarebumper does not exceed the board when combing with the width/height
 */
public class SquareBumper extends Gadget {
    
    private final List<String> representation;
    
    private final Layer layer;
    
    /**
     * Creates a square bumper.
     * 
     * @param board The board on which the square bumper is.
     * @param name The name of this bumper.
     * @param location The grid location of the top left corner of the square bumper.
     */
    public SquareBumper(Board board, String name, GridLocation location) {
        super(board, name, location, Constants.BUMPER_REFLECTION_COEFF);
        
        int[][] displaceBy = new int[][] {{0,0},{0,1},{1,0},{1,1}};
  
        for(int[] displacement: displaceBy){
            collidables.add(new FixedCircle(location.add(displacement).toVect(), 0, this.reflectionCoeff));
        }
        
        collidables.add(new Line(location.toVect(), location.add(displaceBy[1]).toVect(), reflectionCoeff));
        collidables.add(new Line(location.toVect(), location.add(displaceBy[2]).toVect(), reflectionCoeff));
        collidables.add(new Line(location.add(displaceBy[1]).toVect(), location.add(displaceBy[3]).toVect(), reflectionCoeff));
        collidables.add(new Line(location.add(displaceBy[2]).toVect(), location.add(displaceBy[3]).toVect(), reflectionCoeff));

        representation = Collections.unmodifiableList(Arrays.asList("#"));
        layer = Layer.GADGET_FG;
        
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
     * Performs the action of this square bumper.
     * 
     * Square bumpers have no action, so this does nothing.
     */
    @Override public void action() {
        assert(checkRep());
        //Do nothing
    }

    /**
     * Returns a list of strings for the string representation of the square bumper.
     * 
     * Grid representation of the square bumper is "#".
     * 
     * @return the grid representation
     */
    @Override public List<String> gridRepresentation() {
        assert(checkRep());
        return representation;
    }
    
    /**
     * Returns a set of DrawObject representation of this instance 
     * 
     * @param the ratio of the representation in pixel/L
     * 
     * @return the set of DrawObject representation
     */
    @Override public Set<DrawObject> uiRepresentation(double ratio) {
        Shape uiRepresentation = new Rectangle2D.Double((location.x())*ratio, (location.y())*ratio, ratio, ratio);
        assert(checkRep());
        return new HashSet<>(Arrays.asList(new DrawObject(layer, uiRepresentation, GameObjectType.SQUARE_BUMPER, brightLevel)));        
    }

}
