package azul.view.drawables;

import azul.view.Display;

import java.awt.*;

public class PlayerBoard extends Drawable
{
    // Board bg.
    public static final int WIDTH_BOARD = (int) (334 * Display.SIZE_COEF) ;
    public static final int HEIGHT_BOARD = (int) (290 * Display.SIZE_COEF) ;
    // Wall cases and tiles (bg).
    public static final int WIDTH_WALL_TILE = (int) (22 * Display.SIZE_COEF) ;
    public static final int HEIGHT_WALL_TILE = (int) (22 *Display.SIZE_COEF) ;
    public static final int SPACE_H_WALL_TILE = (int) (8 * Display.SIZE_COEF) ;
    public static final int SPACE_V_WALL_TILE = (int) (8 * Display.SIZE_COEF) ;
    public static final int DISTANCE_LEFT_TO_WALL = (int) (145 * Display.SIZE_COEF) ;
    public static final int DISTANCE_TOP_TO_WALL = (int) (95 *Display.SIZE_COEF) ;
    // Pattern lines cases and tiles (bg).
    public static final int WIDTH_PL_TILE = (int) (22 * Display.SIZE_COEF) ;
    public static final int HEIGHT_PL_TILE = (int) (22 * Display.SIZE_COEF) ;
    public static final int SPACE_H_PL_TILE = (int) (0 * Display.SIZE_COEF) ;
    public static final int SPACE_V_PL_TILE = (int) (8 * Display.SIZE_COEF) ;
    public static final int DISTANCE_LEFT_TO_PL = (int) (10 * Display.SIZE_COEF) ;
    public static final int DISTANCE_TOP_TO_PL = (int) (95 * Display.SIZE_COEF) ;

    /**
     * A player board graphical representation.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     */
    public PlayerBoard(Display display, int originalX, int originalY)
    {
        super(display, originalX, originalY) ;
    }

    @Override
    public void paint(Graphics g)
    {
        Point point = computeCoef() ;
        float x = point.x ;
        float y = point.y ;

        paintBg(g, (int) x, (int) y) ;
        paintWallCases(g, (int) (x + DISTANCE_LEFT_TO_WALL * mCoef), (int) (y + DISTANCE_TOP_TO_WALL * mCoef)) ;
        paintPatternLinesCases(g, (int) (x + DISTANCE_LEFT_TO_PL * mCoef), (int) (y + DISTANCE_TOP_TO_PL * mCoef)) ;
        paintWallTiles(g, (int) (x + DISTANCE_LEFT_TO_WALL * mCoef), (int) (y + DISTANCE_TOP_TO_WALL * mCoef)) ;
        paintPatternLinesTiles(g, (int) (x + DISTANCE_LEFT_TO_PL * mCoef), (int) (y + DISTANCE_TOP_TO_PL * mCoef)) ;
    }

    public void paintBg(Graphics g, int x, int y)
    {
        Image img = getImageLoader().getBoard() ;
        int width = (int) (WIDTH_BOARD * mCoef) ;
        int height = (int) (HEIGHT_BOARD * mCoef) ;

        g.drawImage(img,
                x, y,
                width, height,
                null) ;
    }

    public void paintWallCases(Graphics g, int x, int y)
    {
        Image img = getImageLoader().getWallCase() ;
        int width = (int) (WIDTH_WALL_TILE * mCoef) ;
        int height = (int) (HEIGHT_WALL_TILE * mCoef) ;

    	for (int i = 0 ; i < azul.model.player.PlayerBoard.SIZE_WALL ; i ++)
    	{
            for (int j = 0 ; j < azul.model.player.PlayerBoard.SIZE_WALL ; j ++)
            {
                g.drawImage(img,
                        x + j * (int) (width + SPACE_H_WALL_TILE * mCoef),
                        y + i * (int) (height + SPACE_V_WALL_TILE * mCoef),
                        width, height,
                        null) ;
            }
        }
    }
    
    public void paintPatternLinesCases(Graphics g, int x, int y)
    {
        Image img = getImageLoader().getPatternLinesCase() ;
        int width = (int) (WIDTH_PL_TILE * mCoef) ;
        int height = (int) (HEIGHT_PL_TILE * mCoef) ;

        for (int i = 0 ; i < azul.model.player.PlayerBoard.SIZE_PATTERN_LINES ; i ++)
        {
            for (int j = 0 ; j <= i ; j ++)
            {
                g.drawImage(img,
                        x + (azul.model.player.PlayerBoard.SIZE_PATTERN_LINES - 1 - j) * (int) (width + SPACE_H_PL_TILE * mCoef),
                        y + i * (int) (height + SPACE_V_PL_TILE * mCoef),
                        width, height,
                        null) ;
            }
        }
    }

    public void paintWallTiles(Graphics g, int x, int y)
    {
        int width = (int) (WIDTH_WALL_TILE * mCoef) ;
        int height = (int) (HEIGHT_WALL_TILE * mCoef) ;

        for (int i = 0 ; i < azul.model.player.PlayerBoard.SIZE_WALL ; i ++)
        {
            for (int j = 0 ; j < azul.model.player.PlayerBoard.SIZE_WALL ; j ++)
            {
                Image img = getImageLoader().getIngredient(getPlayer().getInWall(i, j)) ;

                g.drawImage(img,
                        x + j * (int) (width + SPACE_H_WALL_TILE * mCoef),
                        y + i * (int) (height + SPACE_V_WALL_TILE * mCoef),
                        width, height,
                        null) ;
            }
        }
    }

    public void paintPatternLinesTiles(Graphics g, int x, int y)
    {
        int width = (int) (WIDTH_PL_TILE * mCoef) ;
        int height = (int) (HEIGHT_PL_TILE * mCoef) ;

        for (int i = 0 ; i < azul.model.player.PlayerBoard.SIZE_PATTERN_LINES ; i ++)
        {
            for (int j = 0 ; j <= i ; j ++)
            {
                Image img = getImageLoader().getIngredient(getPlayer().getInPatternLines(i, j)) ;

                g.drawImage(img,
                        x + (azul.model.player.PlayerBoard.SIZE_PATTERN_LINES - 1 - j) * (int) (width + SPACE_H_PL_TILE * mCoef),
                        y + i * (int) (height + SPACE_V_PL_TILE * mCoef),
                        width, height,
                        null) ;
            }
        }
    }
}