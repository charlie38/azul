package azul.model.move;

import azul.model.Game;
import azul.model.player.Player;
import azul.model.player.PlayerBoard;
import azul.model.tile.Tile;
import azul.model.tile.TilesFactory;

import java.util.ArrayList;

public class DecorateWall extends Move
{
    private ArrayList<PlayerBoard> mPlayersBoards ;
    private ArrayList<PlayerBoard> mPlayersBoardsAfter ;
    private ArrayList<ArrayList<Tile>> mFactoriesTiles ;
    private ArrayList<ArrayList<Tile>> mFactoriesTilesAfter ;
    private ArrayList<Tile> mTable ;
    private ArrayList<Tile> mTableAfter ;
    private ArrayList<Tile> mRemaining ;
    private ArrayList<Tile> mRemainingAfter ;
    private ArrayList<Tile> mAside ;
    private ArrayList<Tile> mAsideAfter ;
    private int mCurrentPlayer ;
    private int mCurrentPlayerAfter ;

    /**
     * When users decorate their wall.
     */
    public DecorateWall(ArrayList<Player> players, ArrayList<TilesFactory> factories, ArrayList<Tile> table,
                        ArrayList<Tile> aside, ArrayList<Tile> remaining, int currentPlayer)
    {
        mPlayersBoards = new ArrayList<>() ;
        mFactoriesTiles = new ArrayList<>() ;

        for (Player player : players)
        {
            mPlayersBoards.add((PlayerBoard) player.getBoard().clone()) ;
        }

        for (TilesFactory factory : factories)
        {
            mFactoriesTiles.add((ArrayList<Tile>) factory.getTiles().clone()) ;
        }

        mTable = (ArrayList<Tile>) table.clone() ;
        mAside = (ArrayList<Tile>) aside.clone();
        mRemaining = (ArrayList<Tile>) remaining.clone();
        mCurrentPlayer = currentPlayer ;
    }

    @Override
    public void undo(Game game)
    {
        mPlayersBoardsAfter = new ArrayList<>() ;
        mFactoriesTilesAfter = new ArrayList<>() ;
        // Save the new round states.
        for (Player player : game.getPlayers())
        {
            mPlayersBoardsAfter.add((PlayerBoard) player.getBoard().clone()) ;
        }

        for (TilesFactory factory : game.getFactories())
        {
            mFactoriesTilesAfter.add((ArrayList<Tile>) factory.getTiles().clone()) ;
        }

        mTableAfter = (ArrayList<Tile>) game.getTilesTable().clone() ;
        mAsideAfter = (ArrayList<Tile>) game.getTilesAside().clone() ;
        mRemainingAfter = (ArrayList<Tile>) game.getTilesRemaining().clone() ;
        mCurrentPlayerAfter = game.getPlayerIndex() ;

        game.setPlayersBoard(mPlayersBoards) ;
        game.setFactoriesTiles(mFactoriesTiles) ;
        game.setTilesTable(mTable) ;
        game.setTilesAside(mAside) ;
        game.setTilesRemaining(mRemaining) ;
        game.setCurrentPlayer(mCurrentPlayer) ;
        game.setState(Game.State.DECORATE_WALL) ;
        game.nextPlayer() ; // A 'previous' one will be called.

        Tile.setFirstPlayerMarkerTaken() ;
    }

    @Override
    public void do_(Game game)
    {
        game.decorateWalls() ;

        if (game.isGameOver() && game.getState() != Game.State.GAME_OVER)
        {
            game.notifyGameOver() ;
        }
        else
        {
            game.prepareForRound() ;
        }
    }

    @Override
    public void redo(Game game)
    {
        game.setPlayersBoard(mPlayersBoardsAfter) ;
        game.setFactoriesTiles(mFactoriesTilesAfter) ;
        game.setTilesTable(mTableAfter) ;
        game.setTilesAside(mAsideAfter) ;
        game.setTilesRemaining(mRemainingAfter) ;
        game.setCurrentPlayer(mCurrentPlayerAfter) ;
        game.setState(Game.State.CHOOSE_TILES) ;

        Tile.onRoundStart() ;
    }
}