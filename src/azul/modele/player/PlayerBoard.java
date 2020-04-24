package azul.modele.player;

import azul.modele.tiles.Tile;

import java.util.ArrayList;

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
    private ArrayList<Tile[]> mPatternLines ;
    // To the right of the pattern lines, where you put your tiles at the end.
    private ArrayList<Tile[]> mWall ;
    // At the bottom of the board, where you put your extra tiles.
    private ArrayList<Tile> mFloorLine ;

    /**
     * Class used to keep in memory the player board during all the game.
     * Instantiated for each player before a game start.
     */
    public PlayerBoard()
    {
        mScoreTrack = 0 ;

        initializePatternLines() ;
        initializeWall() ;
        initializeFloorLine() ;
    }

    /**
     * Initialize
     */

    private void initializePatternLines()
    {
        mPatternLines = new ArrayList<>() ;

        for (int i = 1 ; i <= SIZE_PATTERN_LINES ; i ++)
        {
            Tile[] tiles = new Tile[SIZE_WALL] ;

            for (int j = 0 ; j < i ; j ++)
            {
                tiles[j] = Tile.EMPTY ;
            }

            mPatternLines.add(tiles) ;
        }
    }

    private void initializeWall()
    {
        mWall = new ArrayList<>() ;

        for (int i = 1 ; i <= SIZE_WALL ; i ++)
        {
            Tile[] tiles = new Tile[SIZE_WALL] ;

            for (int j = 0 ; j < SIZE_WALL ; j ++)
            {
                tiles[j] = Tile.EMPTY ;
            }

            mWall.add(tiles) ;
        }
    }

    private void initializeFloorLine()
    {
        mFloorLine = new ArrayList<>() ;

        for (int i = 1 ; i <= SIZE_FLOOR_LINES ; i ++)
        {
            mFloorLine.add(Tile.EMPTY) ;
        }
    }

    /**
     * Add tiles.
     */

    public void addToPatternLine(Tile tile, int line) throws PlayerBoardException
    {
        for (int i = 0 ; i < line ; i ++)
        {
            if (mPatternLines.get(line - 1)[i] != Tile.EMPTY)
            {
                mPatternLines.get(line - 1)[i] = tile ;
                return ;
            }
        }
        // Try to add a tile on a full line.
        throw new PlayerBoardException(PlayerBoardException.FULL_PATTERN_LINES) ;
    }

    public void addToWall(Tile tile, int line, int column) throws PlayerBoardException
    {
        // Check if it's a good tile color for this case.
        if (! canBePlacedOnWall(tile, line, column))
        {
            // Try to add a tile on wall case of a different color than the tile one.
            throw new PlayerBoardException(PlayerBoardException.COLOR_WALL) ;
        }
        // Check if the case is full or empty.
        if (! isWallCaseNotEmpty(line, column))
        {
            // Try to add a tile on a not empty case.
            throw new PlayerBoardException(PlayerBoardException.FULL_WALL) ;
        }
        // This case is empty.
        mWall.get(line - 1)[column - 1] = tile ;
    }

    public void addToFloorLine(Tile tile) throws PlayerBoardException
    {
        for (int i = 0 ; i < SIZE_FLOOR_LINES ; i ++)
        {
            if (mFloorLine.get(i) != Tile.EMPTY)
            {
                mFloorLine.set(i, tile) ;
                return ;
            }
        }
        // Try to add a tile on a full floor line.
        throw new PlayerBoardException(PlayerBoardException.FULL_FLOOR_LINE) ;
    }

    /**
     * Getters.
     */

    public int getScoreTrack()
    {
        return mScoreTrack ;
    }

    public Tile getInPatternLines(int i, int j) throws PlayerBoardException
    {
        if (i >= SIZE_PATTERN_LINES || j >= SIZE_PATTERN_LINES || i < 0 || j < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.OUT_PATTERN_LINES);
        }

        return mPatternLines.get(i)[j] ;
    }

    public Tile getInWall(int i, int j) throws PlayerBoardException
    {
        if (i >= SIZE_WALL || j >= SIZE_WALL || i < 0 || j < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.OUT_WALL);
        }

        return mWall.get(i)[j] ;
    }

    public Tile getInFloorLine(int i) throws PlayerBoardException
    {
        if (i >= SIZE_FLOOR_LINES || i < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.OUT_FLOOR_LINE);
        }

        return mFloorLine.get(i) ;
    }

    public boolean isPatterLineFull(int line)
    {
        return mPatternLines.get(line - 1)[line - 1] != Tile.EMPTY ;
    }

    public boolean isWallCaseNotEmpty(int line, int column)
    {
        return mWall.get(line - 1)[column - 1] != Tile.EMPTY ;
    }

    public boolean isFloorLineFull()
    {
        return mFloorLine.get(SIZE_FLOOR_LINES - 1) != Tile.EMPTY ;
    }

    public boolean canBePlacedOnWall(Tile tile, int line, int column)
    {
        return tile == Tile.BLUE && isWallCaseBlue(line, column)
                || tile == Tile.WHITE && isWallCaseWhite(line, column)
                || tile == Tile.BLACK && isWallCaseBlack(line, column)
                || tile == Tile.RED && isWallCaseRed(line, column)
                || tile == Tile.ORANGE && isWallCaseOrange(line, column) ;
    }

    public boolean isWallCaseBlue(int line, int column)
    {
        return line == column ;
    }

    public boolean isWallCaseWhite(int line, int column)
    {
        return (line == 1 && column == 5) || (line != 1 && line - 1 == column) ;
    }

    public boolean isWallCaseBlack(int line, int column)
    {
        return (line == 1 && column == 4) || (line == 2 && column == 5)
                || (line != 1 && line != 2 && line - 2 == column) ;
    }

    public boolean isWallCaseRed(int line, int column)
    {
        return (line == 4 && column == 1) || (line == 5 && column == 2)
                || (line != 4 && line != 5 && line + 2 == column) ;
    }

    public boolean isWallCaseOrange(int line, int column)
    {
        return (line == 5 && column == 1) || (line != 5 && line + 1 == column) ;
    }

    /**
     * Setters.
     */

    public void setScoreTrack(int newScore) throws PlayerBoardException
    {
        if (newScore > MAX_SCORE_TRACK || newScore < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.OUT_SCORE_TRACK);
        }

        mScoreTrack = newScore ;
    }

    public void setInPatterLines(int i, int j, Tile tile) throws PlayerBoardException
    {
        if (i >= SIZE_PATTERN_LINES || j >= SIZE_PATTERN_LINES || i < 0 || j < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.OUT_PATTERN_LINES);
        }

        mPatternLines.get(i)[j] = tile ;
    }

    public void setInWall(int i, int j, Tile tile) throws PlayerBoardException
    {
        if (i >= SIZE_WALL || j >= SIZE_WALL || i < 0 || j < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.OUT_WALL);
        }

        mWall.get(i)[j] = tile ;
    }

    public void setInFloorLine(int i, Tile tile) throws PlayerBoardException
    {
        if (i >= SIZE_FLOOR_LINES || i < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.OUT_FLOOR_LINE);
        }

        mFloorLine.set(i, tile) ;
    }

    public static class PlayerBoardException extends Exception
    {
        // Messages that can be threw.
        private static final String FULL_PATTERN_LINES = "This pattern line is full." ;
        private static final String FULL_WALL = "This wall case is full." ;
        private static final String FULL_FLOOR_LINE = "The floor line full." ;
        private static final String COLOR_WALL = "This wall case is not of the tile color." ;
        private static final String OUT_SCORE_TRACK = "Score track outbound." ;
        private static final String OUT_PATTERN_LINES = "Pattern lines outbound." ;
        private static final String OUT_WALL = "Wall outbound." ;
        private static final String OUT_FLOOR_LINE = "Floor line outbound." ;

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
