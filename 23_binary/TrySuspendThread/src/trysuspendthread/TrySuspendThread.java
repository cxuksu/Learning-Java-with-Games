/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trysuspendthread;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class TrySuspendThread extends JFrame {

    public TrySuspendThread() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Try suspendRequestor");
        setSize(Consts.frameW, Consts.frameH);
        
        GameCanvas gameCv = new GameCanvas();
        add(gameCv);
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new TrySuspendThread();
    }
}
