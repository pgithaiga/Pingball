package pingball.ui;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * This class contains multiple immutable graphics objects or constants that 
 * are generally used repeatedly in the GUI. The stored objects allow for less 
 * time and memory to be spent on object creation, and serves as a central 
 * record for global UI constants.
 * @author Erik Nguyen
 *
 */
public class UIConstants {
    
    /**
     * Standard Sylfaen font with a font size of 12.
     */
    public static final Font SYLFAEN_12 = new Font("Sylfaen", 0, 12); // NOI18N
    
    /**
     * Standard Sylfaen font with a font size of 14.
     */
    public static final Font SYLFAEN_14 = new Font("Sylfaen", 0, 14); // NOI18N
    
    /**
     * Standard Sylfaen font with a font size of 18.
     */
    public static final Font SYLFAEN_18 = new Font("Sylfaen", 0, 18); // NOI18N
    
    /**
     * Standard Sylfaen font with a font size of 20.
     */
    public static final Font SYLFAEN_20 = new Font("Sylfaen", 0, 20); // NOI18N
    
    /**
     * Standard Sylfaen font with a font size of 24
     */
    public static final Font SYLFAEN_24 = new Font("Sylfaen", 0, 24); // NOI18N
    
    /**
     * Standard Sylfaen font with a font size of 36.
     */
    public static final Font SYLFAEN_36 = new Font("Sylfaen", 0, 36); // NOI18N
    
    /**
     * Standard Sylfaen font with a font size of 48.
     */
    public static final Font SYLFAEN_48 = new Font("Sylfaen", 0, 48); // NOI18N
    
    /**
     * Standard Sylfaen font with a font size of 60.
     */
    public static final Font SYLFAEN_60 = new Font("Sylfaen", 0, 60); // NOI18N
     
    /**
     * Italicized Times New Roman font with a font size of 11.
     */
    public static final Font TIMES_ITAL_11 = new Font("Times New Roman", 2, 11); // NOI18N
    
    /**
     * Light gray color defined as (R=240, G=240, B=240)
     */
    public static final Color LIGHT_GRAY = new Color(240, 240, 240);
    
    /**
     * This is the on blink color
     */
    public static final Color BLINK_ON_COLOR = new Color(255, 220, 220);
    
    /**
     * Standard etched border
     */
    public static final Border DEFAULT_ETCHED_BORDER = BorderFactory.createEtchedBorder();
    
    /**
     * Etched border with a black highlight and no shading
     */
    public static final Border BLACK_ETCHED_BORDER = BorderFactory.createEtchedBorder(new Color(0, 0, 0), null);
    
    /**
     * The default rate at which GUI objects should blink in milliseconds
     */
    public static final int DEFAULT_BLINK_RATE = 100; // in milliseconds
    
    /**
     * The default number of times that a GUI object should blink before
     * stopping the blink process. <b>NOTE<b>: both on and off blinks are
     * counted. 
     */
    public static final int DEFAULT_FINITE_BLINK_COUNT = 6; 
    
    /**
     * The default width in pixels of any given upper level panel. This
     * primarily applies to the GamePanel, BoardPanel, etc. that fill in the
     * playing area.
     */
    public static final int DEFAULT_PANEL_WIDTH = 440;
    
    /**
     * The default height in pixels of any given upper level panel. This
     * primarily applies to the GamePanel, BoardPanel, etc. that fill in the
     * playing area.
     */
    public static final int DEFAULT_PANEL_HEIGHT = 440;
    
    /**
     * The default root location for board files
     */
    public static final String BOARDS_ROOT = "resources/boards";
    
    /**
     * The default root location for board images
     */
    public static final String IMAGES_ROOT = "resources/images";

    /**
     * The default root for the color scheme files
     */
    public static final String COLORS_ROOT = "resources/colorSchemes";
    
    /**
     * Default root for the color scheme files
     */
    public static final File COLORS_ROOT_FILE = new File("resources/colorSchemes");
    
    /**
     * The name of the filre where the default color scheme is stored
     */
    public static final String DEFAULT_COLOR_SCHEME_NAME = "default.csf";
    
    /**
     * The project defined extension for color scheme files
     */
    public static final String COLOR_SCHEME_EXT = ".csf";
    
    /**
     * The project defined extension for board files
     */
    public static final String BOARD_EXTENSION = ".pb";
    
    /**
     * The default port to connect to
     */
    public static final String DEFAULT_PORT = "10987";
}
