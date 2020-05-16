package azul.view.drawable.game.table;

import azul.model.move.Move;
import azul.model.move.TakeInFactory;
import azul.model.move.TakeOnTable;
import azul.model.tile.Tile;
import azul.view.Display;
import azul.view.drawable.Drawable;

import java.awt.*;
import java.awt.event.ActionListener;

public class TableTile extends Drawable
{
    // Request a focus animation.
    private static final int ANIMATION_DELAY = 100 ;
    private final boolean[] ANIMATION_PATTERN = { true, false, true,
            false, false, false, false, false, false, false, false, false, false, false } ;

    // Tile index in the table model representations.
    private int mIndex ;
    // Current part of the animation.
    private int mAnimationIndex ;

    /**
     * A table tile representation.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     * @param index index in the model representation.
     */
    public TableTile(Display display, int originalX, int originalY, int index)
    {
        super(display, originalX, originalY, Table.WIDTH_TILE, Table.HEIGHT_TILE, ANIMATION_DELAY) ;

        mIndex = index ;
        mAnimationIndex = ANIMATION_PATTERN.length - 1 ;
    }

    @Override
    public void onAnimationStarts()
    {
        super.onAnimationStarts() ;

        mAnimationIndex = 0 ;
    }

    @Override
    public ActionListener onAnimationChanged()
    {
        return actionEvent ->
        {
            if (mIsAnimated)
            {
                mAnimationIndex = mAnimationIndex == ANIMATION_PATTERN.length - 1 ? 0 : mAnimationIndex + 1 ;
            }
        } ;
    }

    @Override
    public void onAnimationEnds()
    {
        mAnimationIndex = ANIMATION_PATTERN.length - 1 ;
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

        Image ingredient = ANIMATION_PATTERN[mAnimationIndex] ?
                getResourcesLoader().getIngredientSelected(getGame().getInTilesTable(mIndex)) :
                getResourcesLoader().getIngredient(getGame().getInTilesTable(mIndex)) ;

        g.drawImage(ingredient, x, y, width, height, null) ;

        paintOnTakeMove(g, x, y, width, height) ;
    }

    /**
     * Add an animation when user took a tile from the table.
     */
    private void paintOnTakeMove(Graphics g, int x, int y, int width, int height)
    {
        Move move = null ;

        if (getGame().getHistory().canUndo())
        {
            move = getGame().getHistory().getPrevious() ;
        }
        // If a move was played.
        if (move instanceof TakeOnTable)
        {
            // If wants to take this tile, select it.
            if (((TakeOnTable) move).isFirstToTakeFromTable()
                    && ((TakeOnTable) move).getTilesTable().get(mIndex) == Tile.FIRST_PLAYER_MAKER)
            {
                if (((TakeOnTable) move).getTilesSelected().size() != 1)
                {
                    Image ingredient = getResourcesLoader().getIngredientSelected(Tile.FIRST_PLAYER_MAKER) ;
                    g.drawImage(ingredient, x, y, width, height, null) ;
                }
            }
            else if (((TakeOnTable) move).getTilesSelected().contains(((TakeOnTable) move).getTilesTable().get(mIndex)))
            {
                Image ingredient = getResourcesLoader().getIngredientSelected(((TakeOnTable) move).getTilesTable().get(mIndex)) ;
                g.drawImage(ingredient, x, y, width, height, null) ;
            }
        }
    }

    public int getTileIndex()
    {
        return mIndex ;
    }
}