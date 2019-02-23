/*
 * Consts.java -- A class for defining constants for game programming
 */
package tornado;

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
    
    public static final int BALL_RADIUS = 8;
    public static final int SLEEP_TIME = 1000; // 1000 ms
    public static final int RECTSIZE = 8;
    
    public static final double RADIAN = 2*Math.PI / 360;
    public static final int MAX_MAJOR_R = 100;
    public static final int MAX_MINOR_R = 50;
}
