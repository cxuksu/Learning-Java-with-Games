/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testwheel;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class TestWheel extends JFrame {

    public TestWheel() {
        setTitle("Wheel Forture");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);
        
        GameCanvas gameCanvas = new GameCanvas();
        add(gameCanvas);
        
        this.setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new TestWheel();
    }
}
