package azul.view.drawable.factory;

import azul.model.Game;
import azul.view.Display;
import azul.view.drawable.Drawable;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TilesFactory extends Drawable
{
    // Request a focus animation.
    private static final int ANIMATION_DELAY = 400 ;
    private final boolean[] ANIMATION_PATTERN = { true, false, true, false, false, false, false, false } ;
    // /!\ Change all factory components size.
    public static final float SIZE_COEF = 1.2f ;
    // Factory bg.
    public static final int WIDTH_FACTORY = (int) (70 * SIZE_COEF) ;
    public static final int HEIGHT_FACTORY = (int) (100 * SIZE_COEF) ;
    // Cases and tiles.
    public final int DISTANCE_LEFT_TO_TILE = (int) (10 * SIZE_COEF) ;
    public final int DISTANCE_TOP_TO_TILE = (int) (20 * SIZE_COEF) ;
    public static final int WIDTH_TILE = (int) (22 * SIZE_COEF) ;
    public static final int HEIGHT_TILE = (int) (22 * SIZE_COEF) ;
    public final int SPACE_H_TILE = (WIDTH_FACTORY - (2 * WIDTH_TILE) - (2 * DISTANCE_LEFT_TO_TILE)) ;
    public final int SPACE_V_TILE = (HEIGHT_FACTORY - (2 * HEIGHT_TILE) - (2 * DISTANCE_TOP_TO_TILE)) ;

    // Factory index in the game 'model' list.
    private int mIndex ;
    // Drawables.
    private ArrayList<FactoryTile> mTiles;
    // Current part of the animation.
    private int mAnimationIndex ;

    /**
     * A tiles factory graphical representation.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     * @param index the factory index in the Game model list.
     */
    public TilesFactory(Display display, int originalX, int originalY, int index)
    {
        super(display, originalX, originalY, WIDTH_FACTORY, HEIGHT_FACTORY, ANIMATION_DELAY) ;

        mIndex = index ;
        mAnimationIndex = ANIMATION_PATTERN.length - 1 ;

        createFactoryTileList() ;
	}

	private void createFactoryTileList()
    {
        mTiles = new ArrayList<>() ;

        int tileIndex = 0 ;

        for (int i = 0 ; i < 2 ; i ++)
        {
            for (int j = 0 ; j < 2 ; j ++)
            {
                mTiles.add(new FactoryTile(getDisplay(),
                        mOriginalX + DISTANCE_LEFT_TO_TILE + j * (WIDTH_TILE + SPACE_H_TILE),
                        mOriginalY + DISTANCE_TOP_TO_TILE + i * (HEIGHT_TILE + SPACE_V_TILE),
                        mIndex, tileIndex ++)) ;
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
        for (FactoryTile tile : mTiles)
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
        // If the game model change the current player, and he needs to choose tiles in factories,
        // start the focus animation (also if the factory is not empty).
        setIsAnimated(getGame().getState() == Game.State.CHOOSE_TILES && ! getGame().getFactory(mIndex).isEmpty()) ;
    }

    @Override
    protected void onAnimationStarts()
    {
        super.onAnimationStarts() ;

        mAnimationIndex = 0 ;

        for (FactoryTile tile : mTiles)
        {
            tile.setIsAnimated(true) ;
        }
    }

    @Override
    protected ActionListener onAnimationChanged()
    {
        return actionEvent ->
        {
            if (mIsAnimated)
            {
                mAnimationIndex = mAnimationIndex == ANIMATION_PATTERN.length - 1 ? 0 : mAnimationIndex + 1 ;
            }
        } ;
    }

    @Override
    protected void onAnimationEnds()
    {
        mAnimationIndex = ANIMATION_PATTERN.length - 1 ;

        for (FactoryTile tile : mTiles)
        {
            tile.setIsAnimated(false) ;
        }
    }

    @Override
	public void paint(Graphics g)
    {
        super.paint(g) ;

        Point point = computeCoef() ;
        // Bowl (bg).
        paintBg(g, point.x, point.y) ;
        // Tiles.
        paintTiles(g) ;
    }

    private void paintBg(Graphics g, int x, int y)
    {
        Image img = ANIMATION_PATTERN[mAnimationIndex] ? getResourcesLoader().getFactoryFocused() : getResourcesLoader().getFactory() ;
        int width = (int) (WIDTH_FACTORY * mCoef) ;
        int height = (int) (HEIGHT_FACTORY * mCoef) ;

        g.drawImage(img, x, y, width, height, null) ;
    }

    private void paintTiles(Graphics g)
    {
        for (FactoryTile tile : mTiles)
        {
            tile.paint(g) ;
        }
    }
}