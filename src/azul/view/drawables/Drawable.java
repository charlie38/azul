package azul.view.drawables;

import azul.model.Game;
import azul.model.player.Player;
import azul.view.Display;
import azul.view.images.ImageLoader;

import javax.swing.*;
import java.awt.*;

public abstract class Drawable extends JComponent
{
    // Root ref.
    private Display mDisplay ;
    // Original coordinates (center of screen is (0, 0)).
    protected int mOriginalX ;
    protected int mOriginalY ;
    // Coefficient to compute coordinate on window resize.
    protected float mCoef ;

    /**
     * Used to draw game objects on canvas.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     */
    public Drawable(Display display, int originalX, int originalY)
    {
        mDisplay = display ;
        mOriginalX = originalX ;
        mOriginalY = originalY ;
    }

    /**
     * Compute the resizing coefficient, and return the new coordinates (center of the screen is (0, 0)).
     * <!> Needs to be call in the 'paint' method of the drawable to have everything scaled. </!>
     * @return new drawable coordinates.
     */
    public Point computeCoef()
    {
        mCoef = Math.min(mDisplay.getDrawingPanel().getResizeCoefWidth(),
                mDisplay.getDrawingPanel().getResizeCoefHeight()) ;

        return new Point((int) (mDisplay.getDrawingPanel().getWidth() / 2f + mOriginalX * mCoef),
                (int) (mDisplay.getDrawingPanel().getHeight() / 2f + mOriginalY * mCoef)) ;
    }

    public Display getDisplay()
    {
        return mDisplay ;
    }

    public ImageLoader getImageLoader()
    {
        return mDisplay.getImageLoader() ;
    }

    public Game getGame()
    {
        return mDisplay.getGame() ;
    }

    public Player getPlayer()
    {
        return mDisplay.getGame().getPlayer() ;
    }
}