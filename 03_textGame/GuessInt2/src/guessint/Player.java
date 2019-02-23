/*
 * Player.java - The game player.
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
        guessedIntStr = JOptionPane.showInputDialog(
                "Enter an guess between 1 and 100");
        guessedInt = Integer.parseInt(guessedIntStr);
        System.out.println("Player: My guess is " + guessedInt);
        return guessedInt;
    }
}
