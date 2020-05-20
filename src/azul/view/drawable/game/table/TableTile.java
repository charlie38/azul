package azul.view.drawable.game.table;

import azul.model.move.Move;
import azul.model.move.TakeInFactory;
import azul.model.move.TakeOnTable;
import azul.model.tile.Tile;
import azul.model.tile.TilesFactory;
import azul.view.Display;
import azul.view.drawable.Drawable;

import java.awt.*;
import java.awt.event.ActionListener;

public class TableTile extends Drawable
{
    // Request a focus animation.
    private static final int ANIMATION_DELAY = 350 ;
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

        Move move = null ;

        if (getGame().getHistory().canUndo())
        {
            move = getGame().getHistory().getPrevious() ;
        }
        // If a 'Take in table' move was played.
        if (move instanceof TakeOnTable)
        {
            paintTileOnTakeOnTableMove(g, x, y, width, height, (TakeOnTable) move) ;
        }
        else if (move instanceof TakeInFactory)
        {
            paintTileOnTakeInFactoryMove(g, x, y, width, height, (TakeInFactory) move) ;
        }
        else
        {
            paintTile(g, x, y, width, height) ;
        }
    }

    private void paintTile(Graphics g, int x, int y, int width, int height)
    {
        if (getGame().getInTilesTable(mIndex) != Tile.EMPTY)
        {
            paintBackground(g, x, y, width, height) ;

            Image ingredient = ANIMATION_PATTERN[mAnimationIndex] ?
                    getResourcesLoader().getIngredientSelected(getGame().getInTilesTable(mIndex)) :
                    getResourcesLoader().getIngredient(getGame().getInTilesTable(mIndex)) ;

            g.drawImage(ingredient, x, y, width, height, null) ;
        }
    }

    private void paintTileOnTakeOnTableMove(Graphics g, int x, int y, int width, int height, TakeOnTable move)
    {
        Image ingredient ;

        if (move.getTilesSelected().size() ==  1 && move.getTilesSelected().get(0) == Tile.FIRST_PLAYER_MAKER)
        {
            ingredient = getResourcesLoader().getIngredientBlurred(getGame().getInTilesTable(mIndex)) ;
        }
        else if (move.isFirstToTakeFromTable() && move.getTilesTable().get(mIndex) == Tile.FIRST_PLAYER_MAKER)
        {
            paintBackground(g, x, y, width, height) ;
            ingredient = getResourcesLoader().getIngredientSelected(Tile.FIRST_PLAYER_MAKER) ;
        }
        else if (move.getTilesSelected().contains(move.getTilesTable().get(mIndex)))
        {
            paintBackground(g, x, y, width, height) ;
            ingredient = getResourcesLoader().getIngredientSelected(move.getTilesTable().get(mIndex)) ;
        }
        else
        {
            ingredient = getResourcesLoader().getIngredientBlurred(getGame().getInTilesTable(mIndex)) ;
        }

        g.drawImage(ingredient, x, y, width, height, null) ;
    }

    private void paintTileOnTakeInFactoryMove(Graphics g, int x, int y, int width, int height, TakeInFactory move)
    {
        int delta = TilesFactory.SIZE_FACTORY - move.getTilesSelected().size() ;
        int tableSize = getGame().getTableSize() ;
        // Doesn't draw the tile if coming right from the factory.
        if (! (delta != 0 && mIndex >= tableSize - delta && ! move.getTilesSelected()
                .contains(getGame().getInTilesTable(mIndex))))
        {
            Image ingredient = getResourcesLoader().getIngredientBlurred(getGame().getInTilesTable(mIndex)) ;

            g.drawImage(ingredient, x, y, width, height, null) ;
        }
    }

    private void paintBackground(Graphics g, int x, int y, int width, int height)
    {
        Image bg = ANIMATION_PATTERN[mAnimationIndex] ?
                getResourcesLoader().getFactoryCaseSelected()
                : getResourcesLoader().getFactoryCase() ;

        g.drawImage(bg, x, y, width, height, null) ;
    }

    public int getTileIndex()
    {
        return mIndex ;
    }
}