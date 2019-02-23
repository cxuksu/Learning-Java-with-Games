/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pongextension;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class BrickSprite extends AbsSprite2D {

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public BrickSprite() {
        super();
        initSprite();
    }
    
    @Override
    public void initSprite() {
        setX(0); // the real coordinate value should be assigned by BrickGroup
        setY(0);
        setWidth(Consts.BRICK_W);
        setHeight(Consts.BRICK_H);
        setColor(Color.BLACK);
    }
    
    @Override
    public void updateSprite() {
        // a brick is a static object
    }
    
    @Override
    public void paintSprite(Graphics2D g2d) {
        if (isVisible()) {
            g2d.setColor(getColor());
            g2d.drawRect(getX(), getY(), getWidth(), getHeight());
        }
    }
}
