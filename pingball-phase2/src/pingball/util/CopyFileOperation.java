package pingball.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.SwingWorker;

import pingball.ui.UIConstants;

/**
 * 
 * @author PeterGithaiga
 * Copies the file in filePath and puts the new file in the Boards Root directory
 *
 */
public class CopyFileOperation{
    
    private CopyFiles operation;
    private File file;
    private JButton playButton;

    /**
     * Create and start a new copy operation
     * @param file to be copied
     * @param playButton button is disabled during the copying process so a user does not hit 
     *          the play button while the file is still being imported
     * @throws Exception if error occurs during import
     */
    public CopyFileOperation(File file, JButton playButton) throws Exception {
        this.file = file;
        this.playButton = playButton;
        
        playButton.setEnabled(false); //disable the play button while file is being copied
        
        if (file.isFile() && file.length() > 0) {           

            operation = new CopyFiles();
            
            new Thread(new DownLoader()).start();//start copying the file
            
        } else {
            throw new Exception("file import error!");
        }
               
    }
    
    /**
     * 
     * @author PeterGithaiga
     *  Implements runnable to enable spunning a new thread
     */
    public class DownLoader implements Runnable{
        
        @Override
        public void run() {
            operation.execute();
        }
        
    }
    
    /**
     * 
     * @author PeterGithaiga
     *  Class that performs the actual copy operation
     */
    public class CopyFiles extends SwingWorker<Void, Void> {        
        private static final int PROGRESS_CHECKPOINT = 10000;
        
        /**
         * performs the actual copy operation
         */
        @Override
        public Void doInBackground() {
            int progress = 0;
            
            long totalBytes = file.length();
            long bytesCopied = 0;
            
            while (progress < 100) {                 
                File destFile = new File(UIConstants.BOARDS_ROOT, file.getName());//create a new file in the destination folder
                long previousLen = 0;
                
                try {
                    InputStream in = new FileInputStream(file);
                    OutputStream out = new FileOutputStream(destFile);                    
                    byte[] buf = new byte[1024];//create a buffer
                    int counter = 0;
                    int len;
                    
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                        counter += len;
                        bytesCopied += (destFile.length() - previousLen);
                        previousLen = destFile.length();
                        if (counter > PROGRESS_CHECKPOINT || bytesCopied == totalBytes) {
                            progress = (int)((100 * bytesCopied) / totalBytes);
                            counter = 0;
                        }
                    }
                    in.close();
                    out.close();
                    playButton.setEnabled(true); //enable the play button after the copy operation is finished
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        
    }
}
