/*
 * GamePanel.java - A class to be the coordinator of initializer and player.
 */
package wheelfortune;

/**
 *
 * @author cxu
 */
public class GamePanel {

    private Initializer initializer;
    private String sentenceStr;

    public GamePanel() {
        initComponent();
    }

    private void initComponent() {
        initializer = new Initializer();
        sentenceStr = initializer.initSentence();
        System.out.println(sentenceStr);
    }
}
