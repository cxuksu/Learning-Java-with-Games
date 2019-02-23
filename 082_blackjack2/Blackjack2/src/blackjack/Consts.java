/*
 * Consts.java -- A class defines constants for game programming
 */
package blackjack;

/**
 *
 * @author cxu
 */
public class Consts {

    public Consts() {}
    
    // values need to be set (for Mac)
    public static final int TOP_BAR_HEIGHT = 22; // for Windows: 18
    public static final int EDGE_WIDTH = 0; // for Windows: 4
    
    // setting canvas width, height, and margin
    public static final int CV_WIDTH = 500;
    public static final int CV_HEIGHT = 400;
    
    // when MARGIN = 0
    public static final int FIELD_HEIGHT = CV_HEIGHT-TOP_BAR_HEIGHT;
    public static final int MINX = 0;
    public static final int MAXX = CV_WIDTH - 2 * EDGE_WIDTH;
    public static final int MINY = 0;
    public static final int MAXY = FIELD_HEIGHT - EDGE_WIDTH;
    
    // when MARGIN > 0
    public static final int MARGIN = 20; // depends on the application
    public static final int MINX_DIS = MINX + MARGIN;
    public static final int MAXX_DIS = MAXX - MARGIN;
    public static final int MINY_DIS = MINY + MARGIN;
    public static final int MAXY_DIS = MAXY - MARGIN;
    
    public static final int DECK_LEN = 52;
    public static final int DECK_RENEW = 15;
    public static final int BOARD_H = 300;
    public static final int PLAYER_PANEL_H = 100;
    
    public static final int INIT_X = 120;
    public static final int DEALER_Y = 50;
    public static final int GAMBLER_Y = 180;
    public static final int CARD_GAP = 20;
    public static final int VALUE_X = 290;
    public static final int DEALER_V_Y = 90;
    public static final int GAMBLER_V_Y = 220;
    
    public static final int BLACKJACK = 1;
    public static final int BUSTED = 2;
    public static final int WON = 3;
    public static final int PUSHED = 4;
    public static final int COMPARE1 = 5;
    public static final int COMPARE2 = 6;
    
    public static final int STATUS_X = 390;
    public static final int DEALER_S_Y = 90;
    public static final int GAMBLER_S_Y = 220;
}
