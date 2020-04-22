package azul;

public class PlayerBoard
{
    // Size of the board components.
    public static final int MAX_SCORE_TRACK = 100 ;
    public static final int SIZE_WALL = 5 ;
    public static final int SIZE_PATTERN_LINES = 5 ;
    public static final int SIZE_FLOOR_LINES = 7 ;

    // At the top of the board used to track user score.
    private int mScoreTrack ;
    // The stair on the board, where you put your tiles.
    private TileFactory.Tile[][] mPatternLines ;
    // To the right of the pattern lines, where you put your tiles at the end.
    private TileFactory.Tile[][] mWall ;
    // At the bottom of the board, where you put your extra tiles.
    private TileFactory.Tile[] mFloorLines ;

    /**
     * Class used to keep in memory the player board during all the game.
     */
    public PlayerBoard()
    {
        initialize() ;
    }

    /**
     * Initialize player's board before the game start.
     */
    public void initialize()
    {
        mScoreTrack = 0 ;
        mPatternLines = new TileFactory.Tile[SIZE_PATTERN_LINES][SIZE_PATTERN_LINES] ;
        mWall = new TileFactory.Tile[SIZE_WALL][SIZE_WALL] ;
        mFloorLines = new TileFactory.Tile[SIZE_FLOOR_LINES] ;
    }

    /**
     * Getters.
     */

    public int getScoreTrack()
    {
        return mScoreTrack ;
    }

    public TileFactory.Tile getInPatternLines(int i, int j) throws PlayerBoardException
    {
        if (i >= SIZE_PATTERN_LINES || j >= SIZE_PATTERN_LINES)
        {
            throw new PlayerBoardException(PlayerBoardException.EXCEPTION_PATTERN_LINES);
        }

        return mPatternLines[i][j] ;
    }

    public TileFactory.Tile getInWall(int i, int j) throws PlayerBoardException
    {
        if (i >= SIZE_WALL || j >= SIZE_WALL)
        {
            throw new PlayerBoardException(PlayerBoardException.EXCEPTION_WALL);
        }

        return mWall[i][j] ;
    }

    public TileFactory.Tile getInFloorLines(int i) throws PlayerBoardException
    {
        if (i >= SIZE_FLOOR_LINES)
        {
            throw new PlayerBoardException(PlayerBoardException.EXCEPTION_FLOOR_LINES);
        }

        return mFloorLines[i] ;
    }

    /**
     * Setters.
     */

    public void setScoreTrack(int newScore) throws PlayerBoardException
    {
        if (newScore > MAX_SCORE_TRACK)
        {
            throw new PlayerBoardException(PlayerBoardException.EXCEPTION_SCORE_TRACK);
        }

        mScoreTrack = newScore ;
    }

    public void setInPatterLines(int i, int j, TileFactory.Tile tile) throws PlayerBoardException
    {
        if (i >= SIZE_PATTERN_LINES || j >= SIZE_PATTERN_LINES)
        {
            throw new PlayerBoardException(PlayerBoardException.EXCEPTION_PATTERN_LINES);
        }

        mPatternLines[i][j] = tile ;
    }

    public void setInWall(int i, int j, TileFactory.Tile tile) throws PlayerBoardException
    {
        if (i >= SIZE_WALL || j >= SIZE_WALL)
        {
            throw new PlayerBoardException(PlayerBoardException.EXCEPTION_WALL);
        }

        mWall[i][j] = tile ;
    }

    public void setInFloorLines(int i, TileFactory.Tile tile) throws PlayerBoardException
    {
        if (i >= SIZE_FLOOR_LINES)
        {
            throw new PlayerBoardException(PlayerBoardException.EXCEPTION_FLOOR_LINES);
        }

        mFloorLines[i] = tile ;
    }

    private static class PlayerBoardException extends Exception
    {
        // Messages that can be threw.
        private static final String EXCEPTION_SCORE_TRACK = "Score track outbound." ;
        private static final String EXCEPTION_PATTERN_LINES = "Pattern lines outbound." ;
        private static final String EXCEPTION_WALL = "Wall outbound." ;
        private static final String EXCEPTION_FLOOR_LINES = "Floor lines outbound." ;

        /**
         * To throw exception on outbound board values.
         * @param message message threw.
         */
        public PlayerBoardException(String message)
        {
            super(message) ;
        }
    }
}
