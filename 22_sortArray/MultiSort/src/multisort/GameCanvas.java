/*
 * GameCanvas.java - A class that defines the game canvas.
 */
package multisort;

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
    private ArrowSprite inArrow;
    private ArrowSprite outArrow;
    private ArrowSprite pivotArrow;

    private BubbleSort bubbleSort;
    private QuickSort quickSort;

    @SuppressWarnings("FieldMayBeFinal")
    private boolean mutualLock = false;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public GameCanvas() {
        super();
        setLayout(new BorderLayout());
        initComponent(); // dynamic part: could be re-init
        setSleepTime(50); // for main thread

        startGame(); // nothing will be animated before sorting starts
    }

    @Override
    public void initComponent() {
        initControlPanel(); // static part
        initBarComposite();
        initArrowSprite();
        repaint(); // when game has not been started, repaint() paints sprites
        // initQuickSort() and initInOutArrow() are invoked in controlPanel
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
        bubbleSort.addPropertyChangeListener(new SortAlgoListener());
        // barComposite signals bubbleSort to resume
        barComposite.setBubbleSort(bubbleSort);
    }

    public void initQuickSort() {
        quickSort = new QuickSort();
        quickSort.setBarComposite(barComposite);
        quickSort.initQuickSort();
        // quickSort signals gameCanvas to assign in and out arrows
        quickSort.addPropertyChangeListener(new SortAlgoListener());
        // barComposite signals quickSort to resume
        barComposite.setQuickSort(quickSort);
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
        inArrow.addPropertyChangeListener(new ArrowDone());
        getSpriteAry().add(inArrow); // at index 1
        outArrow = new ArrowSprite();
        outArrow.setColor(Color.BLUE);
        outArrow.addPropertyChangeListener(new ArrowDone());
        getSpriteAry().add(outArrow); // at index 2
    }

    // quick sorting requires a pivot arrow
    public void initPivotArrow() {
        // erase the pivot arrow from spriteAry
        erasePivotArrow();
        // then, initialize a new pivot arrow
        pivotArrow = new ArrowSprite();
        pivotArrow.setColor(Color.GREEN);
        getSpriteAry().add(pivotArrow); // at index 3
    }

    // bubble sorting does not need a pivot arrow
    public void erasePivotArrow() {
        if (getSpriteAry().contains(pivotArrow)) {
            getSpriteAry().remove(pivotArrow);
        }
    }
    
    @Override
    public void announceTermination(Graphics2D g2d) {
    }

    @Override
    public void paintCurrScore(Graphics2D g2d) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // only used for making a "preferred" arrayList
    public void setCurrentArrow(int curIdx) {
        BarSprite curBar = barComposite.getBarList().get(curIdx);
        curArrow.setX(curBar.getX() + Consts.BARWIDTH / 2);
        curArrow.setY(Consts.BASEY + Consts.INITBAR);
    }

    // communicate with sorting algorithm (e.g. QuickSort.java, BubbleSort.java)
    class SortAlgoListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String theChange = evt.getPropertyName();
            if (theChange.equals("inArrow")) {
                String inIdxStr = (String) evt.getNewValue();
                int inIdx = Integer.parseInt(inIdxStr);
                assignInArrow(inIdx);
            } else if (theChange.equals("outArrow")) {
                String outIdxStr = (String) evt.getNewValue();
                int outIdx = Integer.parseInt(outIdxStr);
                assignOutArrow(outIdx);
            } else if (theChange.equals("pivotArrow")) {
                String pivotIdxStr = (String) evt.getNewValue();
                int pivotIdx = Integer.parseInt(pivotIdxStr);
                assignPivotArrow(pivotIdx);
            } else if (theChange.equals("sortDone")) {
                setMutualLock(false);
            }
        }
    }

    public void assignInArrow(int inIdx) {
        BarSprite curBar = barComposite.getBarList().get(inIdx);
        inArrow.setX(curBar.getX() + Consts.BARWIDTH / 2);
        inArrow.setY(Consts.BASEY + Consts.INITBAR);
        inArrow.setLeftIdx(true);
    }

    public void assignOutArrow(int outIdx) {
        BarSprite curBar = barComposite.getBarList().get(outIdx);
        outArrow.setX(curBar.getX() + Consts.BARWIDTH / 2);
        outArrow.setY(Consts.BASEY + Consts.INITBAR);
        outArrow.setRightIdx(true);
    }

    public void assignPivotArrow(int pivotIdx) {
        BarSprite curBar = barComposite.getBarList().get(pivotIdx);
        pivotArrow.setX(curBar.getX() + Consts.BARWIDTH / 2);
        pivotArrow.setY(Consts.BASEY - Consts.ARROWH);
    }

    // only quick sorting uses this listener (sent by ArrowSprite.java)
    class ArrowDone implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String theChange = evt.getPropertyName();
            if (theChange.equals("arrowDone")) {
                if (quickSort != null) {
                    quickSort.setWaiting(false);
                }
            }
        }
    }

    public BubbleSort getBubbleSort() {
        return bubbleSort;
    }

    public QuickSort getQuickSort() {
        return quickSort;
    }

    public boolean isMutualLock() {
        return mutualLock;
    }

    public void setMutualLock(boolean mutualLock) {
        this.mutualLock = mutualLock;
    }

    public ArrowSprite getInArrow() {
        return inArrow;
    }

    public ArrowSprite getOutArrow() {
        return outArrow;
    }
}
