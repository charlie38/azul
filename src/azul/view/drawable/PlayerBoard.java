package azul.view.drawable;

import azul.model.tile.Tile;
import azul.view.Display;

import java.awt.*;
import java.util.ArrayList;

public class PlayerBoard extends Drawable
{
    // Board bg.
    public static final int WIDTH_BOARD = (int) (349 * Display.SIZE_COEF) ;
    public static final int HEIGHT_BOARD = (int) (234 * Display.SIZE_COEF) ;
    // Wall cases and tiles (bg).
    public static final int WIDTH_WALL_TILE = (int) (22 * Display.SIZE_COEF) ;
    public static final int HEIGHT_WALL_TILE = (int) (22 *Display.SIZE_COEF) ;
    public static final int SPACE_H_WALL_TILE = (int) (8 * Display.SIZE_COEF) ;
    public static final int SPACE_V_WALL_TILE = (int) (8 * Display.SIZE_COEF) ;
    public static final int DISTANCE_LEFT_TO_WALL = (int) (155 * Display.SIZE_COEF) ;
    public static final int DISTANCE_TOP_TO_WALL = (int) (38 *Display.SIZE_COEF) ;
    // Pattern lines cases and tiles (bg).
    public static final int WIDTH_PL_TILE = (int) (22 * Display.SIZE_COEF) ;
    public static final int HEIGHT_PL_TILE = (int) (22 * Display.SIZE_COEF) ;
    public static final int SPACE_H_PL_TILE = (int) (0 * Display.SIZE_COEF) ;
    public static final int SPACE_V_PL_TILE = (int) (8 * Display.SIZE_COEF) ;
    public static final int DISTANCE_LEFT_TO_PL = (int) (23 * Display.SIZE_COEF) ;
    public static final int DISTANCE_TOP_TO_PL = (int) (38 * Display.SIZE_COEF) ;

    // Player index in the game 'model' list.
    private int mIndex ;
    // Drawables.
    private ArrayList<WallTile> mWallTiles ;
    private ArrayList<PatternLineTile> mPatternLinesTiles ;

    /**
     * A player board graphical representation.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     * @param index the player index in the Game model list.
     */
    public PlayerBoard(Display display, int originalX, int originalY, int index)
    {
        super(display, originalX, originalY, WIDTH_BOARD, HEIGHT_BOARD) ;

        mIndex = index ;

        createWallTiles() ;
        createPatternLinesTiles() ;
    }

    public void createWallTiles()
    {
        mWallTiles = new ArrayList<>() ;

        for (int i = 0 ; i < azul.model.player.PlayerBoard.SIZE_WALL ; i ++)
        {
            for (int j = 0 ; j < azul.model.player.PlayerBoard.SIZE_WALL ; j ++)
            {
                mWallTiles.add(new WallTile(getDisplay(), mOriginalX, mOriginalY, i, j)) ;
            }
        }
    }

    public void createPatternLinesTiles()
    {
        mPatternLinesTiles = new ArrayList<>() ;

        for (int i = 0 ; i < azul.model.player.PlayerBoard.SIZE_PATTERN_LINES ; i ++)
        {
            for (int j = 0 ; j <= i ; j ++)
            {
                mPatternLinesTiles.add(new PatternLineTile(getDisplay(), mOriginalX, mOriginalY, i, j)) ;
            }
        }
    }

    /**
     * @param x the click x-coordinate on the Swing coordinate system.
     * @param y the click y-coordinate on the Swing coordinate system.
     * @return the drawable hit (if exists).
     */
    public Drawable onClick(int x, int y)
    {
        for (WallTile tile : mWallTiles)
        {
            if (tile.isClicked(x, y))
            {
                return tile ;
            }
        }

        for (PatternLineTile tile : mPatternLinesTiles)
        {
            if (tile.isClicked(x, y))
            {
                return tile ;
            }
        }

        return null ;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g) ;

        Point point = computeCoef() ;
        float x = point.x ;
        float y = point.y ;

        // Board.
        paintBg(g, (int) x, (int) y) ;
        // Empty tiles.
        paintWallCases(g, (int) (x + DISTANCE_LEFT_TO_WALL * mCoef), (int) (y + DISTANCE_TOP_TO_WALL * mCoef)) ;
        paintPatternLinesCases(g, (int) (x + DISTANCE_LEFT_TO_PL * mCoef), (int) (y + DISTANCE_TOP_TO_PL * mCoef)) ;
        // Player tiles.
        paintWallTiles(g) ;
        paintPatternLinesTiles(g) ;
    }

    public void paintBg(Graphics g, int x, int y)
    {
        Image img = getResourcesLoader().getBoard() ;
        int width = (int) (WIDTH_BOARD * mCoef) ;
        int height = (int) (HEIGHT_BOARD * mCoef) ;

        g.drawImage(img,
                x, y,
                width, height,
                null) ;
    }

    public void paintWallCases(Graphics g, int x, int y)
    {
        Image img = getResourcesLoader().getWallCase() ;
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
        Image img = getResourcesLoader().getPatternLinesCase() ;
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

    public void paintWallTiles(Graphics g)
    {
        for (WallTile tile : mWallTiles)
        {
            tile.paint(g) ;
        }
    }

    public void paintPatternLinesTiles(Graphics g)
    {
        for (PatternLineTile tile : mPatternLinesTiles)
        {
            tile.paint(g) ;
        }
    }
}