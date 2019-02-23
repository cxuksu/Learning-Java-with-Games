/*
 * GamePanel.java - A game manager for coordinating the two partners.
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
        initPartner();
    }

    private void initPartner() {
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
        System.out.println("Game terminates");
        System.out.println("\nTotally, you guessed " + numGuess + " times.");
    }
}
