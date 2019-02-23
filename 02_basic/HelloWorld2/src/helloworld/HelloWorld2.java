/*
 * HelloWorld2.java -- To display a text string “Hello World!” on the screen.
 */

package helloworld;

/**
 *
 * @author cxu
 */
public class HelloWorld2 {

    private String message;
    
    public HelloWorld2() {
        message = "Hello World!";
        System.out.println(message);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new HelloWorld2();
    }
}
