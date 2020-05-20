package azul.model.move;

import azul.model.Game;
import azul.model.player.Player;
import azul.model.tile.Tile;

import java.util.ArrayList;

public class ChoosePatternLine extends Move
{
    private Player mPlayer ;
    private int mRow ;
    private Tile[] mPatternLine ;
    private ArrayList<Tile> mFloorLine ;
    private ArrayList<Tile> mRemaining ;
    private ArrayList<Tile> mAside ;

    /**
     * When user put his tiles in a pattern line.
     */
    public ChoosePatternLine(Player player, ArrayList<Tile> aside, ArrayList<Tile> remaining, int rowIndex)
    {
        mPlayer = player ;
        mRow = rowIndex ;
        mPatternLine = player.getPatternLine(rowIndex + 1).clone() ;
        mFloorLine = (ArrayList<Tile>) player.getFloorLine().clone() ;
        mAside = (ArrayList<Tile>) aside.clone() ;
        mRemaining = (ArrayList<Tile>) remaining.clone() ;
    }

    @Override
    public void undo(Game game)
    {
        game.setTilesAside((ArrayList<Tile>) mAside.clone()) ;
        game.setTilesRemaining((ArrayList<Tile>) mRemaining.clone()) ;
        // Go to the previous player.
        game.previousPlayer() ;
        // Restore the pattern line.
        mPlayer.setPatternLine(mRow + 1, mPatternLine) ;
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
        mPlayer.addTilesInPattern(this, game.getTilesAside()) ;

        if (game.isRoundOver())
        {
            game.setState(Game.State.DECORATE_WALL) ;

            if (! game.getHistory().canRedo())
            {
                game.playMove(new DecorateWall(game.getPlayers(), game.getFactories(), game.getTilesTable(),
                        game.getTilesAside(), game.getTilesRemaining(), game.getPlayerIndex())) ;
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

    public int getRow()
    {
        return mRow ;
    }
}
