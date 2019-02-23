/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anagramgui;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class AnagramGUI extends JFrame {
    
    public AnagramGUI() {
        setTitle("Anagram");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Consts.CV_WIDTH, Consts.BOARD_H+Consts.PLAYER_PANEL_H);
        
        GameCanvas gameCanvas = new GameCanvas();
        add(gameCanvas);
        
        setVisible(true);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new AnagramGUI();
    }
    
}
