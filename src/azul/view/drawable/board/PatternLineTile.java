package azul.view.drawable.board;

import azul.view.Display;
import azul.view.drawable.Drawable;

import java.awt.*;

public class PatternLineTile extends Drawable
{
    // Player index in the model representation.
    private int mPlayerIndex ;
    // Tile indexes in the pattern lines graphical and model representations.
    private int mIndexI ;
    private int mIndexJ ;

    /**
     * A player pattern tile graphical representation.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     * @param playerIndex index in the model representation of the player.
     * @param indexI index in the model representation, and Y delta in the view representation of the wall.
     * @param indexJ index in the model representation, and X delta in the view representation of the wall.
     */
    public PatternLineTile(Display display, int originalX, int originalY, int playerIndex, int indexI, int indexJ)
    {
        super(display, originalX, originalY, PlayerBoard.WIDTH_PL_TILE, PlayerBoard.HEIGHT_PL_TILE) ;

        mPlayerIndex = playerIndex ;
        mIndexI = indexI ;
        mIndexJ = indexJ ;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g) ;

        Point point = computeCoef() ;

        paintTile(g, point.x, point.y) ;
    }

    private void paintTile(Graphics g, int x, int y)
    {
        int width = (int) (mOriginalWidth * mCoef) ;
        int height = (int) (mOriginalHeight * mCoef) ;

        Image bg = getResourcesLoader().getPatternLinesCase() ;
        Image ingredient = getResourcesLoader().getIngredient(getPlayer(mPlayerIndex).getInPatternLines(mIndexI, mIndexJ)) ;

        g.drawImage(bg, x, y, width, height, null) ;
        g.drawImage(ingredient, x, y, width, height, null) ;
    }
}