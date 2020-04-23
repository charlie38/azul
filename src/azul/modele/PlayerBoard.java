package azul.modele;

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
    private TilesFactory.Tile[][] mPatternLines ;
    // To the right of the pattern lines, where you put your tiles at the end.
    private TilesFactory.Tile[][] mWall ;
    // At the bottom of the board, where you put your extra tiles.
    private TilesFactory.Tile[] mFloorLines ;

    /**
     * Class used to keep in memory the player board during all the game.
     */
    public PlayerBoard()
    {
        mScoreTrack = 0 ;
        mPatternLines = new TilesFactory.Tile[SIZE_PATTERN_LINES][SIZE_PATTERN_LINES] ;
        mWall = new TilesFactory.Tile[SIZE_WALL][SIZE_WALL] ;
        mFloorLines = new TilesFactory.Tile[SIZE_FLOOR_LINES] ;
    }

    /**
     * Getters.
     */

    public int getScoreTrack()
    {
        return mScoreTrack ;
    }

    public TilesFactory.Tile getInPatternLines(int i, int j) throws PlayerBoardException
    {
        if (i >= SIZE_PATTERN_LINES || j >= SIZE_PATTERN_LINES || i < 0 || j < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.PATTERN_LINES);
        }

        return mPatternLines[i][j] ;
    }

    public TilesFactory.Tile getInWall(int i, int j) throws PlayerBoardException
    {
        if (i >= SIZE_WALL || j >= SIZE_WALL || i < 0 || j < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.WALL);
        }

        return mWall[i][j] ;
    }

    public TilesFactory.Tile getInFloorLines(int i) throws PlayerBoardException
    {
        if (i >= SIZE_FLOOR_LINES || i < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.FLOOR_LINES);
        }

        return mFloorLines[i] ;
    }

    /**
     * Setters.
     */

    public void setScoreTrack(int newScore) throws PlayerBoardException
    {
        if (newScore > MAX_SCORE_TRACK || newScore < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.SCORE_TRACK);
        }

        mScoreTrack = newScore ;
    }

    public void setInPatterLines(int i, int j, TilesFactory.Tile tile) throws PlayerBoardException
    {
        if (i >= SIZE_PATTERN_LINES || j >= SIZE_PATTERN_LINES || i < 0 || j < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.PATTERN_LINES);
        }

        mPatternLines[i][j] = tile ;
    }

    public void setInWall(int i, int j, TilesFactory.Tile tile) throws PlayerBoardException
    {
        if (i >= SIZE_WALL || j >= SIZE_WALL || i < 0 || j < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.WALL);
        }

        mWall[i][j] = tile ;
    }

    public void setInFloorLines(int i, TilesFactory.Tile tile) throws PlayerBoardException
    {
        if (i >= SIZE_FLOOR_LINES || i < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.FLOOR_LINES);
        }

        mFloorLines[i] = tile ;
    }

    private static class PlayerBoardException extends Exception
    {
        // Messages that can be threw.
        private static final String SCORE_TRACK = "Score track outbound." ;
        private static final String PATTERN_LINES = "Pattern lines outbound." ;
        private static final String WALL = "Wall outbound." ;
        private static final String FLOOR_LINES = "Floor lines outbound." ;

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
