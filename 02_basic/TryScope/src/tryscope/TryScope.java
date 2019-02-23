/*
 * TryScope.java - The main class for the project TryScope.
 */
package tryscope;

/**
 *
 * @author cxu
 */
public class TryScope {

    private int a;
    private int b;

    public TryScope() {
        a = 30;
        b = 50;
    }
    
    public void printGlobal() {
        System.out.println("a = " + a);
    }
    
    public void printLocal() {
        int c = 100;
        System.out.println("c = " + c);
        System.out.println("b = " + b);
    }

    public double printDouble() {
        double d = 3.14;
        return d;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TryScope tryScope = new TryScope();
        tryScope.printGlobal();
        tryScope.printLocal();
        System.out.println("The global variable a = " + tryScope.a);
        System.out.println("The local variable d = " + tryScope.printDouble());
    }
}
