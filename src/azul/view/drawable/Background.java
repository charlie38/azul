package azul.view.drawable;

import azul.view.Display;

import java.awt.*;

public class Background extends Drawable
{
    // Size of bg tiles.
    public static final int WIDTH_TILE = (int) (72 * Display.SIZE_COEF) ;
    public static final int HEIGHT_TILE = (int) (72 * Display.SIZE_COEF) ;

    /**
     * The game bg (formed with a background tile)) coordinates not needed.
     * @param display root.
     */
    public Background(Display display)
    {
        super(display, -1, -1, WIDTH_TILE, HEIGHT_TILE) ;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g) ;

        Point point = computeCoef() ;
        float x = point.x ;
        float y = point.y ;

        paintBg(g, (int) x, (int) y) ;
    }

    public void paintBg(Graphics g, int x, int y)
    {
        Image img = getResourcesLoader().getBgTile() ;
        int width = (int) (WIDTH_TILE * mCoef) ;
        int height = (int) (HEIGHT_TILE * mCoef) ;

        for (int i = 0 ; i < getDisplay().getWindowWidth() ; i += width)
        {
            for (int j = 0 ; j < getDisplay().getWindowHeight() ; j += height)
            {
                g.drawImage(img,
                        i, j,
                        width, height,
                        null) ;
            }
        }
    }
}