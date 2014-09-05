package pingball.ui.colorscheme;

import java.awt.Color;

import pingball.simulation.GameObjectType;

/**
 * This interface describes the basic requirements of a color scheme. Color
 * schemes define how any given displayed <code>Game Object</code> will be
 * shaded and outlined. Every color scheme must include values for outline
 * and fill colors along with values for whether the fill and outline of the
 * game object shapes should be displayed. Lastly the color scheme must be
 * immutable.
 * @author Erik
 *
 */
public interface ColorScheme {
    
    /**
     * Returns if the argument type of game object should have its fill
     * color displayed.
     * @param type the type of game object
     * @return true if the game object should have its fill color shown, else
     *      false
     */
    public boolean showFill(GameObjectType type);
    
    /**
     * Returns if the argument type of game object should have its outline
     * and outline color displayed.
     * @param type the type of game object
     * @return true if the game object should have its outline shown, else
     *      false
     */
    public boolean showOutline(GameObjectType type);
    
    /**
     * Returns the color used to fill the shape of any given game object type
     * @param type the type of game object
     * @return the fill color for a game object type
     */
    public Color fillColor(GameObjectType type);
    
    /**
     * Returns the color used to display the outline of a shape of any given
     * game object type
     * @param type the type of game object
     * @return the outline color for a game object type
     */
    public Color outlineColor(GameObjectType type);
}
