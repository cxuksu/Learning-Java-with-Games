/*
 * Initializer.java - The game initializer.
 */
package guessint;

/**
 *
 * @author cxu
 */
public class Initializer {

    private int givenInt;
    private boolean match;

    public Initializer() {
    }

    public void initGame() {
        givenInt = (int) (Math.random() * 100) + 1; // [1, 100]
        match = false;

        System.out.println("I already initialized the game with an integer "
                + "between 1 and 100 in my mind");
        System.out.println("Please start your guess. Good luck!\n");
    }

    public boolean receiveGuess(int receivedGuess) {
        match = isGuessCorrect(receivedGuess);
        return match;
    }

    public boolean isGuessCorrect(int playerGuess) {
        boolean correct = false;
        if (playerGuess == givenInt) {
            System.out.println("Initializer: your guess " + playerGuess
                    + " is correct!");
            correct = true;
        } else if (playerGuess > givenInt) {
            System.out.println("Initilaizer: Your guess " + playerGuess
                    + " is too large");
        } else if (playerGuess < givenInt) {
            System.out.println("Initializer: your guess " + playerGuess
                    + " is too small");
        }
        return correct;
    }
}
