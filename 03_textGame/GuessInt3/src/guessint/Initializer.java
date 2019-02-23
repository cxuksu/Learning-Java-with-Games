/*
 * Initializer - A class defines an initializer.
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
        givenInt = (int) (Math.random() * 100) + 1;
        match = false;
        showMsg("I have initialized the game with an integer "
                + "between 1 and 100 in my mind");
        showMsg("Please start your guess. Gook luck!");
    }

    public boolean receiveGuess(int receivedGuess) {
        match = isGuessCorrect(receivedGuess);
        return match;
    }

    public boolean isGuessCorrect(int playerGuess) {
        boolean correct = false;
        if (playerGuess == givenInt) {
            showMsg("Initializer: your guess " + playerGuess
                    + " is correct!");
            correct = true;
        } else if (playerGuess > givenInt) {
            showMsg("Initializer: Your guess " + playerGuess
                    + " is too large");
        } else if (playerGuess < givenInt) {
            showMsg("Initializer: your guess " + playerGuess
                    + " is too small");
        }
        return correct;
    }

    public void showMsg(String msgStr) {
        System.out.println(msgStr);
    }
}
