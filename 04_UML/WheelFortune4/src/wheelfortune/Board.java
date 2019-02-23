/*
 * Board.java - A class defines a board for displaying.
 */
package wheelfortune;

import java.util.ArrayList;

/**
 *
 * @author cxu
 */
public class Board {

    private String sentenceStr;
    private int strLen;
    private char[] currAry;

    public Board() {
    }

    public void initBoard(String sentence) {
        this.sentenceStr = sentence;
        strLen = sentenceStr.length();
        currAry = new char[strLen];
        initCharAry(currAry); // init it with ' ' char
    }

    public boolean insertCurrAry(ArrayList<Integer> idxAry, char inputChar) {
        boolean exist = false;
        inputChar = Character.toUpperCase(inputChar);
        for (Integer idx : idxAry) {
            if (currAry[idx] == (inputChar)) {
                exist = true;
                break;
            } else {
                currAry[idx] = inputChar;
            }
        }
        return exist;
    }

    public void showBoard() {
        showTop();
        fillCurrentAry(currAry);
        showBottom();
        showLineEnd();
    }

    public void showTop() {
        for (int i = 0; i < strLen; i++) {
            showUnit("-----");
        }
        showLineEnd();
        for (int i = 0; i < strLen; i++) {
            showUnit("|   |");
        }
        showLineEnd();
    }

    public void showBottom() {
        for (int i = 0; i < strLen; i++) {
            showUnit("|   |");
        }
        showLineEnd();
        for (int i = 0; i < strLen; i++) {
            showUnit("-----");
        }
        showLineEnd();
    }

    public void fillCurrentAry(char[] current) {
        for (int i = 0; i < strLen; i++) {
            showUnit("| " + current[i] + " |");
        }
        showLineEnd();
    }

    public void showUnit(String drawStr) {
        System.out.print(drawStr);
    }

    public void showLineEnd() {
        System.out.println();
    }

    public void initCharAry(char[] ary) {
        for (int i = 0; i < strLen; i++) {
            ary[i] = ' ';
        }
    }

    public char[] getCurrAry() {
        return currAry;
    }
}
