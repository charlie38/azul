package azul.view.drawable;

import azul.view.Display;

import java.awt.*;

public class PatternLineTile extends Drawable
{
    // Tile indexes in the pattern lines graphical and model representations.
    private int mIndexI ;
    private int mIndexJ ;

    /**
     * A player pattern tile graphical representation.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     * @param indexI index in the model representation, and Y delta in the view representation of the wall.
     * @param indexJ index in the model representation, and X delta in the view representation of the wall.
     */
    public PatternLineTile(Display display, int originalX, int originalY, int indexI, int indexJ)
    {
        super(display, originalX, originalY, PlayerBoard.WIDTH_PL_TILE, PlayerBoard.HEIGHT_PL_TILE) ;

        mIndexI = indexI ;
        mIndexJ = indexJ ;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g) ;

        Point point = computeCoef() ;
        float x = point.x ;
        float y = point.y ;

        paintTile(g, (int) (x + PlayerBoard.DISTANCE_LEFT_TO_PL * mCoef),
                (int) (y + PlayerBoard.DISTANCE_TOP_TO_PL * mCoef)) ;
    }

    public void paintTile(Graphics g, int x, int y)
    {
        int width = (int) (mOriginalWidth * mCoef) ;
        int height = (int) (mOriginalHeight * mCoef) ;

        Image img = getResourcesLoader().getIngredient(getPlayer().getInPatternLines(mIndexI, mIndexJ)) ;

        g.drawImage(img,
                x + (azul.model.player.PlayerBoard.SIZE_PATTERN_LINES - 1 - mIndexJ)
                        * (int) (width + PlayerBoard.SPACE_H_PL_TILE * mCoef),
                y + mIndexI * (int) (height + PlayerBoard.SPACE_V_PL_TILE * mCoef),
                width, height,
                null) ;
    }

    @Override
    public boolean isClicked(int x, int y)
    {
        int currentWidth =  (int) (mOriginalWidth * mCoef) ;
        int currentHeight =  (int) (mOriginalHeight * mCoef) ;
        int currentX = (int) (x + PlayerBoard.DISTANCE_LEFT_TO_PL * mCoef)
                + (azul.model.player.PlayerBoard.SIZE_PATTERN_LINES - 1 - mIndexJ)
                * (int) (currentWidth + PlayerBoard.SPACE_H_PL_TILE * mCoef) ;
        int currentY = (int) (y + PlayerBoard.DISTANCE_TOP_TO_PL * mCoef)
                + mIndexI * (int) (currentHeight + PlayerBoard.SPACE_V_PL_TILE * mCoef) ;

        return x > currentX && x < currentX + currentWidth && y > currentY && y < currentY + currentHeight ;
    }
}