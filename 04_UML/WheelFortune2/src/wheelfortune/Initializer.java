/*
 * Initializer.java - A class defines the initializer.
 */
package wheelfortune;

import java.util.ArrayList;

/**
 *
 * @author cxu
 */
public class Initializer {

    private String sentence;
    private int sentenceLen;

    public Initializer() {
    }

    public int initSentence() {
        sentence = "Hello World"; // assume
        sentence = sentence.toUpperCase();
        sentenceLen = sentence.length();
        showMsg("Game is ready. Start playing");
        return sentenceLen;
    }

    public ArrayList<Integer> receiveGuess(char inputChar) {
        inputChar = Character.toUpperCase(inputChar);
        ArrayList<Integer> idxList = new ArrayList();
        for (int i = 0; i < sentenceLen; i++) {
            if (sentence.charAt(i) == inputChar) {
                idxList.add(i);
            }
        }
        return idxList;
    }

    public void showMsg(String msg) {
        System.out.println(msg);
    }
}
