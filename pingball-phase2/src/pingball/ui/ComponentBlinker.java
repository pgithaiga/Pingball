package pingball.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * This class acts as an intermediary to cause a <code>JComponent</code>'s 
 * to blink. The rate at which the component will blink can be set as well as 
 * the number of times the component will be expected to blink.
 * By default a <code>ComponentBlinker</code> will blink indefinitely until
 * forcibly told to stop as follows:
 * <pre>
 * {@code
 * ComponentBlinker blinker = new ComponentBlinker( ... );
 *                  ...
 * blinker.stop();
 * }
 * </pre>
 * <b>NOTICE<b>: each on or off blink is counted as a blink. And therefore if
 * the blinker is set for 3 blinks, the component will blink on, off, and then
 * on and stay on.
 * @author Erik
 *
 */
public class ComponentBlinker {

    /**
     * Internal variable representing a non-stopping blinker
     */
    private static final int INFINITE_BLINKS = -1;
    
    /**
     * The object to blink the background color of
     */
    private final JComponent blinkTarget;
    
    /**
     * The blink rate of the blinker
     */
    private final int blinkRate;
    
    /**
     * The maximum number of blinks this blinker will perform
     */
    private final int maxBlinkCount;
    
    /**
     * Whether or not the blinker will continue forever
     */
    private final boolean infiniteRepeat;
    
    /**
     * The Java Swing timer that will fire events repeatedly
     */
    private final Timer blinkTimer;
    
    /**
     * The color for when the blinker is inactive
     */
    private final Color blinkOffColor;
    
    /**
     * The color for when the blinker turns on
     */
    private final Color blinkOnColor;
    
    /**
     * The number of blinks this component has done since it last started blinking
     */
    private int blinkCount;
    
    /**
     * Defines the next state of the blinker
     */
    private boolean blinkOnNext = true;
    
    /**
     * Creates a blinker for the target that will blink for the default number
     * of times as defined by {@code UIConstants.DEFAULT_BLINK_RATE}
     * @param target the object to have it's background blink
     */
    public ComponentBlinker(JComponent target){
        this(target, UIConstants.DEFAULT_BLINK_RATE);
    }
    
    /**
     * Creates a new blinker that will blink at a specific blink rate 
     * non-stop.
     * @param target the object to have it's background blink
     * @param blinkRate the time between blink states in milliseconds
     */
    public ComponentBlinker(JComponent target, int blinkRate){
        this(target, blinkRate, ComponentBlinker.INFINITE_BLINKS, true);
    }
    
    /**
     * Creates a blinker that will blink at a specific blink rate for a finite
     * number of blinks.
     * @param target the object to have it's background blink
     * @param blinkRate the time between blink states in milliseconds
     * @param maxBlinkCount the maximum number of state transitions of the
     *      blinker
     */
    public ComponentBlinker(JComponent target, int blinkRate, int maxBlinkCount){
        this(target, blinkRate, maxBlinkCount, false);
    }
    
    /**
     * Private blinker constructor
     * @param target the object to have it's background blink
     * @param blinkRate the time between blink states in milliseconds
     * @param maxBlinkCount the maximum number of state transitions of the
     *      blinker
     * @param repeat whether or not the blinker should continue indefinitely
     */
    private ComponentBlinker(JComponent target, int blinkRate, int maxBlinkCount, boolean repeat){
        this.blinkTarget = target;
        this.blinkRate = blinkRate;
        this.maxBlinkCount = maxBlinkCount;
        this.infiniteRepeat = repeat;
        this.blinkOffColor = target.getBackground();
        this.blinkOnColor = UIConstants.BLINK_ON_COLOR;
        
        this.blinkTimer = new Timer(blinkRate, new ActionListener(){
            
            @Override public void actionPerformed(ActionEvent e) {
                //Stop after a while
                if(!infiniteRepeat && blinkCount == ComponentBlinker.this.maxBlinkCount){
                    blinkTimer.stop();
                    return;
                }
                
                //swap background colors
                if(blinkOnNext){
                    blinkTarget.setBackground(blinkOnColor);
                } else {
                    blinkTarget.setBackground(blinkOffColor);
                }
                
                //update values
                blinkOnNext = !blinkOnNext;
                blinkCount++;
            }
        });
        
        blinkTimer.setInitialDelay(0);
    }
    
    /**
     * Starts the blinker
     */
    public void start(){
        blinkTimer.start();
    }
    
    /**
     * Restarts the blinker, identical to if the blinker was freshly created and
     * the {@code start} method was run
     */
    public void restart(){
        blinkCount = 0;
        blinkOnNext = true;
        blinkTimer.restart();
    }
    
    /**
     * Tells the blinker to stop blinking
     */
    public void stop(){
        blinkTimer.stop();
    }
}
