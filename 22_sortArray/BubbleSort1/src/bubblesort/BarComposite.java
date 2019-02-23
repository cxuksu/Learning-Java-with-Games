/*
 * BarComposite.java - A composite class that contains a set of barSprites 
 * for representing the value of the corresponding data in the array. It has
 * all of methods for manipulating the array.
 */
package bubblesort;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author cxu
 */
public class BarComposite extends AbsSprite {

    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<BarSprite> barList;
    private int gap;
    private int numberCells = 10; // size of the array
    private int numberData; // size of the data in the array
    private int appendValue;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public BarComposite() {
        barList = new ArrayList<BarSprite>();
        setVisible(true);
        setActive(false);
        initSprite(); // should be invoked by gameCanvas after setNumberCells()
    }

    @Override
    public void initSprite() {
        gap = (int) ((Consts.CV_WIDTH - 2 * Consts.MARGIN - Consts.BARWIDTH)
                / (numberCells - 1));
        BarSprite aBar;
        for (int i = 0; i < numberCells; i++) {
            aBar = new BarSprite();
            aBar.setX(Consts.MARGIN + gap * i);
            aBar.setY(Consts.BASEY - aBar.getHeight());
            barList.add(aBar);
        }
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        BarSprite aBar;
        for (int i = 0; i < barList.size(); i++) {
            aBar = barList.get(i);
            aBar.paintSprite(g2d);
        }
    }

    @Override
    public void updateSprite() {
    }

    public void eraseBarList() {
        barList.clear();
    }

    public void fillBars() {
        for (int i = 0; i < numberData; i++) {
            // multiply 0.9 makes not too height; plus 
            // INITBAR prevents zero
            int fillValue = (int) (Math.random()
                    * Consts.BASEY * 0.9) + Consts.INITBAR;
            BarSprite aBar = barList.get(i);
            aBar.setHeight(fillValue);
            aBar.setY(Consts.BASEY - aBar.getHeight());
        }
    }

    public void appendElement() {
        int appendIdx = numberData;
        BarSprite aBarS = getBarList().get(appendIdx);
        aBarS.setHeight(appendValue);
        aBarS.setY(Consts.BASEY - aBarS.getHeight());
        numberData++;
    }

    public void deleteElement(int idx) {
        for (int i = idx; i < numberData - 1; i++) {
            BarSprite nextBar = barList.get(i + 1);
            int theData = nextBar.getHeight();
            BarSprite thisBar = barList.get(i);
            thisBar.setHeight(theData);
            thisBar.setY(Consts.BASEY - thisBar.getHeight());
        }
        BarSprite lastBar = barList.get(numberData - 1);
        lastBar.setHeight(Consts.INITBAR);
        lastBar.setY(Consts.BASEY - Consts.INITBAR);
    }

    public int getNumberCells() {
        return numberCells;
    }

    public void setNumberCells(int numberCells) {
        this.numberCells = numberCells;
    }

    public void setNumberData(int numberData) {
        this.numberData = numberData;
    }

    public void setAppendValue(int appendValue) {
        this.appendValue = appendValue;
    }

    public ArrayList<BarSprite> getBarList() {
        return barList;
    }
}
