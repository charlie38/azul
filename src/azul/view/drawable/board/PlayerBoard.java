package azul.view.drawable.board;

import azul.view.Display;
import azul.view.drawable.Drawable;

import java.awt.*;
import java.util.ArrayList;

public class PlayerBoard extends Drawable
{
    // /!\ Change all board components size.
    public static final float SIZE_COEF = 1.4f ;
    // Board bg.
    public static final int WIDTH_BOARD = (int) (349 * SIZE_COEF) ;
    public static final int HEIGHT_BOARD = (int) (234 * SIZE_COEF) ;
    // Wall cases and tiles (bg).
    public static final int WIDTH_WALL_TILE = (int) (22 * SIZE_COEF) ;
    public static final int HEIGHT_WALL_TILE = (int) (22 * SIZE_COEF) ;
    public final int SPACE_H_WALL_TILE = (int) (8 * SIZE_COEF) ;
    public final int SPACE_V_WALL_TILE = (int) (8 * SIZE_COEF) ;
    public final int DISTANCE_LEFT_TO_WALL = (int) (155 * SIZE_COEF) ;
    public final int DISTANCE_TOP_TO_WALL = (int) (38 * SIZE_COEF) ;
    // Pattern lines cases and tiles (bg).
    public static final int WIDTH_PL_TILE = (int) (22 * SIZE_COEF) ;
    public static final int HEIGHT_PL_TILE = (int) (22 * SIZE_COEF) ;
    public final int SPACE_H_PL_TILE = (int) (0 * SIZE_COEF) ;
    public final int SPACE_V_PL_TILE = (int) (8 * SIZE_COEF) ;
    public final int DISTANCE_LEFT_TO_PL = (int) (23 * SIZE_COEF) ;
    public final int DISTANCE_TOP_TO_PL = (int) (38 * SIZE_COEF) ;
    // Player name.
    public final int DISTANCE_LEFT_TO_NAME = WIDTH_BOARD - (int) (WIDTH_BOARD / 3) ;
    public final int DISTANCE_TOP_TO_NAME = HEIGHT_BOARD - HEIGHT_BOARD / 9 ;
    public final Color COLOR_PLAYER_NAME = new Color(0x4A4E49) ;
    public final int MAX_LENGTH_PLAYER_NAME = 12 ;
    // Player points.
    public final int DISTANCE_LEFT_TO_POINTS = DISTANCE_LEFT_TO_NAME ;
    public final int DISTANCE_TOP_TO_POINTS = DISTANCE_TOP_TO_NAME + (int) (17 * SIZE_COEF) ;
    public final Color COLOR_PLAYER_POINTS = new Color(0x696969) ;
    public final String MESSAGE_POINTS = " points" ;

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
                mWallTiles.add(new WallTile(getDisplay(),
                        mOriginalX + DISTANCE_LEFT_TO_WALL + j * (WIDTH_WALL_TILE + SPACE_H_WALL_TILE),
                        mOriginalY + DISTANCE_TOP_TO_WALL + i * (HEIGHT_WALL_TILE + SPACE_V_WALL_TILE),
                        mIndex, i, j)) ;
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
                mPatternLinesTiles.add(new PatternLineTile(getDisplay(),
                        mOriginalX + DISTANCE_LEFT_TO_PL + (azul.model.player.PlayerBoard.SIZE_PATTERN_LINES - 1 - j)
                                * (WIDTH_PL_TILE + SPACE_H_PL_TILE),
                        mOriginalY + DISTANCE_TOP_TO_PL + i * (HEIGHT_PL_TILE + SPACE_V_PL_TILE),
                        mIndex, i, j)) ;
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
        // Board.
        paintBg(g, point.x, point.y) ;
        // Player tiles.
        paintWallTiles(g) ;
        paintPatternLinesTiles(g) ;
        // Player information.
        paintPlayerName(g, point.x, point.y) ;
        paintPlayerPoints(g, point.x, point.y) ;
    }

    private void paintBg(Graphics g, int x, int y)
    {
        Image img = getResourcesLoader().getBoard() ;
        int width = (int) (WIDTH_BOARD * mCoef) ;
        int height = (int) (HEIGHT_BOARD * mCoef) ;

        g.drawImage(img, x, y, width, height, null) ;
    }

    private void paintWallTiles(Graphics g)
    {
        for (WallTile tile : mWallTiles)
        {
            tile.paint(g) ;
        }
    }

    private void paintPatternLinesTiles(Graphics g)
    {
        for (PatternLineTile tile : mPatternLinesTiles)
        {
            tile.paint(g) ;
        }
    }

    private void paintPlayerName(Graphics g, int x, int y)
    {
        String name = getPlayer(mIndex).getName() ;

        g.setFont(getResourcesLoader().getFont(20 * mCoef)) ;
        g.setColor(COLOR_PLAYER_NAME) ;
        g.drawString((name.length() > MAX_LENGTH_PLAYER_NAME) ?
                        name.substring(0, MAX_LENGTH_PLAYER_NAME).concat("..") : name,
                x + (int) (DISTANCE_LEFT_TO_NAME * mCoef),
                y + (int) (DISTANCE_TOP_TO_NAME * mCoef)) ;
    }

    private void paintPlayerPoints(Graphics g, int x, int y)
    {
        g.setFont(getResourcesLoader().getFont(18 * mCoef)) ;
        g.setColor(COLOR_PLAYER_POINTS) ;
        g.drawString(String.valueOf(getPlayer(mIndex).getScore()).concat(MESSAGE_POINTS),
                x + (int) (DISTANCE_LEFT_TO_POINTS * mCoef),
                y + (int) (DISTANCE_TOP_TO_POINTS * mCoef)) ;
    }
}