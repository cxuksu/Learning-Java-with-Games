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
    private Player[] playerAry;
    private int numPlayer;
    private int currPlayer;

    public GamePanel() {
        numPlayer = 3;
        currPlayer = 0;
        initComponent();
    }

    private void initComponent() {
        initPlayer(); // three players with name, ...
        initializer = new Initializer();
        initializer.initSentence();

        startGame();
    }

    public void initPlayer() {
        playerAry = new Player[numPlayer];
        for (int i = 0; i < numPlayer; i++) {
            playerAry[i] = new Player();
            playerAry[i].setName(initPlayerData(i, "name"));
            playerAry[i].setSex(initPlayerData(i, "sex"));
            playerAry[i].setNumPlay(0);
            playerAry[i].setScore(0);
        }
    }

    public String initPlayerData(int idx, String title) {
        boolean hasInput = false;
        String titleStr = "";
        char isEmpty;

        String numTh = "first";
        switch (idx) {
            case 0:
                numTh = "first ";
                break;
            case 1:
                numTh = "second ";
                break;
            case 2:
                numTh = "third ";
                break;
        }
        while (!hasInput) {
            titleStr = JOptionPane.showInputDialog(
                    "Enter " + numTh + "player's " + title);
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

        while (currPlayer < 3) { // allows next player
            guessValid = true;
            showMsg("The current player " + playerAry[currPlayer].getName()
                    + " is playing:");
            while (guessValid) { // current player continues
                playerGuess = playerAry[currPlayer].inputGuess();
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
            currPlayer = (currPlayer + 1) % numPlayer; // switch to next player
        }
    }

    public void abortGame() {
        showMsg("The game is aborted");
        System.exit(0);
    }
    
    public void showMsg(String msg) {
        System.out.println(msg);
    }
}
