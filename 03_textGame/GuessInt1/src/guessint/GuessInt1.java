/*
 * GuessInt1.java - A main class of the game of GuessInt in text.
 */

package guessint;

/**
 *
 * @author cxu
 */
public class GuessInt1 {

    private String gameDescription;
    private TheGame theGame;
            
    public GuessInt1() {
        gameDescription = "This is a game GuessInt. The system initializes " +
                "an integer between 1 and 100. The user guesses what the " +
                "value is. The user who spent the least number of guesses " +
                "wins the game.";
        System.out.println(gameDescription);
        theGame = new TheGame();
        theGame.inputGuess();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new GuessInt1();
    }
}
