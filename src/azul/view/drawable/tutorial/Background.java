package azul.view.drawable.tutorial;

import azul.view.Display;
import azul.view.drawable.Drawable;

import java.awt.*;
import java.awt.event.ActionListener;

public class Background extends Drawable
{
    // /!\ Change all background components size.
    public static final float SIZE_COEF = 2.5f ;
    // Size of bg tiles.
    public static final int WIDTH_TILE = (int) (72 * SIZE_COEF) ;
    public static final int HEIGHT_TILE = (int) (72 * SIZE_COEF) ;

    /**
     * The game bg (formed with a background tile)) coordinates not needed.
     * <!> Special drawable. Width, height and coordinates are not corresponding. </!>
     * @param display root.
     */
    public Background(Display display)
    {
        super(display, -1, -1, WIDTH_TILE, HEIGHT_TILE, Drawable.DEFAULT_ANIMATION_DELAY) ;
    }

    @Override
    protected void onAnimationStarts()
    {
        super.onAnimationStarts() ;
    }

    @Override
    protected ActionListener onAnimationChanged() { return null ; }

    @Override
    protected void onAnimationEnds() { }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g) ;

        computeCoef() ;

        paintBg(g) ;
    }

    private void paintBg(Graphics g)
    {
        Image img = getResourcesLoader().getBgTile() ;
        int width = (int) (mOriginalWidth * mCoef) ;
        int height = (int) (mOriginalHeight * mCoef) ;

        for (int i = 0 ; i < getDrawingPanel().getWidth() ; i += width)
        {
            for (int j = 0 ; j < getDrawingPanel().getHeight() ; j += height)
            {
                g.drawImage(img, i, j, width, height, null) ;
            }
        }
    }
}