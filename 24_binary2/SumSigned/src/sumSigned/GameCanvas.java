/*
 * GameCanvas.java - A class that implements the game canvas.
 */
package sumSigned;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author cxu
 */
public class GameCanvas extends AbsGameCanvas {

    private ConvSprite convSprite;
    private SumSprite sumSprite;

    private ControlPanel controlPanel;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public GameCanvas() {
        super();
        setLayout(new BorderLayout());
        setSleepTime(1000); // set the thread sleep time for different games 

        initComponent();
        startGame();
    }

    @Override
    public void initComponent() {
        getSpriteAry().clear();
        if (controlPanel == null) { // it does not allow init it twice
            initControlPanel();
        }
        initConvSprite();
        initSumSprite();
        controlPanel.setGameCanvas(this);
    }

    public void initControlPanel() {
        controlPanel = new ControlPanel();
        add(controlPanel, BorderLayout.NORTH);
    }

    public void initConvSprite() {
        convSprite = new ConvSprite();
        convSprite.setActive(false);
        convSprite.setVisible(false);
        convSprite.addPropertyChangeListener(new ChangeCommand());
        getSpriteAry().add(convSprite);
    }

    public void initSumSprite() {
        sumSprite = new SumSprite();
        sumSprite.setActive(false);
        sumSprite.setVisible(false);
        sumSprite.addPropertyChangeListener(new ChangeCommand());
        getSpriteAry().add(sumSprite);
    }

    class ChangeCommand implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String theChange = evt.getPropertyName();
            if (theChange.equals("convDone")) {
                controlPanel.getConvBtn().setEnabled(false);
                controlPanel.getSumBtn().setEnabled(true);
            } else if (theChange.equals("sumDone")) {
                controlPanel.getSumBtn().setEnabled(false);
                controlPanel.getResetBtn().setEnabled(true);
            }
        }
    }

    public void setReset() {
        stopGame();
        renewGame();
        // keep input panel but re-init sprites 
        initConvSprite();
        initSumSprite();
    }

    @Override
    public void announceTermination(Graphics2D g2d) {

    }

    @Override
    public void paintCurrScore(Graphics2D g2d) {

    }

    public ConvSprite getConvSprite() {
        return convSprite;
    }

    public SumSprite getSumSprite() {
        return sumSprite;
    }
}
