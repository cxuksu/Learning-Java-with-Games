/*
 * TrySwitch.java - The main class for the project TrySwitch.
 */
package tryswitch;

/**
 *
 * @author cxu
 */
public class TrySwitch {

    private int vacation;

    public TrySwitch() {
        vacation = 6;
        makeDecision();
    }

    private void makeDecision() {
        switch (vacation) {
            case 10:
            case 9:
            case 8:
            case 7:
            case 6:
                System.out.println("I am going to New York");
            case 5:
            case 4:
            case 3:
                System.out.println("I am going to Chicago");
                break;
            case 2:
            case 1:
                System.out.println("I will stay at home");
                break;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new TrySwitch();
    }
}
