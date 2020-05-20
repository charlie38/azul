package azul.view.drawable.game.board;

import azul.model.Game;
import azul.view.Display;
import azul.view.drawable.Drawable;

import java.awt.*;
import java.awt.event.ActionListener;

public class WallTile extends Drawable
{
    // Request a select animation.
    private static final int ANIMATION_DELAY = 800 ;

    // Player index in the model representation.
    private int mPlayerIndex ;
    // Tile indexes in the wall graphical and model representations.
    private int mIndexI ;
    private int mIndexJ ;
    // True if the current tile image is selected.
    private boolean mIsSelected ;

    /**
     * A player wall tile graphical representation.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     * @param playerIndex index in the model representation of the player.
     * @param indexI index in the model representation, and Y delta in the view representation of the wall.
     * @param indexJ index in the model representation, and X delta in the view representation of the wall.
     */
    public WallTile(Display display, int originalX, int originalY, int playerIndex, int indexI, int indexJ)
    {
        super(display, originalX, originalY, PlayerBoard.WIDTH_WALL_TILE, PlayerBoard.HEIGHT_WALL_TILE, ANIMATION_DELAY) ;

        mPlayerIndex = playerIndex ;
        mIndexI = indexI ;
        mIndexJ = indexJ ;
        mIsSelected = false ;
    }

    @Override
    public void update(java.util.Observable observable, Object object)
    {
        // Show the animated tiles when the user wants to see the decoration walls move.
        setIsAnimated(getGame().getState() == Game.State.DECORATE_WALL) ;
    }

    @Override
    protected void onAnimationStarts()
    {
        super.onAnimationStarts() ;
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

        Image bg = mIsSelected ?
                getResourcesLoader().getWallCaseSelected() :
                getResourcesLoader().getWallCase() ;
        Image ingredient = mIsSelected ?
                getResourcesLoader().getIngredient(getPlayer(mPlayerIndex).getInWall(mIndexI, mIndexJ)) :
                getResourcesLoader().getIngredientSelected(getPlayer(mPlayerIndex).getInWall(mIndexI, mIndexJ)) ;
        Image ingredientBlurred = getResourcesLoader().getIngredientBlurred(
                azul.model.player.PlayerBoard.getWallTile(mIndexI + 1, mIndexJ + 1)) ;

        g.drawImage(bg, x, y, width, height, null) ;
        g.drawImage(ingredientBlurred, x, y, width, height, null) ;
        g.drawImage(ingredient, x, y, width, height, null) ;
    }
}