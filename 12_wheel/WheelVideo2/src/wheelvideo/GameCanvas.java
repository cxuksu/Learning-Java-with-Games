/*
 * GameCanvas.java - A class defines the canvas.
 */
package wheelvideo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class GameCanvas extends JPanel {

    private Board board;
    private WheelGUI wheelGUI;
    private PlayerPanel playerPanel;

    public GameCanvas() {
        setPreferredSize(new Dimension(Consts.CV_WIDTH,
                Consts.BOARD_H + Consts.CV_HEIGHT + Consts.PLAYER_PANEL_H));
        this.setLayout(new BorderLayout());

        initComponent();
    }

    private void initComponent() {
        initBoard();
        initWheelGUI();
        initPlayerPanel();
    }

    public void initBoard() {
        board = new Board();
        add(board, BorderLayout.NORTH);
    }

    public void initWheelGUI() {
        wheelGUI = new WheelGUI();
        this.add(wheelGUI, BorderLayout.CENTER);
        wheelGUI.setBoard(board);
    }

    public void initPlayerPanel() {
        playerPanel = new PlayerPanel();
        add(playerPanel, BorderLayout.SOUTH);
        playerPanel.setWheelGUI(wheelGUI);
        board.setPlayerPanel(playerPanel);
        wheelGUI.setPlayerPanel(playerPanel);
    }

    public void showMsgSameLine(String msg) {
        System.out.print(msg);
    }

    public void showMsg(String msg) {
        System.out.println(msg);
    }
}
