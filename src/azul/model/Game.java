package azul.model;

import azul.model.move.PlayerMove;
import azul.model.player.HumanPlayer;
import azul.model.player.Player;
import azul.model.tile.Tile;
import azul.model.tile.TilesFactory;
import azul.view.drawable.factory.FactoryTile;

import java.util.ArrayList;
import java.util.Observable;

public class Game extends Observable
{
    // Game states.
    public enum State { CHOOSE_TILES, SELECT_ROW, GAME_OVER }

    // Size of the tiles bag.
    public static final int SIZE_TILES_REMAINING = 100 ;
    // Size of the tiles on the table.
    public static final int SIZE_TILES_TABLE = 9 * 4 ;
    // Number of tiles of the same color in the bag at the start.
    public final int NB_TILES_COLOR = 20 ;

    // All the game players.
    private ArrayList<Player> mPlayers ;
    // Tiles factories.
    private ArrayList<TilesFactory> mTilesFactories ;
    // Tiles in the bag.
    private ArrayList<Tile> mTilesRemaining ;
    // Tiles in the box cover.
    private ArrayList<Tile> mTilesAside ;
    // Tiles on the table.
    private ArrayList<Tile> mTilesTable ;
    // The player who must play during this game turn.
    private int mCurrentPlayer ;
    // Current game state.
    private State mState ;

    /**
     * Contains the game objects ; players, tiles factories and tiles bag.
     * Allows player to play their turn, and keep it in memory.
     */
    public Game()
    {
        mPlayers = new ArrayList<>() ;
        mTilesFactories = new ArrayList<>() ;
        mTilesRemaining = new ArrayList<>() ;
        mTilesAside = new ArrayList<>() ;
        mTilesTable = new ArrayList<>() ;
    }

