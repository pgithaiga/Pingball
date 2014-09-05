package pingball.ui.colorscheme;


import java.awt.Color;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import pingball.simulation.GameObjectType;
import pingball.ui.UIConstants;

/**
 * This class creates a GUI that can be used to create new color schemes for
 * Pingball. The GUI loads up a color chooser as well as example images of
 * the pingball game objects. The user is then able to select preferences for
 * how the game objects are displayed - with a fill color, without a fill
 * color, with/without an outline, the colors, etc. The GUI supports loading
 * existing color scheme files for reference as well as saving user designed
 * color schemes, incorporating these into both new files for future use and
 * into the GUI for immediate use in game. By design, walls are only allowed
 * to have fill colors and no outlines.
 * 
 * @author Erik
 */
public class ColorSchemeCreatorFrame extends JFrame {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * The border for a selected MiniPanel
     */
    private static final Border SELECTED_ETCHED_BORDER = UIConstants.BLACK_ETCHED_BORDER;
    
    /**
     * The border for an unselected MiniPanel
     */
    private static final Border STANDARD_ETCHED_BORDER = UIConstants.DEFAULT_ETCHED_BORDER;
    
    /**
     * The color scheme to load up the Color Scheme creation frame with by default
     */
    private static final ColorScheme DEFAULT_COLOR_SCHEME = UIColorSchemes.DEFAULT_COLOR_SCHEME;
    
    /**
     * Maps each type of MiniPanel to the GameObjectType that it portrays
     */
    private final Map<GameObjectType, MiniPanel> panelTypeMap;
    
    //File and color choosers
    private final JFileChooser fileChooser;
    private final JColorChooser colorChooser;
    
    //The mini preview panels for each type of game object
    private final MiniPanel sqBumperPanel;
    private final MiniPanel triBumperPanel;
    private final MiniPanel cirBumperPanel;
    private final MiniPanel absorberPanel;
    private final MiniPanel flipperPanel;
    private final MiniPanel wallPanel;
    private final MiniPanel ballPanel;
    private final MiniPanel portalPanel;
    
    /*
     * GUI Elements that decide which aspect of a game object display is being
     * edited
     */
    private final JLabel setColorLabel;
    private final ButtonGroup radioButtonGroup;
    private final JRadioButton fillRadioButton;
    private final JRadioButton outlineRadioButton;
    
    /*
     * GUI elements that display whether the fill or outlines are enabled for
     * the selected game object 
     */
    private final JLabel enableDisableLabel;
    private final JCheckBox fillCheckBox;
    private final JCheckBox outlineCheckBox;
    
    /*
     * GUI elements that handle loading, saving, and closing this frame
     */
    private final JPanel buttonPanel;
    private final JButton loadButton;
    private final JButton saveButton;
    private final JButton cancelButton;
    
    /**
     * Reference to the currently selected frame
     */
    private MiniPanel selectedPanel;
    
    /**
     * Reference to the last loaded color scheme
     */
    private ColorScheme colorScheme;
    
    /**
     * Creates new form ColorSchemeCreationFrame
     */
    public ColorSchemeCreatorFrame() {
        setTitle("Color Scheme Editor");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setResizable(false);

        colorChooser = new JColorChooser();
        
        sqBumperPanel  = makeMiniPanel("Square Bumper", GameObjectType.SQUARE_BUMPER);
        triBumperPanel = makeMiniPanel("Triangle Bumper", GameObjectType.TRIANGLE_BUMPER);
        cirBumperPanel = makeMiniPanel("Circle Bumper", GameObjectType.CIRCLE_BUMPER);
        absorberPanel  = makeMiniPanel("Absorber", GameObjectType.ABSORBER);
        flipperPanel   = makeMiniPanel("Flipper", GameObjectType.FLIPPER);
        wallPanel      = makeMiniPanel("Wall", GameObjectType.WALL);
        portalPanel    = makeMiniPanel("Portal", GameObjectType.PORTAL);
        ballPanel      = makeMiniPanel("Ball", GameObjectType.BALL);
        
        setColorLabel = new JLabel();
        radioButtonGroup = new ButtonGroup();
        fillRadioButton = new JRadioButton();
        outlineRadioButton = new JRadioButton();
        
        enableDisableLabel = new JLabel();
        outlineCheckBox = new JCheckBox();
        fillCheckBox = new JCheckBox();
        
        buttonPanel = new JPanel();
        loadButton = new JButton();
        saveButton = new JButton();
        cancelButton = new JButton();
        
        panelTypeMap = new HashMap<>();
        panelTypeMap.put(GameObjectType.SQUARE_BUMPER, sqBumperPanel);
        panelTypeMap.put(GameObjectType.TRIANGLE_BUMPER, triBumperPanel);
        panelTypeMap.put(GameObjectType.CIRCLE_BUMPER, cirBumperPanel);
        panelTypeMap.put(GameObjectType.ABSORBER, absorberPanel);
        panelTypeMap.put(GameObjectType.FLIPPER, flipperPanel);
        panelTypeMap.put(GameObjectType.WALL, wallPanel);
        panelTypeMap.put(GameObjectType.PORTAL, portalPanel);
        panelTypeMap.put(GameObjectType.BALL, ballPanel);
        
        fileChooser = new JFileChooser(UIConstants.COLORS_ROOT_FILE.getAbsoluteFile());
        fileChooser.setFileFilter(new FileNameExtensionFilter("Color Scheme File .csf", "csf"));
        
        initComponents();
        setUpListeners();
        organizeComponents();
        loadShapes();
        
        //Initially select the MiniPanel for a Square Bumper and load it's data
        selectedPanel = sqBumperPanel;
        selectedPanel.setBorder(SELECTED_ETCHED_BORDER);
        fillRadioButton.setSelected(true);
        fillCheckBox.setSelected(selectedPanel.hasFill());
        outlineCheckBox.setSelected(selectedPanel.hasOutline());
        
        pack();
    }
    
