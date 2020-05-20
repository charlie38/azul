package azul.view.drawable.game.table;

import azul.model.Game;
import azul.view.Display;
import azul.view.drawable.Drawable;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Table extends Drawable
{
    // /!\ Change all table components size.
    public static final float SIZE_COEF = 0.9f ;
    // Size of bg tiles.
    public static final int WIDTH_TABLE = (int) (305 * SIZE_COEF) ;
    public static final int HEIGHT_TABLE = (int) (345 * SIZE_COEF) ;
    // Tiles on the table.
    public final int TABLE_ROWS = 9 ;
    public final int TABLE_COLUMNS = 4 ;
    public static final int WIDTH_TILE = (int) (37.5 * SIZE_COEF) ;
    public static final int HEIGHT_TILE = (int) (37.5 * SIZE_COEF) ;
    public final int DISTANCE_LEFT_TO_TILE = (int) (25 * SIZE_COEF) ;
    public final int DISTANCE_TOP_TO_TILE = (int) (25 * SIZE_COEF) ;
    public final int SPACE_H_TILE = (WIDTH_TABLE - 2 * DISTANCE_LEFT_TO_TILE - TABLE_COLUMNS * WIDTH_TILE) / (TABLE_COLUMNS - 1) ;
    public final int SPACE_V_TILE = (HEIGHT_TABLE - 2 * DISTANCE_TOP_TO_TILE - TABLE_ROWS * HEIGHT_TILE) / (TABLE_ROWS - 1) ;

    // Tiles on the table.
    private ArrayList<TableTile> mTiles ;

    /**
     * The remaining tiles background.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     */
    public Table(Display display, int originalX, int originalY)
    {
        super(display, originalX, originalY, WIDTH_TABLE, HEIGHT_TABLE, Drawable.DEFAULT_ANIMATION_DELAY) ;

        createTiles() ;
    }

    private void createTiles()
    {
        mTiles = new ArrayList<>() ;

        int tile = 0 ;

        for (int i = 0 ; i < 9 ; i ++)
        {
            for (int j = 0 ; j < 4 ; j ++)
            {
                mTiles.add(new TableTile(getDisplay(),
                        mOriginalX + DISTANCE_LEFT_TO_TILE + j * (WIDTH_TILE + SPACE_H_TILE),
                        mOriginalY + DISTANCE_TOP_TO_TILE + i * (HEIGHT_TILE + SPACE_V_TILE),
                        tile ++)) ;
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
        for (TableTile tile : mTiles)
        {
            if (tile.isClicked(x, y))
            {
                return tile ;
            }
        }

        return null ;
    }

    @Override
    public void update(java.util.Observable observable, Object object)
    {
        // Animated when user needs to choose a tile, and when the table is not empty.
        setIsAnimated(! getGame().isTableEmpty() && getGame().getState() == Game.State.CHOOSE_TILES) ;
    }

    @Override
    protected void onAnimationStarts()
    {
        super.onAnimationStarts() ;

        for (TableTile tile : mTiles)
        {
            tile.setIsAnimated(true) ;
        }
    }

    @Override
    protected ActionListener onAnimationChanged()
    {
        return null ;
    }

    @Override
    protected void onAnimationEnds()
    {
        for (TableTile tile : mTiles)
        {
            tile.setIsAnimated(false) ;
        }
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g) ;

        Point point = computeCoef() ;

        paintBg(g, point.x, point.y) ;
        paintTiles(g) ;
    }

    private void paintBg(Graphics g, int x, int y)
    {
        Image img = getResourcesLoader().getTable() ;
        int width = (int) (WIDTH_TABLE * mCoef) ;
        int height = (int) (HEIGHT_TABLE * mCoef) ;

        g.drawImage(img, x, y, width, height, null) ;
    }

    private void paintTiles(Graphics g)
    {
        for (TableTile tile : mTiles)
        {
            tile.paint(g) ;
        }
    }
}