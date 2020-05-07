package azul.view.drawable.table;

import azul.view.Display;
import azul.view.drawable.Drawable;

import java.awt.*;
import java.awt.event.ActionListener;

public class TableTile extends Drawable
{
    // Request a select animation.
    private static final int ANIMATION_DELAY = 400 ;

    // Tile index in the table model representations.
    private int mIndex ;
    // True if the current tile image is selected.
    private boolean mIsSelected ;

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
        mIsSelected = false ;
    }

    @Override
    public void onAnimationStarts()
    {
        mIsSelected = true ;
    }

    @Override
    public ActionListener onAnimationChanged()
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
    public void onAnimationEnds()
    {
        mIsSelected = false ;
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

        Image ingredient = mIsSelected ?
                getResourcesLoader().getIngredientBlurred(getGame().getInTilesTable(mIndex)) :
                getResourcesLoader().getIngredient(getGame().getInTilesTable(mIndex)) ;

        g.drawImage(ingredient, x, y, width, height, null) ;
    }

    public int getTileIndex()
    {
        return mIndex ;
    }
}