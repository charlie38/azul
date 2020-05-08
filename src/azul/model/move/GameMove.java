package azul.model.move;

import azul.model.player.Player;
import azul.model.tile.Tile;
import azul.model.tile.TilesFactory;

import java.util.ArrayList;

public class GameMove extends Move
{
    // If 'decorate wall' move.
    private ArrayList<Player> mPlayers ;
    private ArrayList<TilesFactory> mFactories ;
    private ArrayList<Tile> mTable ;
    private int mCurrentPlayer ;

    /**
     * When users decorate their wall.
     * @param type can only be 'Type.DECORATE_WALLS'.
     */
    public GameMove(Type type, ArrayList<Player> players, ArrayList<TilesFactory> factories, ArrayList<Tile> table,
                    int currentPlayer)
    {
        super(type, Type.DECORATE_WALLS) ;

        mPlayers = new ArrayList<>() ;
        mFactories = new ArrayList<>() ;

        for (Player player : players)
        {
            mPlayers.add((Player) player.clone()) ;
        }

        for (TilesFactory factory : factories)
        {
            mFactories.add((TilesFactory) factory.clone()) ;
        }

        mTable = (ArrayList<Tile>) table.clone() ;
        mCurrentPlayer = currentPlayer ;
    }

    public ArrayList<Player> getPlayers()
    {
        return mPlayers ;
    }

    public ArrayList<TilesFactory> getFactories()
    {
        return mFactories ;
    }

    public ArrayList<Tile> getTable()
    {
        return mTable ;
    }

    public int getCurrentPlayer()
    {
        return mCurrentPlayer ;
    }
}
