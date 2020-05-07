package azul.view.drawable.factory;

import azul.view.Display;
import azul.view.drawable.Drawable;

import java.awt.*;
import java.awt.event.ActionListener;

public class Tile extends Drawable
{
    // Request a select animation.
    private static final int ANIMATION_DELAY = 200 ;

    // Tile index in model representations.
    private int mFactoryIndex ;
    private int mIndex ;
    // True if the current tile image is selected.
    private boolean mIsSelected ;

    /**
     * A player wall tile graphical representation.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     * @param factoryIndex index of its container in the model representation.
     * @param index index in the model representation.
     */
    public Tile(Display display, int originalX, int originalY, int factoryIndex, int index)
    {
        super(display, originalX, originalY, TilesFactory.WIDTH_TILE, TilesFactory.HEIGHT_TILE, ANIMATION_DELAY) ;

        mFactoryIndex = factoryIndex ;
        mIndex = index ;
        mIsSelected = false ;
    }

    @Override
    protected void onAnimationStarts()
    {
        mIsSelected = true ;
    }

    @Override
    protected ActionListener onAnimationChanged()
    {
        return actionEvent ->
        {
            if (mIsAnimated)
            {
                mIsSelected = ! mIsSelected ;
            }
        } ;
    }

    @Override
    protected void onAnimationEnds()
    {
        mIsSelected = false ;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g) ;

        Point point = computeCoef() ;

        paintTile(g, point.x, point.y);
    }

    private void paintTile(Graphics g, int x, int y)
    {
        int width = (int) (mOriginalWidth * mCoef) ;
        int height = (int) (mOriginalHeight * mCoef) ;

        Image bg = mIsSelected ? getResourcesLoader().getFactoryCaseSelected() : getResourcesLoader().getFactoryCase() ;
        Image ingredient = mIsSelected ?
                getResourcesLoader().getIngredientBlurred(getGame().getFactory(mFactoryIndex).getTile(mIndex)) :
                getResourcesLoader().getIngredient(getGame().getFactory(mFactoryIndex).getTile(mIndex)) ;

        g.drawImage(bg, x, y, width, height, null) ;
        g.drawImage(ingredient, x, y, width, height, null) ;
    }

    public int getFactoryIndex()
    {
        return mFactoryIndex ;
    }

    public int getTileIndex()
    {
        return mIndex ;
    }
}