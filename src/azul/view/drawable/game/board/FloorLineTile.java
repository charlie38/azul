package azul.view.drawable.game.board;

import azul.model.move.Move;
import azul.model.move.TakeOnTable;
import azul.model.tile.Tile;
import azul.view.Display;
import azul.view.drawable.Drawable;

import java.awt.*;
import java.awt.event.ActionListener;

public class FloorLineTile extends Drawable
{
    // Request a select animation.
    private static final int ANIMATION_DELAY = 200 ;

    // Player index in the model representation.
    private int mPlayerIndex ;
    // Tile index in the floor lines graphical and model representations.
    private int mIndex ;
    // True if the current tile image is selected.
    private boolean mIsSelected ;

    /**
     * A player floor tile graphical representation.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     * @param playerIndex index in the model representation of the player.
     * @param index index in the model representation, and X delta in the view representation.
     */
    public FloorLineTile(Display display, int originalX, int originalY, int playerIndex, int index)
    {
        super(display, originalX, originalY, PlayerBoard.WIDTH_FL_TILE, PlayerBoard.HEIGHT_FL_TILE, ANIMATION_DELAY) ;

        mPlayerIndex = playerIndex ;
        mIndex = index ;
        mIsSelected = false ;
    }

    @Override
    public void onAnimationStarts()
    {
        super.onAnimationStarts() ;
        mIsSelected = true ;
    }

    @Override
    public ActionListener onAnimationChanged()
    {
        return actionEvent ->
        {
            if (mIsAnimated)
            {
                mIsSelected = ! mIsSelected ;
            }
        } ;
    }

    @Override
    public void onAnimationEnds()
    {
        mIsSelected = false ;
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

        Image ingredient ;
        Move move = null ;

        if (getGame().getHistory().canUndo())
        {
            move = getGame().getHistory().getPrevious() ;
        }
        // If a 'Take in table' move was played and it was the first, doesn't draw the '1' token.
        if (move instanceof TakeOnTable && ((TakeOnTable) move).isFirstToTakeFromTable()
                && getGame().getPlayer(mPlayerIndex).getInFloorLine(mIndex) == Tile.FIRST_PLAYER_MAKER
                && ! (((TakeOnTable) move).getTilesSelected().size() ==  1
                && ((TakeOnTable) move).getTilesSelected().get(0) == Tile.FIRST_PLAYER_MAKER))
        {
            ingredient = getResourcesLoader().getIngredient(Tile.EMPTY) ;
        }
        else
        {
            ingredient = mIsSelected ?
                    getResourcesLoader().getIngredient(getGame().getPlayer(mPlayerIndex).getInFloorLine(mIndex)) :
                    getResourcesLoader().getIngredientSelected(getGame().getPlayer(mPlayerIndex).getInFloorLine(mIndex)) ;
        }

        g.drawImage(ingredient, x, y, width, height, null) ;
    }

    public int getColumnIndex()
    {
        return mIndex ;
    }
}