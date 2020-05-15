package azul.view.drawable.game.board;

import azul.model.Game;
import azul.view.Display;
import azul.view.drawable.Drawable;

import java.awt.*;
import java.awt.event.ActionListener;

public class FloorLineArrow extends Drawable
{
    // Request a select animation.
    private static final int ANIMATION_DELAY = 400 ;

    // Player index in the model representation.
    private int mPlayerIndex ;
    // True if the current arrow image is focused.
    private boolean mIsFocused ;

    /**
     * The player's board floor line arrow graphical representation.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     * @param playerIndex index in the model representation of the player.
     */
    public FloorLineArrow(Display display, int originalX, int originalY, int playerIndex)
    {
        super(display, originalX, originalY, PlayerBoard.WIDTH_FL_ARROW, PlayerBoard.HEIGHT_FL_ARROW, ANIMATION_DELAY) ;

        mPlayerIndex = playerIndex ;
        mIsFocused = false ;
    }

    @Override
    public void update(java.util.Observable observable, Object object)
    {
        // Arrows are drawn/animated only when this user needs to select a pattern line or the floor one.
        setIsAnimated(getGame().getPlayerIndex() == mPlayerIndex && getGame().getState() == Game.State.SELECT_ROW) ;
    }

    @Override
    public void onAnimationStarts()
    {
        super.onAnimationStarts() ;
        mIsFocused = true ;
    }

    @Override
    public ActionListener onAnimationChanged()
    {
        return actionEvent ->
        {
            if (mIsAnimated)
            {
                mIsFocused = ! mIsFocused ;
            }
        } ;
    }

    @Override
    public void onAnimationEnds()
    {
        mIsFocused = false ;
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
        if (! mIsAnimated || ! getPlayer(mPlayerIndex).isFloorLineAccessible())
        {
            // Arrows are drawn only when user needs to select a pattern/floor line, and if this line is accessible.
            return ;
        }

        int width = (int) (mOriginalWidth * mCoef) ;
        int height = (int) (mOriginalHeight * mCoef) ;

        Image img = mIsFocused ? getResourcesLoader().getArrowFocused() : getResourcesLoader().getArrow() ;

        g.drawImage(img, x, y, width, height, null) ;
    }

    public int getPlayerIndex()
    {
        return mPlayerIndex ;
    }
}