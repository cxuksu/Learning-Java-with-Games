/*
 * Consts.java -- A class that defines constants for the project.
 * Defining a Consts class makes the access of constants easier and 
 * makes the change of constants much convenient.
 */
package quicksort;

/**
 *
 * @author cxu
 */
public class Consts {
    
    public Consts() {
    }
    
    // values need to be set (for Mac)
    public static final int TOP_BAR_HEIGHT = 22; // for Windows: 18
    public static final int EDGE_WIDTH = 0; // for Windows: 4
    
    // setting canvas width, height, and margin
    public static final int CV_WIDTH = 680;
    public static final int CV_HEIGHT = 500;
    
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
    
    // required constants for sprites
    public static final int SCORE_UNIT = 5;
    
    // required sound constants to be index for the sound array clips
    public static final int HIT_EDGE = 0;
    
    public static final int BASEY = CV_HEIGHT - 130; // base line
    public static final int INITBAR = 5;
    public static final int BARWIDTH = 10;
    public static final int ARROWH = 15;
}
