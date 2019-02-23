/*
 * Consts.java -- A class that defines constants for the project.
 * Defining a Consts class makes the access of constants easier and 
 * makes the change of constants much convenient.
 */
package trysuspendthread;

public class Consts {
    
    public Consts() {
    }
    
    public static final int TITLEHEIGHT = 24;
    public static final int EDGEWIDTH = 0;
    public static final int MARGIN = 0; // the entire window is the display area
    public static final int frameH = 400;
    public static final int TOTALH = frameH + TITLEHEIGHT; // the total height
    public static final int RADIUS = 7; // radius of ball.png
    
    public static final int NUM_BRICK_IMAGES = 8; // num of images
    public static final int BRICK_W = 30;
    public static final int BRICK_H = 15;
    public static final int BRICKS_PER_ROW = 15;
    public static final int MAX_ROWS = 4;
    public static final int frameW = BRICKS_PER_ROW * BRICK_W;
    public static final int PADDLE_W = 75;
    public static final int PADDLE_H = 13;
    public static final int PADDLE_INIT_Y = 60;
    public static final int MAXPROBS = 8;
    
    public static final int SCORE_UNIT = 5;
    public static final int PENALTY_LOST_BALL = 80;
    public static final int SCORE_FOR_SWITCH_BALL = 80;
}
