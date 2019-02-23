/*
 * Initializer.java - A class implements the game initializer.
 */
package anagramtext;

import java.util.Random;

/**
 *
 * @author cxu
 */
public class Initializer {

    private ReadFile readFile;

    public Initializer() {
    }

    public String readGivenWord() {
        String givenWord;
        readFile = new ReadFile();
        readFile.selectFile();
        givenWord = readFile.readContent();
        return givenWord;
    }

    public char[] scrembleWord(char[] givenAry) {
        int num = givenAry.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < num; i++) {
            int destIdx = i + random.nextInt(num - i);
            swap(givenAry, i, destIdx);
        }
        return givenAry;
    }

    private static void swap(char[] givenAry, int i, int destIdx) {
        char theChar = givenAry[i];
        givenAry[i] = givenAry[destIdx];
        givenAry[destIdx] = theChar;
    }

    public void printAry(char[] ary) {
        for (int i = 0; i < ary.length; i++) {
            System.out.print(ary[i]);
        }
        System.out.println();
    }
}
