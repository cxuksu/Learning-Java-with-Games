/*
 * GameCanvas.java - A class that implements the game canvas.
 */
package sumonescomp;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author cxu
 */
public class GameCanvas extends AbsGameCanvas {

    private DToSSprite dToSSprite;
    private SToOSprite sToOSprite;
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
        if (controlPanel == null) { // preventing init it twice
            initControlPanel();
        }
        initDToSSprite();
        initSToOSprite();
        initSumSprite();
        controlPanel.setGameCanvas(this);
    }

    public void initControlPanel() {
        controlPanel = new ControlPanel();
        add(controlPanel, BorderLayout.NORTH);
    }

    public void initDToSSprite() {
        dToSSprite = new DToSSprite();
        dToSSprite.setActive(false);
        dToSSprite.setVisible(false);
        dToSSprite.addPropertyChangeListener(new ChangeCommand());
        getSpriteAry().add(dToSSprite);
    }

    public void initSToOSprite() {
        sToOSprite = new SToOSprite();
        sToOSprite.setActive(false);
        sToOSprite.setVisible(false);
        sToOSprite.addPropertyChangeListener(new ChangeCommand());
        getSpriteAry().add(sToOSprite);
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
            if (theChange.equals("dToSDone")) {
                controlPanel.getdToSBtn().setEnabled(false);
                controlPanel.getsToOBtn().setEnabled(true);
            } else if (theChange.equals("sToODone")) {
                controlPanel.getsToOBtn().setEnabled(false);
                controlPanel.getSumOnesCompBtn().setEnabled(true);
            } else if (theChange.equals("sumDone")) {
                controlPanel.getSumOnesCompBtn().setEnabled(false);
                controlPanel.getResetBtn().setEnabled(true);
            }
        }
    }

    public void setReset() {
        stopGame();
        renewGame();
        // keep input panel but re-init sprites 
        initDToSSprite();
        initSumSprite();
    }

    @Override
    public void announceTermination(Graphics2D g2d) {

    }

    @Override
    public void paintCurrScore(Graphics2D g2d) {

    }

    public DToSSprite getdToSSprite() {
        return dToSSprite;
    }

    public SToOSprite getsToOSprite() {
        return sToOSprite;
    }

    public SumSprite getSumSprite() {
        return sumSprite;
    }
}
