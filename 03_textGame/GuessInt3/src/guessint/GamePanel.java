/*
 * GamePanel.java - A class for coordinating the initializer and the player.
 */
package guessint;

/**
 *
 * @author cxu
 */
public class GamePanel {

    private Initializer initializer;
    private Player player;
    private int playerInt;
    private boolean guessCorrect;
    private int numGuess;

    public GamePanel() {
        guessCorrect = false;
        numGuess = 0;
        initPlayers();
    }

    private void initPlayers() {
        initializer = new Initializer();
        player = new Player();

        startGame();
    }

    public void startGame() {
        initializer.initGame();
        playGame();
    }

    public void playGame() {
        while (!guessCorrect) {
            numGuess++;
            playerInt = player.playingGame();
            guessCorrect = initializer.receiveGuess(playerInt);
        }
        terminateGame();
    }

    public void terminateGame() {
        showMsg("Game terminates");
        showMsg("\nTotally, you guessed " + numGuess + " times.");
    }
    
    public void showMsg(String msgStr) {
        System.out.println(msgStr);
    }
}
