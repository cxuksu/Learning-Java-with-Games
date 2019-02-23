/*
 * Deck.java - A class defines a deck of cards.
 */
package blackjack;

import java.awt.*;
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
    }

    private void initDeck() {
        deck.clear();
        for (int i = 0; i < Consts.DECK_LEN; i++) {
            card = initNewCard(i);
            deck.add(card);
        }
        shuffleCard();
    }
    
    public Card initNewCard(int idx) {
        Image img = null;
        int aValue;
        try {
            img = ImageIO.read(getClass().getResource(
                    "../cards/" + (idx+1) + ".gif"));
        } catch (Exception e) {
        }
        Card aCard = new Card();
        aCard.setCardFront(img);
        // set a value for a card
        aValue = idx % 13;
        if (aValue >= 10) {
            aValue = 10; // Jack, Queen, and King
        } else if (aValue == 0) {
            aValue = 11; // A
        } else {
            aValue += 1; // 2 to 10
        }
        aCard.setValue(aValue);
        
        return aCard;
    }

    private void shuffleCard() {
        int randIdx1, randIdx2;
        for (int i = 0; i < 1000; i++) {
            randIdx1 = ((int)(Math.random() * 10000)) % Consts.DECK_LEN;
            randIdx2 = ((int)(Math.random() * 10000)) % Consts.DECK_LEN;
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
        if (deck.size() <= Consts.DECK_RENEW) {
            initDeck();
        }
        return theCard;
    }
    
    public ArrayList<Card> getDeck() {
        return deck;
    }
}
