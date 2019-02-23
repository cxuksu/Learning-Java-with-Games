/*
 * GameControl.java - A class that implements the controls.
 */
package anagramtext;

import javax.swing.JOptionPane;

/**
 *
 * @author cxu
 */
public class GameControl {

    private Initializer initializer;
    private Player player;
    private String givenWord;
    private char[] scrambledWord;
    private char[] charAry;
    private String guessedWord;

    public GameControl() {
        initComponent();
    }

    private void initComponent() {
        initializer = new Initializer();
        player = new Player();
        playGame();
    }

    public void playGame() {
        boolean guessMore = true;
        while (guessMore) {
            makeScrambledWord();
            guessedWord = player.guessWord();
            if (guessedWord.equals(givenWord)) {
                showMsg("Congratulation!!! Your guess is correct");
            } else {
                showMsg("Sorry! Your guess is incorrect");
            }
            guessMore = moreGuess();
        }
    }

    public void makeScrambledWord() {
        givenWord = initializer.readGivenWord();
        charAry = givenWord.toCharArray();
        scrambledWord = initializer.scrembleWord(charAry);
        initializer.printAry(scrambledWord);
    }

    public boolean moreGuess() {
        boolean guessAgain = false;
        Object[] selectionValues = {"Yes", "No"};
        String initialSelection;
        initialSelection = "Yes";
        Object selection = JOptionPane.showInputDialog(null,
                "Would you like to play again?",
                "Select a file", JOptionPane.QUESTION_MESSAGE, null,
                selectionValues, initialSelection);

        try {
            if (selection.equals("Yes")) {
                guessAgain = true;
            } else {
                System.out.println("The user terminates the program.");
            }
        } catch (NullPointerException nullex) {
            System.out.println("You clicked Cancel.");
            System.out.println("Program is aborted by the user.");
            System.exit(0);
        }
        return guessAgain;
    }

    public void showMsg(String msgStr) {
        System.out.println(msgStr);
    }
}