    /**
     * Only called by the constructor. Creates a new MiniPanel for a given type
     * of game object with a new name. The data is loaded from the default
     * color scheme.
     * @param name the name of the game object
     * @param type the type of game object the panel is for
     * @return the new specialized mini panel
     */
    private MiniPanel makeMiniPanel(String name, GameObjectType type){
        Color fillColor = DEFAULT_COLOR_SCHEME.fillColor(type);
        boolean hasFill = DEFAULT_COLOR_SCHEME.showFill(type);
        Color outlineColor = DEFAULT_COLOR_SCHEME.outlineColor(type);
        boolean hasOutline = DEFAULT_COLOR_SCHEME.showOutline(type);
        return new MiniPanel(name, fillColor, hasFill, outlineColor, hasOutline);
    }
    
    /**
     * Resets all of the panels to default settings.
     */
    public void reset(){
        deselectSelectedPanel();
        selectPanel(sqBumperPanel);
        colorScheme = DEFAULT_COLOR_SCHEME;
        loadColorScheme();
    }
    
    /**
     * Loads a color scheme and applies the color scheme and its data to all 
     * MiniPanels. After all of the data is loaded, the painted screen is
     * refreshed.
     */
    private void loadColorScheme(){
        for(GameObjectType type : GameObjectType.values()){
            Color fillColor = colorScheme.fillColor(type);
            boolean hasFill = colorScheme.showFill(type);
            Color outlineColor = colorScheme.outlineColor(type);
            boolean hasOutline = colorScheme.showOutline(type);
            
            MiniPanel panel = panelTypeMap.get(type);
            panel.setFillColor(fillColor);
            panel.setOutlineColor(outlineColor);
            panel.setHasFill(hasFill);
            panel.setHasOutline(hasOutline);
        }

        //Set up all of the boxes
        fillCheckBox.setSelected(selectedPanel.hasFill());
        outlineCheckBox.setSelected(selectedPanel.hasOutline());
        this.repaint();
    }
    
