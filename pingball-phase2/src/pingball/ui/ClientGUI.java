package pingball.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.Arrays;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import pingball.ui.colorscheme.ColorSchemeCreatorFrame;
import pingball.ui.colorscheme.UIColorSchemes;

/**
 * This is the Frame in which the Client is able to see the Pingball Game. The
 * frame also has multiple built in functions, primarily in the menu bar. These
 * functions allow the user to manipulate the game (pause, play, reset, etc)
 * and select and create color schemes for the games. When the GUI loads up,
 * the user is prompted to select a game mode <b>Online</b> or <b>Local</b>.
 * If the user decides to play local, said user is then immediately allowed to
 * pick a board to play on. Otherwise the user must input data about the server
 * the user will connect to first, and then will be allowed to select a board.
 * Once all of the data needed is collected, the user can then begin playing
 * on the board. This GUI does allow for booting up with predefined settings,
 * e.g. a given board designated by a file path, a host name, and/or a port
 * name. The GUI responds to these inputs as follows:
 * 
 *  HOST, PORT, FILE given - Attempts to start up the game immediately in online
 *          mode. If unable to connect to server, the GUI jumps to the frame where
 *          server info is input and then required to put in accurate data before
 *          continuing. If able to connect to the server, but unable to find the
 *          desired board, the user must select a board before beginning play.
 *  HOST, FILE given - Assumes the PORT is the default port "10987" and does as
 *          is listed in the "HOST, PORT, FILE" option
 *  PORT, FILE given - Assumes the HOST is "localhost" and does as is given in
 *          the "HOST, PORT, FILE" option
 *  HOST, PORT - Attempts to connect to the server in online mode and if 
 *          successful, jumps the GUI to the board selection frame. Otherwise 
 *          the user is prompted for more accurate information about the server 
 *          to connect to
 *  FILE given - Attempts to start the game up immediately into Local mode if
 *          the board given is a path to a valid and existing board file.
 *  HOST given - Assumes the PORT is the default port "10987" and does as in 
 *          the "HOST, PORT" option
 *  PORT given - Assumes the HOST is the default host "localhost" and does as in
 *          the "HOST, PORT" option.
 *  NO INPUT - Starts at the indicated view with the user selecting online v local
 *  
 *  Thread Safety Argument - The entire graphics part of this project is only
 *      touched via the Java EventQueue thread. This includes everything from
 *      initialization to event processing etc. The Swing Timer used in the
 *      blinkers of the ConnectionInfoPanel are guaranteed to be safe with
 *      the Java EventQueue by Java. Further, the only spawned threads that have
 *      the possibility of touching the graphics are in the Save option of the
 *      UIColorSchemes file, which only shares immutable data types, and is
 *      hence thread safe. Further, the entire physics simulation is run in
 *      the EventQueue thread, with external listening threads only forwarding
 *      to the physics engine. Hence the Graphics are safely contained within
 *      the Java EventQueue thread as required.
 *  
 *  GUI TESTING Strategy: By nature of GUI's, most testing was done manually
 *  and modularly by package. 
 *  
 *  Color Schemes: In this set of tests, we tried various permutations of
 *      inputs into the GUI and saw how they responded. 
 *      
 *      * When we loaded color schemes repeatedly, we expected the displayed 
 *        game object previews to reflect the loaded color scheme. This was 
 *        tested by selected multiple different color schemes in succession, 
 *        by loading the same color scheme multiple times, by selecting color 
 *        schemes then editing color values and reloading the color schemes. 
 *        Each time the expected color scheme appeared and the frame was 
 *        changed to reflect settings in the color scheme. 
 *      * We tried changing settings randomly across preview types, 
 *        expecting the new changes to be preserved. This was done by selecting
 *        a random MiniPanel, editing colors and fill/outline options, selecting
 *        other panels, editing their options, and then coming back to the
 *        original panel to make sure changes were kept. 
 *      * We then tested the save option. Saving a color scheme should both 
 *        create a file for use later and an immediate object that can be used 
 *        to load the GUI with. We tried creating color schemes, saving them, 
 *        and then exiting and restarting the program. The created color schemes
 *        had their properties saved and could be loaded again whenever the game
 *        was started. 
 *      * Then we tried creating color schemes, saving them and then 
 *        immediately setting the game board to the color schemes. We expected 
 *        no lag when the color scheme was loaded, and we expected the colors
 *        in the game board to change to the selected color scheme. We did this
 *        by rapidly selecting new color schemes, and making sure that the
 *        entire board changed rapidly and smoothly regardless of whether the
 *        simulation was still running. The result was a success. 
 *  
 *  ClientGUI: The core client gui was tested by selecting different permutations
 *      of inputs depending on the situation. Because the GUI is run on a single
 *      thread, it was possible to insert print statements before, during and
 *      after operations to ensure that they worked as performed.
 *      
 *      * First we tested the menu items in the "Game" menu. The new game option
 *        is expected to reset the entire GUI to the first frame where the user
 *        selects online v local. We tested this by selecting new game after 
 *        starting a game, when we're selecting a board, when we're inputing server
 *        data, etc. We also expected the previous game (if one started) to be
 *        irrevocably lost, so as to preserve resources. Regardless of where we
 *        selected the new game, and when we did it (after starting a game for 
 *        the 1st, 3rd time, or 10th times from any panel), the item faithfully
 *        stopped all processes and went to the initial screen.
 *      * Then we tried the reset option. This is expected to have no effect
 *        unless we are in a game. To test this we first made sure that nothing
 *        happened when we were in any of the input frames. Then we tested to
 *        see if the game would restart regardless of how long the game had
 *        played. After that we tested to see if resetting the game would start
 *        it either in foreground or background after an existing game was
 *        killed or backed out of. In all test cases, the rest option only
 *        worked if a game was actively being simulated in the foreground. If
 *        no game was being played, or the game was left (either via disconnect
 *        or new game) the reset button had no effect. 
 *      * Next we tried the Pause/Resume option. First, the expectation was that
 *        this item has no effect unless a game is simulating (same as with 
 *        reset). Therefore we tested the pause/resume option by clicking it
 *        in any frame that was not the game simulation frame (before and after
 *        games were first initialized) to see if there was any console output
 *        detecting an action. There was none and these tests passed. Then we
 *        tried repeatedly pausing and resuming a game to see if the game would
 *        somehow miss an input. The results were again a pass. Lastly we checked
 *        to see if restarting a game would enable the pause/resume option to
 *        still work (Regardless of the state it was in previously). If a game
 *        was paused, restarted and then paused again. We expected the game to
 *        stay paused. The button passed these tests.
 *      * Disconnect: Disconnect is expected to only work if in an online game.
 *        When the disconnect is called, the game immediately stops and the
 *        user is sent back to the server info input frame. We again tested
 *        to see if there was any console response to selecting the disconnect
 *        when out of game or in a local game. We expect that if the game is in
 *        local, even if previous games were in online mode, that the disconnect
 *        has no effect. Further, the disconnect should have no effect when the
 *        game hasn't even begun simulating. All tests for expected behavior
 *        passed.
 *      * Exit: We expected the game to immediately end when exit is selected.
 *        This was tested from all frames at all stages of input. Each time the
 *        entire program closed.
 *      * Color Set: As mentioned before, choosing this item and then selecting
 *        a new color scheme should have an immediate effect on the board. This
 *        was tested by selecting different color schemes in rapid succession,
 *        and by selecting the same color scheme in rapid succession. The first
 *        had the expected output of an immediately changing the board. The
 *        second has the expected output of not changing the board color scheme
 *        at all. In both cases, the color scheme changed (or didn't change)
 *        as expected with no lag. 
 *      * Color Scheme Creation: This button is expected to simply boot up the
 *        color scheme creator form. This form was tested independently as 
 *        detailed above. However there was an added expectation that newly
 *        created color schemes can immediately be used in the Color Scheme
 *        Set option. These tests all passed.
 *        
 *  Testing the Game Panel:
 *      Testing of the game panel was partially done concurrently as the testing
 *      of the menu items.
 *      * OnlineVLocalPanel: This panel is expected to change the game mode
 *        type to whatever the user selected, regardless of previous settings.
 *        We tested selecting Local and then backing out, expecting the game
 *        state to change from None to Local to None. We did the same with the
 *        Online option. This was further tested with the New Game option
 *        which boots into this frame. Each time, the panel produced expected
 *        behavior. If the Local game was chosen, the game state changed to local.
 *        If the online game was chosen, the game was changed to online. And if
 *        for any reason, the game went back to this panel, the game state
 *        changed to none and forced the user to select a game type.
 *      * ConnectionInfoPanel: This panel is expected to only allow the user to
 *        input a valid port number into the port field, and that the user
 *        must input valid server information or else nothing would happen. 
 *        * We first tested the port field by inputting nothing, numbers 
 *        outside of the designated port range and non-number characters. 
 *        Non-number characters were immediately rejected and not displayed as 
 *        was expected. Numbers outside of the range were not displayed as well.
 *        For instance 10000 would be displayed, but inputting another number 
 *        after that would not. IF nothing was input, then the user was greeted 
 *        by a briefly blinking field as was expected.
 *        * Then we tested to see if server connection tests were done properly.
 *        We expected that if no server existed, that the panel would refuse
 *        to let the user move on. While if the server existed, the connection
 *        would be made and then the panel would continue to the selection
 *        page. These tests passed.
 *      * ChooseBoardPanel: In this panel we expected all boards in the
 *        resource folder to be loaded at initialization time. Further we
 *        expected correct previews of the boards to appear when boards were
 *        selected. This was tested by hand drawing the boards and then 
 *        testing to make sure that all of the boards produced the correct
 *        preview along with the panel displaying all possible boards in the
 *        project. This passed.
 *        * Then we tested to see if importing a board worked. Importing a 
 *        board from the resource folder to the resource folder was expected
 *        to do nothing. Importing a valid board from elsewhere in the system
 *        was expected to place it into the resource folder and be immediately
 *        reflected in the list of available boards. If the board had the
 *        same name as another resource board, this boards is expected to be
 *        overwritten. The GUI passed all of these tests
 *      * BoardPanel: This panel allowed us to visually test our gadgets and
 *        key input. We expected that keys that were mapped to by board would
 *        activate the expected gadgets. And that changing board files would
 *        change the key mappings. We also expected that killing a game
 *        in a board would remove all resources used for the board (the client,
 *        simulation, etc). Lastly we expected that changing color schemes
 *        would be immediately reflected in the displayed board as well as
 *        changes in neighboring connected boards for OnlineMode. All of these
 *        tests passed as well.
 *          
 * @author Erik
 */
