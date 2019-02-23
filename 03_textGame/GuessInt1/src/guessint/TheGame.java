/*
 * TheGame.java - A class that implements the game GuessInt in-text.
 */
package guessint;

import javax.swing.JOptionPane;

/**
 *
 * @author cxu
 */
public class TheGame {

    private int givenInt;
    private String guessedIntStr;
    private int guessedInt;
    private boolean match;
    private int numGuess;
    private int numWrong;
    private boolean abortGame;

    public TheGame() {
        givenInt = (int) (Math.random() * 100) + 1;
        match = false;
        numGuess = 0;
        numWrong = 0;
        abortGame = false;
        System.out.println("The system has made an integer between 1 and 100");
        System.out.println("Please start your guess. Gook luck!");
    }

    public void inputGuess() {
        while (!match) {
            numGuess++;
            guessedIntStr = JOptionPane.showInputDialog(
                    "Enter a guess between 1 and 100");
            if (isInteger(guessedIntStr)) {
                guessedInt = Integer.parseInt(guessedIntStr);
            } else {
                System.out.println("You must enter an integer!");
                numWrong++;
                if (numWrong == 3) {
                    System.out.println("Your input was wrong three times.");
                    abortGame = true;
                    break;
                } else {
                    continue;
                }
            }
            match = guessCorrect();
        }
        if (abortGame) {
            System.out.println("The game is aborted.");
        } else {
            System.out.println("Your guess " + guessedInt + " is correct.");
            System.out.println("Congratulation! Totally, you guessed "
                    + numGuess + " times");
        }
    }

    public boolean guessCorrect() {
        boolean correct = false;
        if (givenInt == guessedInt) {
            correct = true;
        } else if (guessedInt > givenInt) {
            System.out.println("Your guess " + guessedInt + " is too large");
        } else if (guessedInt < givenInt) {
            System.out.println("your guess " + guessedInt + " is too small");
        }
        return correct;
    }

    // it also assures that the input won't exceed the range of an integer
    public boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        if (str.equals("")) {
            return false;
        }
        int length = str.length();
        int i = 0;
        int maxlength = 10;
        String maxnum = String.valueOf(Integer.MAX_VALUE);
        if (str.charAt(0) == '-') {
            maxlength = 11;
            if (length == 1 || length > maxlength) {
                return false;
            }
            i = 1;
            maxnum = String.valueOf(Integer.MIN_VALUE);
        } else {
            if (length > maxlength) {
                return false;
            }
        }
        for (int digit = i; digit < length; digit++) {
            char c = str.charAt(digit);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        if (length == maxlength) {
            for (; i < length; i++) {
                if (str.charAt(i) < maxnum.charAt(i)) {
                    return true;
                } else if (str.charAt(i) > maxnum.charAt(i)) {
                    return false;
                }
            }
        }
        return true;
    }
}
