/*
 * ShowMessage.java - A main class for the project ShowMessage.
 */

package showmessage;

/**
 *
 * @author cxu
 */
public class ShowMessage {

    private static Message messageObj;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        messageObj = new Message();
        messageObj.initMessage();
        messageObj.printMessage();
    }
}
