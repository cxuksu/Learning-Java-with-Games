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
    private ReadFile readFile;

    public Initializer() {
        readFile = new ReadFile();
    }

    public String initSentence() {
        readFile.selectFile();
        sentence = readFile.readContent();
        System.out.println(sentence);
        sentenceLen = sentence.length();
        sentence = sentence.toUpperCase();
        return sentence;
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

    public String getSentence() {
        return sentence;
    }

    public void showMsg(String msg) {
        System.out.println(msg);
    }
}
