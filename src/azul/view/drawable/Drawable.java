package azul.view.drawable;

import azul.model.Game;
import azul.model.player.Player;
import azul.view.Display;
import azul.view.resource.ResourcesLoader;
import azul.view.ui.screen.InGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observer;

public abstract class Drawable extends JComponent implements Observer
{
    // Big value to avoid updating often the not-animated drawables.
    protected static final int DEFAULT_ANIMATION_DELAY = 10000 ;

    // Root ref.
    private Display mDisplay ;
    // Original coordinates (center of screen is (0, 0)).
    protected int mOriginalX ;
    protected int mOriginalY ;
    // Original width et height.
    protected int mOriginalWidth ;
    protected int mOriginalHeight ;
    // Coefficient to compute coordinate on window resize.
    protected float mCoef ;
    // True if animated.
    protected boolean mIsAnimated ;
    // Animation.
    protected Timer mTimer ;

    /**
     * Used to draw game objects on canvas.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     */
    public Drawable(Display display, int originalX, int originalY, int originalWidth, int originalHeight,
                    int animationDelay)
    {
        mDisplay = display ;
        mOriginalX = originalX ;
        mOriginalY = originalY ;
        mOriginalWidth = originalWidth ;
        mOriginalHeight = originalHeight ;
        mIsAnimated = false ;
        // Observe the game.
        display.getGame().addObserver(this) ;
        // Animation.
        mTimer = new Timer(animationDelay, onAnimationChanged()) ;
        mTimer.start() ;
    }

    /**
     * Compute the resizing coefficient, and return the new coordinates (center of the screen is (0, 0)).
     * <!> Needs to be call in the 'paint' method of the drawable to have everything scaled. </!>
     * @return new drawable coordinates.
     */
    protected Point computeCoef()
    {
        mCoef = Math.min(getDrawingPanel().getResizeWidthCoef(), getDrawingPanel().getResizeHeightCoef()) ;

        return new Point((int) (getDrawingPanel().getWidth() / 2f + mOriginalX * mCoef),
                (int) (getDrawingPanel().getHeight() / 2f + mOriginalY * mCoef)) ;
    }

    /**
     * Check if the drawable was hit.
     * @param x the click x-coordinate on the Swing coordinate system.
     * @param y the click y-coordinate on the Swing coordinate system.
     * @return true if hit.
     */
    public boolean isClicked(int x, int y)
    {
        Point point = computeCoef() ;

        return x >= point.x && x <= point.x + mOriginalWidth * mCoef
                && y >= point.y && y <= point.y + mOriginalHeight * mCoef ;

    }

    /** Observer. **/

    @Override
    public void update(java.util.Observable observable, Object o)
    {
        // Do nothing for most of drawables.
    }

    /** Animation. **/

    public void setIsAnimated(boolean isAnimated)
    {
        if (isAnimated == mIsAnimated)
        {
            return ;
        }

        mIsAnimated = isAnimated ;

        if (isAnimated)
        {
            onAnimationStarts() ;
        }
        else
        {
            onAnimationEnds() ;
        }
    }

    protected void onAnimationStarts()
    {
        mTimer.start() ;
    }

    protected abstract ActionListener onAnimationChanged() ;

    protected abstract void onAnimationEnds() ;

    /** Getters. **/

    protected Display getDisplay()
    {
        return mDisplay ;
    }

    protected ResourcesLoader getResourcesLoader()
    {
        return mDisplay.getResourcesLoader() ;
    }

    protected Game getGame()
    {
        return mDisplay.getGame() ;
    }

    protected DrawingGamePanel getDrawingPanel()
    {
        return mDisplay.getUIPanel().getGameCanvas() ;
    }

    protected Player getPlayer()
    {
        return mDisplay.getGame().getPlayer() ;
    }

    protected Player getPlayer(int index)
    {
        return mDisplay.getGame().getPlayer(index) ;
    }
}