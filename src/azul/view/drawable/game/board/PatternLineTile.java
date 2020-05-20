package azul.view.drawable.game.board;

import azul.view.Display;
import azul.view.drawable.Drawable;

import java.awt.*;
import java.awt.event.ActionListener;

public class PatternLineTile extends Drawable
{
    // Request a select animation.
    private static final int ANIMATION_DELAY = 200 ;

    // Player index in the model representation.
    private int mPlayerIndex ;
    // Tile indexes in the pattern lines graphical and model representations.
    private int mIndexI ;
    private int mIndexJ ;
    // True if the current tile image is selected.
    private boolean mIsSelected ;

    /**
     * A player pattern tile graphical representation.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     * @param playerIndex index in the model representation of the player.
     * @param indexI index in the model representation, and Y delta in the view representation.
     * @param indexJ index in the model representation, and X delta in the view representation.
     */
    public PatternLineTile(Display display, int originalX, int originalY, int playerIndex, int indexI, int indexJ)
    {
        super(display, originalX, originalY, PlayerBoard.WIDTH_PL_TILE, PlayerBoard.HEIGHT_PL_TILE, ANIMATION_DELAY) ;

        mPlayerIndex = playerIndex ;
        mIndexI = indexI ;
        mIndexJ = indexJ ;
        mIsSelected = false ;
    }

    @Override
    public void onAnimationStarts()
    {
        super.onAnimationStarts() ;
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

        Image bg = mIsSelected ? getResourcesLoader().getPatternLinesCaseSelected() : getResourcesLoader().getPatternLinesCase() ;
        Image ingredient = mIsSelected ?
                getResourcesLoader().getIngredient(getGame().getPlayer(mPlayerIndex).getInPatternLines(mIndexI, mIndexJ)) :
                getResourcesLoader().getIngredientSelected(getGame().getPlayer(mPlayerIndex).getInPatternLines(mIndexI, mIndexJ)) ;

        g.drawImage(bg, x, y, width, height, null) ;
        g.drawImage(ingredient, x, y, width, height, null) ;
    }

    public int getRowIndex()
    {
        return mIndexI ;
    }

    public int getColumnIndex()
    {
        return mIndexJ ;
    }
}