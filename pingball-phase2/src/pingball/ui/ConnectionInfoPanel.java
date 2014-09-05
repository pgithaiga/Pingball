package pingball.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

/**
 * This is a GamePanel sub-panel and only shown for players in Online play.
 * This panel handles getting host and port information about the server the
 * user wants to connect to. This panel also tests the connection to see if the
 * connection is a valid one before letting the user proceed to board selection.
 * Valid port numbers must be in the range 0 <= port <= 65535
 * @author Erik
 */
public class ConnectionInfoPanel extends JPanel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Reference to the containing parent panel
     */
    private final GamePanel parentPanel;

    //Inner components
    private final JLabel titleLabel;
    private final JLabel hostURLLabel;
    private final JTextField hostURLField;    
    private final JButton backButton;
    private final JButton nextButton;
    private final JLabel portLabel;
    private final JTextField portField;
    
    //Blinkers for the fields if any input is bad
    private final ComponentBlinker hostURLFieldBlinker;
    private final ComponentBlinker portFieldBlinker;
    
    /**
     * Creates new form ConnectionInfoPanel
     */
    public ConnectionInfoPanel(GamePanel parent) {
        this.parentPanel = parent;
        this.setPreferredSize(new Dimension(UIConstants.DEFAULT_PANEL_WIDTH, 
                UIConstants.DEFAULT_PANEL_HEIGHT));
        
        titleLabel = new JLabel();
        hostURLLabel = new JLabel();
        hostURLField = new JTextField();
        backButton = new JButton();
        nextButton = new JButton();
        portLabel = new JLabel();
        portField = new JTextField();
        
        hostURLFieldBlinker = new ComponentBlinker(hostURLField, 
                UIConstants.DEFAULT_BLINK_RATE,
                UIConstants.DEFAULT_FINITE_BLINK_COUNT);
        portFieldBlinker = new ComponentBlinker(portField, 
                UIConstants.DEFAULT_BLINK_RATE,
                UIConstants.DEFAULT_FINITE_BLINK_COUNT);
        
        
        initComponents();
        setUpListeners();
        organizeComponents();
        
        
    }
    
    /**
     * Initialize the component settings. Only called from the constructor.
     */
    private void initComponents() {

        titleLabel.setFont(UIConstants.SYLFAEN_48); 
        titleLabel.setText("Multiplayer Pingball");

        hostURLField.setFont(UIConstants.SYLFAEN_12); 

        hostURLLabel.setFont(UIConstants.SYLFAEN_14); 
        hostURLLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hostURLLabel.setLabelFor(hostURLField);
        hostURLLabel.setText("Host URL");

        portField.setFont(UIConstants.SYLFAEN_12); 
        portField.setHorizontalAlignment(JTextField.CENTER);
        portField.setText(UIConstants.DEFAULT_PORT);
        portField.setToolTipText("Enter an integer from 0 to 65535 inclusive.");

        nextButton.setFont(UIConstants.SYLFAEN_14); 
        nextButton.setText("Next");
        nextButton.setPreferredSize(new Dimension(70, 31));

        backButton.setFont(UIConstants.SYLFAEN_14); 
        backButton.setText("Back");        
        backButton.setPreferredSize(new Dimension(70, 31));

        portLabel.setFont(UIConstants.SYLFAEN_14); 
        portLabel.setHorizontalAlignment(SwingConstants.CENTER);
        portLabel.setLabelFor(portField);
        portLabel.setText("Port");
    }                   

    /**
     * Adds listeners to the components. Only called from the constructor
     */
    private void setUpListeners(){
        backButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                parentPanel.backButtonPressed(ConnectionInfoPanel.this);
            }
            
        });
        
        nextButton.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });
        
        //This prevents bad values from being put into the port field
        ((PlainDocument) portField.getDocument()).setDocumentFilter(new DocumentFilter(){
            
            private boolean validInt(String text){
                try{
                    int val = Integer.parseInt(text);
                    return val >= 0 && val <= 65535;
                } catch (NumberFormatException e){
                    return false;
                }
            }
            
            @Override public void insertString(FilterBypass fb, int offset, 
                    String string, AttributeSet attr) throws BadLocationException {
                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder();
                sb.append(doc.getText(0, doc.getLength()));
                sb.insert(offset, string);
                
                if(validInt(sb.toString())){
                    super.insertString(fb, offset, string, attr);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
            
            @Override public void replace(FilterBypass fb, int offset, int length, 
                    String text, AttributeSet attrs) throws BadLocationException{
                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder();
                sb.append(doc.getText(0, doc.getLength()));
                sb.replace(offset, offset + length, text);
                
                if(validInt(sb.toString())){
                    super.replace(fb, offset, length, text, attrs);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
    }
    
    /**
     * Loads the given host and port values and tries to connect. IF connection
     * is successful, the parent panel is notified and the user is forwarded
     * to the board selection panel.
     * @param hostName the host to connect to
     * @param port the port to connect to
     * @return true if the hostName and port supplied allowed for a valid 
     *      connection
     */
    public boolean loadInfo(String hostName, String port){
        try{
            int portNum = Integer.valueOf(port);
            if(portNum < 0 || portNum > Short.MAX_VALUE){
                throw new NumberFormatException();
            }
            Socket socket = new Socket(hostName, portNum);
            socket.close();
        } catch (Exception e){
            JOptionPane.showMessageDialog(parentPanel, 
                    "Could not connect to host and port:\n"
                    + "Host: " + hostName + "\nPort: " +port, 
                    "ConnectionError", JOptionPane.ERROR_MESSAGE);
            portField.setText(port);
            hostURLField.setText(hostName);
            return false;
        }
        parentPanel.nextButtonPressed(this, hostName, port);
        return true;
    }
    
    /**
     * Checks to make sure that all fields are filled in and that the 
     * information supplied is valid. If any fields are left blank, they will
     * blink rapidly to notify the user that they need values put in. If the
     * connection is bad, the user is also notified and is not allowed to
     * proceed until the connection is good or if the user backs out of
     * online mode.
     * @param evt the action event that is fired - unused
     */
    private void nextButtonActionPerformed(ActionEvent evt){
        String hostName = hostURLField.getText();
        String port = portField.getText();
        boolean noHost = hostName.equals("");
        boolean noPort = port.equals("");
        
        if(noHost || noPort){//If a field doesn't have data in it
            if(noHost){ hostURLFieldBlinker.restart(); }
            if(noPort){ portFieldBlinker.restart(); }
            return;
        }
        
        //Make sure the hostname and port will allow a valid connection
        try {
            Socket socket = new Socket(hostName, Integer.valueOf(port));
            socket.close();
            parentPanel.nextButtonPressed(this, hostName, port);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, 
                                          "Error connecting to host: \n" 
                                              + hostName 
                                              + "\n with port number: " 
                                              + port, 
                                              "Connection Error", 
                                              JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Organize the internal components.
     */
    private void organizeComponents(){
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nextButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                                .addGap(146, 146, 146))
                            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 420, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(portLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hostURLLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(hostURLField, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111))
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(portField, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                .addGap(161, 161, 161))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(hostURLLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hostURLField, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(portLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(portField, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nextButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                    .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );
    }
    
}
