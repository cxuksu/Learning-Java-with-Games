/*
 * Player.java - A class implements a player.
 */
package anagramtext;

import javax.swing.JOptionPane;

/**
 *
 * @author cxu
 */
public class Player {

    public Player() {
    }

    public String guessWord() {
        String guessedWord = "";
        char inputChar;
        boolean hasInput = false;

        while (!hasInput) {
            guessedWord = JOptionPane.showInputDialog(
                    "Please enter a word for guessing");
            try {
                inputChar = guessedWord.charAt(0);
                hasInput = true;
            } catch (StringIndexOutOfBoundsException siex) {
                showMsg("You did not input any char. Input again.");
            } catch (NullPointerException npex) {
                showMsg("You clicked Cancel. The program is aborted.");
                System.exit(0);
            }
        }
        return guessedWord;
    }

    public void showMsg(String msg) {
        System.out.println(msg);
    }
}
