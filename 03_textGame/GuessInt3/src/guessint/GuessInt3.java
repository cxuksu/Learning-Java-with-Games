/*
 * GuessInt3.java - The main class of the version GuessInt3.
 */

package guessint;

/**
 *
 * @author cxu
 */
public class GuessInt3 {

    private String gameDescription;
            
    public GuessInt3() {
        gameDescription = "This is a game GuessInt. The game initializer " +
                "thinks of an integer between 0 and 100.\n" +
                "The game player guesses what the integer is.\n" +
                "The player who spent the least number of guesses " +
                "wins the game.\n";
        System.out.println(gameDescription);
        new GamePanel();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new GuessInt3();
    }
}
