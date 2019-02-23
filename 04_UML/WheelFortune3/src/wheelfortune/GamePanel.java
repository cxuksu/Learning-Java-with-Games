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
    private int oriStrLen;
    private int numPlayer;
    private int currPlayer;
    private Board board;
    private Wheel wheel;

    public GamePanel() {
        numPlayer = 3;
        currPlayer = 0;
        initComponent();
    }

    private void initComponent() {
        initInitializer();
        initBoard();
        initWheel();
        initPlayer(); // three players with name, ...
        
        startGame();
    }

    public void initInitializer() {
        initializer = new Initializer();
        oriStrLen = initializer.initSentence();
    }
    
    public void initBoard() {
        board = new Board();
        board.initBoard(oriStrLen);
    }

    public void initWheel() {
        wheel = new Wheel();
    }
    
    public void initPlayer() {
        playerAry = new Player[numPlayer];
        for (int i = 0; i < numPlayer; i++) {
            playerAry[i] = new Player();
            playerAry[i].setName(initPlayerData(i, "name"));
            playerAry[i].setSex(initPlayerData(i, "sex"));
            playerAry[i].setNumPlay(0);
            playerAry[i].setTotalScore(0);
            playerAry[i].setWheel(wheel);
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
        boolean gameDone = false;
        boolean guessValid;
        char playerGuess;
        int wheelScore;

        while (!gameDone) { // allows next player
            guessValid = true;
            while (guessValid) { // current player continues
                showMsgSameLine(playerAry[currPlayer].getName() + " is playing: ");
                wheelScore = playerAry[currPlayer].turnWheel();
                playerGuess = playerAry[currPlayer].inputGuess();
                idxList = initializer.receiveGuess(playerGuess);
                // player's guessed char mismatch, break
                if (idxList.isEmpty()) {
                    showMsgSameLine("'" + playerGuess + "' is mismatch.");
                    showMsg(" Current score: " + 
                            playerAry[currPlayer].getTotalScore());
                    break; // next player takes turn
                } 
                // player's guessed char is match
                // insert the matched char into board for displaying
                board.insertCharAry(idxList, playerGuess);
                // calculate current-player's wheel-score
                wheelScore = idxList.size() * wheelScore;
                showMsgSameLine("'" + playerGuess + "' is match.");
                // add wheel-score to player's total score
                playerAry[currPlayer].setTotalScore(
                        playerAry[currPlayer].getTotalScore() + wheelScore);
                showMsg(" Current score: " + playerAry[currPlayer].getTotalScore());
                // show the board
                board.showBoard();
                // is the sentence completely matched?
                if (initializer.getSentence().equals(
                        new String(board.getCharAry()))) {
                    gameDone = true;
                    break;
                }
            }
            if (gameDone) {
                break; // game is completed
            } else { // turn to the next player
                currPlayer = (currPlayer + 1) % numPlayer;
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
        for (int i = 0; i < playerAry.length - 1; i++) {
            for (int j = i + 1; j < playerAry.length; j++) {
                if (ary[i].getTotalScore() < ary[j].getTotalScore()) {
                    temp = ary[i];
                    ary[i] = ary[j];
                    ary[j] = temp;
                }
            }
        }
    }

    public void abortGame() {
        showMsg("The game is aborted");
        System.exit(0);
    }
    
    public void showMsgSameLine(String msg) {
        System.out.print(msg);
    }

    public void showMsg(String msg) {
        System.out.println(msg);
    }
}
