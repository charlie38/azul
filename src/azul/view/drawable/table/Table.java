package azul.view.drawable.table;

import azul.view.Display;
import azul.view.drawable.Drawable;

import java.awt.*;

public class Table extends Drawable
{
    // /!\ Change all table components size.
    public static final float SIZE_COEF = 0.9f ;
    // Size of bg tiles.
    public static final int WIDTH_TABLE = (int) (305 * SIZE_COEF) ;
    public static final int HEIGHT_TABLE = (int) (345 * SIZE_COEF) ;

    /**
     * The remaining tiles background.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     */
    public Table(Display display, int originalX, int originalY)
    {
        super(display, originalX, originalY, WIDTH_TABLE, HEIGHT_TABLE) ;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g) ;

        Point point = computeCoef() ;

        paintBg(g, point.x, point.y) ;
    }

    private void paintBg(Graphics g, int x, int y)
    {
        Image img = getResourcesLoader().getTable() ;
        int width = (int) (WIDTH_TABLE * mCoef) ;
        int height = (int) (HEIGHT_TABLE * mCoef) ;

        g.drawImage(img, x, y, width, height, null) ;
    }
}