    /**
     * Initialize component settings. This method is only called from the
     * constructor.
     */
    private void initComponents() {

        //Setup the gadget preview windows with the tooltip texts
        sqBumperPanel.setBorder(STANDARD_ETCHED_BORDER);
        sqBumperPanel.setToolTipText("Square Bumper");

        triBumperPanel.setBorder(STANDARD_ETCHED_BORDER);
        triBumperPanel.setToolTipText("Triangle Bumper");

        cirBumperPanel.setBorder(STANDARD_ETCHED_BORDER);
        cirBumperPanel.setToolTipText("Circle Bumper");

        absorberPanel.setBorder(STANDARD_ETCHED_BORDER);
        absorberPanel.setToolTipText("Absorber");

        flipperPanel.setBorder(STANDARD_ETCHED_BORDER);
        flipperPanel.setToolTipText("Flipper");

        wallPanel.setBorder(STANDARD_ETCHED_BORDER);
        wallPanel.setToolTipText("Wall");

        portalPanel.setBorder(STANDARD_ETCHED_BORDER);
        portalPanel.setToolTipText("Portal");
        
        ballPanel.setBorder(STANDARD_ETCHED_BORDER);
        ballPanel.setToolTipText("Ball");

        outlineCheckBox.setFont(UIConstants.SYLFAEN_18);
        outlineCheckBox.setText("Outline");

        fillCheckBox.setFont(UIConstants.SYLFAEN_18);
        fillCheckBox.setText("Fill");

        radioButtonGroup.add(fillRadioButton);
        fillRadioButton.setFont(UIConstants.SYLFAEN_18);
        fillRadioButton.setText("Fill");

        radioButtonGroup.add(outlineRadioButton);
        outlineRadioButton.setFont(UIConstants.SYLFAEN_18);
        outlineRadioButton.setText("Outline");

        setColorLabel.setFont(UIConstants.SYLFAEN_24);
        setColorLabel.setText("Set Color");

        enableDisableLabel.setFont(UIConstants.SYLFAEN_24);
        enableDisableLabel.setText("Enable/Disable");

        loadButton.setFont(UIConstants.SYLFAEN_14);
        loadButton.setText("Load");
        
        saveButton.setFont(UIConstants.SYLFAEN_14);
        saveButton.setText("Save");

        cancelButton.setFont(UIConstants.SYLFAEN_14);
        cancelButton.setText("Cancel");

    }
    
    /**
     * Sets up all listeners in internal components. This method is only called
     * from the constructor.
     */
    private void setUpListeners(){

        MouseListener mListener = new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent evt) {}

            @Override
            public void mouseEntered(MouseEvent evt) {}

            @Override
            public void mouseExited(MouseEvent evt) {}

            @Override
            public void mousePressed(MouseEvent evt) {}

