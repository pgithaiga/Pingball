package pingball.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

/**
 * This is one of the GamePanel sub-panels. This Panel handles displaying the
 * type of game that the user can select - Online or Local. It then forwards
 * this user's decision to the parent GamePanel.
 * @author Erik
 */
public class OnlineVLocalPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Reference to the containing parent panel
     */
    private final GamePanel parentPanel;
    
    //Inner components
    private final JButton localButton = new JButton();
    private final JLabel messageLabel = new JLabel();
    private final JButton onlineButton = new JButton();
    private final JLabel pingballLabel = new JLabel();
    
    /**
     * Creates new form newGamePanel
     */
    public OnlineVLocalPanel(GamePanel parent) {
        this.parentPanel = parent;
        this.setPreferredSize(new Dimension(UIConstants.DEFAULT_PANEL_WIDTH, 
                UIConstants.DEFAULT_PANEL_HEIGHT));
        
        initComponents();
        setUpListeners();
        organizeComponents();
    }

    /**
     * Initialize all of the inner components.
     */
    private void initComponents() {

        onlineButton.setFont(UIConstants.SYLFAEN_20); 
        onlineButton.setText("<html><center>Online<br>Multiplayer</center></html>");
        onlineButton.setActionCommand("ONLINE_PLAY");

        localButton.setFont(UIConstants.SYLFAEN_20); 
        localButton.setText("<html><center>Local<br>Singleplayer</center></html>");
        localButton.setActionCommand("LOCAL_PLAY");

        pingballLabel.setFont(UIConstants.SYLFAEN_60); 
        pingballLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pingballLabel.setText("PINGBALL");

        messageLabel.setFont(UIConstants.TIMES_ITAL_11); 
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setText("Please select a gameplay type.");
    }
    
    /**
     * Sets up the listeners for the inner game objects
     */
    private void setUpListeners(){
        onlineButton.addActionListener(new ActionListener(){

            @Override public void actionPerformed(ActionEvent evt) {
                parentPanel.onlineButtonPressed(OnlineVLocalPanel.this);
            }
            
        });
        
        localButton.addActionListener(new ActionListener(){

            @Override public void actionPerformed(ActionEvent evt) {
                parentPanel.localButtonPressed(OnlineVLocalPanel.this);
            }
            
        });
    }
    
    /**
     * Organize the components inside of the panel.
     */
    private void organizeComponents(){
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(onlineButton, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(localButton, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                    .addComponent(pingballLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(messageLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(46, 46, 46))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(pingballLabel)
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(onlineButton, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
                    .addComponent(localButton, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(messageLabel)
                .addGap(93, 93, 93))
        );
    }
}
