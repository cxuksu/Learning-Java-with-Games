/*
 * Messsage.java - A class for defining messages.
 */

package showmessage;

import javax.swing.JOptionPane;

/**
 *
 * @author cxu
 */
public class Message {
    
    private String message;
    
    public Message() {
    }
    
    public void initMessage() {
        message = JOptionPane.showInputDialog("Enter a String: ");
    }
    
    public void printMessage() {
        System.out.println(message);
    }
}
