/*
 * GameCanvas.java - A class that defines the game canvas.
 */
package bubblesort;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author cxu
 */
public class GameCanvas extends AbsGameCanvas {

    private ControlPanel controlPanel; // static part
    private BarComposite barComposite; // sprites
    private ArrowSprite curArrow;
    private BubbleSort bubbleSort;
    private ArrowSprite inArrow;
    private ArrowSprite outArrow;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public GameCanvas() {
        super();
        setLayout(new BorderLayout());
        initComponent(); // dynamic part: could be re-init
        setSleepTime(50);

        startGame(); // nothing will be animated before sorting starts
    }

    @Override
    public void initComponent() {
        initControlPanel(); // static part
        initBarComposite();
        initArrowSprite();
        repaint(); // when game has not been started, repaint() paints sprites
        // initBubbleSort() and initInOutArrow() are invoked in controlPanel
    }

    public void initControlPanel() {
        controlPanel = new ControlPanel();
        controlPanel.setGameCanvas(this); // gameCanvas.repaint() and set arrow
        add(controlPanel, BorderLayout.SOUTH);
    }

    public void initBarComposite() {
        barComposite = new BarComposite();
        getSpriteAry().add(barComposite); // at position [0]
        // controlPanel needs to accesses barComponent
        // after passed gameCanvas and init barComposite
        // controlPanel finds the barComposite
        controlPanel.findBarComposite();
    }

    public void initArrowSprite() {
        curArrow = new ArrowSprite();
        getSpriteAry().add(curArrow); // at position [1]
    }

    public void initBubbleSort() {
        bubbleSort = new BubbleSort();
        bubbleSort.setBarComposite(barComposite);
        bubbleSort.initBubbleSort();
        // bubbleSort signals gameCanvas to assign in and out arrows
        bubbleSort.addPropertyChangeListener(new InOutArrowListener());
        // barComposite signals bubbleSort to resume
        barComposite.setBubbleSort(bubbleSort);
    }

    // init two arrows and add them into spriteAry for the sorting process
    public void initInOutArrow() {
        // in case, bubble sort is invoked again, re-init in and out arrows
        if (getSpriteAry().contains(curArrow)) {
            getSpriteAry().remove(curArrow); // the currentArrow is no longer needed
        } 
        if (getSpriteAry().contains(inArrow)) {
            getSpriteAry().remove(inArrow);
        } 
        if (getSpriteAry().contains(outArrow)) {
            getSpriteAry().remove(outArrow);
        }    
        inArrow = new ArrowSprite();
        inArrow.setColor(Color.red);
        getSpriteAry().add(inArrow); // at index 1
        outArrow = new ArrowSprite();
        outArrow.setColor(Color.BLUE);
        getSpriteAry().add(outArrow); // at index 2
    }

    @Override
    public void announceTermination(Graphics2D g2d) {
    }

    @Override
    public void paintCurrScore(Graphics2D g2d) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCurrentArrow(int curIdx) {
        BarSprite curBar = barComposite.getBarList().get(curIdx);
        curArrow.setX(curBar.getX() + Consts.BARWIDTH / 2);
        curArrow.setY(Consts.BASEY + Consts.INITBAR);
    }

    class InOutArrowListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String theChange = evt.getPropertyName();
            if (theChange.equals("inArrow")) {
                String inIdxStr = (String)evt.getNewValue();
                int inIdx = Integer.parseInt(inIdxStr);
                assignInArrow(inIdx);
            } else if (theChange.equals("outArrow")) {
                String outIdxStr = (String)evt.getNewValue();
                int outIdx = Integer.parseInt(outIdxStr);
                assignOutArrow(outIdx);
            }
        }
    }
    
    public void assignInArrow(int inIdx) {
        BarSprite curBar = barComposite.getBarList().get(inIdx);
        inArrow.setX(curBar.getX() + Consts.BARWIDTH / 2);
        inArrow.setY(Consts.BASEY + Consts.INITBAR);
    }

    public void assignOutArrow(int outIdx) {
        BarSprite curBar = barComposite.getBarList().get(outIdx);
        outArrow.setX(curBar.getX() + Consts.BARWIDTH / 2);
        outArrow.setY(Consts.BASEY + Consts.INITBAR);
    }

    public BubbleSort getBubbleSort() {
        return bubbleSort;
    }
}
