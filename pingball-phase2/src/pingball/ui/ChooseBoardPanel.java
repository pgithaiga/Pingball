package pingball.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import pingball.util.CopyFileOperation;
import pingball.util.ImageUtils;


/**
 * This panel lets the user choose a board to play on. The panel has many
 * features including an option to import an external board file into the 
 * project as well as preview the game board. Once a valid board is chosen for
 * play, the user is able to begin playing on the board by selecting "Play"
 * @author Erik, Peter
 */
public class ChooseBoardPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Reference to the containing parent GamePanel
     */
    private final GamePanel parentPanel;
    
    //Inner components
    private final JLabel titleLabel;
    private final JButton backButton;
    private final JButton playButton;
    private final JButton importButton;
    private final JLabel boardLabel;
    private final DefaultListModel<String> listModel; 
    private final JList<String> boardList;
    private final JScrollPane boardScrollPane;
    private final JLabel previewLabel;
    private final JLabel imageLabel;
    private final JFileChooser fileChooser;
    
    //map file name to file path
    private Map<String,String> fileMap = new HashMap<>();
    
    //map file name to image preview
    private Map<String,String> imageMap = new HashMap<>();
    
    /**
     * Creates new form ChooseBoardPanel
     */
    public ChooseBoardPanel(GamePanel parent) {
        this.parentPanel = parent;
        this.setPreferredSize(new Dimension(UIConstants.DEFAULT_PANEL_WIDTH, 
                UIConstants.DEFAULT_PANEL_HEIGHT));
        
        titleLabel = new JLabel();

        backButton = new JButton();
        playButton = new JButton();
        importButton = new JButton();
        
        boardLabel = new JLabel();
        listModel = new DefaultListModel<>();
        boardList = new JList<>();
        boardScrollPane = new JScrollPane();
                
        previewLabel = new JLabel();
        imageLabel = new JLabel();
        fileChooser = new JFileChooser();
        
        initComponents();
        setUpListeners();
        organizeComponents();
    }

    /**
     * Initialize the components. Only called from the constructor
     */
    private void initComponents() {
        titleLabel.setFont(UIConstants.SYLFAEN_36); 
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Board Selection");

        boardLabel.setFont(UIConstants.SYLFAEN_14); 
        boardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        boardLabel.setLabelFor(boardList);
        boardLabel.setText("Available Boards");
        boardLabel.setMaximumSize(new Dimension(166, 24));
        boardLabel.setMinimumSize(new Dimension(166, 24));
        boardLabel.setPreferredSize(new Dimension(166, 24));

        boardList.setBackground(UIConstants.LIGHT_GRAY);
        boardList.setFont(UIConstants.SYLFAEN_12); 
        boardList.setModel(listModel);
        boardScrollPane.setViewportView(boardList);

        previewLabel.setFont(UIConstants.SYLFAEN_14); 
        previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        previewLabel.setLabelFor(previewLabel);
        previewLabel.setText("Preview");
        previewLabel.setMaximumSize(new Dimension(220, 24));
        previewLabel.setMinimumSize(new Dimension(220, 24));
        previewLabel.setPreferredSize(new Dimension(220, 24));

        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setMaximumSize(new Dimension(220, 220));
        imageLabel.setMinimumSize(new Dimension(220, 220));
        imageLabel.setPreferredSize(new Dimension(220, 220));
        imageLabel.setOpaque(true);
        imageLabel.setBackground(UIConstants.LIGHT_GRAY);
        
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Pingball Board Files", "pb"));

        importButton.setFont(UIConstants.SYLFAEN_14); 
        importButton.setText("Import");
        importButton.setPreferredSize(new Dimension(71, 31));

        backButton.setFont(UIConstants.SYLFAEN_14); 
        backButton.setText("Back");
        backButton.setPreferredSize(new Dimension(70, 31));

        playButton.setFont(UIConstants.SYLFAEN_14); 
        playButton.setText("Play");
        playButton.setPreferredSize(new Dimension(70, 31));
        
        //list available boards
        File folder = new File(UIConstants.BOARDS_ROOT);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if(file.isFile() && file.getName().endsWith(UIConstants.BOARD_EXTENSION)){
                fileMap.put(file.getName(), file.getAbsolutePath());
                listModel.addElement(file.getName());
                File imgFile = new File(UIConstants.IMAGES_ROOT, file.getName() + ".png");
                if(imgFile.exists()){
                    imageMap.put(file.getName(), imgFile.getAbsolutePath());
                }
            }
        }
        if(listModel.size()> 0){
            boardList.setSelectedIndex(0);
            String imageKey = boardList.getSelectedValue();
            //System.out.println(imageKey);
            if(!imageMap.containsKey(imageKey)){
                new BackgroundPanel(fileMap.get(imageKey),imageKey);
                File f = new File(UIConstants.IMAGES_ROOT, imageKey + ".png");
                imageMap.put(imageKey, f.getAbsolutePath());      
            }
            BufferedImage image = ImageUtils.scaleImage(220, 220, imageMap.get(imageKey));
            ImageIcon icon = new ImageIcon(image);
            imageLabel.setIcon(icon);
        }
        
        boardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
    }                     
    
    /**
     * Add listeners to the components. Only called from the constructor
     */
    private void setUpListeners(){
        backButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                parentPanel.backButtonPressed(ChooseBoardPanel.this);
            }
            
        });
        
        playButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                String fileName = boardList.getSelectedValue();
                if(fileName != null){
                    String filePath = fileMap.get(fileName);
                    parentPanel.playButtonPressed(ChooseBoardPanel.this, filePath);
                }
            }
            
        });
        
        importButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                importExternalBoardAction(evt);
            }
            
        });
        
        boardList.addListSelectionListener(new BoardListSelectionListener());
        
        
    }
    
    /**
     * This class handles events regarding selection of values in the list.
     * When a value is selected, this listener updates the preview label to
     * show a preview of the selected board.
     * @author Peter
     *
     */
    private class BoardListSelectionListener implements ListSelectionListener{
        
        @Override
        public void valueChanged(ListSelectionEvent e) {
            
            
            String imageKey = boardList.getSelectedValue();
            //System.out.println(imageKey);
            if(!imageMap.containsKey(imageKey)){ //Create an image for the board if one doesn't exist
                new BackgroundPanel(fileMap.get(imageKey),imageKey);
                File f = new File(UIConstants.IMAGES_ROOT, imageKey + ".png");
                imageMap.put(imageKey, f.getAbsolutePath());      
            }
            BufferedImage image = ImageUtils.scaleImage(imageLabel.getWidth(), imageLabel.getHeight(), imageMap.get(imageKey));
            ImageIcon icon = new ImageIcon(image);
            imageLabel.setIcon(icon);
            
        }
        
    }
    
    /**
     * Imports a board if needed and then selects it in the list. If the game
     * should immediately start after successful selection of the board, the
     * game starts. Otherwise the user must select play to begin the game. The
     * <code>startGame</code> parameter is only valid if a board was succesfully
     * chosen. Reasons for an unsuccessful choose would be either because the
     * file given does not exist or it does not represent a board file.
     * @param boardPath the file path to the board desired
     * @param startGame whether the game should start immediately
     */
    public void selectBoard(String boardPath, boolean startGame){
        File boardFile = new File(boardPath);
        
        if(!boardFile.exists() || !boardPath.endsWith(UIConstants.BOARD_EXTENSION)){
            JOptionPane.showMessageDialog(null, "Board File not found:\n" 
                    + boardPath, 
                    "Error Loading Board", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        importBoard(boardFile);       
        if(startGame){
            playButton.doClick();
        }
    }
    
    /**
     * Handles user selection of an external board and importing said board
     * into the project.
     * @param evt the fired action event - unused
     */
    private void importExternalBoardAction(ActionEvent evt){
        int returnVal = fileChooser.showOpenDialog(this);
        
        if(returnVal == JFileChooser.APPROVE_OPTION){
            importBoard(fileChooser.getSelectedFile());
        }
    }
    
    /**
     * Imports a board into the project if it does not already exist. If the
     * board to import has a different file path but a same name as a board
     * in the folder, the original board is overwritten.
     * @param selectedFile the file to import
     */
    private void importBoard(File selectedFile){
        String fileName = selectedFile.getName();
        
        //Check to see if we already have the file
        if(fileMap.containsKey(fileName) && fileMap.get(fileName).equals(selectedFile.getAbsolutePath())){
            boardList.setSelectedValue(fileName, true);
            return;
        }
        
        //Attempt to copy the file
        try {
            new CopyFileOperation(selectedFile,playButton);
            
            File importFile = new File(UIConstants.BOARDS_ROOT, fileName);
            
            if(!fileMap.containsKey(fileName)){
                listModel.addElement(importFile.getName());
            }
            fileMap.put(fileName, importFile.getAbsolutePath());
            
            String imageKey = fileName;
            //System.out.println(imageKey);
            if(!imageMap.containsKey(imageKey)){
                new BackgroundPanel(fileMap.get(imageKey),imageKey);
                File f = new File(UIConstants.IMAGES_ROOT, imageKey + ".png");
                imageMap.put(imageKey, f.getAbsolutePath());            
            }
            
            boardList.setSelectedValue(selectedFile.getName(), true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Unable to import board file", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }
    
    /**
     * Organizes the internal components of the panel
     */
    private void organizeComponents(){
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(importButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95)
                .addComponent(backButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(playButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(boardLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(boardScrollPane, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(imageLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE)
                        .addComponent(previewLabel, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(boardLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(previewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(boardScrollPane, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
                    .addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(backButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(playButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(importButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))))
        );
    }
    
    
    
    
}
