package azul.controller.ia.easy;

import azul.controller.ia.IA;
import azul.model.Game;
import azul.model.move.*;
import azul.model.player.PlayerBoard;
import azul.model.tile.Tile;
import azul.model.tile.TilesFactory;

import java.util.ArrayList;
import java.util.Random;

public class IAEasy extends IA
{
    // To be unpredictable
    private Random mRandom ;
    
    // The tile type of the target tile in the wall
    private Tile mTargetTile ;

    /**
     * IA choosing random plays.
     */
    public IAEasy(Game game)
    {
        super(game) ;

        mRandom = new Random() ;
    }

    @Override
    public void initialize()
    {
    	
    }

    @Override
    public Move play()
    {
        switch (mGame.getState())
        {
            case CHOOSE_TILES : return chooseTiles() ;
            case SELECT_ROW : return selectRow() ;
            default : return null ;
        }
    }

    private Move chooseTiles()
    {
        if (mGame.isTableEmpty() || mGame.isNotInTable(mTargetTile))
        {
            return chooseInFactory() ;
        }

        if (mGame.isFactoriesEmpty())
        {
            return chooseOnTable() ;
        }

        return mRandom.nextBoolean() ? chooseInFactory() : chooseOnTable() ;
    }

    private Move selectRow()
    {
        if (! mGame.getPlayer().isPatternLinesAccessible())
        {
            return selectFloorLine() ;
        }
        // In most cases, should be in a pattern line.
        return selectPatternLine() ;
    }

    private Move chooseInFactory()
    {
        ArrayList<Integer> selectableFactories = new ArrayList<>() ;

        for (int i = 0 ; i < mGame.getFactories().size() ; i ++)
        {
            if (! mGame.getFactory(i).isEmpty())
            {
                selectableFactories.add(i) ;
            }
        }
        // Get the selected factory.
        TilesFactory factory = mGame.getFactory(selectableFactories.get(mRandom.nextInt(selectableFactories.size()))) ;
        // Get the tiles in the factory.
        ArrayList<Tile> factoryTiles = (ArrayList<Tile>) factory.getTiles().clone() ;
        // Get the selected tile.
        Tile tile = factory.getTile(mRandom.nextInt(TilesFactory.SIZE_FACTORY)) ;
        // And get all the factory tiles of this color.
        ArrayList<azul.model.tile.Tile> tilesSelected = factory.take(tile) ;
        // Play it.
        return new TakeInFactory(mGame.getPlayer(), tilesSelected, factory, factoryTiles) ;
    }

    private Move chooseOnTable()
    {

        ArrayList<Integer> selectableTiles = new ArrayList<>() ;

        for (int i = 0 ; i < Game.SIZE_TILES_TABLE ; i ++)
        {
            if (mGame.getInTilesTable(i) != Tile.EMPTY)
            {
                selectableTiles.add(i) ;
            }
        }
        // Get the tiles on the table.
        ArrayList<Tile> tableTiles = (ArrayList<Tile>) mGame.getTilesTable().clone() ;
        // Get the selected tile.
        Tile tile = mGame.getInTilesTable(selectableTiles.get(mRandom.nextInt(selectableTiles.size()))) ;
        // And get all the table tiles of this color.
        ArrayList<azul.model.tile.Tile> tilesSelected = mGame.takeOnTable(tile) ;
        // Play it.
        return new TakeOnTable(mGame.getPlayer(), tilesSelected,
                ! azul.model.tile.Tile.isFirstPlayerMakerTaken(), tableTiles) ;
    }

    private Move selectPatternLine()
    {

        ArrayList<Integer> selectableRows = new ArrayList<>() ;

        for (int i = 1 ; i <= PlayerBoard.SIZE_PATTERN_LINES ; i ++)
        {
            if (mGame.getPlayer().isPatternLineAccessible(i))
            {
                selectableRows.add(i) ;
            }
        }
        // Get the selected row.
        int row = selectableRows.get(mRandom.nextInt(selectableRows.size())) ;
        // Play it.
        return new ChoosePatternLine(mGame.getPlayer(), row - 1) ;
    }

    private Move selectFloorLine()
    {
        // Play it.
        return new ChooseFloorLine(mGame.getPlayer()) ;
    }
}