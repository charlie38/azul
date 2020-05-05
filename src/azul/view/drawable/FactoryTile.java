package azul.view.drawable;

import azul.view.Display;

import java.awt.*;

public class FactoryTile extends Drawable
{
    // Tile index in graphical representations.
    private int mIndexI, mIndexJ ;
    // Tile index in model representations.
    private int mIndex ;

    /**
     * A player wall tile graphical representation.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     * @param indexI Y delta in the view representation of the factory.
     * @param indexJ X delta in the view representation of the wall.
     * @param index index in the model representation.
     */
    public FactoryTile(Display display, int originalX, int originalY, int indexI, int indexJ, int index)
    {
        super(display, originalX, originalY, TilesFactory.WIDTH_TILE, TilesFactory.HEIGHT_TILE) ;

        mIndexI = indexI ;
        mIndexJ = indexJ ;
        mIndex = index ;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g) ;

        Point point = computeCoef() ;
        float x = point.x ;
        float y = point.y ;

        paintTile(g, (int) (x + TilesFactory.DISTANCE_LEFT_TO_TILE * mCoef),
                (int) (y + TilesFactory.DISTANCE_TOP_TO_TILE * mCoef)) ;
    }

    public void paintTile(Graphics g, int x, int y)
    {
        int width = (int) (mOriginalWidth * mCoef) ;
        int height = (int) (mOriginalHeight * mCoef) ;

        Image img = getResourcesLoader().getIngredient(getGame().getFactory(mIndex).getTile(mIndex)) ;

        g.drawImage(img,
                x + mIndexJ * (int) (width + TilesFactory.SPACE_H_TILE * mCoef),
                y + mIndexI * (int) (height + TilesFactory.SPACE_V_TILE * mCoef),
                width, height,
                null) ;
    }

    @Override
    public boolean isClicked(int x, int y)
    {
        int currentWidth =  (int) (mOriginalWidth * mCoef) ;
        int currentHeight =  (int) (mOriginalHeight * mCoef) ;
        int currentX = (int) (x + TilesFactory.DISTANCE_LEFT_TO_TILE * mCoef)
                + mIndexJ * (int) (currentWidth + TilesFactory.SPACE_H_TILE * mCoef) ;
        int currentY = (int) (y + TilesFactory.DISTANCE_TOP_TO_TILE * mCoef)
                + mIndexI * (int) (currentHeight + TilesFactory.SPACE_V_TILE * mCoef) ;

        return x > currentX && x < currentX + currentWidth && y > currentY && y < currentY + currentHeight ;
    }
}