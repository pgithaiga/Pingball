package pingball.util;

import java.awt.Color;
import java.awt.Shape;

import pingball.simulation.GameObjectType;
import pingball.ui.colorscheme.UIColorSchemes;

/**
 * An immutable object represeting an object to draw. It contains shape, layer, and color
 * 
 * Rep-Invariant:
 *      all variables must not be null
 */
public class DrawObject {
    
    public enum Layer {
        BALL,
        GADGET_BG,
        GADGET_FG,
        FLIPPER,
        WALL;
    }
    
    private final Layer layer;
    
    private final Shape shape;
    
    private final Color fillColor;
    
    private final Color outlineColor;
    
    private final boolean hasFill;
    
    private final boolean hasOutline;
    
    /**
     * Creates a DrawObject object
     * @param layer the layer of this object
     * @param shape the shape of this object
     * @param type the type of game object this draw object represents
     * @param brightLevel the brightLevel of this object should be between 0 to 1. But this function accepts any
     * kind of double and it automatically rounds it for you. brightLevel 1 means the original color and brightLevel 0 
     * means really brithColor (essentially white).
     */
    public DrawObject(Layer layer, Shape shape, GameObjectType type, double brightLevel) {
        if( brightLevel < 0.0 ) {
            brightLevel = 0.0;
        }
        if( brightLevel > 1.0 ) {
            brightLevel = 1.0;
        }
        
        Color fillColor = UIColorSchemes.getFillColor(type);
        Color outlineColor = UIColorSchemes.getOutlineColor(type);
        
        this.hasFill = UIColorSchemes.getHasFill(type);
        this.hasOutline = UIColorSchemes.getHasOutline(type);
        
        this.layer = layer;
        this.shape = shape;
        this.fillColor = new Color((int) (255 - brightLevel*(255 - fillColor.getRed())), 
                               (int) (255 - brightLevel*(255 - fillColor.getGreen())), 
                               (int) (255 - brightLevel*(255 - fillColor.getBlue())));
        
        brightLevel = 1 - (1 - brightLevel) / 3;
        this.outlineColor = new Color(
                (int) (255 - brightLevel*(255 - outlineColor.getRed())), 
                (int) (255 - brightLevel*(255 - outlineColor.getGreen())), 
                (int) (255 - brightLevel*(255 - outlineColor.getBlue())));
    }
    
    /**
     * Returns the layer of this DrawObject
     * @return its layer
     */
    public Layer getLayer() {
        return layer;
    }
    
    /**
     * Returns the shape of this DrawObject 
     * @return its shape
     */
    public Shape getShape() {
        return shape;
    }
    
    /**
     * Returns the color of this DrawObject after adjusting with brightLevel
     * @return its color
     */
    public Color getFillColor() {
        return fillColor;
    }
    
    /**
     * Returns the outlinecolor of this DrawObjet after adjusting with brightLevel
     * @return its outlinecolor
     */
    public Color getOutlineColor() {
        return outlineColor;
    }
    
    /**
     * Returns whether this object has fillcolor
     * @return the fact that it has fillcolor
     */
    public boolean hasFill(){
        return hasFill;
    }
    
    /**
     * Returns whether this object has outlinecolor
     * @return the fact that it has outlinecolor
     */
    public boolean hasOutline(){
        return hasOutline;
    }
    
}
