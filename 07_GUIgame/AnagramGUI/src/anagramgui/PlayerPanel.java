/*
 * PlayerPanel.java - A class defines the player's panel for the player to
 * input data and controls.
 */
package anagramgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author cxu
 */
public class PlayerPanel extends JPanel {

    private ButtonGroup fileGroup;
    private JLabel fileLbl;
    private JRadioButton fileajRBtn;
    private JRadioButton filekzRBtn;

    private JLabel guessedWordLbl;
    private JTextField guessedWordTxt;
    private JButton morePlayBtn;
    private JButton terminateBtn;

    private JLabel msgLbl;
    private JTextField msgTxt;

    private JLabel scoreLbl;
    private JTextField scoreTxt;

    private JPanel selFileP;
    private JPanel playP;
    private JPanel playerP;
    private JPanel scoreP;
    private JPanel msgP;

    private ReadFile readFile;
    private Board board;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PlayerPanel() {
        this.setPreferredSize(new Dimension(
                Consts.CV_WIDTH, Consts.PLAYER_PANEL_H));
        this.setLayout(new BorderLayout());
        initPlayerPanel();
    }

    private void initPlayerPanel() {
        ActionListener myActionListener = new MyActionListener();

        // Select a file for reading a given word
        selFileP = new JPanel();
        selFileP.setLayout(new GridLayout(3, 1));
        EmptyBorder eb = new EmptyBorder(new Insets(30, 150, 5, 30));
        selFileP.setBorder(eb);
        fileLbl = new JLabel("Select a file for reading a word: ");
        fileGroup = new ButtonGroup();
        fileajRBtn = new JRadioButton("wordaj.txt");
        fileajRBtn.setActionCommand("wordaj.txt");
        fileajRBtn.addActionListener(myActionListener);
        filekzRBtn = new JRadioButton("wordkz.txt");
        filekzRBtn.setActionCommand("wordkz.txt");
        filekzRBtn.addActionListener(myActionListener);
        fileGroup.add(fileajRBtn);
        fileGroup.add(filekzRBtn);
        selFileP.add(fileLbl);
        selFileP.add(fileajRBtn);
        selFileP.add(filekzRBtn);

        // Player plays the game and gets a score
        playerP = new JPanel();
        EmptyBorder eb2 = new EmptyBorder(new Insets(5, 30, 5, 30));
        playerP.setBorder(eb2);
        guessedWordLbl = new JLabel("Enter your guess word: ");
        guessedWordTxt = new JTextField(15);
        guessedWordTxt.addActionListener(myActionListener);
        morePlayBtn = new JButton("More Playing");
        morePlayBtn.addActionListener(myActionListener);
        terminateBtn = new JButton("Terminate");
        terminateBtn.addActionListener(myActionListener);
        playerP.add(guessedWordLbl);
        playerP.add(guessedWordTxt);
        playerP.add(morePlayBtn);
        playerP.add(terminateBtn);

        scoreP = new JPanel();
        scoreLbl = new JLabel("Total Scores: ");
        scoreTxt = new JTextField(10);
        scoreP.add(scoreLbl);
        scoreP.add(scoreTxt);

        playP = new JPanel();
        playP.setLayout(new BorderLayout());
        playP.add(playerP, BorderLayout.CENTER);
        playP.add(scoreP, BorderLayout.SOUTH);

        // A message panel
        msgP = new JPanel();
        msgLbl = new JLabel("Message: ");
        msgTxt = new JTextField(28);
        msgTxt.setForeground(Color.red);
        msgP.add(msgLbl);
        msgP.add(msgTxt);

        this.add(selFileP, BorderLayout.NORTH);
        this.add(playP, BorderLayout.CENTER);
        this.add(msgP, BorderLayout.SOUTH);

        setMsg("Please select a file for reading a given word.");
    }

    class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            if ((evt.getSource() == fileajRBtn)
                    || (evt.getSource() == filekzRBtn)) {
                readFile.selectFile(evt.getActionCommand());
                board.initGivenWord();
                filekzRBtn.setEnabled(false);
                fileajRBtn.setEnabled(false);
                guessedWordTxt.setEnabled(true);
                guessedWordTxt.setText("");
                guessedWordTxt.requestFocus();
                setMsg("Please enter your word.");
            } else if (evt.getSource() == guessedWordTxt) {
                board.receiveGuess(guessedWordTxt.getText());
                guessedWordTxt.setEnabled(false);
            } else if (evt.getSource() == morePlayBtn) {
                // reset the board displaying
                fileajRBtn.setEnabled(true);
                filekzRBtn.setEnabled(true);
                board.setInitPaint(true);
                board.repaint();
                setMsg("Please select a file for reading a given word.");
            } else if (evt.getSource() == terminateBtn) {
                System.exit(0);
            }
        }
    }

    public void setReadFile(ReadFile readFile) {
        this.readFile = readFile;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setMsg(String msg) {
        msgTxt.setText(msg);
    }

    public void setScoreTxt(String score) {
        this.scoreTxt.setText(score);
    }
}
