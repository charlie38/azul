package azul.modele;

import azul.modele.move.PlayerMove;
import azul.modele.player.HumanPlayer;
import azul.modele.player.Player;
import azul.modele.tiles.Tile;
import azul.modele.tiles.TilesFactory;

import java.util.ArrayList;
import java.util.Observable;

public class Game extends Observable
{
    // Size of the tiles bag.
    public static final int SIZE_TILES_REMAINING = 100 ;
    // Number of tiles of the same color in the bag at the start.
    public static final int NB_TILES_COLOR = 20 ;

    // All the game players.
    private ArrayList<Player> mPlayers ;
    // Tiles factories.
    private ArrayList<TilesFactory> mTilesFactories ;
    // Tiles in the bag.
    private ArrayList<Tile> mTilesRemaining ;
    // The player who must play during this game turn.
    private int mCurrentPlayer ;

    /**
     * Contains the game objects ; players, tiles factories and tiles bag.
     * Allows player to play their turn, and keep it in memory.
     */
    public Game()
    {
        mPlayers = new ArrayList<>() ;
        mTilesFactories = new ArrayList<>() ;
        mTilesRemaining = new ArrayList<>() ;
    }

    /**
     * Initialize a new game, depending on the number of players.
     * @param nbPlayers the number of players.
     */
    public void startGame(int nbPlayers)
    {
        try
        {
            checkNbPlayers(nbPlayers) ;
        }
        catch (GameException e)
        {
            // Can't play the game.
            e.printStackTrace() ;
            return ;
        }
        // Initialize the 'first player marker'.
        Tile.onGameStart() ;

        initializePlayers(nbPlayers) ;
        initializeTilesFactories(getNbTilesFactories(nbPlayers)) ;
        initializeTilesRemaining() ;
    }

    private void initializePlayers(int nbPlayers)
    {
        mPlayers.clear() ;

        for (int i = 1 ; i <= nbPlayers ; i ++)
        {
            mPlayers.add(new HumanPlayer()) ;
        }
    }

    private void initializeTilesFactories(int nbFactories)
    {
        mTilesFactories.clear() ;

        for (int i = 1 ; i <= nbFactories ; i ++)
        {
            mTilesFactories.add(new TilesFactory()) ;
        }
    }

    private void initializeTilesRemaining()
    {
        mTilesRemaining.clear() ;

        for (int i = 1 ; i <= SIZE_TILES_REMAINING ; i ++)
        {
            mTilesRemaining.add(Tile.values()[i % (Tile.values().length - 2)]) ;
        }
    }

    /**
     * Find the correct number of tiles factories needed for this game, depending on the game rules and players number.
     * @param nbPlayers the number of player for this game.
     * @return the number of factories needed.
     */
    private int getNbTilesFactories(int nbPlayers)
    {
        switch (nbPlayers)
        {
            case 2 : return 5 ;
            case 3 : return 7 ;
            case 4 : return 9 ;
            default : return 0 ;
        }
    }

    /**
     * Throw an exception if the number of player is too high or low, according to the game rules.
     * @param nbPlayers the number of player for this game.
     * @throws GameException when not enough or too much players.
     */
    private void checkNbPlayers(int nbPlayers) throws GameException
    {
        if (nbPlayers <= 1)
        {
            throw new GameException(GameException.LOW_PLAYERS) ;
        }

        if (nbPlayers >= 5)
        {
            throw new GameException(GameException.HIGH_PLAYERS) ;
        }
    }

    /**
     * Play the player's intentions and update the UI.
     * @param move player's intentions.
     */
    public void playMove(PlayerMove move)
    {
        getPlayer().play(move) ;
        // Notify the UI.
        notifyObservers() ;
        // Go to the next player.
        mCurrentPlayer ++ ;
    }

    /**
     * @return the current player.
     */
    public Player getPlayer()
    {
        return mPlayers.get(mCurrentPlayer) ;
    }

    public ArrayList<Tile> getTilesRemaining()
    {
        return mTilesRemaining ;
    }

    public ArrayList<TilesFactory> getTilesFactories()
    {
        return mTilesFactories ;
    }

    private static class GameException extends Exception
    {
        // Messages that can be threw.
        private static final String HIGH_PLAYERS = "Too much players to play the game." ;
        private static final String LOW_PLAYERS = "Not enough players to play the game." ;

        /**
         * To throw exception when it's impossible to play the game.
         * @param message message threw.
         */
        public GameException(String message)
        {
            super(message) ;
        }
    }
}
