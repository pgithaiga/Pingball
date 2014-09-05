package pingball.ui.colorscheme;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

/**
 * This class acts as a miniature view panel for game objects when displayed
 * in the <code>ColorSchemeCreatorFrame</code>. All MiniPanels are guaranteed
 * to have a size of 100 x 100 pixels.
 * @author Erik
 */
class MiniPanel extends JPanel {

    /**
     * The side length of the panel of a MiniPanel
     */
    public static final int SIDE_LENGTH = 100;
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * The default size of a minipanel is 100 px by 100 px
     */
    private static final Dimension DEFAULT_SIZE = new Dimension(100, 100);
    
    /**
     * The name of this mini panel
     */
    private final String name;
    
    //Instance variables that define the coloring
    
    private boolean hasFill = true;
    private boolean hasOutline = true;
    private Color fillColor = Color.BLUE;
    private Color outlineColor = Color.BLACK;
    
    /**
     * Stores the shapes used to define the fill area
     */
    private final Set<Shape> fillShapes = new HashSet<>();
    
    /**
     * Stores the shapes used to define the outlines
     */
    private final Set<Shape> outlineShapes = new HashSet<>();
    
    /**
     * Creates a new MiniPanel with the desired name, fill color and
     * background color
     * @param name the name of the panel
     * @param fillColor the desired fill color for the shapes in the panel
     * @param hasFill whether or not the shapes should display a fill color
     * @param outlineColor the desired color for the shape outlines
     * @param hasOutline whether or not the shapes should display their outlines
     */
    public MiniPanel(String name, Color fillColor, boolean hasFill, 
            Color outlineColor, boolean hasOutline){
        this.setPreferredSize(DEFAULT_SIZE);
        this.setMaximumSize(DEFAULT_SIZE);
        this.setMinimumSize(DEFAULT_SIZE);
        this.name = name;
        this.hasFill = hasFill;
        this.hasOutline = hasOutline;
        this.fillColor = fillColor;
        this.outlineColor = outlineColor;
    }
    
    /**
     * Sets the shapes that will be filled in in this mini panel
     * @param shapes the set of shapes to be filled in
     */
    public void setFillShapes(Collection<Shape> shapes){
        fillShapes.clear();
        fillShapes.addAll(shapes);
    }
    
    /**
     * Sets the shapes that will be drawn as outlines
     * @param shapes the outline shapes
     */
    public void setOutlineShapes(Collection<Shape> shapes){
        outlineShapes.clear();
        outlineShapes.addAll(shapes);
    }
    
    /**
     * Sets if this mini panel should fill in the shapes with color
     * @param hasFill whether or not the panel should display fill color
     */
    public void setHasFill(boolean hasFill){
        this.hasFill = hasFill;
    }
    
    /**
     * Sets if this mini panel should display the outline shapes
     * @param hasOutline whether or not the panel should display an outline
     */
    public void setHasOutline(boolean hasOutline){
        this.hasOutline = hasOutline;
    }
    
    /**
     * Sets the fill color for this mini panel
     * @param fillColor the new fill color
     */
    public void setFillColor(Color fillColor){
        this.fillColor = fillColor;
    }
    
    /**
     * Sets the outline color for the mini panel
     * @param outlineColor the new outline color
     */
    public void setOutlineColor(Color outlineColor){
        this.outlineColor = outlineColor;
    }
    
    /**
     * Returns the color used to fill in the mini panel's shapes
     * @return the color used to fill in the mini panel's shapes
     */
    public Color fillColor(){
        return fillColor;
    }
    
    /**
     * Returns the color used to outline the mini panel's shapes
     * @return the color used to outline the mini panel's shapes
     */
    public Color outlineColor(){
        return outlineColor;
    }
    
    /**
     * Returns whether or not the mini panel's game object should have an outline
     * @return true if this mini panel should display the game object's outline
     */
    public boolean hasOutline(){
        return hasOutline;
    }
    
    /**
     * Returns whether or not the mini panel's game object should display it's
     * fill
     * @return true if this mini panel should display the game object's fill
     */
    public boolean hasFill(){
        return hasFill;
    }
    
    @Override public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        //Set the rendering to a high quality
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.addRenderingHints(rh);
        
        //display the fill if needed
        if(hasFill){
            g2.setColor(fillColor);
            for(Shape shape : fillShapes){
                g2.fill(shape);
            }
        }
        //display the outline if needed
        if(hasOutline){
            g2.setColor(outlineColor);
            for(Shape shape : outlineShapes){
                g2.draw(shape);
            }
        }
    }    
    
    @Override public String toString(){
        return name;
    }
}
