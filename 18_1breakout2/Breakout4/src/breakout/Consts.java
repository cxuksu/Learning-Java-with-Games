/*
 * Consts.java -- A class that defines constants for the project.
 * Defining a Consts class makes the access of constants easier and 
 * makes the change of constants much convenient.
 */
package breakout;

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
    
    // constsnts for bricks
    public static final int NUM_BRICK_IMAGES = 8; // num of images
    public static final int BRICK_W = 30;
    public static final int BRICK_H = 15;
    public static final int BRICKS_PER_ROW = 15;
    public static final int MAX_ROWS = 4;
    
    // setting canvas width, height, and margin
    public static final int CV_WIDTH = BRICKS_PER_ROW * BRICK_W;
    public static final int CV_HEIGHT = 450;
    
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
    
    public static final int SCORE_UNIT = 5;
    public static final int PENALTY_LOST_BALL = 30;
    public static final int BALL_RADIUS = 7;
    public static final int PADDLE_W = 75;
    public static final int PADDLE_H = 13;
    public static final int PADDLE_INIT_Y = 60;
    public static final int MAX_LOST = 3;
    
    public static final int HIT_EDGE = 0;
    public static final int HIT_BRICK = 1;
    public static final int HIT_PADDLE = 2;
    public static final int BALL_LOST = 3;
}

