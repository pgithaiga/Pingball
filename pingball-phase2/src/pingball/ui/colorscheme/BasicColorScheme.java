package pingball.ui.colorscheme;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import pingball.simulation.GameObjectType;

/**
 * This class is both immutable and the most basic type of color scheme. When
 * created, if any game object doesn't have an explicitly given value for fill
 * or outline color, the color is set to black. If a value isn't given to tell
 * if a fill or outline should be used, the value is set to false.
 * @author Erik
 *
 */
public class BasicColorScheme implements ColorScheme{
    
    //Rep invariant:
    //fillMap, outlineMap, hasFillMap, hasOutlineMap != null
    //every element of GameObjectType.values() has a value in each map
    
    /**
     * Stores the fill colors for each type of game object
     */
    private final Map<GameObjectType, Color> fillMap = new HashMap<>();
    
    /**
     * Stores the outline colors for each type of game object
     */
    private final Map<GameObjectType, Color> outlineMap = new HashMap<>();
    
    /**
     * Stores whether or not the fill color should be shown for each type of
     * game object
     */
    private final Map<GameObjectType, Boolean> hasFillMap = new HashMap<>();
    
    /**
     * Stores whether or not the outline should be shown for each type of
     * game object
     */
    private final Map<GameObjectType, Boolean> hasOutlineMap = new HashMap<>();
    
    /**
     * Creates a new basic color scheme
     * @param fills the fill colors for game objects
     * @param outlines the outline colors for game objects
     * @param hasFill map of game objects to the value for displaying fill color
     * @param hasOutline map of game objects to the value for displaying outline
     */
    public BasicColorScheme(Map<GameObjectType, Color> fills, 
                            Map<GameObjectType, Color> outlines, 
                            Map<GameObjectType, Boolean> hasFill, 
                            Map<GameObjectType, Boolean> hasOutline){
        
        for(GameObjectType type : GameObjectType.values()){
            fillMap.put(type, fills.containsKey(type) ? fills.get(type) 
                                                      : Color.BLACK);
            outlineMap.put(type, outlines.containsKey(type) ? outlines.get(type) 
                                                            : Color.BLACK);
            hasFillMap.put(type, hasFill.containsKey(type) ? hasFill.get(type) 
                                                           : false);
            hasOutlineMap.put(type, hasOutline.containsKey(type) ? hasOutline.get(type) 
                                                                 : false);
        }
        checkRep();
    }
    
    @Override public boolean showFill(GameObjectType type){
        return hasFillMap.get(type);
    }
    
    @Override public boolean showOutline(GameObjectType type){
        return hasOutlineMap.get(type);
    }
    
    @Override public Color fillColor(GameObjectType type){
        return fillMap.get(type);
    }
    
    @Override public Color outlineColor(GameObjectType type){
        return outlineMap.get(type);
    }
    
    /**
     * Checks to see if the rep invariant is preserved at creation time
     */
    private void checkRep(){
        for(GameObjectType type : GameObjectType.values()){
            assert fillMap.containsKey(type);
            assert hasFillMap.containsKey(type);
            assert outlineMap.containsKey(type);
            assert hasOutlineMap.containsKey(type);
        }
    }
    
}
