package pingball.util;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Converts key text descriptions back and forth between the specified key texts
 * for <code>ANTLR</code> and the <code>Java.awt.event.KeyEvent</code> key texts.
 * The <code>ANTLR</code> key texts are described as follows:
 * <pre>
 * {@code
 * 
 *        [a-z] | [0-9] | 'shift' | 'ctrl' | 'alt' | 'meta' | 'space'
 *      | 'left' | 'right' | 'up' | 'down' | 'minus' | 'equals' | 'backspace'
 *      | 'openbracket' | 'closebracket' | 'backslash' | 'semicolon' | 'quote' 
 *      | 'enter' | 'comma' | 'period' | 'slash' ;
 * }
 * </pre>
 * @author Erik
 *
 */
public class KeyTextConvertor {

    private static final Map<String, String> ANTLR_TO_JAVA_MAP;
    private static final Map<String, String> JAVA_TO_ANTLR_MAP;
    static{
        ANTLR_TO_JAVA_MAP = new HashMap<>();
        JAVA_TO_ANTLR_MAP = new HashMap<>();
        
        //Put all keys and values together
        ANTLR_TO_JAVA_MAP.put("a", KeyEvent.getKeyText(KeyEvent.VK_A));
        ANTLR_TO_JAVA_MAP.put("b", KeyEvent.getKeyText(KeyEvent.VK_B));
        ANTLR_TO_JAVA_MAP.put("c", KeyEvent.getKeyText(KeyEvent.VK_C));
        ANTLR_TO_JAVA_MAP.put("d", KeyEvent.getKeyText(KeyEvent.VK_D));
        ANTLR_TO_JAVA_MAP.put("e", KeyEvent.getKeyText(KeyEvent.VK_E));
        ANTLR_TO_JAVA_MAP.put("f", KeyEvent.getKeyText(KeyEvent.VK_F));
        ANTLR_TO_JAVA_MAP.put("g", KeyEvent.getKeyText(KeyEvent.VK_G));
        ANTLR_TO_JAVA_MAP.put("h", KeyEvent.getKeyText(KeyEvent.VK_H));
        ANTLR_TO_JAVA_MAP.put("i", KeyEvent.getKeyText(KeyEvent.VK_I));
        ANTLR_TO_JAVA_MAP.put("j", KeyEvent.getKeyText(KeyEvent.VK_J));
        ANTLR_TO_JAVA_MAP.put("k", KeyEvent.getKeyText(KeyEvent.VK_K));
        ANTLR_TO_JAVA_MAP.put("l", KeyEvent.getKeyText(KeyEvent.VK_L));
        ANTLR_TO_JAVA_MAP.put("m", KeyEvent.getKeyText(KeyEvent.VK_M));
        ANTLR_TO_JAVA_MAP.put("n", KeyEvent.getKeyText(KeyEvent.VK_N));
        ANTLR_TO_JAVA_MAP.put("o", KeyEvent.getKeyText(KeyEvent.VK_O));
        ANTLR_TO_JAVA_MAP.put("p", KeyEvent.getKeyText(KeyEvent.VK_P));
        ANTLR_TO_JAVA_MAP.put("q", KeyEvent.getKeyText(KeyEvent.VK_Q));
        ANTLR_TO_JAVA_MAP.put("r", KeyEvent.getKeyText(KeyEvent.VK_R));
        ANTLR_TO_JAVA_MAP.put("s", KeyEvent.getKeyText(KeyEvent.VK_S));
        ANTLR_TO_JAVA_MAP.put("t", KeyEvent.getKeyText(KeyEvent.VK_T));
        ANTLR_TO_JAVA_MAP.put("u", KeyEvent.getKeyText(KeyEvent.VK_U));
        ANTLR_TO_JAVA_MAP.put("v", KeyEvent.getKeyText(KeyEvent.VK_V));
        ANTLR_TO_JAVA_MAP.put("w", KeyEvent.getKeyText(KeyEvent.VK_W));
        ANTLR_TO_JAVA_MAP.put("x", KeyEvent.getKeyText(KeyEvent.VK_X));
        ANTLR_TO_JAVA_MAP.put("y", KeyEvent.getKeyText(KeyEvent.VK_Y));
        ANTLR_TO_JAVA_MAP.put("z", KeyEvent.getKeyText(KeyEvent.VK_Z));
        ANTLR_TO_JAVA_MAP.put("0", KeyEvent.getKeyText(KeyEvent.VK_0));
        ANTLR_TO_JAVA_MAP.put("1", KeyEvent.getKeyText(KeyEvent.VK_1));
        ANTLR_TO_JAVA_MAP.put("2", KeyEvent.getKeyText(KeyEvent.VK_2));
        ANTLR_TO_JAVA_MAP.put("3", KeyEvent.getKeyText(KeyEvent.VK_3));
        ANTLR_TO_JAVA_MAP.put("4", KeyEvent.getKeyText(KeyEvent.VK_4));
        ANTLR_TO_JAVA_MAP.put("5", KeyEvent.getKeyText(KeyEvent.VK_5));
        ANTLR_TO_JAVA_MAP.put("6", KeyEvent.getKeyText(KeyEvent.VK_6));
        ANTLR_TO_JAVA_MAP.put("7", KeyEvent.getKeyText(KeyEvent.VK_7));
        ANTLR_TO_JAVA_MAP.put("8", KeyEvent.getKeyText(KeyEvent.VK_8));
        ANTLR_TO_JAVA_MAP.put("9", KeyEvent.getKeyText(KeyEvent.VK_9));
        ANTLR_TO_JAVA_MAP.put("shift", KeyEvent.getKeyText(KeyEvent.VK_SHIFT));
        ANTLR_TO_JAVA_MAP.put("ctrl", KeyEvent.getKeyText(KeyEvent.VK_CONTROL));
        ANTLR_TO_JAVA_MAP.put("alt", KeyEvent.getKeyText(KeyEvent.VK_ALT));
        ANTLR_TO_JAVA_MAP.put("meta", KeyEvent.getKeyText(KeyEvent.VK_META));
        ANTLR_TO_JAVA_MAP.put("space", KeyEvent.getKeyText(KeyEvent.VK_SPACE));
        ANTLR_TO_JAVA_MAP.put("left", KeyEvent.getKeyText(KeyEvent.VK_LEFT));
        ANTLR_TO_JAVA_MAP.put("right", KeyEvent.getKeyText(KeyEvent.VK_RIGHT));
        ANTLR_TO_JAVA_MAP.put("up", KeyEvent.getKeyText(KeyEvent.VK_UP));
        ANTLR_TO_JAVA_MAP.put("down", KeyEvent.getKeyText(KeyEvent.VK_DOWN));
        ANTLR_TO_JAVA_MAP.put("minus", KeyEvent.getKeyText(KeyEvent.VK_MINUS));
        ANTLR_TO_JAVA_MAP.put("equals", KeyEvent.getKeyText(KeyEvent.VK_EQUALS));
        ANTLR_TO_JAVA_MAP.put("backspace", KeyEvent.getKeyText(KeyEvent.VK_BACK_SPACE));
        ANTLR_TO_JAVA_MAP.put("openbracket", KeyEvent.getKeyText(KeyEvent.VK_OPEN_BRACKET));
        ANTLR_TO_JAVA_MAP.put("closebracket", KeyEvent.getKeyText(KeyEvent.VK_CLOSE_BRACKET));
        ANTLR_TO_JAVA_MAP.put("backslash", KeyEvent.getKeyText(KeyEvent.VK_BACK_SLASH));
        ANTLR_TO_JAVA_MAP.put("semicolon", KeyEvent.getKeyText(KeyEvent.VK_SEMICOLON));
        ANTLR_TO_JAVA_MAP.put("quote", KeyEvent.getKeyText(KeyEvent.VK_QUOTE));
        ANTLR_TO_JAVA_MAP.put("enter", KeyEvent.getKeyText(KeyEvent.VK_ENTER));
        ANTLR_TO_JAVA_MAP.put("comma", KeyEvent.getKeyText(KeyEvent.VK_COMMA));
        ANTLR_TO_JAVA_MAP.put("period", KeyEvent.getKeyText(KeyEvent.VK_PERIOD));
        ANTLR_TO_JAVA_MAP.put("slash", KeyEvent.getKeyText(KeyEvent.VK_SLASH));
        
        //Swap the key-value mappings
        for(String key : ANTLR_TO_JAVA_MAP.keySet()){
            JAVA_TO_ANTLR_MAP.put(ANTLR_TO_JAVA_MAP.get(key), key);
        }
    }
    
    /**
     * Converts the <code>ANTLR</code> version of a key text value to a 
     * <code>Java</code> defined version.
     * @param keyText the text to convert, must be a valid <code>ANTLR</code>
     *  defined text
     * @return the <code>Java</code> version of the text
     */
    public static String antlrToJavaText(String keyText){
        if(!ANTLR_TO_JAVA_MAP.containsKey(keyText)){
            throw new RuntimeException(keyText + " is not a valid ANTLR key text");
        }
        
        return ANTLR_TO_JAVA_MAP.get(keyText);
    }
    
    /**
     * Converts the <code>Java</code> version of a key text value to an 
     * <code>ANTLR</code> defined version.
     * @param keyText the <code>Java</code> defined text to convert, must have 
     *      a valid <code>ANTLR</code> defined text version
     * @return the <code>ANTLR</code> version of the text
     */
    public static String javaToANTLRText(String keyText){
        if(!JAVA_TO_ANTLR_MAP.containsKey(keyText)){
            throw new RuntimeException(keyText + " is not a valid mapped Java key text");
        }
        
        return JAVA_TO_ANTLR_MAP.get(keyText);
    }
}
