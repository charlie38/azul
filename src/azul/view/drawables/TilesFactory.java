package azul.view.drawables;

import azul.view.Display;

import java.awt.*;

public class TilesFactory extends Drawable
{
    // Factory bg.
    public static final int WIDTH_FACTORY = (int) (100 * Display.SIZE_COEF) ;
    public static final int HEIGHT_FACTORY = (int) (70 * Display.SIZE_COEF) ;
    // Cases and tiles.
    public static final int DISTANCE_LEFT_TO_TILE = (int) (20 * Display.SIZE_COEF) ;
    public static final int DISTANCE_TOP_TO_TILE = (int) (10 * Display.SIZE_COEF) ;
    public static final int WIDTH_TILE = (int) (22 * Display.SIZE_COEF) ;
    public static final int HEIGHT_TILE = (int) (22 * Display.SIZE_COEF) ;
    public static final int SPACE_H_TILE = (WIDTH_FACTORY - (2 * WIDTH_TILE) - (2 * DISTANCE_LEFT_TO_TILE)) ;
    public static final int SPACE_V_TILE = (HEIGHT_FACTORY - (2 * HEIGHT_TILE) - (2 * DISTANCE_TOP_TO_TILE)) ;

    // Factory index in the game 'model' list.
    private int mIndex ;

    /**
     * A tiles factory graphical representation.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     */
    public TilesFactory(Display display, int originalX, int originalY, int index)
    {
        super(display, originalX, originalY) ;

        mIndex = index ;
	}

	public void paint(Graphics g)
    {
        super.paint(g) ;

        Point point = computeCoef() ;
        float x = point.x ;
        float y = point.y ;

        paintBg(g, (int) x, (int) y) ;
        paintCases(g, (int) (x + DISTANCE_LEFT_TO_TILE * mCoef), (int) (y + DISTANCE_TOP_TO_TILE * mCoef)) ;
        paintTiles(g, (int) (x + DISTANCE_LEFT_TO_TILE * mCoef), (int) (y + DISTANCE_TOP_TO_TILE * mCoef)) ;
    }

    public void paintBg(Graphics g, int x, int y)
    {
        Image img = getImageLoader().getFactory() ;
        int width = (int) (WIDTH_FACTORY * mCoef) ;
        int height = (int) (HEIGHT_FACTORY * mCoef) ;

        g.drawImage(img,
                x, y,
                width, height,
                null) ;
    }

    public void paintCases(Graphics g, int x, int y)
    {
        Image img = getImageLoader().getFactoryCase() ;
        int width = (int) (WIDTH_TILE * mCoef) ;
        int height = (int) (HEIGHT_TILE * mCoef) ;

        for (int i = 0 ; i < 2 ; i ++)
        {
            for (int j = 0 ; j < 2 ; j ++)
            {
                g.drawImage(img,
                        x + j * (int) (width + SPACE_H_TILE * mCoef),
                        y + i * (int) (height + SPACE_V_TILE * mCoef),
                        width, height,
                        null) ;
            }
        }
    }

    public void paintTiles(Graphics g, int x, int y)
    {
        int width = (int) (WIDTH_TILE * mCoef) ;
        int height = (int) (HEIGHT_TILE * mCoef) ;
        int tile = 0 ;

        for (int i = 0 ; i < 2 ; i ++)
        {
            for (int j = 0 ; j < 2 ; j ++)
            {
                g.drawImage(getImageLoader().getIngredient(getGame().getFactory(mIndex).getTile(tile ++)),
                        x + j * (int) (width + SPACE_H_TILE * mCoef),
                        y + i * (int) (height + SPACE_V_TILE * mCoef),
                        width, height,
                        null) ;
            }
        }
    }
}