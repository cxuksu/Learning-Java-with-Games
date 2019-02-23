/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author cxu
 */
public class Chip {

    private int x, y;
    private int width = 25, height = 25;
    private BufferedImage chipFace;

    public Chip() {
    }

    public void initChip(int chipValue) {
        chipFace = null;
        switch (chipValue) {
            case 25:
                try {
                    chipFace = ImageIO.read(
                            getClass().getResource("../chips/25Chip.gif"));
                } catch (Exception ex) {
                }
                break;
            case 100:
                try {
                    chipFace = ImageIO.read(
                            getClass().getResource("../chips/100Chip.gif"));
                } catch (Exception ex) {
                }
                break;
            case 500:
                try {
                    chipFace = ImageIO.read(
                            getClass().getResource("../chips/500Chip.gif"));
                } catch (Exception ex) {
                }
                break;
            case 1000:
                try {
                    chipFace = ImageIO.read(
                            getClass().getResource("../chips/1000Chip.gif"));
                } catch (Exception ex) {
                }
                break;
            case 5000:
                try {
                    chipFace = ImageIO.read(
                            getClass().getResource("../chips/5000Chip.gif"));
                } catch (Exception ex) {
                }
                break;
            default:
        }
    }

    public void paintChip(Graphics2D g2d) {
        g2d.drawImage(chipFace, x, y, width, height, null);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
