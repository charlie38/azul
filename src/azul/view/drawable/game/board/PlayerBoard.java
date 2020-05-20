package azul.view.drawable.game.board;

import azul.model.Game;
import azul.view.Display;
import azul.view.drawable.Drawable;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlayerBoard extends Drawable
{
    // Request a select animation.
    private static final int ANIMATION_DELAY = 400 ;
    // /!\ Change all board components size.
    public static final float SIZE_COEF = 1.4f ;
    // Board bg.
    public static final int WIDTH_BOARD = (int) (349 * SIZE_COEF) ;
    public static final int HEIGHT_BOARD = (int) (234 * SIZE_COEF) ;
    // Wall cases and tiles (bg).
    public static final int WIDTH_WALL_TILE = (int) (27 * SIZE_COEF) ;
    public static final int HEIGHT_WALL_TILE = (int) (27 * SIZE_COEF) ;
    public final int SPACE_H_WALL_TILE = (int) (2. * SIZE_COEF) ;
    public final int SPACE_V_WALL_TILE = (int) (2. * SIZE_COEF) ;
    public final int DISTANCE_LEFT_TO_WALL = (int) (155 * SIZE_COEF) ;
    public final int DISTANCE_TOP_TO_WALL = (int) (38 * SIZE_COEF) ;
    // Pattern lines cases and tiles (bg).
    public static final int WIDTH_PL_TILE = (int) (26 * SIZE_COEF) ;
    public static final int HEIGHT_PL_TILE = (int) (26 * SIZE_COEF) ;
    public final int SPACE_H_PL_TILE = (int) (0 * SIZE_COEF) ;
    public final int SPACE_V_PL_TILE = (int) (2.2 * SIZE_COEF) ;
    public final int DISTANCE_LEFT_TO_PL = (int) (20 * SIZE_COEF) ;
    public final int DISTANCE_TOP_TO_PL = (int) (38 * SIZE_COEF) ;
    // Pattern lines arrows.
    public static final int WIDTH_PL_ARROW = (int) (25 * SIZE_COEF) ;
    public static final int HEIGHT_PL_ARROW = (int) (25 * SIZE_COEF) ;
    public final int SPACE_V_PL_ARROW = (int) (4.5 * SIZE_COEF) ;
    public final int DISTANCE_LEFT_TO_PL_ARROW = (int) (-8 * SIZE_COEF) ;
    public final int DISTANCE_TOP_TO_PL_ARROW = (int) (35 * SIZE_COEF) ;
    // Floor line tiles (bg).
    public static final int WIDTH_FL_TILE = (int) (22 * SIZE_COEF) ;
    public static final int HEIGHT_FL_TILE = (int) (22 * SIZE_COEF) ;
    public final int SPACE_H_FL_TILE = (int) (7.5 * SIZE_COEF) ;
    public final int DISTANCE_LEFT_TO_FL = (int) (21 * SIZE_COEF) ;
    public final int DISTANCE_TOP_TO_FL = (int) (4.15 * HEIGHT_BOARD / 5) ;
    // Floor line arrow.
    public static final int WIDTH_FL_ARROW = WIDTH_PL_ARROW ;
    public static final int HEIGHT_FL_ARROW = HEIGHT_PL_ARROW ;
    public final int DISTANCE_LEFT_TO_FL_ARROW = DISTANCE_LEFT_TO_PL_ARROW ;
    public final int DISTANCE_TOP_TO_FL_ARROW = (int) (4.1 * HEIGHT_BOARD / 5) ;
    // Player name.
    public final int DISTANCE_LEFT_TO_NAME = WIDTH_BOARD - WIDTH_BOARD / 3 ;
    public final int DISTANCE_TOP_TO_NAME = HEIGHT_BOARD - HEIGHT_BOARD / 9 ;
    public final int MAX_LENGTH_PLAYER_NAME = 10 ;
    // Player points.
    public final int DISTANCE_LEFT_TO_POINTS = DISTANCE_LEFT_TO_NAME ;
    public final int DISTANCE_TOP_TO_POINTS = DISTANCE_TOP_TO_NAME + (int) (17 * SIZE_COEF) ;
    public final String MESSAGE_POINTS = " points" ;

    // Player index in the game 'model' list.
    private int mIndex ;
    // Drawables.
    private ArrayList<WallTile> mWallTiles ;
    private ArrayList<PatternLineArrow> mPatternLinesArrows ;
    private ArrayList<PatternLineTile> mPatternLinesTiles ;
    private ArrayList<FloorLineTile> mFloorLineTiles ;
    private FloorLineArrow mFloorLineArrow ;
    // True if this board is focused (current player board).
    private boolean mIsFocused ;

    /**
     * A player board graphical representation.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     * @param index the player index in the Game model list.
     */
    public PlayerBoard(Display display, int originalX, int originalY, int index)
    {
        super(display, originalX, originalY, WIDTH_BOARD, HEIGHT_BOARD, ANIMATION_DELAY) ;

        mIndex = index ;
        mIsFocused = false ;

        createWallTiles() ;
        createPatternLinesArrows() ;
        createPatternLinesTiles() ;
        createFloorLineArrow() ;
        createFloorLineTiles() ;
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

    public void createPatternLinesArrows()
    {
        mPatternLinesArrows = new ArrayList<>() ;

        for (int i = 0 ; i < azul.model.player.PlayerBoard.SIZE_PATTERN_LINES ; i ++)
        {
            mPatternLinesArrows.add(new PatternLineArrow(getDisplay(),
                    mOriginalX + DISTANCE_LEFT_TO_PL_ARROW,
                    mOriginalY + DISTANCE_TOP_TO_PL_ARROW + i * (HEIGHT_PL_ARROW + SPACE_V_PL_ARROW),
                    mIndex, i)) ;
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

    public void createFloorLineTiles()
    {
        mFloorLineTiles = new ArrayList<>() ;

        for (int i = 0 ; i < azul.model.player.PlayerBoard.SIZE_FLOOR_LINE ; i ++)
        {
            mFloorLineTiles.add(new FloorLineTile(getDisplay(),
                    mOriginalX + DISTANCE_LEFT_TO_FL + i * (WIDTH_FL_TILE + SPACE_H_FL_TILE),
                    mOriginalY + DISTANCE_TOP_TO_FL,
                    mIndex, i)) ;
        }
    }

    public void createFloorLineArrow()
    {
        mFloorLineArrow = new FloorLineArrow(getDisplay(),
                mOriginalX + DISTANCE_LEFT_TO_FL_ARROW,
                mOriginalY + DISTANCE_TOP_TO_FL_ARROW,
                mIndex) ;
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

        for (PatternLineArrow arrow : mPatternLinesArrows)
        {
            if (arrow.isClicked(x, y))
            {
                return arrow ;
            }
        }

        for (PatternLineTile tile : mPatternLinesTiles)
        {
            if (tile.isClicked(x, y))
            {
                return tile ;
            }
        }

        for (FloorLineTile tile : mFloorLineTiles)
        {
            if (tile.isClicked(x, y))
            {
                return tile ;
            }
        }

        if (mFloorLineArrow.isClicked(x, y))
        {
            return mFloorLineArrow ;
        }

        return null ;
    }

    @Override
    public void update(java.util.Observable observable, Object object)
    {
        // If the game model change the current player, and if it's his board, (or if it's the decorate wall animation)
        // start the focus animation.
        setIsAnimated(getGame().getPlayerIndex() == mIndex || getGame().getState() == Game.State.DECORATE_WALL) ;
    }

    @Override
    protected void onAnimationStarts()
    {
        super.onAnimationStarts() ;
        mIsFocused = true ;
    }

    @Override
    protected ActionListener onAnimationChanged()
    {
        return null ;
    }

    @Override
    protected void onAnimationEnds()
    {
        mIsFocused = false ;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g) ;

        Point point = computeCoef() ;
        // Board.
        paintBg(g, point.x, point.y) ;
        paintPatternLinesArrows(g) ;
        paintFloorLineArrow(g) ;
        // Player tiles.
        paintWallTiles(g) ;
        paintPatternLinesTiles(g) ;
        paintFloorLinesTiles(g) ;
        // Player information.
        paintPlayerName(g, point.x, point.y) ;
        paintPlayerPoints(g, point.x, point.y) ;
    }

    private void paintBg(Graphics g, int x, int y)
    {
        Image img = mIsAnimated ? getResourcesLoader().getBoardFocused() : getResourcesLoader().getBoard() ;
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

    private void paintPatternLinesArrows(Graphics g)
    {
        for (PatternLineArrow arrow : mPatternLinesArrows)
        {
            arrow.paint(g) ;
        }
    }

    private void paintPatternLinesTiles(Graphics g)
    {
        for (PatternLineTile tile : mPatternLinesTiles)
        {
            tile.paint(g) ;
        }
    }

    private void paintFloorLinesTiles(Graphics g)
    {
        for (FloorLineTile tile : mFloorLineTiles)
        {
            tile.paint(g) ;
        }
    }

    private void paintFloorLineArrow(Graphics g)
    {
        mFloorLineArrow.paint(g) ;
    }

    private void paintPlayerName(Graphics g, int x, int y)
    {
        String name = getPlayer(mIndex).getName() ;

        g.setFont(getResourcesLoader().getFont(22 * mCoef)) ;
        g.setColor(mIndex == getGame().getPlayerIndex() ? Display.CD_PRIMARY : Display.CL_PRIMARY) ;
        g.drawString((name.length() > MAX_LENGTH_PLAYER_NAME) ?
                        name.substring(0, MAX_LENGTH_PLAYER_NAME).concat("..") : name,
                x + (int) (DISTANCE_LEFT_TO_NAME * mCoef),
                y + (int) (DISTANCE_TOP_TO_NAME * mCoef)) ;
    }

    private void paintPlayerPoints(Graphics g, int x, int y)
    {
        g.setFont(getResourcesLoader().getFont(20 * mCoef)) ;
        g.setColor(mIndex == getGame().getPlayerIndex() ? Display.CD_TERTIARY : Display.CL_PRIMARY) ;
        g.drawString(String.valueOf(getPlayer(mIndex).getScore()).concat(MESSAGE_POINTS),
                x + (int) (DISTANCE_LEFT_TO_POINTS * mCoef),
                y + (int) (DISTANCE_TOP_TO_POINTS * mCoef)) ;
    }
}