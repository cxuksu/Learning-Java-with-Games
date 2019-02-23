/*
 * PlayerPanel.java - A class defines a panel for players.
 */
package wheelvideo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author cxu
 */
public class PlayerPanel extends JPanel {

    private Player[] playerAry;
    private int numPlayer;
    private int currPlayerIdx;

    private JLabel nameLabel;
    private JTextField playerName1;
    private JTextField playerName2;
    private JTextField playerName3;
    private JLabel tScoreLabel;
    private JTextField totalScore1;
    private JTextField totalScore2;
    private JTextField totalScore3;

    private JLabel cScore;
    private JTextField currScore;
    private JButton turnWheel;

    private JLabel msgLabel;
    private JTextField msg;

    private JPanel playerP;
    private JPanel scoreP;
    private JPanel msgP;

    public PlayerPanel() {
        this.setPreferredSize(new Dimension(
                Consts.CV_WIDTH, Consts.PLAYER_PANEL_H));
        this.setLayout(new BorderLayout());
        initPlayer();
        initPlayerPanel();
    }

    private void initPlayer() {
        numPlayer = 3;
        currPlayerIdx = 0;
        playerAry = new Player[numPlayer];
        for (int i = 0; i < numPlayer; i++) {
            playerAry[i] = new Player();
            playerAry[i].setName(initPlayerData(i, "name"));
            playerAry[i].setSex(initPlayerData(i, "sex"));
            playerAry[i].setTotalScore(0);
        }
    }

    public String initPlayerData(int idx, String title) {
        String numTh = "first";
        switch (idx) {
            case 0:
                numTh = "first ";
                break;
            case 1:
                numTh = "second ";
                break;
            case 2:
                numTh = "third ";
                break;
        }
        return JOptionPane.showInputDialog(
                "Enter " + numTh + " player's " + title);
    }

    private void initPlayerPanel() {
        playerP = new JPanel();
        EmptyBorder eb = new EmptyBorder(new Insets(5, 30, 5, 30));
        playerP.setBorder(eb);
        playerP.setLayout(new GridLayout(2, 4, 8, 5));

        nameLabel = new JLabel("Player Name: ");
        playerP.add(nameLabel);
        playerName1 = new JTextField(10);
        playerP.add(playerName1);
        playerName2 = new JTextField(10);
        playerP.add(playerName2);
        playerName3 = new JTextField(10);
        playerP.add(playerName3);
        tScoreLabel = new JLabel("Total Scores: ");
        playerP.add(tScoreLabel);
        totalScore1 = new JTextField(10);
        playerP.add(totalScore1);
        totalScore2 = new JTextField(10);
        playerP.add(totalScore2);
        totalScore3 = new JTextField(10);
        playerP.add(totalScore3);

        scoreP = new JPanel();
        cScore = new JLabel("Current Score");
        scoreP.add(cScore);
        currScore = new JTextField(8);
        scoreP.add(currScore);
        turnWheel = new JButton("Turn The Wheel");
        scoreP.add(turnWheel);

        msgP = new JPanel();
        msgLabel = new JLabel("Message: ");
        msg = new JTextField(28);
        msgP.add(msgLabel);
        msgP.add(msg);
        msg.setForeground(Color.red);

        this.add(playerP, BorderLayout.NORTH);
        this.add(scoreP, BorderLayout.CENTER);
        this.add(msgP, BorderLayout.SOUTH);

        showData();
    }

    public void showData() {
        Font newFont = new Font("Times", Font.BOLD, 26);
        JTextField modelText = new JTextField();
        Font defaultFont = modelText.getFont();
        switch (currPlayerIdx) {
            case 0:
                playerName1.setForeground(Color.red);
                playerName1.setFont(newFont);
                playerName1.setText(playerAry[0].getName());
                totalScore1.setForeground(Color.red);
                totalScore1.setFont(newFont);
                totalScore1.setText("" + playerAry[0].getTotalScore());

                playerName2.setForeground(Color.black);
                playerName2.setFont(defaultFont);
                playerName2.setText(playerAry[1].getName());
                totalScore2.setForeground(Color.black);
                totalScore2.setFont(defaultFont);
                totalScore2.setText("" + playerAry[1].getTotalScore());

                playerName3.setForeground(Color.black);
                playerName3.setFont(defaultFont);
                playerName3.setText(playerAry[2].getName());
                totalScore3.setForeground(Color.black);
                totalScore3.setFont(defaultFont);
                totalScore3.setText("" + playerAry[2].getTotalScore());
                break;
            case 1:
                playerName1.setForeground(Color.black);
                playerName1.setFont(defaultFont);
                playerName1.setText(playerAry[0].getName());
                totalScore1.setForeground(Color.black);
                totalScore1.setFont(defaultFont);
                totalScore1.setText("" + playerAry[0].getTotalScore());

                playerName2.setForeground(Color.red);
                playerName2.setFont(newFont);
                playerName2.setText(playerAry[1].getName());
                totalScore2.setForeground(Color.red);
                totalScore2.setFont(newFont);
                totalScore2.setText("" + playerAry[1].getTotalScore());

                playerName3.setForeground(Color.black);
                playerName3.setFont(defaultFont);
                playerName3.setText(playerAry[2].getName());
                totalScore3.setForeground(Color.black);
                totalScore3.setFont(defaultFont);
                totalScore3.setText("" + playerAry[2].getTotalScore());
                break;
            case 2:
                playerName1.setForeground(Color.black);
                playerName1.setFont(defaultFont);
                playerName1.setText(playerAry[0].getName());
                totalScore1.setForeground(Color.black);
                totalScore1.setFont(defaultFont);
                totalScore1.setText("" + playerAry[0].getTotalScore());

                playerName2.setForeground(Color.black);
                playerName2.setFont(defaultFont);
                playerName2.setText(playerAry[1].getName());
                totalScore2.setForeground(Color.black);
                totalScore2.setFont(defaultFont);
                totalScore2.setText("" + playerAry[1].getTotalScore());

                playerName3.setForeground(Color.red);
                playerName3.setFont(newFont);
                playerName3.setText(playerAry[2].getName());
                totalScore3.setForeground(Color.red);
                totalScore3.setFont(newFont);
                totalScore3.setText("" + playerAry[2].getTotalScore());
                break;
        }
        msg.setText("Current Player "
                + playerAry[currPlayerIdx].getName() + " Plays.");
    }
}
