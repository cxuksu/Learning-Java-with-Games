/*
 * Deck.java - A class defines a deck of cards.
 */
package testdrag;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author cxu
 */
public class Deck {

    private ArrayList<Card> deck;
    private Card card;

    public Deck() {
        deck = new ArrayList();
        initDeck();
        shuffleCard();
    }

    private void initDeck() { // deck is sorted
        deck.clear();
        for (int i = 0; i < Consts.DECK_LEN; i++) {
            card = initNewCard(i);
            deck.add(card);
        }
    }
    
    public Card initNewCard(int idx) {
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("../cards/" + (idx+1) + ".gif"));
        } catch (IOException e) {
        }
        Card aCard = new Card();
        aCard.setCardFront(img);

        return aCard;
    }

    private void shuffleCard() {
        int randIdx1, randIdx2;
        for (int i = 0; i < 1000; i++) {
            randIdx1 = (int)(Math.random() * 10000) % Consts.DECK_LEN;
            randIdx2 = (int)(Math.random() * 10000) % Consts.DECK_LEN;
            swapCard(randIdx1, randIdx2);
        }
    }
    
    public void swapCard(int idx1, int idx2) {
        Card tCard;
        tCard = deck.get(idx1);
        deck.set(idx1, deck.get(idx2));
        deck.set(idx2, tCard);
    }
    
    public Card dealCard() {
        Card theCard;
        theCard = deck.get(0);
        deck.remove(0);
        return theCard;
    }
    
    public ArrayList<Card> getDeck() {
        return deck;
    }
}
