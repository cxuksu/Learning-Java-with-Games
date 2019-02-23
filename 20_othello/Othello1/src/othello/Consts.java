/*
 * Consts.java -- A class that defines constants for the project.
 * Defining a Consts class makes the access of constants easier and 
 * makes the change of constants much convenient.
 */
package othello;

import java.awt.Color;

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
    
    public static final int MAXCELL = 8;
    public static final int CELLW = 40;
    public static final int CELLH = 40;
    public static final int PIECEW = 36;
    public static final int PIECEH = 36;
    public static final int GAPW = 2;
    public static final int RIGHTSHIFT = 8;
    public static final int BOARDW = MAXCELL*CELLW + (MAXCELL+1)*GAPW;
    public static final int BOARDH = MAXCELL*CELLH + (MAXCELL+1)*GAPW;
    public static final int PLAYERW = 3*CELLW+5*GAPW;
    public static final int PLAYERH = 4*CELLH+5*GAPW;
    public static final int PLAYERX = BOARDW;
    public static final int PLAYERY = MAXCELL/2*(CELLH+GAPW);
    public static final int SCOREW = 3*CELLW+5*GAPW;
    public static final int SCOREH = 4*CELLH+5*GAPW;
    public static final int SCOREX = BOARDW;
    public static final int SCOREY = 0;
    
    // CV_WIDTH and CV_HEIGHT are calculated
    public static final int CV_WIDTH = BOARDW + PLAYERW;
    public static final int CV_HEIGHT = BOARDH + TOP_BAR_HEIGHT;
    
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
    
    public static final Color BGCOLOR = new Color(210, 255, 210);
    public static final Color CELLCOLOR = new Color(40, 160, 40);
    public static final Color SCORECOLOR = Color.WHITE;
    public static final Color WHITEP = Color.WHITE;
    public static final Color BLACKP = Color.BLACK;
    public static final Color BORDERC = Color.DARK_GRAY;
    public static final Color MOUSEENTERC = Color.YELLOW;
    public static final Color MOUSEEXITED = CELLCOLOR;
    
}
