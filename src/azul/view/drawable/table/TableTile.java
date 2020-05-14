package azul.view.drawable.table;

import azul.view.Display;
import azul.view.drawable.Drawable;

import java.awt.*;
import java.awt.event.ActionListener;

public class TableTile extends Drawable
{
    // Request a focus animation.
    private static final int ANIMATION_DELAY = 400 ;
    private final boolean[] ANIMATION_PATTERN = { true, false, true, false, false, false, false, false } ;

    // Tile index in the table model representations.
    private int mIndex ;
    // Current part of the animation.
    private int mAnimationIndex ;

    /**
     * A table tile representation.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     * @param index index in the model representation.
     */
    public TableTile(Display display, int originalX, int originalY, int index)
    {
        super(display, originalX, originalY, Table.WIDTH_TILE, Table.HEIGHT_TILE, ANIMATION_DELAY) ;

        mIndex = index ;
        mAnimationIndex = ANIMATION_PATTERN.length - 1 ;
    }

    @Override
    public void onAnimationStarts()
    {
        super.onAnimationStarts() ;

        mAnimationIndex = 0 ;
    }

    @Override
    public ActionListener onAnimationChanged()
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
    public void onAnimationEnds()
    {
        mAnimationIndex = ANIMATION_PATTERN.length - 1 ;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g) ;

        Point point = computeCoef() ;

        paintTile(g, point.x, point.y) ;
    }

    private void paintTile(Graphics g, int x, int y)
    {
        int width = (int) (mOriginalWidth * mCoef) ;
        int height = (int) (mOriginalHeight * mCoef) ;

        Image ingredient = ANIMATION_PATTERN[mAnimationIndex] ?
                getResourcesLoader().getIngredientSelected(getGame().getInTilesTable(mIndex)) :
                getResourcesLoader().getIngredient(getGame().getInTilesTable(mIndex)) ;

        g.drawImage(ingredient, x, y, width, height, null) ;
    }

    public int getTileIndex()
    {
        return mIndex ;
    }
}