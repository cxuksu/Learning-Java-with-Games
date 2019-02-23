/*
 * Consts.java -- A class defines constants for game programming
 */
package agnes;

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
    
    public static final int NUM_SUIT = 4;
    public static final int CARD_CLUBS = 0;
    public static final int CARD_DIAMONDS = 1;
    public static final int CARD_HEARTS = 2;
    public static final int CARD_SPADES = 3;
    
    public static final int DECK_LEN = 52;
    
    public static final int NUM_ROW = 7;
    public static final int NUM_COL = 7;
    public static final int CARD_GAP_X = 10;
    public static final int CARD_GAP_Y = 20;
    public static final int CARD_W = 40;
    public static final int CARD_H = 54;
    public static final int COL1_X = 80;
    public static final int COL1_Y = 50;
    public static final int FOUNDATION_X = COL1_X;
    public static final int FOUNDATION_Y = 10;
    
    public static final int PLAYER_PANEL_H = 50;
    
}
