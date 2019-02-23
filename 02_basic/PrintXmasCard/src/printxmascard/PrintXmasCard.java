/*
 * PrintXmasCard -- The main class for printing a Christmas card
 */
package printxmascard;

/**
 *
 * @author cxu
 */
public class PrintXmasCard {

    private static XmasCard aCard;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        aCard = new XmasCard();
        aCard.printCard();
    }
}
