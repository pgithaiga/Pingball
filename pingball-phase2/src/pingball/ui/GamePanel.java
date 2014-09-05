package pingball.ui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * This class handles collecting information about the type of game a user
 * wants at the uppermost level. It consolidates information about server
 * host and port values to connect to, online v local play, and board type.
 * After collecting enough information, it can begin a game for the user.
 * It does this by managing 4 sub-panels - <code>ChooseBoardPanel,
 * ConnectionInfoPanel, OnlineVLocalPanel, BoardPanel</code> which handle
 * the data collection and display. The managing includes swapping the view
 * that a user sees depending on input and routing pertinent information
 * to the managed boards. When allowing a game to start, this panel must have
 * information about the type of board to play on. If the user desires an
 * online experience, the panels must have information about <b>both</b> the
 * server host and port.
 * @author Erik
 */
public class GamePanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Describes the state that the game can be in. By default, the game loads
     * up in the NONE state because the user has not defined if they want
     * LOCAL or ONLINE play.
     * @author Erik
     *
     */
    enum GameType{
        ONLINE, LOCAL, NONE;
    }
    
    /**
     * The layout that will be used to swap between contained panels
     */
    private final CardLayout layout;
    
    //The three sub panels that collect information
    private final OnlineVLocalPanel ovlPanel;
    private final ChooseBoardPanel cbPanel;
    private final ConnectionInfoPanel ciPanel;
    
    //The sub panel that displays the games
    private final BoardPanel bPanel;
    private final String OVL_PANEL = "OnlineVLocalPanel";
    private final String CB_PANEL = "ChooseBoardPanel";
    private final String CI_PANEL = "ConnectionInfoPanel";
    private final String B_PANEL = "BoardPanel";
    
    private GameType type = GameType.NONE;
    private String hostName;
    private String portNum;
    private String boardPath;
    
    /**
     * Creates new form GamePanel
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(UIConstants.DEFAULT_PANEL_WIDTH, 
                UIConstants.DEFAULT_PANEL_HEIGHT));

        layout = new CardLayout();
        ovlPanel = new OnlineVLocalPanel(this);
        cbPanel = new ChooseBoardPanel(this);
        ciPanel = new ConnectionInfoPanel(this);
        bPanel = new BoardPanel();
        
        this.setLayout(layout);
        this.add(ovlPanel, OVL_PANEL);
        this.add(ciPanel, CI_PANEL);
        this.add(cbPanel, CB_PANEL);
        this.add(bPanel, B_PANEL);
        layout.show(this, OVL_PANEL);
        
    }
    
    /**
     * Immediately loads into local player mode and loads the desired board
     * file. If the board file has not been imported into the project yet,
     * it is first imported into the project resources and then the game starts.
     * If the given file does not represent a board or is not an existing
     * file in the file system, the user must select or import another board
     * before the game may start.
     * @param boardFile the path to the board file
     */
    public void loadIntoLocalPlayer(String boardFile){
        localButtonPressed(ovlPanel);
        cbPanel.selectBoard(boardFile, true);
    }
    
    /**
     * Immediately loads into online player mode and attempts to create
     * connections with the desired host and port values. If successful, the
     * user is then allowed to select a board to play on. Otherwise the user
     * must correct the host and port values given.
     * @param host the server host to connect to
     * @param port the server port to connect to
     */
    public void loadIntoOnlinePlayer(String host, String port){
        onlineButtonPressed(ovlPanel);
        ciPanel.loadInfo(host, port);
    }
    
    /**
     * Immediately loads into online player mode with the host and port values
     * given and the board file given. If unable to connect to the given server
     * the client must correct the host and port values given before proceeding.
     * Likewise if unable to import or find the board represented by the
     * given board file path, the user must select or import a different board
     * before being allowed to play the game.
     * @param host the host of the server
     * @param port the port of the server
     * @param boardFile the file path to the desired board file
     */
    public void loadIntoOnlinePlayer(String host, String port, String boardFile){
        onlineButtonPressed(ovlPanel);
        boolean successfulConnect = ciPanel.loadInfo(host, port);
        cbPanel.selectBoard(boardFile, successfulConnect);
    }
    
    /**
     * Starts a completely new game from scratch. This forces the user to go
     * back to the beginning a choose an online v local game.
     */
    public void newGame() {
        bPanel.killGame();
        type = GameType.NONE;
        hostName = portNum = boardPath = null;
        layout.show(this, OVL_PANEL);
    }
    
    /**
     * Toggles the game between play and pause
     */
    public void pauseResumeGame(){
        bPanel.pauseResume();
    }
    
    /**
     * Disconnects the user from the server and kills the game the user is
     * playing. This method has no effect if the user is in local player mode
     */
    public void disconnect(){
        if(type == GameType.ONLINE && bPanel.killGame()){
            type = GameType.NONE;
            layout.show(this, CI_PANEL);
        }
    }
    
    /**
     * Resets the game using the initial user settings. This resets the board
     * entirely, killing and deleting the original and then recreating the
     * board from scratch. This also removes any adjoining walls in Online mode.
     */
    public void resetGame() {
        if(bPanel.killGame()){
            startGame();
        }
    }
    
    /**
     * Alerts this board that the user has selected a local game. This causes
     * the displayed panel to swap out, and lets the user now select a board
     * to play on.
     * @param source the panel that told the Game Panel this, must be the
     *      GamePanel's OnlineVLocalPanel
     */
    void localButtonPressed(JComponent source){
        if(source != ovlPanel){
            throw new IllegalArgumentException("Source must be the OnlineVLocalPanel");
        }

        type = GameType.LOCAL;
        layout.show(this, CB_PANEL);
    }
    
    /**
     * Alerts this board that the user has selected an online game. This causes
     * the displayed panel to swap out, and lets the user provide the server
     * information for the game.
     * @param source the panel that told the Game Panel this, must be the
     *      GamePanel's OnlineVLocalPanel
     */
    void onlineButtonPressed(JComponent source){
        if(source != ovlPanel){
            throw new IllegalArgumentException("Source must be the OnlineVLocalPanel");
        }

        type = GameType.ONLINE;
        layout.show(this, CI_PANEL);
    }
    
    /**
     * Alerts this board that the user has wished to go back one panel, causing
     * the displayed panel to swap out.
     * @param source the panel that told the Game Panel this, must be the
     *      GamePanel's OnlineVLocalPanel or the ConnectionInfoPanel
     */
    void backButtonPressed(JComponent source){
        if(source != ciPanel && source != cbPanel){
            throw new IllegalArgumentException("Source must be either the ConnectionInfoPanel or ChooseBoardPanel");
        }
        
        if(source == ciPanel){
            newGame();
            return;
        }
        
        //back button can either be in CI or CB panel if we're online
        if(type == GameType.ONLINE){
            layout.previous(this);
        } else {//If we're local, we can just jump to the opening screen
            layout.show(this, OVL_PANEL);
        }
    }
    
    /**
     * Tells the game that the user has told the program that the game is ready
     * for play. This starts up the game.
     * @param source the panel that told the Game Panel this, must be the
     *      GamePanel's ChooseBoardPanel
     * @param boardFilePath the path to the board that the user wants
     */
    void playButtonPressed(JComponent source, String boardFilePath){
        if(source != cbPanel){
            throw new IllegalArgumentException("Source must be ChooseBoardPanel");
        }
        boardPath = boardFilePath;
        layout.next(this);

        startGame();
    }
    
    /**
     * Starts the game. This method consolidates the information the user has 
     * input on the <code>ConnectionInfoPanel</code> and the 
     * <code>ChooserBoardPanel</code> to give to the <code>BoardPanel</code>
     * which handles the actual construction of the game engine.
     */
    private void startGame(){
        String[] args;
        if(hostName == null){
            args = new String[]{boardPath};
        } else {
            args = new String[]{"--host", hostName, "--port", portNum, boardPath};
        }
        
        try {
            bPanel.play(args);
            bPanel.requestFocusInWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Tells this GamePanel that a next button in the enclosed panels of its
     * card layout has been pressed. This forwards the displayed panel to
     * the proper successor panel.
     * @param source the component that is calling this method, must be
     *      this GamePanel's ConnectionInfoPanel
     * @param hostName the host name for the server to connect to
     * @param port the value of the port on the server to connect to
     */
    void nextButtonPressed(JComponent source, String hostName, String port){
        if(source != ciPanel){
            throw new IllegalArgumentException("Source must be the ChooseBoardPanel");
        }
        this.hostName = hostName;
        this.portNum = port;
        layout.show(this, CB_PANEL);
    }
}
