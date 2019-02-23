/*
 * NotePanel.java - A class specifies all of key commands.
 */
package sudoku;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author cxu
 */
public class NotePanel extends JPanel {

    private JLabel easyLbl;
    private JLabel mediumLbl;
    private JLabel hardLbl;
    private JLabel msgLbl;
    private JLabel upLbl;
    private JLabel downLbl;
    private JLabel leftLbl;
    private JLabel rightLbl;
    private JLabel newLbl;
    private JLabel checkLbl;
    private JLabel colorLbl;
    private JLabel quitLbl;
    private JLabel emptyLbl;
    private JLabel topEmptyLbl;

    private JPanel levelP;
    private JPanel moveP;
    private JPanel commandP;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public NotePanel() {
        setLayout(new GridLayout(3, 1));
        setPreferredSize(new Dimension(Consts.PLAYER_P_W, Consts.PLAYER_P_H));
        initLabel();
    }

    public void initLabel() {
        EmptyBorder eb = new EmptyBorder(new Insets(30, 0, 30, 30));
        this.setBorder(eb);
        emptyLbl = new JLabel("        ");

        levelP = new JPanel(new GridLayout(5, 1));
        easyLbl = new JLabel("E - easy level");
        mediumLbl = new JLabel("M - medium level");
        hardLbl = new JLabel("H - hard level");
        msgLbl = new JLabel("     You can reset level any time");
        levelP.add(easyLbl);
        levelP.add(mediumLbl);
        levelP.add(hardLbl);
        levelP.add(msgLbl);
        levelP.add(emptyLbl);
        add(levelP);
        
        moveP = new JPanel(new GridLayout(4, 1));
        upLbl = new JLabel("Up key - move cursorMark up");
        upLbl.setForeground(Color.BLUE);
        downLbl = new JLabel("Down key - move cursorMark down");
        downLbl.setForeground(Color.BLUE);
        leftLbl = new JLabel("Left key - move cursorMark left");
        leftLbl.setForeground(Color.BLUE);
        rightLbl = new JLabel("Right key - move cursorMark right");
        rightLbl.setForeground(Color.BLUE);
        moveP.add(upLbl);
        moveP.add(downLbl);
        moveP.add(leftLbl);
        moveP.add(rightLbl);
        add(moveP);

        commandP = new JPanel(new GridLayout(5, 1));
        topEmptyLbl = new JLabel("            ");
        checkLbl = new JLabel("C - check guessed digits");
        checkLbl.setForeground(Color.RED);
        colorLbl = new JLabel("     GREEN - correct guess");
        colorLbl.setForeground(Color.RED);
        newLbl = new JLabel("N - new game");
        newLbl.setForeground(Color.RED);
        quitLbl = new JLabel("Q - quit the game");
        quitLbl.setForeground(Color.RED);
        commandP.add(topEmptyLbl);
        commandP.add(checkLbl);
        commandP.add(colorLbl);
        commandP.add(newLbl);
        commandP.add(quitLbl);
        add(commandP);
    }
}
