package pingball.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import pingball.client.PingballClient;
import pingball.simulation.Wall;
import pingball.util.DrawObject;
import pingball.util.DrawObject.Layer;

/**
 * BoardPanel class. This class represents the screen where the game is going on.
 * It handles recognizing key inputs and forwards the input to the physics
 * engine to activate pertinent game objects. 
 */
public class BoardPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final double FRAMERATE = 20; // in FPS
    
    private final JLabel downLabel, leftLabel, rightLabel, upLabel;                        
        
    private final JPanel innerPanel;
    
    private PingballClient client = null;
    
    private final Timer timer;
    
    /**
     * Tells if the game is currently in play or pause mode. Only valid if
     * gameActive is true
     */
    private boolean playPause = false;
    
    /**
     * Tells if the game actually has a board loaded that is being played
     */
    private boolean gameActive = false;
    
    /**
     * Contains which keys are currently pressed down. Prevents multiply key
     * pressed events from being fired.
     */
    private Set<Integer> keyDown = new HashSet<>();
        
    /**
     * Creates a BoardPanel
     */
    public BoardPanel(){
        
        timer = new Timer((int) (1e3 / FRAMERATE), new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                client.evolveFrame(1.0 / FRAMERATE);
                innerPanel.repaint();
                upLabel.setText(client.getWallConnection(Wall.Side.TOP));
                downLabel.setText(client.getWallConnection(Wall.Side.BOTTOM));
                leftLabel.setText(client.getWallConnection(Wall.Side.LEFT));
                rightLabel.setText(client.getWallConnection(Wall.Side.RIGHT));
            }
        });
        
        this.setPreferredSize(new Dimension(UIConstants.DEFAULT_PANEL_WIDTH, 
                UIConstants.DEFAULT_PANEL_HEIGHT));

        upLabel = new JLabel();
        downLabel = new JLabel();
        leftLabel = new JLabel();
        rightLabel = new JLabel();
        
        innerPanel = new JPanel(){
            
            private static final long serialVersionUID = 1L;

            @Override public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, 
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.addRenderingHints(rh);
                
                //grab the game object representations and paints the board
                for(Layer l: new Layer[]{Layer.BALL, Layer.GADGET_BG, Layer.GADGET_FG, Layer.FLIPPER, Layer.WALL}) {
                    for(DrawObject d: client.uiRrepresentation(20.0)) {
                        if( d.getLayer() == l ) {
                            if(d.hasFill()){
                                g2d.setColor(d.getFillColor());
                                g2d.fill(d.getShape());
                            }
                            if(d.hasOutline()){
                                g2d.setColor(d.getOutlineColor());
                                g2d.draw(d.getShape());
                            }
                        }
                    }
                }
            }
        };
        
        initComponents();
        organizeComponents();
        
        this.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e){
                if(gameActive && playPause && !keyDown.contains(e.getKeyCode())){
                    keyDown.add(e.getKeyCode());
                    client.keyDown(KeyEvent.getKeyText(e.getKeyCode()));
                }
            }
            
            @Override public void keyReleased(KeyEvent e){
                if(gameActive && playPause && keyDown.contains(e.getKeyCode())){
                    keyDown.remove(e.getKeyCode());
                    client.keyUp(KeyEvent.getKeyText(e.getKeyCode()));
                }
            }
        });
    }
    
    /**
     * Initializes the board
     */
    private void initComponents() {
        innerPanel.setPreferredSize(new Dimension(400, 400));

        upLabel.setFont(UIConstants.SYLFAEN_12);
        upLabel.setHorizontalAlignment(SwingConstants.CENTER);
        upLabel.setPreferredSize(new java.awt.Dimension(400, 20));

        downLabel.setFont(UIConstants.SYLFAEN_12);
        downLabel.setHorizontalAlignment(SwingConstants.CENTER);
        downLabel.setPreferredSize(new java.awt.Dimension(400, 20));

        leftLabel.setFont(UIConstants.SYLFAEN_12);
        leftLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftLabel.setPreferredSize(new java.awt.Dimension(20, 400));

        rightLabel.setFont(UIConstants.SYLFAEN_12);
        rightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightLabel.setPreferredSize(new java.awt.Dimension(20, 400));
    }                
    
    /**
     * Organizes the internal components into the desired arrangement.
     * Only called from the constructor.
     */
    private void organizeComponents(){
        GroupLayout innerPanelLayout = new GroupLayout(innerPanel);
        innerPanel.setLayout(innerPanelLayout);
        innerPanelLayout.setHorizontalGroup(
            innerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        innerPanelLayout.setVerticalGroup(
            innerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(leftLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(downLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(upLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(innerPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(rightLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(upLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(rightLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(leftLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(innerPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(downLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
    }
    
    /**
     * Starts a pingball game!!! Also starts simulating and rendering this panel
     * 
     * @param args the argument that will be passed to create a client (filename, [host, port])
     */
    public void play(String[] args) throws IOException {
        client = new PingballClient(args);
        timer.start();
        gameActive = true;
        playPause = true;
    }
    
    /**
     * Toggles between pausing the game and starting the game. If there is
     * no game being played, this does nothing.
     */
    public void pauseResume(){
        if(!gameActive) {
            return;
        }
        
        if(playPause){
            timer.stop();
        } else {
            timer.start();
        }
        playPause = !playPause;
    }
    
    /**
     * Ends the current game in client. Kills all relating threads as well as 
     * set client back to null. 
     * @return true if there was a game to kill, otherwise false
     */
    public boolean killGame() {
        if(gameActive){
            client.endGame();
            client = null;
        
            gameActive = false;
            playPause = false;
            timer.stop();
            return true;
        }
        return false;
    }
}
