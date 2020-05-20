package azul.view.drawable.game.factory;

import azul.model.move.Move;
import azul.model.move.TakeInFactory;
import azul.model.move.TakeOnTable;
import azul.model.tile.Tile;
import azul.view.Display;
import azul.view.drawable.Drawable;

import java.awt.*;
import java.awt.event.ActionListener;

public class FactoryTile extends Drawable
{
    // Request a focus animation.
    private static final int ANIMATION_DELAY = 350 ;
    private final boolean[] ANIMATION_PATTERN = { true, false, true,
            false, false, false, false, false, false, false, false, false, false, false } ;

    // Tile index in model representations.
    private int mFactoryIndex ;
    private int mIndex ;
    // Current part of the animation.
    private int mAnimationIndex ;

    /**
     * A player wall tile graphical representation.
     * @param display root.
     * @param originalX coordinate relative to : center of screen = (0, 0)
     * @param originalY coordinate relative to : center of screen = (0, 0)
     * @param factoryIndex index of its container in the model representation.
     * @param index index in the model representation.
     */
    public FactoryTile(Display display, int originalX, int originalY, int factoryIndex, int index)
    {
        super(display, originalX, originalY, TilesFactory.WIDTH_TILE, TilesFactory.HEIGHT_TILE, ANIMATION_DELAY) ;

        mFactoryIndex = factoryIndex ;
        mIndex = index ;
        mAnimationIndex = ANIMATION_PATTERN.length - 1 ;
    }

    @Override
    protected void onAnimationStarts()
    {
        super.onAnimationStarts() ;
        mAnimationIndex = 0 ;
    }

    @Override
    protected ActionListener onAnimationChanged()
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
    protected void onAnimationEnds()
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
        // If a 'Take in factory' move was played.
        if (move instanceof TakeInFactory)
        {
            paintTileOnTakeInFactory(g, x, y, width, height, (TakeInFactory) move) ;
        }
        else if (move instanceof TakeOnTable)
        {
            paintTileOnTakeOnTableMove(g, x, y, width, height, (TakeOnTable) move) ;
        }
        else
        {
            paintTile(g, x, y, width, height) ;
        }
    }

    private void paintTile(Graphics g, int x, int y, int width, int height)
    {
        paintBackground(g, x, y, width, height) ;

        Image ingredient = ANIMATION_PATTERN[mAnimationIndex] ?
                getResourcesLoader().getIngredientSelected(getGame().getFactory(mFactoryIndex).getTile(mIndex)) :
                getResourcesLoader().getIngredient(getGame().getFactory(mFactoryIndex).getTile(mIndex)) ;

        g.drawImage(ingredient, x, y, width, height, null) ;
    }

    private void paintTileOnTakeInFactory(Graphics g, int x, int y, int width, int height, TakeInFactory move)
    {
        Image ingredient ;
        // If the factory of the move is this one.
        if (move.getFactory() == getGame().getFactory(mFactoryIndex))
        {
            if (move.getTilesSelected().get(0) == move.getFactoryTiles().get(mIndex))
            {
                paintBackground(g, x, y, width, height) ;
                // If this tile was selected, draw it with a border.
                ingredient = getResourcesLoader().getIngredientSelected(move.getFactoryTiles().get(mIndex)) ;
            }
            else
            {
                // Else blurred (it will go to the table if the player continues).
                ingredient = getResourcesLoader().getIngredientBlurred(move.getFactoryTiles().get(mIndex)) ;
            }
        }
        else
        {
            ingredient = getResourcesLoader().getIngredientBlurred(getGame().getFactory(mFactoryIndex).getTile(mIndex)) ;
        }

        g.drawImage(ingredient, x, y, width, height, null) ;
    }

    private void paintTileOnTakeOnTableMove(Graphics g, int x, int y, int width, int height, TakeOnTable move)
    {
        if (move.getTilesSelected().size() ==  1 && move.getTilesSelected().get(0) == Tile.FIRST_PLAYER_MAKER)
        {
            paintTile(g, x, y, width, height) ;
        }
        else
        {
            Image ingredient = getResourcesLoader().getIngredientBlurred(getGame().getFactory(mFactoryIndex).getTile(mIndex)) ;

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

    public int getFactoryIndex()
    {
        return mFactoryIndex ;
    }

    public int getTileIndex()
    {
        return mIndex ;
    }
}