            @Override
            public void mouseReleased(MouseEvent evt) {
                panelSelectedEvent(evt);
            }
            
        };
        
        sqBumperPanel.addMouseListener(mListener);
        triBumperPanel.addMouseListener(mListener);
        cirBumperPanel.addMouseListener(mListener);
        absorberPanel.addMouseListener(mListener);
        flipperPanel.addMouseListener(mListener);
        wallPanel.addMouseListener(mListener);
        portalPanel.addMouseListener(mListener);
        ballPanel.addMouseListener(mListener);
        
        colorChooser.getSelectionModel().addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent evt) {
                colorChooserChangePerformed(evt);
            }
        });
        
        fillRadioButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                radioButtonActionPerformed(evt);
            }
        });
        
        outlineRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                radioButtonActionPerformed(evt);
            }
        });
        
        outlineCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                outlineCheckBoxActionPerformed(evt);
            }
        });
        
        fillCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                fillCheckBoxActionPerformed(evt);
            }
        });
        
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                loadButtonActionPerformed(evt);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
    }
    
    /**
     * Deselects the old panel mini panel and then selects the new mini panel
     * while updating the check boxes. The deselection and selection changes
     * the border of the involved panels. Selected panels have a blackened 
     * border. Only called from the Mouse Released method in the listener.
     * @param evt the reported mouse event
     */
    private void panelSelectedEvent(MouseEvent evt){
        deselectSelectedPanel();
        selectPanel((MiniPanel) evt.getSource());
    }

    /**
     * De-selects the current selected panel and re-enables relevent buttons.
     * This does not refresh the paint screen.
     */
    private void deselectSelectedPanel(){
        //Visually de-select the old panel
        selectedPanel.setBorder(STANDARD_ETCHED_BORDER);
        if(selectedPanel == wallPanel){
            fillRadioButton.setEnabled(true);
            outlineCheckBox.setEnabled(true);
            outlineRadioButton.setEnabled(true);
        }
    }
    
    /**
     * Selects a specific panel and un-enables specific buttons if the selected
     * panel is a MiniPanel for a wall.
     * @param panel the selected MiniPanel
     */
    private void selectPanel(MiniPanel panel){
        
        selectedPanel = panel;
        //Walls can only have a fill color, nothing else can be enabled
        if(selectedPanel == wallPanel){
            fillRadioButton.doClick();
            fillRadioButton.setEnabled(false);
            outlineCheckBox.setEnabled(false);
            outlineRadioButton.setEnabled(false);
        }
        
        //Visually select the new panel
        selectedPanel.setBorder(SELECTED_ETCHED_BORDER);
                
        //Set up all of the boxes
        fillCheckBox.setSelected(selectedPanel.hasFill());
        outlineCheckBox.setSelected(selectedPanel.hasOutline());
    }
    
    /**
     * Changes the displayed color in the color chooser to either the fill
     * or outline color of the selected MiniPanel's game object, depending
     * on the selection in the radio button.
     * @param evt the reported action event - unused
     */
    private void radioButtonActionPerformed(ActionEvent evt) {
        colorChooser.setColor(fillRadioButton.isSelected() ? 
                selectedPanel.fillColor() : selectedPanel.outlineColor());
    }
    
    /**
     * Updates the selected panel's color scheme based on user selection and
     * refreshes the selected panel's display.
     * @param evt the reported change event - unused
     */
    private void colorChooserChangePerformed(ChangeEvent evt){
        //Update the color based on selection
        Color newCol = colorChooser.getColor();
        if(fillRadioButton.isSelected()){
            selectedPanel.setFillColor(newCol);
        } else {
            selectedPanel.setOutlineColor(newCol);
        }
        selectedPanel.repaint();
    }

    /**
     * Updates the selected panel's fill option. If the box has been unchecked,
     * the panel will no longer fill in shapes. If the box has been checked,
     * the panel will fill in the shapes in the panel.
     * @param evt the reported action event - unused
     */
    private void fillCheckBoxActionPerformed(ActionEvent evt) {                                             
        selectedPanel.setHasFill(fillCheckBox.isSelected());
        selectedPanel.repaint();
    }

    /**
     * Updates the selected panel's outline option. If the box has been
     * unchecked, shape outlines will no longer show. If the box has been
     * checked, shape outlines will be shown.
     * @param evt the reported action event - unused
     */
    private void outlineCheckBoxActionPerformed(ActionEvent evt) {                                                
        selectedPanel.setHasOutline(outlineCheckBox.isSelected());
        selectedPanel.repaint();
    }
    
    /**
     * Loads a specific color scheme if the user selects one. Otherwise does
     * nothing.
     * @param evt the reported event - unused
     */
    private void loadButtonActionPerformed(ActionEvent evt){
        int returnVal = UIColorSchemes.selectColorScheme();
        if(returnVal != UIColorSchemes.APPROVE_OPTION){
            return;
        }
        
        colorScheme = UIColorSchemes.getSelectedColorScheme();
        loadColorScheme();
    }
    
    /**
     * Saves the input data as a new color scheme in the resources folder
     * and creates an instance of the new color scheme to pass on to the
     * available pool.
     * @param evt the reported event - unused
     */
    private void saveButtonActionPerformed(ActionEvent evt) {
        int returnVal = fileChooser.showSaveDialog(this);
        if(returnVal != JFileChooser.APPROVE_OPTION){
            return;
        }
        
        File saveFile = fileChooser.getSelectedFile();
        String path = saveFile.getParent();
        String fileName = saveFile.getName();
        if(!fileName.endsWith(UIConstants.COLOR_SCHEME_EXT)){
            saveFile = new File(path, fileName + UIConstants.COLOR_SCHEME_EXT);
        }
        
        //Programmatically save the color scheme data
        Map<GameObjectType, Color> fillColors = new HashMap<>();
        Map<GameObjectType, Color> outlineColors = new HashMap<>();
        Map<GameObjectType, Boolean> hasFill = new HashMap<>();
        Map<GameObjectType, Boolean> hasOutline = new HashMap<>();
        
        for(GameObjectType type : GameObjectType.values()){
            MiniPanel panel = panelTypeMap.get(type);
            fillColors.put(type, panel.fillColor());
            outlineColors.put(type, panel.outlineColor());
            hasFill.put(type, panel.hasFill());
            hasOutline.put(type, panel.hasOutline());
        }
        
        //Tell the Color Scheme loader to save the new color scheme
        UIColorSchemes.storeNewColorScheme(saveFile, new BasicColorScheme(fillColors, outlineColors, hasFill, hasOutline));
    }

    /**
     * Cancels the color scheme changes and closes the window, erasing all
     * unsaved data.
     * @param evt the reported action event - unused
     */
    private void cancelButtonActionPerformed(ActionEvent evt) {                                             
        this.setVisible(false);
        this.dispose();
    }
    
    /**
     * Organize the internal swing and awt components as desired. This method
     * is only called by the constructor.
     */
    private void organizeComponents(){
        GroupLayout buttonPanelLayout = new GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saveButton)
                .addGap(4, 4, 4)
                .addComponent(cancelButton)
                .addContainerGap())
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(loadButton)
                .addContainerGap())
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(loadButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(buttonPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(colorChooser, GroupLayout.PREFERRED_SIZE, 670, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(setColorLabel)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(fillRadioButton)
                                        .addComponent(outlineRadioButton))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(outlineCheckBox)
                                            .addComponent(fillCheckBox, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(enableDisableLabel)
                                    .addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sqBumperPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(triBumperPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cirBumperPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(absorberPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(flipperPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(portalPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wallPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ballPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(setColorLabel)
                        .addGap(18, 18, 18)
                        .addComponent(fillRadioButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outlineRadioButton)
                        .addGap(18, 18, 18)
                        .addComponent(enableDisableLabel)
                        .addGap(18, 18, 18)
                        .addComponent(fillCheckBox)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outlineCheckBox)
                        .addGap(31, 31, 31)
                        .addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addComponent(colorChooser, GroupLayout.PREFERRED_SIZE, 407, GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(sqBumperPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(triBumperPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addComponent(portalPanel, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addComponent(wallPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(cirBumperPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(absorberPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(flipperPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addComponent(ballPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }
    
    /**
     * Loads the shapes that are desired for the gadgets into their respective panels.
     */
    private void loadShapes(){
        int sideLength = MiniPanel.SIDE_LENGTH;
        
        //Ordinals of a square centered in the panel
        int x1 = sideLength / 4, x2 = sideLength * 3 / 4;
        int y1 = sideLength / 4, y2 = sideLength * 3 / 4;
        int width = sideLength / 2;
        int height = sideLength / 2;
        
        //Create the square
        Shape[] shapes = new Shape[]{new Polygon(new int[]{x1, x1, x2, x2}, new int[]{y1, y2, y2, y1}, 4)};
        
        sqBumperPanel.setFillShapes(Arrays.asList(shapes));
        sqBumperPanel.setOutlineShapes(Arrays.asList(shapes));
        absorberPanel.setFillShapes(Arrays.asList(shapes));
        absorberPanel.setOutlineShapes(Arrays.asList(shapes));
        
        //Create a triangle
        shapes = new Shape[]{new Polygon(new int[]{x1, x2, x1}, new int[]{y1, y2, y2}, 3)};
        
        triBumperPanel.setFillShapes(Arrays.asList(shapes));
        triBumperPanel.setOutlineShapes(Arrays.asList(shapes));
        
        //Create a circle centered in panel of diameter half the panel
        shapes = new Shape[]{new Ellipse2D.Float(x1, y1, sideLength/2, sideLength/2)};
        
        cirBumperPanel.setFillShapes(Arrays.asList(shapes));
        cirBumperPanel.setOutlineShapes(Arrays.asList(shapes));
        portalPanel.setFillShapes(Arrays.asList(shapes));
        portalPanel.setOutlineShapes(Arrays.asList(shapes));
        
        //Create a circle centered in panel of diameter a quarter the panel
        float x = sideLength * 3 / 8f, y = x;
        float size = sideLength / 4f;
        
        shapes = new Shape[]{new Ellipse2D.Float(x, y, size, size)};
        ballPanel.setFillShapes(Arrays.asList(shapes));
        ballPanel.setOutlineShapes(Arrays.asList(shapes));
        
        //Create a rectangle centered in the panel of dimension 1/6 x 1/2
        height = sideLength / 6;
        shapes = new Shape[]{new Rectangle(x1, (sideLength - height) / 2, width, height)};
        flipperPanel.setFillShapes(Arrays.asList(shapes));
        flipperPanel.setOutlineShapes(Arrays.asList(shapes));
        
        //Create the rectangle of the walls. Wall width will be 1/5
        int thickness = sideLength / 10;
        int length = sideLength * 8 / 10;
        int outX1 = sideLength / 10, outX2 = sideLength * 4 / 5;
        int outY1 = outX1, outY2 = outX2;
        shapes = new Shape[]{
                new Rectangle(outX1, outY1, thickness, length), // left
                new Rectangle(outX1, outY1, length, thickness), // top
                new Rectangle(outX2, outY1, thickness, length), // right
                new Rectangle(outX1, outY2, length, thickness)}; // bottom
        wallPanel.setFillShapes(Arrays.asList(shapes));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ColorSchemeCreatorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ColorSchemeCreatorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ColorSchemeCreatorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ColorSchemeCreatorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
             JFrame frame = new ColorSchemeCreatorFrame();
             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             frame.setVisible(true);
            }
        });
    }                  
}

