/*
 * FieldWall.java - A class for implementing a field wall that consists
 * of four wallUnits.
 */
package pong;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author cxu
 */
public class FieldWall {

    WallUnit[] aWall = new WallUnit[4];

    public FieldWall() {
        initFieldWall();
    }

    private void initFieldWall() {
        WallUnit topUnit = new WallUnit();
        topUnit.setX(Consts.MINX);
        topUnit.setY(Consts.MINY);
        topUnit.setWidth(Consts.MAXX - Consts.MINX);
        topUnit.setHeight(Consts.WALL_THICK);
        aWall[0] = topUnit;
        WallUnit bottomUnit = new WallUnit();
        bottomUnit.setX(Consts.MINX);
        bottomUnit.setY(Consts.MAXY - Consts.WALL_THICK);
        bottomUnit.setWidth(Consts.MAXX - Consts.MINX);
        bottomUnit.setHeight(Consts.WALL_THICK);
        aWall[1] = bottomUnit;
        WallUnit leftUnit = new WallUnit();
        leftUnit.setX(Consts.MINX);
        leftUnit.setY(Consts.MINY);
        leftUnit.setWidth(Consts.WALL_THICK);
        leftUnit.setHeight(Consts.MAXY - Consts.MINY);
        aWall[2] = leftUnit;
        WallUnit rightUnit = new WallUnit();
        rightUnit.setX(Consts.MAXX - Consts.WALL_THICK);
        rightUnit.setY(Consts.MINY);
        rightUnit.setWidth(Consts.WALL_THICK);
        rightUnit.setHeight(Consts.MAXY - Consts.MINY);
        aWall[3] = rightUnit;
        for (int i = 0; i < 4; i++) {
            aWall[i].setUnitColor(Color.decode("#bbaa00"));
        }
    }

    public void paintFieldWall(Graphics g) {
        for (int i = 0; i < 4; i++) {
            aWall[i].paintUnit(g);
        }
    }
}
