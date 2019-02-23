/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wormgame;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author cxu
 */
public class Pond {
    
    private BufferedImage[] ponds;
    Point[] pos;
    
    public Pond() {
        ponds = new BufferedImage[12];
        pos = new Point[12];
        initPond();
    }
    
    private void initPond() {
        try {
            ponds[0] =
                    ImageIO.read(getClass().getResource("../images/pond.jpeg"));
            ponds[1] =
                    ImageIO.read(getClass().getResource("../images/pond2.jpeg"));
            ponds[2] =
                    ImageIO.read(getClass().getResource("../images/pond3.jpeg"));
            ponds[3] =
                    ImageIO.read(getClass().getResource("../images/stone.jpeg"));
            ponds[4] =
                    ImageIO.read(getClass().getResource("../images/stone2.jpeg"));
            ponds[5] =
                    ImageIO.read(getClass().getResource("../images/stone3.jpeg"));
            ponds[6] =
                    ImageIO.read(getClass().getResource("../images/pond.jpeg"));
            ponds[7] =
                    ImageIO.read(getClass().getResource("../images/pond2.jpeg"));
            ponds[8] =
                    ImageIO.read(getClass().getResource("../images/pond3.jpeg"));
            ponds[9] =
                    ImageIO.read(getClass().getResource("../images/stone.jpeg"));
            ponds[10] =
                    ImageIO.read(getClass().getResource("../images/stone2.jpeg"));
            ponds[11] =
                    ImageIO.read(getClass().getResource("../images/stone3.jpeg"));
        } catch (IOException ex) {
        }
        int x, y;
        for (int i = 0; i < 12; i++) {
            x = (int)(Math.random()*(Consts.CV_WIDTH-30));
            y = (int)(Math.random()*(Consts.FIELD_HEIGHT-20));
            pos[i] = new Point(x, y);
        }
    }
    
    public void paintPond(Graphics2D g2d) {
        for (int i = 0; i < 12; i++) {
            g2d.drawImage(ponds[i], pos[i].x, pos[i].y, 30, 20, null);
        }
    }
}
