package azul.model;

import azul.model.history.History;
import azul.model.move.Move;
import azul.model.player.HumanPlayer;
import azul.model.player.IAPlayer;
import azul.model.player.Player;
import azul.model.player.PlayerBoard;
import azul.model.tile.Tile;
import azul.model.tile.TilesFactory;

import java.util.ArrayList;
import java.util.Observable;

public class Game extends Observable
{
    // Game states.
    public enum State { START, CHOOSE_TILES, SELECT_ROW, DECORATE_WALL, INTERRUPT_IAS, CONTINUE_IAS_DELAY,
        CONTINUE_IAS_NO_DELAY, BEGINNING, GAME_OVER }

    // Size of the tiles bag.
    public static final int SIZE_TILES_REMAINING = 100 ;
    // Size of the tiles on the table.
    public static final int SIZE_TILES_TABLE = 9 * 4 ;
    // Number of tiles of the same color in the bag at the start.
    public final int NB_TILES_COLOR = 20 ;

    // Game history.
    private History mHistory ;
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
    private State mState, mPreviousState ;
    // True if game finished (in the history).
    private boolean mIsGameFinished ;

    /**
     * Contains the game objects ; players, tiles factories and tiles bag.
     * Allows player to play their turn, and keep it in memory.
     */
    public Game()
    {
        mHistory = new History() ;
        mPlayers = new ArrayList<>() ;
        mTilesFactories = new ArrayList<>() ;
        mTilesRemaining = new ArrayList<>() ;
        mTilesAside = new ArrayList<>() ;
        mTilesTable = new ArrayList<>() ;
    }

    /**
     * Initialize a new game, depending on the number of players.
     * @param playersTypes the players' type.
     * @param playerNames the players' name.
     */
    public void startGame(Player.Type[] playersTypes, String[] playerNames)
    {
        int nbPlayers = playersTypes.length ;

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

        mIsGameFinished = false ;
        mHistory.clean() ;
        notifyGameStart() ;
        // Initialize game objects.
        initializePlayers(playersTypes, playerNames) ;
        initializeTilesRemaining() ;
        initializeTilesAside() ;
        initializeTilesTable() ;
        initializeTilesFactories(getNbTilesFactories(nbPlayers)) ;
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
        setState(State.CHOOSE_TILES) ;
        // Notify the UI.
        notifyUI() ;
    }

    /** Initialization. **/

