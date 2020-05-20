package azul.model.move;

import azul.model.Game;
import azul.model.player.Player;
import azul.model.tile.Tile;
import azul.model.tile.TilesFactory;

import java.util.ArrayList;

public class TakeInFactory extends Move
{
    private Player mPlayer ;
    private ArrayList<Tile> mTilesSelected ;
    private TilesFactory mFactory ;
    private ArrayList<Tile> mFactoryTiles ;

    /**
     * When user takes tiles in the factory.
     */
    public TakeInFactory(Player player, ArrayList<Tile> tilesSelected, TilesFactory factory,
                         ArrayList<Tile> factoryTiles)
    {
        mPlayer = player ;
        mTilesSelected = tilesSelected ;
        mFactory = factory ;
        mFactoryTiles = factoryTiles ;
    }

    @Override
    public void undo(Game game)
    {
        // Restore the factory tiles.
        mFactory.setTiles((ArrayList<Tile>) mFactoryTiles.clone()) ;
        // Restore the tiles in the table.
        int i = 1 ;

        while (game.getTilesTable().get(i ++) != Tile.EMPTY) ;

        for (int j = 4 - mTilesSelected.size() + 1 ; j >= 0 ; j --)
        {
            if (i - j >= 0)
            {
                game.getTilesTable().set(i- j, Tile.EMPTY) ;
            }
        }
        // Clean the player.
        mPlayer.clearTilesSelected() ;
        // Change the game state.
        game.setState(Game.State.CHOOSE_TILES) ;
    }

    @Override
    public void do_(Game game)
    {
        mPlayer.takeTilesFromFactory(this, game.getTilesTable()) ;

        game.setState(Game.State.SELECT_ROW) ;

        if (mTilesSelected.get(0) == Tile.FIRST_PLAYER_MAKER && mTilesSelected.size() == 1)
        {
            // User chosen the token, needs to be automatically placed on the floor line.
            game.nextPlayer() ;
        }
    }

    @Override
    public void redo(Game game)
    {
        // Get all the factory tiles of this color.
        mFactory.take(mTilesSelected.get(0)) ;

        do_(game) ;
    }

    public ArrayList<Tile> getFactoryTiles()
    {
        return mFactoryTiles ;
    }

    public ArrayList<Tile> getTilesSelected()
    {
        return mTilesSelected ;
    }

    public TilesFactory getFactory()
    {
        return mFactory ;
    }
}