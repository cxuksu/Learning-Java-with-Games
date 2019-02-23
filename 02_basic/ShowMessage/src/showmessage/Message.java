/*
 * Messsage.java - A class for defining messages.
 */
package showmessage;

/**
 *
 * @author cxu
 */
public class Message {

    private final String message;

    public Message() {
        message = "Hello World!";
    }

    public void printMessage() {
        System.out.println(message);
    }
}
