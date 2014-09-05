package pingball.ui.colorscheme;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import pingball.simulation.GameObjectType;
import pingball.ui.UIConstants;

/**
 * This class acts as a static loader and global dynamic storage system for
 * everything related to Color Schemes. This class also acts similar to a
 * wrapper for Color Scheme objects such that accessing color scheme values
 * (the colors and booleans) must be done via this class's public methods.
 * Furthermore any access to system loaded color schemes can only be done within
 * this package's ColorSchemeCreatorFrame and therefore no color scheme
 * references are leaked. This ensures color scheme uniformity across the
 * entire Pingball GUI.
 * 
 * Color Scheme Files (.csf) are defined as follows. Each line represents and
 * distinct and unique game object type. The first non-whitespace value is
 * the game object type that it represents. The second and third are boolean
 * values for whether the game object type should show it's <code>fill</code>
 * and <code>outline</code> respectively. The last two values are the ARGB color
 * values (Encoded as ints as defined by Java) for the <code>fill</code> and
 * <code>outline</code> respectively. Each game object type must be represented
 * in the file.
 * 
 * GUI color schemes can be selected via the Color Scheme Selection GUI created
 * by <code>UIColorSchemes.selectColorScheme()</code>
 * 
 * Thread Safety Argument:
 *  This class is completely contained and accessed only within the Java
 *  EventQueue thread. However, it does spawn threads in order to save color
 *  schemes. Because the only referenced values are immutable. A Color Scheme
 *  data structure is immutable by design, GameObjectType represents an enum
 *  which is also immutable, and both Files and Colors are immutable by Java's
 *  design. The PrintWriters of the spawned threads are contained within the
 *  threads. Lastly the JOptionPane that is displayed in case of an error is
 *  sent to the EventQueue to be displayed. Hence this Multi-threading instance
 *  is safe and isolated from the rest of the system.
 * @author Erik
 *
 */
public class UIColorSchemes {

    /**
     * Constant that represents that a color scheme was selected from the
     * selection GUI
     */
    public static final int APPROVE_OPTION = JOptionPane.OK_OPTION;
    
    /**
     * Constant that represents that the selection GUI was cancelled out of
     */
    public static final int CANCEL_OPTION = JOptionPane.CANCEL_OPTION;
    
    /**
     * Constant that represents that the selection GUI was 'x'-ed out of
     */
    public static final int CLOSE_OPTION = JOptionPane.CLOSED_OPTION;
    
    /**
     * The default color scheme for the system
     */
    static final ColorScheme DEFAULT_COLOR_SCHEME;
    
    //Storage structures for the color schemes
    private static final Map<String, ColorScheme> availableColorSchemes;
    private static final JList<String> list;
    private static final DefaultListModel<String> listModel;
    
    /**
     * The color scheme that is in use with the Pingball GUI
     */
    private static ColorScheme useScheme;
    
    /**
     * Must be called first to ensure that the class is loaded by the 
     * ClassLoader.
     */
    public static void load(){}
    
    /**
     * Statically load the saved color systems
     */
    static{
        availableColorSchemes = new HashMap<>();
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        loadColorSchemes();
        useScheme = DEFAULT_COLOR_SCHEME = availableColorSchemes.get(UIConstants.DEFAULT_COLOR_SCHEME_NAME);
        list.setSelectedValue(UIConstants.DEFAULT_COLOR_SCHEME_NAME, true);
    }    
    
