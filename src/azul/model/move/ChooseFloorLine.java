package azul.model.move;

import azul.model.Game;
import azul.model.player.Player;
import azul.model.tile.Tile;

import java.util.ArrayList;

public class ChooseFloorLine extends Move
{
    private Player mPlayer ;
    private ArrayList<Tile> mFloorLine ;

    /**
     * When user put his tiles in the floor line.
     */
    public ChooseFloorLine(Player player)
    {
        mPlayer = player ;
        mFloorLine = (ArrayList<Tile>) mPlayer.getFloorLine().clone() ;
    }

    @Override
    public void undo(Game game)
    {
        // Go to the previous player.
        game.previousPlayer() ;
        // Restore the floor line.
        mPlayer.setFloorLine(mFloorLine) ;
        // Restore the tiles selected.
        Move previous = game.getHistory().getPrevious() ;

        if (previous instanceof TakeInFactory)
        {
            mPlayer.setTilesSelected(((TakeInFactory) game.getHistory().getPrevious()).getTilesSelected()) ;
        }
        else if (previous instanceof TakeOnTable)
        {
            mPlayer.setTilesSelected(((TakeOnTable) game.getHistory().getPrevious()).getTilesSelected()) ;
        }
        // Change the game state.
        game.setState(Game.State.SELECT_ROW) ;
    }

    @Override
    public void do_(Game game)
    {
        mPlayer.addTilesInFloor(this, game.getTilesAside()) ;

        // Check if game over, or round over.
        if (game.isGameOver())
        {
            game.playMove(new DecorateWall(game.getPlayers(), game.getFactories(), game.getTilesTable(),
                    game.getPlayerIndex())) ;
            game.setState(Game.State.GAME_OVER) ;
        }
        else if (game.isRoundOver())
        {
            game.setState(Game.State.DECORATE_WALL) ;

            if (! game.getHistory().canRedo())
            {
                game.playMove(new DecorateWall(game.getPlayers(), game.getFactories(), game.getTilesTable(),
                        game.getPlayerIndex())) ;

                game.prepareForRound() ;
            }
        }
        else
        {
            game.setState(Game.State.CHOOSE_TILES) ;
            game.nextPlayer() ;
        }
    }

    @Override
    public void redo(Game game)
    {
        do_(game) ;
    }
}
