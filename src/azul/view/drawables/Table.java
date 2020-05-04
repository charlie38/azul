package azul.view.drawables;

import azul.view.Display;

import java.awt.*;

public class Table extends Drawable
{
    // Size of bg tiles.
    public static final int WIDTH_TABLE = (int) (446 * Display.SIZE_COEF) ;
    public static final int HEIGHT_TABLE = (int) (305 * Display.SIZE_COEF) ;

    /**
     * The bowls background.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     */
    public Table(Display display, int originalX, int originalY)
    {
        super(display, originalX, originalY) ;
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
        Image img = getImageLoader().getTable() ;
        int width = (int) (WIDTH_TABLE * mCoef) ;
        int height = (int) (HEIGHT_TABLE * mCoef) ;

        g.drawImage(img,
                x, y,
                width, height,
                null) ;
    }
}