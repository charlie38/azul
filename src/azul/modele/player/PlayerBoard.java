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
    // The stair on the board, where you put your tiles
    // /!\ (the tiles are added from the left to the right but should be displayed from the right to the left).
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
     * Decorate the wall with the tiles in the pattern lines and track the score.
     * Called by the player at the end of the round.
     * @param asideTiles the tiles in the box cover.
     */
    protected void decorateWall(ArrayList<Tile> asideTiles)
    {
        for (int i = 0 ; i < SIZE_PATTERN_LINES ; i ++)
        {
            if (mPatternLines.get(i)[0] != Tile.EMPTY && mPatternLines.get(i)[i] != Tile.EMPTY)
            {
                ArrayList<Tile> tiles = new ArrayList<>() ;
                // If the pattern line is full, add the rightmost (leftmost in the array) tile to the wall.
                try
                {
                    addToWall(mPatternLines.get(i)[0], i + 1) ;
                }
                catch (PlayerBoardException e)
                {
                    e.printStackTrace() ;
                }
                // Track the player's score.
                // TODO Track the user score
                // Remove the remaining tiles of this pattern line.
                for (int j = 1 ; j <= i ; j ++)
                {
                    tiles.add(mPatternLines.get(i)[j]) ;
                    // Initialize the pattern line (which is empty now).
                    mPatternLines.get(i)[j] = Tile.EMPTY ;
                }
                // And put them inside the box cover.
                asideTiles.addAll(tiles) ;
            }
        }
    }

    /**
     * Initialize
     */

    private void initializePatternLines()
    {
        mPatternLines = new ArrayList<>() ;

        for (int i = 1 ; i <= SIZE_PATTERN_LINES ; i ++)
        {
            Tile[] tiles = new Tile[SIZE_PATTERN_LINES] ;

            for (int j = SIZE_PATTERN_LINES-1 ; j > SIZE_PATTERN_LINES-1-i ; j --)
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

    public void addToPatternLine(Tile tile, int row) throws PlayerBoardException
    {
        // Check if it's a good tile color for this row.
        if (! canBePlacedOnPatternLine(tile, row))
        {
            // Try to add a tile on wall case of a different color than the tile one.
            throw new PlayerBoardException(PlayerBoardException.COLOR_PATTERN_LINES) ;
        }

        for (int i = 0 ; i < row ; i ++)
        {
            if (mPatternLines.get(row - 1)[i] != Tile.EMPTY)
            {
                mPatternLines.get(row - 1)[i] = tile ;
                return ;
            }
        }
        // Try to add a tile on a full line.
        throw new PlayerBoardException(PlayerBoardException.FULL_PATTERN_LINES) ;
    }

    public void addToWall(Tile tile, int row, int column) throws PlayerBoardException
    {
        // Check if it's a good tile color for this case.
        if (! canBePlacedOnWall(tile, row, column))
        {
            // Try to add a tile on wall case of a different color than the tile one.
            throw new PlayerBoardException(PlayerBoardException.COLOR_WALL) ;
        }
        // Check if the case is full or empty.
        if (isWallCaseNotEmpty(row, column))
        {
            // Try to add a tile in a not empty case.
            throw new PlayerBoardException(PlayerBoardException.FULL_WALL) ;
        }
        // This case is empty.
        mWall.get(row - 1)[column - 1] = tile ;
    }

    public void addToWall(Tile tile, int row) throws PlayerBoardException
    {
        int i ;
        // Search for the corresponding case in the wall, depending on the tile color.
        for (i = 1 ; i <= SIZE_WALL ; i ++)
        {
            switch (tile)
            {
                case CRYSTAL : if (isWallCaseCrystal(row, i)) break ; break ;
                case EYE : if (isWallCaseEye(row, i)) break ; break ;
                case CLAW : if (isWallCaseClaw(row, i)) break ; break ;
                case FLOWER : if (isWallCaseFlower(row, i)) break ; break ;
                case MUSHROOM : if (isWallCaseMushroom(row, i)) break ;
            }
        }

        if (isWallCaseNotEmpty(row, i))
        {
            // Try to add a tile in a not empty case.
            throw new PlayerBoardException(PlayerBoardException.FULL_WALL) ;
        }
        // Add the tile to the wall.
        mWall.get(row - 1)[i - 1] = tile ;
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

    public boolean isPatterLineFull(int row)
    {
        return mPatternLines.get(row - 1)[row - 1] != Tile.EMPTY ;
    }

    public boolean isWallCaseNotEmpty(int row, int column)
    {
        return mWall.get(row - 1)[column - 1] != Tile.EMPTY ;
    }

    public boolean isFloorLineFull()
    {
        return mFloorLine.get(SIZE_FLOOR_LINES - 1) != Tile.EMPTY ;
    }

    public boolean canBePlacedOnPatternLine(Tile tile, int row)
    {
        // Tiles should be of the same color in a row.
        // This will be the first tile / the tile in the row are of the same color.
        return mPatternLines.get(row)[0] == Tile.EMPTY || mPatternLines.get(row)[0] == tile ;
    }

    public boolean canBePlacedOnWall(Tile tile, int row, int column)
    {
        // Check if the corresponding case in the wall is of the tile color.
        return tile == Tile.CRYSTAL && isWallCaseCrystal(row, column)
                || tile == Tile.EYE && isWallCaseEye(row, column)
                || tile == Tile.CLAW && isWallCaseClaw(row, column)
                || tile == Tile.FLOWER && isWallCaseFlower(row, column)
                || tile == Tile.MUSHROOM && isWallCaseMushroom(row, column) ;
    }

    public boolean isWallCaseMushroom(int row, int column)
    {
        return row == column ;
    }

    public boolean isWallCaseCrystal(int row, int column)
    {
        return (row == 1 && column == 5) || (row != 1 && row - 1 == column) ;
    }

    public boolean isWallCaseEye(int row, int column)
    {
        return (row == 1 && column == 4) || (row == 2 && column == 5)
                || (row != 1 && row != 2 && row - 2 == column) ;
    }

    public boolean isWallCaseClaw(int row, int column)
    {
        return (row == 4 && column == 1) || (row == 5 && column == 2)
                || (row != 4 && row != 5 && row + 2 == column) ;
    }

    public boolean isWallCaseFlower(int row, int column)
    {
        return (row == 5 && column == 1) || (row != 5 && row + 1 == column) ;
    }

    public boolean isWallRowFull()
    {
        // Check if a row in the wall is full.
        for (int i = 0 ; i < SIZE_WALL ; i ++)
        {
            for (int j = 0 ; j < SIZE_WALL ; j ++)
            {
                if (mWall.get(i)[j] == Tile.EMPTY)
                {
                    break ;
                }

                if (j == SIZE_WALL - 1)
                {
                    return true ;
                }
            }
        }

        return false ;
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
        private static final String FULL_WALL = "This wall case is not empty." ;
        private static final String FULL_FLOOR_LINE = "The floor line full." ;
        private static final String COLOR_PATTERN_LINES = "This pattern line is not of the tile color." ;
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
