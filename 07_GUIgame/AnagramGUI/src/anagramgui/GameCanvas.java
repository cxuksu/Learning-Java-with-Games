/*
 * GameCanvas.java - A class that sets up communication pathes between related
 * classes.
 */
package anagramgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class GameCanvas extends JPanel {

    private ReadFile readFile;
    private Board board;
    private PlayerPanel playerPanel;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public GameCanvas() {
        setPreferredSize(new Dimension(Consts.CV_WIDTH,
                Consts.BOARD_H + Consts.PLAYER_PANEL_H));
        this.setLayout(new BorderLayout());

        initComponent();
    }

    private void initComponent() {
        initReadFile();
        initBoard();
        initPlayerPanel();
    }

    public void initReadFile() {
        readFile = new ReadFile();
    }

    public void initBoard() {
        board = new Board();
        board.setReadFile(readFile);
        add(board, BorderLayout.NORTH);
    }

    public void initPlayerPanel() {
        playerPanel = new PlayerPanel();
        add(playerPanel, BorderLayout.SOUTH);
        playerPanel.setReadFile(readFile);
        playerPanel.setBoard(board);
        board.setPlayerPanel(playerPanel);
    }
}
