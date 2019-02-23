/*
 * PrintXmasCard -- The main class for printing a Christmas card
 */
package printxmascard;

import java.io.IOException;

/**
 *
 * @author cxu
 */
public class PrintXmasCard {

    private static XmasCard aCard;

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        aCard = new XmasCard();
        aCard.printCard();
    }
}