public class ClientGUI extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    //internal components
    private final JMenuBar menuBar;
    
    private final JMenu gameMenu;
    private final JMenuItem newGameItem;
    private final JMenuItem resetGameItem;
    private final JMenuItem pauseResumeItem;
    private final JMenuItem disconnectItem;
    private final JMenuItem exitItem;
    
    private final JMenu optionsMenu;
    private final JMenuItem createSchemeItem;
    private final JMenuItem setSchemeItem;
    
    private final JMenu helpMenu;
    private final JMenuItem aboutItem;
    
    private final GamePanel gamePanel;
    
    //references to the colorCreator frame because creating them is expensive.
    private final ColorSchemeCreatorFrame colorCreator;
        
    /**
     * Creates new form ClientGui
     */
    public ClientGUI(String[] args) {
        this.setTitle("Pingball");
        menuBar = new JMenuBar();
        
        gameMenu = new JMenu();
        newGameItem = new JMenuItem();
        resetGameItem = new JMenuItem();
        pauseResumeItem = new JMenuItem();
        disconnectItem = new JMenuItem();
        exitItem = new JMenuItem();
        
        optionsMenu = new JMenu();
        createSchemeItem = new JMenuItem();
        setSchemeItem = new JMenuItem();
        
        helpMenu = new JMenu();
        aboutItem = new JMenuItem();
        
        gamePanel = new GamePanel();
        colorCreator = new ColorSchemeCreatorFrame();
        
        initComponents();
        setUpListeners();
        organizeComponents();
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setCenterLocation(Toolkit.getDefaultToolkit().getScreenSize());
        
        handleArguments(args);
    }
    
    /**
     * Handles any input arguments: [--host HOST] [--port PORT] [FILE] and
     * starts the GUI at whatever stage the user needs to input more information.
     * @param args the input arguments
     */
    private void handleArguments(String[] args){
        boolean initialHost = false;
        boolean initialPort = false;
        boolean initialBoard = false;
        
        String host = null, port = null, boardPath = null;
        
        ArrayDeque<String> allArgs = new ArrayDeque<>(Arrays.asList(args));
        while(!allArgs.isEmpty()){
            String s = allArgs.removeFirst();
            if(s.equals("--host")){
                initialHost = true;
                host = allArgs.removeFirst();
            } else if(s.equals("--port")){
                initialPort = true;
                port = allArgs.removeFirst();
            } else {
                initialBoard = true;
                boardPath = s;
            }
        }
        
        //If nothing was given to us, proceed as normal
        if(!initialHost && !initialPort && !initialBoard){
            return;
        }
        
        //If one of the host/port dual are given, assume defaults
        if(initialPort && !initialHost){
            initialHost = true;
            host = "localhost";
        } else if (initialHost && !initialPort){
            initialPort = true;
            port = UIConstants.DEFAULT_PORT;
        }
        
        if(!initialPort && !initialHost){
            gamePanel.loadIntoLocalPlayer(boardPath);
        } else if(initialPort && initialHost && !initialBoard){
            gamePanel.loadIntoOnlinePlayer(host, port);
        } else if(initialPort && initialHost && initialBoard){
            gamePanel.loadIntoOnlinePlayer(host, port, boardPath);
        }
    }

    /**
     * Centers the window in the area defined by the dimension.
     * @param d the dimension of the screen
     */
    private void setCenterLocation(Dimension d) {
        int x = (d.width - getWidth()) / 2;
        int y = (d.height - getHeight()) / 2;
        setLocation(x, y);
    }
    
    /**
     * Initialize the inner components. Only called from the constructor.
     */
    private void initComponents() {

        //File menu items
        newGameItem.setText("New Game");
        pauseResumeItem.setText("Pause/Resume");
        resetGameItem.setText("Reset");
        disconnectItem.setText("Disconnect");
        exitItem.setText("Exit");
        
        newGameItem.setToolTipText("End this game and start a new game from scratch.");
        disconnectItem.setToolTipText("Disconnect from server if in multiplayer game.");
        pauseResumeItem.setToolTipText("Pause/Resume the game.");
        resetGameItem.setToolTipText("Reset the board of the current game. Disconnects walls if in multiplayer.");
        exitItem.setToolTipText("Close the window and game.");
//        newGameItem.setEnabled(false);
//        pauseResumeItem.setEnabled(false);
//        resetGameItem.setEnabled(false);
        
        //Set up the file menu
        gameMenu.setText("Game");
        gameMenu.add(newGameItem);
        gameMenu.add(resetGameItem);
        gameMenu.add(pauseResumeItem);
        gameMenu.add(disconnectItem);
        gameMenu.add(exitItem);

        //Option Menu Items
        createSchemeItem.setText("Create New Color Scheme");
        setSchemeItem.setText("Set Color Scheme");
        
        createSchemeItem.setToolTipText("Create a new color scheme.");
        setSchemeItem.setToolTipText("Set the current game color scheme.");
        
        //Set up the options menu
        optionsMenu.setText("Options");
        optionsMenu.add(setSchemeItem);
        optionsMenu.add(createSchemeItem);

        //Help menu items
        aboutItem.setText("About...");
        aboutItem.setToolTipText("About the game.");
        
        //Set up the help menu
        helpMenu.setText("Help");
        helpMenu.add(aboutItem);
        
        //Create the menu bar
        menuBar.add(gameMenu);
        menuBar.add(optionsMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    /**
     * Setup the listeners. Only called from the constructor.
     */
    private void setUpListeners(){
        newGameItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent evt) {
                gamePanel.newGame();
            }
        });
        
        pauseResumeItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent evt) {
                gamePanel.pauseResumeGame();
            }
        });
        
        resetGameItem.addActionListener(new ActionListener(){
            @Override public void actionPerformed(ActionEvent evt){
                gamePanel.resetGame();
            }
        });
        
        disconnectItem.addActionListener(new ActionListener(){
            @Override public void actionPerformed(ActionEvent evt){
                gamePanel.disconnect();
            }
        });
        
        exitItem.addActionListener(new ActionListener(){
            @Override public void actionPerformed(ActionEvent evt) {
                gamePanel.newGame();
                System.exit(0);
            }
        });
        
        createSchemeItem.addActionListener(new ActionListener(){
            @Override public void actionPerformed(ActionEvent evt) {
                colorCreator.reset();
                colorCreator.setVisible(true);
            }
        });
        
        setSchemeItem.addActionListener(new ActionListener(){
            @Override public void actionPerformed(ActionEvent evt){
                int returnVal = UIColorSchemes.selectColorScheme();
                if(returnVal != UIColorSchemes.APPROVE_OPTION){
                    return;
                }
                
                UIColorSchemes.setUseSchemeToSelectedScheme();
                ClientGUI.this.repaint();
            }
        });
        
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                aboutItemActionPerformed(evt);
            }
        });
        
        
    }
    
    /**
     * Displays a new panel when the user asks for information about the game
     * @param evt the action event fired - unused.
     */
    private void aboutItemActionPerformed(ActionEvent evt) {                                          
        JOptionPane.showMessageDialog(this, "Pingball Version 1.0 \n"
                + "Written by Sorawit Suriyakarn, Peter Githaiga and Erik Nguyen", 
                "About Pingball", JOptionPane.INFORMATION_MESSAGE);
    }  
    
    /**
     * Organizes the layout of the internal components. Only called from the
     * constructor.
     */
    private void organizeComponents(){
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(gamePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(gamePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        );
    }                                     

                                           

    /**
     * @param args the command line arguments: [--host Host] [--port Port] [File]
     */
    public static void main(final String args[]) {
        //Boot into Nimbus
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //UIColorSchemes.nothing();
                new ClientGUI(args).setVisible(true);
            }
        });
    }
}
