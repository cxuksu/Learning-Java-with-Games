/*
 * Board.java - A class defines a board for displaying.
 */
package anagramgui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class Board extends JPanel {

    private ReadFile readFile;
    private PlayerPanel playerPanel; // for communication

    private String givenWord;
    private int wordLen;
    private char[] scrambledWord;
    private String guessedWord;
    private char[] guessedWordAry;
    private boolean hasGuessed = false;
    private int totalScore = 0;
    private boolean initPaint = true;

    private char[] currAry;
    private int cx; // center point (x) of board
    private int bx, by; // starting point (x, y) for painting
    private int sx, sy; // starting point (x, y) of each character

    public Board() {
        this.setPreferredSize(new Dimension(Consts.CV_WIDTH, Consts.BOARD_H));
        wordLen = 8; // assume the wordLen = 8 initially
        repaint();
    }

    public void initGivenWord() {
        hasGuessed = false;
        givenWord = readFile.readContent();
        //System.out.println(givenWord);
        givenWord = givenWord.toLowerCase();
        currAry = givenWord.toCharArray();
        scrambledWord = scrambleWord(currAry);
        wordLen = givenWord.length();
        repaint();
    }

    public char[] scrambleWord(char[] givenAry) {
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

    public void receiveGuess(String aGuessedWord) {
        try {
            this.guessedWord = aGuessedWord;
            guessedWord = guessedWord.toLowerCase();
            guessedWordAry = guessedWord.toCharArray();
            hasGuessed = true;
            if (guessedWord.equals(givenWord)) {
                playerPanel.setMsg("Congratulations!!! Your word is correct.");
                totalScore += Consts.SCORE + wordLen * 2;
                playerPanel.setScoreTxt("" + totalScore);
            } else {
                playerPanel.setMsg("Sorry! Your word is incorrect.");
                totalScore -= Consts.SCORE;
                playerPanel.setScoreTxt("" + totalScore);
            }
            repaint();
        } catch (StringIndexOutOfBoundsException siex) {
            playerPanel.setMsg("You did not enter a word.");
            totalScore -= Consts.SCORE;
            playerPanel.setScoreTxt("" + totalScore);
        } catch (NullPointerException nullex) {
            playerPanel.setMsg("You click the Cancel that aborts the game.");
            System.exit(0);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        bx = (int) (Consts.CV_WIDTH / 2)
                - (int) ((wordLen * 1.0 / 2) * Consts.CHAR_BLOCK_W);
        // showing the board area
        g2d.setColor(Color.RED);
        g2d.drawRect(0, 0, Consts.CV_WIDTH, Consts.BOARD_H);

        Stroke solid = new BasicStroke(5.0f);
        g2d.setStroke(solid);

        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Times", Font.BOLD, 30));

        if (initPaint) {
            paintEmpty(g2d);
            initPaint = false;
        } else {
            paintScrambledWord(g2d);
            if (hasGuessed) {
                paintGuessedWord(g2d);
            }
        }
    }

    public void paintEmpty(Graphics2D g2d) {
        // paint an empty board for the scrambled word
        by = Consts.BLOCK_Y;
        for (int i = 0; i < wordLen; i++) {
            g2d.drawRect(bx + i * Consts.CHAR_BLOCK_W, by, Consts.CHAR_BLOCK_W,
                    Consts.CHAR_BLOCK_H);
        }
        // paint an empty board for the guessedWord
        by = Consts.BLOCK_Y + 2 * Consts.CHAR_BLOCK_H;
        for (int i = 0; i < wordLen; i++) {
            g2d.drawRect(bx + i * Consts.CHAR_BLOCK_W, by, Consts.CHAR_BLOCK_W,
                    Consts.CHAR_BLOCK_H);
        }
    }

    public void paintScrambledWord(Graphics2D g2d) {
        paintEmpty(g2d); // two empty blocks must always be there 

        // paint the scrambled word
        sx = bx + 8;
        sy = Consts.BLOCK_Y;
        for (int k = 0; k < wordLen; k++) {
            if (scrambledWord[k] != ' ') {
                g2d.drawString(Character.toString(scrambledWord[k]),
                        sx + k * Consts.CHAR_BLOCK_W,
                        sy + (int) (Consts.CHAR_BLOCK_H * 0.8));
            }
        }
    }

    public void paintGuessedWord(Graphics2D g2d) {
        sx = bx + 8;
        sy = Consts.BLOCK_Y + 2 * Consts.CHAR_BLOCK_H;
        try {
            for (int k = 0; k < wordLen; k++) {
                if (guessedWordAry[k] != ' ') {
                    g2d.drawString(Character.toString(guessedWordAry[k]),
                            sx + k * Consts.CHAR_BLOCK_W,
                            sy + (int) (Consts.CHAR_BLOCK_H * 0.8));
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            // this try block prevents the case if the length of 
            // the guessedWord is shorted than the wordLen that 
            // comes from the scrambledWord
        }
    }

    public void setReadFile(ReadFile readFile) {
        this.readFile = readFile;
    }

    public void setPlayerPanel(PlayerPanel playerPanel) {
        this.playerPanel = playerPanel;
    }

    public void setInitPaint(boolean initPaint) {
        this.initPaint = initPaint;
    }
}
