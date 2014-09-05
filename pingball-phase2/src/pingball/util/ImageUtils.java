package pingball.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class ImageUtils {
    
    /**
     * Scales the image in the file path to the dimensions given by width and height
     * @param width width of the scaled down image
     * @param height height of the scaled down image
     * @param filepath path to the image file
     * @return a new scaled down image
     */
    public static BufferedImage scaleImage(int width, int height, String filepath) {
        BufferedImage bufferedImage = null;
        try {
            ImageIcon icon = new ImageIcon(filepath);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) bufferedImage.createGraphics();
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY));
            g2d.drawImage(icon.getImage(), 0, 0, width, height, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bufferedImage;
    }
}
