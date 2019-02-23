/*
 * GameCanvas.java - A class that defines the game canvas.
 */
package bubblesort;

import java.awt.BorderLayout;
import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class GameCanvas extends AbsGameCanvas {

    private ControlPanel controlPanel; // static part
    private BarComposite barComposite; // sprites
    private ArrowSprite arrowSprite;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public GameCanvas() {
        super();
        setLayout(new BorderLayout());
        initComponent(); // dynamic part: could be re-init

        //startGame(); // before sorting, startGame() cannot be invoked
    }

    @Override
    public void initComponent() {
        initControlPanel(); // static part
        initBarComposite();
        initArrowSprite();
        repaint(); // when game has not been started, repaint() paints sprites
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
        arrowSprite = new ArrowSprite();
        getSpriteAry().add(arrowSprite); // at position [1]
    }

    @Override
    public void announceTermination(Graphics2D g2d) {
    }

    @Override
    public void paintCurrScore(Graphics2D g2d) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCurrentArrow(int curIdx) {
        BarComposite aBarC = (BarComposite) (getSpriteAry().get(0));
        BarSprite curBar = aBarC.getBarList().get(curIdx);
        arrowSprite.setX(curBar.getX() + Consts.BARWIDTH / 2);
        arrowSprite.setY(Consts.BASEY + Consts.INITBAR);
    }
}
