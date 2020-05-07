package azul.model.player;

import azul.model.Game;
import azul.model.move.PlayerMove;
import azul.model.tile.Tile;

import java.util.ArrayList;

public abstract class Player
{
    // Model root ref.
    private Game mGame ;
    // The player name.
    private String mName ;
    // His board.
    private PlayerBoard mPlayerBoard ;
    // The tiles selected in the factory, or empty.
    private ArrayList<Tile> mTilesSelected ;

    /**
     * Extended by the IA or human player.
     */
    public Player(Game game, String playerName)
    {
        mGame = game ;
        mName = playerName ;
        mPlayerBoard = new PlayerBoard() ;
        mTilesSelected = new ArrayList<>() ;
    }

    /**
     * Play the player's intentions.
     * @param move contains the player's intentions.
     * @param asideTiles contains the tiles in the box cover.
     */
    public void play(PlayerMove move, ArrayList<Tile> asideTiles)
    {
        switch (move.getType())
        {
            case PLAYER_TAKE_FACTORY : takeTilesFromFactory(move) ; break ;
            case PLAYER_TAKE_TABLE : takeTilesFromTable(move) ; break ;
            case PLAYER_PLACE_TILES_IN_PATTERN : addTilesInPattern(move) ; break ;
            case PLAYER_PLACE_TILES_IN_FLOOR : addTilesInFloor(move, asideTiles) ;
        }
    }

    private void takeTilesFromFactory(PlayerMove move)
    {
        mTilesSelected = move.getTilesSelected() ;
    }

    private void takeTilesFromTable(PlayerMove move)
    {
        // Check if the player have to take the 'first player marker'.
        if (move.isFirstToTakeFromTable())
        {
            try
            {
                mPlayerBoard.addToFloorLine(Tile.takeFirstPlayerMaker()) ;
            }
            catch (Tile.FirstPlayerMarkerException | PlayerBoard.PlayerBoardException e)
            {
                e.printStackTrace() ;
            }
        }
    }

    private void addTilesInPattern(PlayerMove move)
    {
        try
        {
            for (Tile tile : mTilesSelected)
            {
                mPlayerBoard.addToPatternLine(tile, move.getRow() + 1) ;
            }

            mTilesSelected.clear() ;
        }
        catch (PlayerBoard.PlayerBoardException e)
        {
            e.printStackTrace() ;
        }
    }

    /*
    private void addTileInWall(PlayerMove move)
    {
        try
        {
            mPlayerBoard.addToWall(move.getTileSelected(), move.getRow(), move.getColumn()) ;
        }
        catch (PlayerBoard.PlayerBoardException e)
        {
            e.printStackTrace() ;
        }
    }*/

    private void addTilesInFloor(PlayerMove move, ArrayList<Tile> asideTiles)
    {
        if (mPlayerBoard.isFloorLineFull())
        {
            // If full, according to the rules the tile should go to the box cover.
            asideTiles.addAll(move.getTilesSelected()) ;
        }
        else
        {
            try
            {
                for (Tile tile : move.getTilesSelected())
                {
                    mPlayerBoard.addToFloorLine(tile) ;
                }
            }
            catch (PlayerBoard.PlayerBoardException e)
            {
                e.printStackTrace() ;
            }
        }
    }

    /**
     * Decorate the wall with the tiles in the pattern lines and track the player's score.
     * Called at the end of a round by each player.
     * @param asideTiles the tiles in the box cover.
     */
    public void decorateWall(ArrayList<Tile> asideTiles)
    {
        mPlayerBoard.decorateWall(asideTiles) ;
    }

    /**
     * Check if the game is over by checking the user's wall.
     * @return true if the game is over
     */
    public boolean checkGameOver()
    {
        return mPlayerBoard.isWallRowFull() ;
    }

    public Tile getInPatternLines(int i, int j)
    {
        Tile tile = Tile.EMPTY ;

        try
        {
            tile = mPlayerBoard.getInPatternLines(i, j) ;
        }
        catch (PlayerBoard.PlayerBoardException e)
        {
            e.printStackTrace() ;
        }

        return tile ;
    }

    public Tile getInWall(int i, int j)
    {
        Tile tile = Tile.EMPTY ;

        try
        {
            tile = mPlayerBoard.getInWall(i, j) ;
        }
        catch (PlayerBoard.PlayerBoardException e)
        {
            e.printStackTrace() ;
        }

        return tile ;
    }

    public String getName()
    {
        return mName ;
    }

    public int getScore()
    {
        return mPlayerBoard.getScoreTrack() ;
    }

    public boolean isPatternLineRowAccessible(int row)
    {
        return ! (mPlayerBoard.isPatterLineFull(row) ||
                (mTilesSelected.size() != 0 && ! mPlayerBoard.canBePlacedOnPatternLine(mTilesSelected.get(0), row))) ;
    }
}
