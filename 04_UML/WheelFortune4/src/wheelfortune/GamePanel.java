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
    private String sentenceStr;
    private Board board;
    private Wheel wheel;
    private int numPlayer;
    private Player[] playerAry;
    private int currPlayerIdx;
    private Player currPlayer;

    public GamePanel() {
        numPlayer = 3;
        initComponent();
    }

    private void initComponent() {
        initializer = new Initializer();
        sentenceStr = initializer.initSentence();
        board = new Board();
        board.initBoard(sentenceStr);
        wheel = new Wheel();
        initPlayer();

        startGame();
    }

    private void initPlayer() {
        showMsg("Initialize three players.");
        playerAry = new Player[numPlayer];
        for (int i = 0; i < numPlayer; i++) {
            playerAry[i] = new Player();
            playerAry[i].setName(initPlayerData(i, "name"));
            playerAry[i].setSex(initPlayerData(i, "sex"));
            playerAry[i].setTotalScore(0);
            playerAry[i].setWheel(wheel);
        }
        currPlayerIdx = 0;
    }

    public String initPlayerData(int idx, String title) {
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
        return JOptionPane.showInputDialog(
                "Enter " + numTh + " player's " + title);
    }

    public void startGame() {
        ArrayList<Integer> idxList; // one letter may have multiple matches
        boolean gameDone = false;
        boolean guessValid;
        char playerGuess;
        int wheelScore;
        boolean exist;

        showMsg("Game is ready. Start playing");
        while (!gameDone) { // allows next player
            currPlayer = playerAry[currPlayerIdx];
            guessValid = true;
            while (guessValid) { // current player continues
                showMsgSameLine(currPlayer.getName() + " is playing: ");
                wheelScore = currPlayer.turnWheel();
                showMsgSameLine("wheel score is " + wheelScore + ". ");
                // player gets bankrupt, break
                if (wheelScore < 0) { // bankrupt
                    currPlayer.setTotalScore(0);
                    showMsgSameLine(currPlayer.getName() + " is Bankrupt.");
                    showMsg(" Current score: " + currPlayer.getTotalScore());
                    break; // next player takes turn
                }
                // player does not bankrupt
                playerGuess = currPlayer.inputGuess();
                idxList = initializer.receiveGuess(playerGuess);
                // player's guessed char mismatch, break
                if (idxList.isEmpty()) {
                    showMsgSameLine("'" + playerGuess + "' is mismatch.");
                    showMsg(" Current score: " + currPlayer.getTotalScore());
                    break; // next player takes turn
                }
                // player's guessed char match
                // insert the matched char into board for displaying
                exist = board.insertCurrAry(idxList, playerGuess);
                // the guessed char exists already, break
                if (exist) {
                    showMsgSameLine("'" + playerGuess + "' exists already.");
                    showMsg(" Current score: " + currPlayer.getTotalScore());
                    break; // next player takes turn
                }
                // the guessed char does not exist, the best case
                // calculate current-player's total-score
                wheelScore = idxList.size() * wheelScore;
                showMsgSameLine("'" + playerGuess + "' is match.");
                // add wheel-score to player's total score
                currPlayer.setTotalScore(
                        currPlayer.getTotalScore() + wheelScore);
                showMsg(" Current score: " + currPlayer.getTotalScore());
                // show the board
                board.showBoard();
                // is the sentence completely matched?
                if (initializer.getSentence().equals(
                        new String(board.getCurrAry()))) {
                    gameDone = true;
                    break;
                }
            }
            if (gameDone) {
                break; // game is completed
            } else { // turn to the next player
                currPlayerIdx = (currPlayerIdx + 1) % numPlayer;
            }
        }
        terminateGame();
    }

    public void terminateGame() {
        showMsg("The game is completed.");
        sortAry(playerAry);
        showMsg("Sorted players' scores are: ");
        for (Player player : playerAry) {
            showMsg(String.format("%-10s %6d",
                    player.getName(), player.getTotalScore()));
        }

        showMsg("Congratulation " + playerAry[0].getName()
                + "! You won the game!");
    }

    public void sortAry(Player[] ary) {
        Player temp;
        for (int i = 0; i < ary.length - 1; i++) {
            for (int j = i + 1; j < ary.length; j++) {
                if (ary[i].getTotalScore() < ary[j].getTotalScore()) {
                    temp = ary[i];
                    ary[i] = ary[j];
                    ary[j] = temp;
                }
            }
        }
    }

    public void showMsgSameLine(String msg) {
        System.out.print(msg);
    }

    public void showMsg(String msg) {
        System.out.println(msg);
    }
}
