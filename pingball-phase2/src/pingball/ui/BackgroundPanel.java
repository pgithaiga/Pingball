package pingball.ui;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author PeterGithaiga
 * A panel that is created in the background and so is invisible to the user
 * 
 */
public class BackgroundPanel extends JPanel {

    private final BoardPanel bPanel = new BoardPanel();
    private final JFrame frame = new JFrame();
    private String filePath;
    private String fileName;
    
    /**
     * Creates a new BackgroundPanel
     * @param filePath path of boardFile to use
     * @param fileName name of boardFile
     */
    public BackgroundPanel(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
        frame.setContentPane(bPanel);
        frame.pack();
        frame.setVisible(false);
        try {
            bPanel.play(new String[]{filePath});//start game with file in the filePath
            runBackgroundGUI(); //take a screenshot of the game
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }  
    }
    
    /**
     * Takes screenshot of a component
     * @param component to take picture of
     * @return the image of the screenshot
     */
    public static BufferedImage getScreenShot(
            Component component) {

        BufferedImage image = new BufferedImage(
          component.getWidth(),
          component.getHeight(),
          BufferedImage.TYPE_INT_RGB
          );
        component.paint( image.getGraphics() );
        return image;
      }
    
    /**
     * Takes a screenshot and creates a new .png file with the image
     */
    public void takeScreenShot(){
        BufferedImage img = getScreenShot(
                frame.getContentPane() );
              try {
                ImageIO.write( //write the new image
                  img,
                  "png",
                  new File(UIConstants.IMAGES_ROOT, fileName + ".png"));
              } catch(Exception e) {
                e.printStackTrace();
              }
    }
    
    /**
     * Runs a game in the background for the purposes of taking a screenshot
     * @throws InterruptedException when thread is interrupted
     */
    public void runBackgroundGUI() throws InterruptedException{
        Runnable r = new Runnable() {
            public void run() {
              
              takeScreenShot();
             
            }
          };
          Thread genPreview = new Thread(r);
          genPreview.start();
          
          genPreview.join(); //wait for thread to finish execution then kill game
          
          bPanel.killGame(); //kill the game after the screenshot is taken
          
        }
    
}  
    

