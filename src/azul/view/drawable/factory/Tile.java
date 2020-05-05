package azul.view.drawable.factory;

import azul.view.Display;
import azul.view.drawable.Drawable;

import javax.swing.*;
import java.awt.*;

public class Tile extends Drawable
{
    // Request a select animation.
    private final int ANIMATION_DELAY = 250 ;

    // Tile index in model representations.
    private int mFactoryIndex ;
    private int mIndex ;
    // True if the current tile image is blurred.
    private boolean mIsBlurred ;
    // True if animated.
    private boolean mIsAnimated ;

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
        super(display, originalX, originalY, TilesFactory.WIDTH_TILE, TilesFactory.HEIGHT_TILE) ;

        mFactoryIndex = factoryIndex ;
        mIndex = index ;
        mIsBlurred = false ;
        mIsAnimated = false ;

        new Timer(ANIMATION_DELAY,
                actionEvent ->
                {
                    if (mIsAnimated)
                    {
                        mIsBlurred = ! mIsBlurred ;
                        getDisplay().getDrawingPanel().repaint() ;
                    }
                }
        ).start() ;
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

        Image bg = getResourcesLoader().getFactoryCase() ;
        Image ingredient = mIsBlurred ?
                getResourcesLoader().getIngredientBlurred(getGame().getFactory(mFactoryIndex).getTile(mIndex)) :
                getResourcesLoader().getIngredient(getGame().getFactory(mFactoryIndex).getTile(mIndex)) ;

        g.drawImage(bg, x, y, width, height, null) ;
        g.drawImage(ingredient, x, y, width, height, null) ;
    }

    public void setIsAnimated(boolean isAnimated)
    {
        mIsAnimated = isAnimated ;
        mIsBlurred = isAnimated ;
    }
}