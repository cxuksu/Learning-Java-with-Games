/*
 * Player.java - A class defines a player.
 */
package guessint;

import javax.swing.JOptionPane;

/**
 *
 * @author cxu
 */
public class Player {

    private String guessedIntStr;
    private int guessedInt;

    public Player() {
    }

    public int playingGame() {
        boolean qualified = false;
        while (!qualified) {
            guessedIntStr = JOptionPane.showInputDialog(
                    "Enter an integer between 1 and 100");
            qualified = isQualifiedData(guessedIntStr);
        }
        showMsg("Player: My guess is " + guessedInt);
        return guessedInt;
    }

    public boolean isQualifiedData(String inputStr) {
        try {
            guessedInt = Integer.parseInt(inputStr);
        } catch (NumberFormatException ex) {
            System.out.println();
            isInteger(inputStr);
            return false;
        }
        return isInRange(); // check the range, returns false or true
    }

    public boolean isInteger(String inputStr) {
        if (inputStr == null) { // esc key or click Cancel button
            showMsg("You entered the ESC key or clicked the Cancel button"
                    + " that aborted the program.");
            System.exit(0);
        }
        if (inputStr.equals("")) { // enter key or click OK without input
            showMsg("You entered the Retuen key or clicked the OK button"
                    + " without data. Input again.");
            return false;
        }

        int length = inputStr.length();
        int i = 0;
        int maxlength = 10; // max integer has 10 digits
        String maxnum = String.valueOf(Integer.MAX_VALUE); //2147483647
        // the length of the input must be less than the max integer
        if (inputStr.charAt(0) == '-') {
            maxlength = 11;
            if (length == 1 || length > maxlength) {
                showMsg("You entered a negative integer with zero digit"
                        + " or its length is larger than the maximum length"
                        + " of a negative integer. Input again.");
                return false;
            }
            i = 1;
            maxnum = String.valueOf(Integer.MIN_VALUE); //-2147483648
        } else {
            if (length > maxlength) {
                showMsg("You entered a positive integer,"
                        + " its length is larger than the maximum length"
                        + " of a positive integer. Input again.");
                return false;
            }
        }
        // every character must be a digit
        for (int digit = i; digit < length; digit++) {
            char c = inputStr.charAt(digit);
            if (c < '0' || c > '9') {
                showMsg("Your inputted data contains non digit character."
                        + " Input again.");
                return false;
            }
        }
        // the input value must be less than the max integer
        if (length == maxlength) {
            for (; i < length; i++) {
                if (inputStr.charAt(i) < maxnum.charAt(i)) {
                    return true;
                } else if (inputStr.charAt(i) > maxnum.charAt(i)) {
                    showMsg("The value of your input is larger than"
                            + " the possible maximum value"
                            + " of an integer. Input again.");
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isInRange() {
        if ((guessedInt >= 1) && (guessedInt <= 100)) {
            return true;
        } else {
            showMsg("\nYour input is out of the bound of [1, 100]. Input again");
            return false;
        }
    }

    public void showMsg(String msgStr) {
        System.out.println(msgStr);
    }
}
