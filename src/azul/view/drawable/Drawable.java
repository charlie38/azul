package azul.view.drawable;

import azul.model.Game;
import azul.model.player.Player;
import azul.view.Display;
import azul.view.resource.ResourcesLoader;

import javax.swing.*;
import java.awt.*;

public abstract class Drawable extends JComponent
{
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

    /**
     * Used to draw game objects on canvas.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     */
    public Drawable(Display display, int originalX, int originalY, int originalWidth, int originalHeight)
    {
        mDisplay = display ;
        mOriginalX = originalX ;
        mOriginalY = originalY ;
        mOriginalWidth = originalWidth ;
        mOriginalHeight = originalHeight ;
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

    protected DrawingPanel getDrawingPanel()
    {
        return mDisplay.getUIPanel().getDrawingPanel() ;
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