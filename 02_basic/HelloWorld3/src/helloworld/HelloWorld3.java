/*
 * HelloWorld3.java -- To display a text string “Hello World!” on the screen.
 */
package helloworld;

/**
 *
 * @author cxu
 */
public class HelloWorld3 {

    private String message;

    public HelloWorld3() {
        message = "Hello World!";
    }

    public void printMessage() {
        System.out.println(message);
    }

    public static void main(String[] args) {
        (new HelloWorld3()).printMessage();
    }
}
