/*
 * GamePanel.java - A class to be the coordinator of initializer and player.
 */
package wheelfortune;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author cxu
 */
public class GamePanel {

    private Initializer initializer;
    private Player player;

    public GamePanel() {
        initComponent();
    }

    private void initComponent() {
        initPlayer();
        initializer = new Initializer();
        initializer.initSentence();

        startGame();
    }

    public void initPlayer() {
        player = new Player();
        player.setName(initPlayerData("name"));
        player.setSex(initPlayerData("sex"));
        player.setScore(0);
    }

    public String initPlayerData(String title) {
        boolean hasInput = false;
        String titleStr = "";
        char isEmpty;

        while (!hasInput) {
            titleStr = JOptionPane.showInputDialog("Enter " + title);
        
            try {
                isEmpty = titleStr.charAt(0);
                hasInput = true;
            } catch (StringIndexOutOfBoundsException siex) {
                showMsg("You did not input the " + title + ". Input again.");
            } catch (NullPointerException npex) {
                showMsg("You clicked Cancel.");
                abortGame();
            }
        }
        return titleStr;
    }

    public void startGame() {
        ArrayList<Integer> idxList; // one letter may have multiple matches
        boolean guessValid;
        char playerGuess;

        guessValid = true;
        showMsg("The player " + player.getName()
                + " is playing:");
        while (guessValid) { // current player continues
            playerGuess = player.inputGuess();
            idxList = initializer.receiveGuess(playerGuess);
            if (idxList.isEmpty()) { // guess has no match,
                showMsg("The guessed char " + playerGuess
                        + " matches 0 character in the sentence.");
                guessValid = false; // next player takes turn
            } else {
                showMsg("The guessed char " + playerGuess
                        + " matches " + idxList.size()
                        + " character(s) in the sentence.");
            }
        }
        abortGame();
    }

    public void abortGame() {
        showMsg("The game is aborted");
        System.exit(0);
    }

    public void showMsg(String msg) {
        System.out.println(msg);
    }
}