    /**
     * Initialize a new game, depending on the number of players.
     * @param nbPlayers the number of players.
     */
    public void startGame(int nbPlayers, String[] playerNames)
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
        // Initialize game objects.
        initializePlayers(nbPlayers, playerNames) ;
        initializeTilesRemaining() ;
        initializeTilesAside() ;
        initializeTilesTable() ;
        initializeTilesFactories(getNbTilesFactories(mPlayers.size())) ;
        // This will be the first round.
        prepareForRound() ;
    }

    /**
     * Prepare the game for a round.
     * Can be called at the start of the game, and during the game before a round starts.
     */
    public void prepareForRound()
    {
        // Prepare the factories, with the tiles in the bag; if the bag is not empty, each factory gets 4 tiles.
        for (TilesFactory factory : mTilesFactories)
        {
            factory.prepare(mTilesRemaining, mTilesAside) ;
        }
        // Set the token.
        mTilesTable.set(0, Tile.FIRST_PLAYER_MAKER) ;
        // Initialize the 'first player marker'.
        Tile.onRoundStart() ;

        mCurrentPlayer = 0 ;
        mState = State.CHOOSE_TILES ;
        // Notify the UI.
        setChanged() ;
        notifyObservers() ;
    }

    private void initializePlayers(int nbPlayers, String[] playersNames)
    {
        mPlayers.clear() ;

        for (int i = 1 ; i <= nbPlayers ; i ++)
        {
            mPlayers.add(new HumanPlayer(playersNames[i - 1])) ;
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

    private void initializeTilesAside()
    {
        mTilesAside.clear() ;
    }

    private void initializeTilesTable()
    {
        mTilesTable.clear() ;

        mTilesTable.add(Tile.FIRST_PLAYER_MAKER) ;

        for (int i = 2 ; i <= SIZE_TILES_TABLE ; i ++)
        {
            mTilesTable.add(Tile.EMPTY) ;
        }
    }

    /**
     * Find the correct number of tiles factories needed for this game, depending on the game rules and players number.
     * @param nbPlayers the number of player for this game.
     * @return the number of factories needed.
     */
    public int getNbTilesFactories(int nbPlayers)
    {
        return 2 * nbPlayers + 1 ;
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
        getPlayer().play(move, mTilesAside, mTilesTable) ;
        // Change the game state.
        switch (move.getType())
        {
            case PLAYER_PLACE_TILES_IN_FLOOR :
            case PLAYER_PLACE_TILES_IN_PATTERN :

                mState = State.CHOOSE_TILES ;
                // Check if game over, or round over.
                if (isGameOver())
                {
                    decorateWalls() ;
                    mState = State.GAME_OVER ;
                }
                else if (isRoundOver())
                {
                    decorateWalls() ;
                    prepareForRound() ;
                }

                break ;

            case PLAYER_TAKE_TABLE :

                if (move.getTilesSelected().get(0) == Tile.FIRST_PLAYER_MAKER)
                {
                    // User chosen the token, needs to be automatically placed on the floor line.
                    changePlayer() ;
                    break ;
                }

            case PLAYER_TAKE_FACTORY : mState = State.SELECT_ROW ;
        }
        // Notify the UI.
        setChanged() ;
        notifyObservers() ;
    }

    public void changePlayer()
    {
        // Go to the next player.
        mCurrentPlayer = (mCurrentPlayer == getNbPlayers() - 1) ? 0 : mCurrentPlayer + 1 ;
        // Change the game state.
        mState = State.CHOOSE_TILES ;
        // Notify the UI.
        setChanged() ;
        notifyObservers() ;
    }

    public void goToPreviousMove()
    {
    }

    public void goToNextMove()
    {
    }

    /**
     * Decorate the players' wall. Called at the end of a round.
     */
    public void decorateWalls()
    {
        for (Player player : mPlayers)
        {
            player.decorateWall(mTilesAside) ;
        }
    }

    /**
     * Take all the tiles of the same color on the table.
     * @param selected the tiles chosen by the player.
     * @return the tiles of the same color as 'selected'.
     */
    public ArrayList<Tile> takeOnTable(Tile selected)
    {
        ArrayList<Tile> tilesSelected = new ArrayList<>() ;

        for (int i = 0 ; i < SIZE_TILES_TABLE ; i ++)
        {
            if (mTilesTable.get(i) == selected)
            {
                tilesSelected.add(mTilesTable.get(i)) ;
                mTilesTable.set(i, Tile.EMPTY) ;
            }
        }

        return tilesSelected ;
    }

    /**
     * Check if the game is over by checking all users' wall.
     * According to the rules, if a row is full in a user wall, the game is over.
     * @return true if the game is over.
     */
    public boolean isGameOver()
    {
        for (Player player : mPlayers)
        {
            if (player.checkGameOver())
            {
                return true ;
            }
        }

        return false ;
    }

    /**
     * Check if the round is over by checking all the factories and table.
     * According to the rules, if the factories and the table are empty, the round is over.
     * @return true if the round is over.
     */
    public boolean isRoundOver()
    {
        for (Tile tile : mTilesTable)
        {
            if (tile != Tile.EMPTY)
            {
                return false ;
            }
        }

        return isFactoriesEmpty() ;
    }

    public boolean isFactoriesEmpty()
    {
        for (TilesFactory factory : mTilesFactories)
        {
            if (! factory.isEmpty())
            {
                return false ;
            }
        }

        return true ;
    }

    public boolean isTableEmpty()
    {
        return mTilesTable.size() == 0 ;
    }

    public Player getPlayer()
    {
        return mPlayers.get(mCurrentPlayer) ;
    }

    public Player getPlayer(int index)
    {
        return mPlayers.get(index) ;
    }

    public int getPlayerIndex()
    {
        return mCurrentPlayer ;
    }

    public int getNbPlayers()
    {
        return mPlayers.size() ;
    }

    public TilesFactory getFactory(int i)
    {
        return mTilesFactories.get(i) ;
    }

    public Tile getInTilesRemaining(int i)
    {
        return mTilesRemaining.get(i) ;
    }

    public Tile getInTilesAside(int i)
    {
        return mTilesAside.get(i) ;
    }

    public Tile getInTilesTable(int i)
    {
        return mTilesTable.get(i) ;
    }

    public State getState()
    {
        return mState ;
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