    /**
     * Loads the saved color schemes in the resources folder
     */
    private static void loadColorSchemes(){
        //grab only color scheme files
        File[] colorSchemeFiles = new File(UIConstants.COLORS_ROOT).listFiles(new FilenameFilter(){
            @Override public boolean accept(File dir, String name) {
                return name.endsWith(UIConstants.COLOR_SCHEME_EXT);
            }
        });
        
        //Store the color schemes
        for(File file : colorSchemeFiles){
            try {
                availableColorSchemes.put(file.getName(), readInColorScheme(file));
                listModel.addElement(file.getName());
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                        "Error reading in Color Scheme from File." 
                        + "\nFile: " + file.getAbsolutePath(), "File Parse Error", 
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        }        
    }
    
    /**
     * Reads in a color scheme from file
     * @param file the file where the color scheme is stored
     * @return the read in color scheme
     * @throws Exception if the file doesn't exist or the data wasn't parsed
     *      as expected
     */
    private static ColorScheme readInColorScheme(File file) throws Exception{
        Scanner sc = new Scanner(file);
        Map<GameObjectType, Color> fillMap = new HashMap<>();
        Map<GameObjectType, Color> outlineMap = new HashMap<>();
        Map<GameObjectType, Boolean> hasFill = new HashMap<>();
        Map<GameObjectType, Boolean> hasOutline = new HashMap<>();
        
        while(sc.hasNextLine()){
            String[] data = sc.nextLine().trim().split("\\s+");
            GameObjectType type = GameObjectType.valueOf(data[0]);
            boolean fill = Boolean.parseBoolean(data[1]);
            boolean outline = Boolean.parseBoolean(data[2]);
            Color fillColor = new Color(Integer.valueOf(data[3]), true);
            Color outlineColor = new Color(Integer.valueOf(data[4]), true);
            
            fillMap.put(type, fillColor);
            outlineMap.put(type, outlineColor);
            hasFill.put(type, fill);
            hasOutline.put(type, outline);
        }
        sc.close();
        return new BasicColorScheme(fillMap, outlineMap, hasFill, hasOutline);
    }
    
    /**
     * Stores a new color scheme into the intended save file.
     * @param saveFile the location to store the color scheme
     * @param scheme the new color scheme
     */
    public static void storeNewColorScheme(final File saveFile, final ColorScheme scheme){
        String schemeName = saveFile.getName();
        if(!availableColorSchemes.containsKey(schemeName)){
            listModel.addElement(schemeName);
        }
        availableColorSchemes.put(schemeName, scheme);
        
        //Try to store the color scheme
        new Thread(new Runnable(){

            @Override public void run() {
                try {
                    writeToFile(saveFile, scheme);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    SwingUtilities.invokeLater(new Runnable(){
                        @Override public void run(){
                            JOptionPane.showMessageDialog(null, 
                                    "Unable to save color scheme", 
                                    "File Error", JOptionPane.ERROR_MESSAGE);    
                        }
                    });
                }
            }
            
            /**
             * Writes a color scheme out to a specified save file. If the file
             * already exists, the new data over writes the old data
             * @param saveFile the file to save the color scheme to
             * @param scheme the color scheme to be saved
             * @throws FileNotFoundException
             */
            void writeToFile(File saveFile, ColorScheme scheme) throws FileNotFoundException{
                PrintWriter out = new PrintWriter(saveFile);
                for(GameObjectType type : GameObjectType.values()){
                    String gType = type.toString();
                    boolean hasFill = scheme.showFill(type);
                    boolean hasOutline = scheme.showOutline(type);
                    Color fillColor = scheme.fillColor(type);
                    Color outlineColor = scheme.outlineColor(type);
                    out.println(gType + " " + hasFill + " " + hasOutline + " " + 
                                fillColor.getRGB() + " " + outlineColor.getRGB());
                }
                out.close();
            }
            
        }).start();
    }
    
    /**
     * Returns if the system already contains a color scheme by this name
     * @param colorSchemeName the name to search for
     * @return true if a color scheme already exists by a given name, else false
     */
    static boolean hasName(String colorSchemeName) {
        return availableColorSchemes.containsKey(colorSchemeName);
    }
    
    /**
     * Returns the selected color scheme from the selection GUI 
     * @return the color scheme selected
     */
    static ColorScheme getSelectedColorScheme(){
        return availableColorSchemes.get(listModel.get(list.getSelectedIndex()));
    }
    
    /**
     * Returns the set of all color scheme names in the system
     * @return the set of color scheme names
     */
    static Set<String> colorSchemeNames(){
        return availableColorSchemes.keySet();
    }
    
    /**
     * Returns the color scheme represented by the name
     * @param colorSchemeName the name of the desired color scheme, must be a
     *      valid name in the system
     * @return the color scheme corresponding to the name argument
     */
    static ColorScheme getColorScheme(String colorSchemeName){
        return availableColorSchemes.get(colorSchemeName);
    }
    
    /**
     * Sets the Pingball GUI's color scheme to the most recently selected value
     * in the Color Scheme Selection GUI
     */
    public static void setUseSchemeToSelectedScheme(){
        useScheme = getSelectedColorScheme();
    }
    
    /**
     * Returns the fill color in the current color scheme for a given type of
     * game object
     * @param type the type of game object to look for
     * @return the fill color
     */
    public static Color getFillColor(GameObjectType type){
        return useScheme.fillColor(type);
    }
    
    /**
     * Returns the outline color in the current color scheme for a given
     * type of game object
     * @param type the type of game object to look for
     * @return the outline color
     */
    public static Color getOutlineColor(GameObjectType type){
        return useScheme.outlineColor(type);
    }
    
    /**
     * Returns whether or not the given type of game object should have its
     * fill color used
     * @param type the type of game object to look for
     * @return true if the fill color should be used
     */
    public static boolean getHasFill(GameObjectType type){
        return useScheme.showFill(type);
    }
    
    /**
     * Returns whether or not the given type of game object should have its
     * outline shown
     * @param type the type of game object to look for
     * @return true if the outline should be shown
     */
    public static boolean getHasOutline(GameObjectType type){
        return useScheme.showOutline(type);
    }
    
    /**
     * Creates a GUI for the user to select a color scheme with. The returned
     * value will depend on the decision that the user makes when selecting and
     * exiting out of the selection frame. If the user accepts the selection,
     * {@code UIColorSchemes.APPROVE_OPTION} is returned. If the user cancel's
     * the selection {@code UIColorSchemes.CANCEL_OPTION} is returned. Otherwise
     * if the user closes out of the frame via the "X" button, 
     * {@code UIColorSchemes.CLOSE_OPTION} is returned.
     * @return a value representing the user's decision
     */
    public static int selectColorScheme(){
        return JOptionPane.showOptionDialog(null, list, "Select Color Scheme", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, 
                null, null, null);
    }
}
