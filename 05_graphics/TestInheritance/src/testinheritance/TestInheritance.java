/*
 * TestInheritance.java -- A class that instantiates an object of 
 * ChildClass.java for testing the inheritance.
 */
package testinheritance;

/**
 *
 * @author cxu
 */
public class TestInheritance {
    private static ChildClass childObj;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        childObj = new ChildClass(60, 20);
        System.out.println("Parent age = " + childObj.getParentAge());
        System.out.println("Child age = " + childObj.getAge());
        System.out.println("Parent money = " + childObj.getParentMoney());
    }
}
