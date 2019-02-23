/*
 * SliderPanel.java - A class defines the canvas panel for the image slider.
 */
package imageslider;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class SliderPanel extends JPanel implements ActionListener {

    private ReadImage readImage;
    private ShowBoard showBoard;
    private JButton leftBtn;
    private JButton rightBtn;

    public SliderPanel() {
        initComponent();
    }

    private void initComponent() {
        setLayout(new BorderLayout());
        initReadImage();
        initShowBoard();
        initButtons();
    }

    public void initReadImage() {
        readImage = new ReadImage();
    }

    public void initShowBoard() {
        showBoard = new ShowBoard();
        showBoard.setReadImage(readImage); // showBoard accesses readImage
        showBoard.initShowImg();
        showBoard.findShowImg();
        add(showBoard, BorderLayout.CENTER);
    }

    public void initButtons() {
        leftBtn = new JButton();
        leftBtn.setPreferredSize(new Dimension(
                Consts.BUTTON_W, (int) (Consts.BUTTON_W * 1.5)));
        BufferedImage leftArrow = zoomImage(readImage.getLeftArrow());
        leftBtn.setIcon(new ImageIcon(leftArrow));
        leftBtn.setBorder(null);
        add(leftBtn, BorderLayout.WEST);
        leftBtn.addActionListener(this);

        rightBtn = new JButton();
        rightBtn.setPreferredSize(new Dimension(
                Consts.BUTTON_W, (int) (Consts.BUTTON_W * 1.5)));
        BufferedImage rightArrow = zoomImage(readImage.getRightArrow());
        rightBtn.setIcon(new ImageIcon(rightArrow));
        rightBtn.setBorder(null);
        add(rightBtn, BorderLayout.EAST);
        rightBtn.addActionListener(this);
    }

    public BufferedImage zoomImage(Image arrow) {
        BufferedImage bufferedArrow = (BufferedImage) arrow;
        BufferedImage newArrow;
        
        double ratio = (Consts.BUTTON_W * 1.0) / bufferedArrow.getWidth();
        int newW = (int) (bufferedArrow.getWidth() * ratio);
        int newH = (int) (bufferedArrow.getHeight() * ratio);
        newArrow = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics g = newArrow.createGraphics();
        g.drawImage(arrow, 0, 0, newH, newH, null);
        g.dispose();
        return newArrow;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        int listSize = readImage.getSceneryList().size();
        if (evt.getSource() == rightBtn) {
            int newIdx = showBoard.getImgIdx() - 1; // move idx to left
            if (newIdx == -1) {
                newIdx = listSize - 1; // the last index of the list
            }
            showBoard.setImgIdx(newIdx % listSize);
            showBoard.findShowImg();
            showBoard.repaint();
        } else if (evt.getSource() == leftBtn) {
            showBoard.setImgIdx((showBoard.getImgIdx() + 1)
                    % listSize); // move idx to right
            showBoard.findShowImg();
            showBoard.repaint();
        }
    }
}
