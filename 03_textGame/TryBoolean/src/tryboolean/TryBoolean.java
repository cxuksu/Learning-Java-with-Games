/*
 * TryBoolean.java - The main class of the project TryBooean.
 */
package tryboolean;

/**
 *
 * @author cxu
 */
public class TryBoolean {

    private double x;
    private double y;
    private int z = 5;

    public TryBoolean() {
        x = 4.6;
        y = 5.2;
        printResult();
    }

    private void printResult() {
        System.out.println(x > y);
        System.out.println(++x > y++);
        System.out.println(x++ > y);
        System.out.println(x > y);

        for (int i = 0; i < 5; i++) {
            System.out.println((i * z++) + " " + ++z);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new TryBoolean();
    }
}