    private void initializePlayers(Player.Type[] playersTypes, String[] playersNames)
    {
        mPlayers.clear() ;

        for (int i = 0 ; i < playersTypes.length ; i ++)
        {
            mPlayers.add(playersTypes[i] != Player.Type.HUMAN ?
                    new IAPlayer(playersTypes[i], playersNames[i]) :
                    new HumanPlayer(playersNames[i])) ;
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
    
    public int numberOfTileTable(Tile tile)
    {
    	int res = 0;
    	for (Tile t : mTilesTable)
        {
            if (t == tile)
            {
                res++;
            }
        }
    	return res;
    }

    private void notifyGameStart()
    {
        setState(State.START) ;
        // Notify the mediator.
        notifyUI() ;
    }

    public void notifyGameOver()
    {
        setState(Game.State.GAME_OVER) ;
        // Notify the mediator.
        notifyUI() ;
    }

    public void notifyBeginning()
    {
        setState(State.BEGINNING) ;
        // Notify the UI.
        notifyUI() ;
    }

    public void notifyUI()
    {
        setChanged() ;
        notifyObservers() ;
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
     * Moves.
     */

    public void playPreviousMove()
    {
        // Get from the history.
        Move move = mHistory.undo() ;
        // Undo the move.
        move.undo(this) ;
        // Notify the UI.
        notifyUI() ;
    }

    public void playMove(Move move)
    {
        // Stack in the history.
        mHistory.do_(move) ;
        // Do the move.
        move.do_(this) ;
        // Notify the UI.
        notifyUI() ;
    }

    public void playNextMove()
    {
        // Get from the history.
        Move move = mHistory.redo() ;
        // Redo the move.
        move.redo(this) ;
        // Notify the UI.
        notifyUI() ;
    }

    /**
     * When only IAs playing.
     */

    public void goToFirstMove()
    {
        // Read all the moves.
        Move move ;

        while (mHistory.canUndo() && (move = mHistory.undo()) != null)
        {
            // Undo the move.
            move.undo(this) ;
        }

        notifyBeginning() ;
    }

    public void goToLastMove()
    {
        if (! mIsGameFinished)
        {
            continueIAs(State.CONTINUE_IAS_NO_DELAY) ;
        }
        else
        {
            // Read all the moves.
            Move move ;

            while (mHistory.canRedo() && (move = mHistory.redo()) != null)
            {
                // Undo the move.
                move.redo(this) ;
            }

            notifyGameOver() ;
        }
    }

    public void interruptIAs()
    {
        setState(State.INTERRUPT_IAS) ;
        // Notify the Mediator.
        notifyUI() ;
    }

    public void continueIAs(State state)
    {
        setState(state) ;
        // Notify the Mediator.
        notifyUI() ;
    }

    /**
     * Game actions.
     */

    public void nextPlayer()
    {
        mCurrentPlayer = (mCurrentPlayer == getNbPlayers() - 1) ? 0 : mCurrentPlayer + 1 ;
    }

    public void previousPlayer()
    {
        mCurrentPlayer = (mCurrentPlayer == 0) ? getNbPlayers() - 1 : mCurrentPlayer - 1 ;
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
     * Setters.
     */

    public void setState(State newState)
    {
        if (newState != State.CONTINUE_IAS_DELAY && newState != State.CONTINUE_IAS_NO_DELAY)
        {
            // Needs to keep in memory this state to continue the game when IAs playing.
            mPreviousState = mState ;
        }

        mState = newState ;
    }

    public void setTilesTable(ArrayList<Tile> newTiles)
    {
        mTilesTable = newTiles ;
    }

    public void setTilesAside(ArrayList<Tile> newTiles)
    {
        mTilesAside = newTiles ;
    }

    public void setTilesRemaining(ArrayList<Tile> newTiles)
    {
        mTilesRemaining = newTiles ;
    }

    public void setCurrentPlayer(int newPlayer)
    {
        mCurrentPlayer = newPlayer ;
    }

    public void setFactoriesTiles(ArrayList<ArrayList<Tile>> tiles)
    {
        int i = 0 ;

        for (TilesFactory factory : mTilesFactories)
        {
            factory.setTiles(tiles.get(i ++)) ;
        }
    }

    public void setPlayersBoard(ArrayList<PlayerBoard> boards)
    {
        int i = 0 ;

        for (Player player : mPlayers)
        {
            player.setBoard(boards.get(i ++)) ;
        }
    }

    /**
     * Getters.
     */

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
                mIsGameFinished = true ;
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
        for (Tile tile : mTilesTable)
        {
            if (tile != Tile.EMPTY)
            {
                return false ;
            }
        }

        return true ;
    }
    
    public boolean isNotInTable(Tile selected)
    {
        for (Tile tile : mTilesTable)
        {
            if (tile == selected)
            {
                return false ;
            }
        }

        return true ;
    }

    public boolean isOnlyIAs()
    {
        for (Player player : mPlayers)
        {
            if (player instanceof HumanPlayer)
            {
                return false ;
            }
        }

        return true ;
    }

    public boolean isHumanVsIAs()
    {
        boolean human = false ;
        boolean ia = false ;

        for (Player player : mPlayers)
        {
            if (player instanceof HumanPlayer)
            {
                human = true ;
            }

            if (player instanceof IAPlayer)
            {
                ia = true ;
            }
        }

        return ia && human ;
    }

    public boolean isGameFinished()
    {
        return mIsGameFinished ;
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

    public ArrayList<Player> getPlayers()
    {
        return mPlayers ;
    }

    public ArrayList<TilesFactory> getFactories()
    {
        return mTilesFactories ;
    }

    public ArrayList<Tile> getTilesRemaining()
    {
        return mTilesRemaining ;
    }

    public ArrayList<Tile> getTilesAside()
    {
        return mTilesAside ;
    }

    public ArrayList<Tile> getTilesTable()
    {
        return mTilesTable ;
    }

    public State getState()
    {
        return mState ;
    }

    public State getPreviousState()
    {
        return mPreviousState ;
    }

    public History getHistory()
    {
        return mHistory ;
    }

    public int getTableSize()
    {
        int i = 0 ;

        while (getInTilesTable(++ i) != Tile.EMPTY) ;

        return i ;
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