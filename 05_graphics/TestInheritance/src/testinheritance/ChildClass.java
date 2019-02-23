/*
 * ChildClass.java -- A class that inherits the ParentClass.
 */
package testinheritance;

/**
 *
 * @author cxu
 */
public class ChildClass extends ParentClass {

    private int childAge;

    public ChildClass(int parentAge, int childAge) {
        super(parentAge);
        this.childAge = childAge;
    }

    @Override
    public int getAge() {
        return this.childAge;
    }

    @Override
    public void setAge(int age) {
        this.childAge = age;
    }

    public int getParentAge() {
        return super.getAge();
    }

    public double getParentMoney() {
        return getMoney();
    }
}
