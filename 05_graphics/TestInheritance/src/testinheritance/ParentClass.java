/*
 * ParentClass.java -- A parent class.
 */
package testinheritance;

/**
 *
 * @author cxu
 */
public class ParentClass {

    private int parentAge;
    private double money;

    public ParentClass(int parentAge) {
        this.parentAge = parentAge;
        this.money = 1000.00;
    }

    public int getAge() {
        return this.parentAge;
    }

    public void setAge(int age) {
        this.parentAge = age;
    }

    protected double getMoney() {
        return money;
    }

    protected void setMoney(double money) {
        this.money = money;
    }
}
