package azul.model.move;

import azul.model.Game;
import azul.model.player.Player;
import azul.model.tile.Tile;

import java.util.ArrayList;

public class TakeOnTable extends Move
{
    private Player mPlayer ;
    private ArrayList<Tile> mTilesSelected ;
    private ArrayList<Tile> mTableTiles ;
    private boolean mIsFirstToTakeFromTable ;
    private ArrayList<Tile> mFloorLine ;

    /**
     * When user takes tiles from the table.
     */
    public TakeOnTable(Player player, ArrayList<Tile> tilesSelected, boolean isFirstToTakeFromTable,
                       ArrayList<Tile> tableTiles)
    {
        mPlayer = player ;
        mTilesSelected = tilesSelected ;
        mIsFirstToTakeFromTable = isFirstToTakeFromTable ;
        mTableTiles = (ArrayList<Tile>) tableTiles.clone();
        mFloorLine = (ArrayList<Tile>) mPlayer.getFloorLine().clone() ;
    }

    @Override
    public void undo(Game game)
    {
        // Restore the table tiles.
        game.setTilesTable((ArrayList<Tile>) mTableTiles.clone()) ;
        // If the first to take from the table/take the token.
        if (mIsFirstToTakeFromTable)
        {
            if (mTilesSelected.get(0) == Tile.FIRST_PLAYER_MAKER && mTilesSelected.size() == 1)
            {
                // Go to the previous player.
                game.previousPlayer() ;
            }
            // Restore the floor line.
            mPlayer.setFloorLine(mFloorLine) ;
            // Initialize the token.
            Tile.onRoundStart() ;
        }
        // Clean the player.
        mPlayer.clearTilesSelected() ;
        // Change the game state.
        game.setState(Game.State.CHOOSE_TILES) ;
    }

    @Override
    public void do_(Game game)
    {
        mPlayer.takeTilesFromTable(this, game.getTilesTable(), game.getTilesAside()) ;
        // Change the game state.
        game.setState(Game.State.SELECT_ROW) ;

        if (mTilesSelected.get(0) == Tile.FIRST_PLAYER_MAKER && mTilesSelected.size() == 1)
        {
            // User chosen the token, needs to be automatically placed on the floor line.
            game.nextPlayer() ;
            // Change the game state.
            game.setState(Game.State.CHOOSE_TILES) ;
        }
    }

    @Override
    public void redo(Game game)
    {
        // Get all the table tiles of this color.
        game.takeOnTable(mTilesSelected.get(0)) ;

        do_(game) ;
    }

    public boolean isFirstToTakeFromTable()
    {
        return mIsFirstToTakeFromTable ;
    }

    public ArrayList<Tile> getTilesSelected()
    {
        return mTilesSelected ;
    }

    public ArrayList<Tile> getTilesTable()
    {
        return mTableTiles ;
    }
}
