/*
 * TryRandom.java - The main class of the project TryRandom.
 */
package tryrandom;

import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author cxu
 */
public class TryRandom {

    private int givenInt;
    private Random random2;
    private int min;
    private int max;

    public TryRandom() {
        min = Integer.parseInt(JOptionPane.showInputDialog(
                "Enter the lower bound integer min: (e.g. 5)"));
        max = Integer.parseInt(JOptionPane.showInputDialog(
                "Enter the upper bound integer max: (e.g. 88"));
        System.out.println("The range is " + "[" + min + "," + max + "]");
        
        givenInt = (int) (Math.random() * (max-min+1)) + min;
        System.out.println("givenInt1 = " + givenInt);
        
        random2 = new Random();
        givenInt = random2.nextInt(max-min+1) + min;
        System.out.println("givenInt2 = " + givenInt);

        givenInt = Math.abs(random2.nextInt()) % (max-min+1) + min;
        System.out.println("givenInt3 = " + givenInt);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new TryRandom();
    }
}
