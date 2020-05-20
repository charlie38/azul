package azul.model.player;

import azul.model.Game;
import azul.model.move.*;
import azul.model.tile.Tile;
import azul.model.tile.TilesFactory;

import java.util.ArrayList;

public abstract class Player
{
    // Player types.
    public enum Type { HUMAN, IA_RANDOM, IA_MINIMAX, IA_EASY }

    // The player name.
    private String mName ;
    // His board.
    private PlayerBoard mPlayerBoard ;
    // The tiles selected in the factory, or empty.
    private ArrayList<Tile> mTilesSelected ;

    /**
     * Extended by the IA and human players.
     * @param playerName name printed during the game.
     */
    public Player(String playerName)
    {
        mName = playerName ;
        mPlayerBoard = new PlayerBoard() ;
        mTilesSelected = new ArrayList<>() ;
    }

    public void takeTilesFromFactory(TakeInFactory move, ArrayList<Tile> tableTiles)
    {
        mTilesSelected = (ArrayList<Tile>) move.getTilesSelected().clone() ;
        // Add the remaining tiles in the factory on the table.
        for (int i = 0 ; i < TilesFactory.SIZE_FACTORY ; i ++)
        {
            Tile tile = move.getFactory().getTile(i) ;

            if (tile != Tile.EMPTY)
            {
                for (int j = 1 ; j < Game.SIZE_TILES_TABLE ; j ++)
                {
                    if (tableTiles.get(j) == Tile.EMPTY)
                    {
                        tableTiles.set(j, tile) ;
                        break ;
                    }
                }

                move.getFactory().getTiles().set(i, Tile.EMPTY) ;
            }
        }
    }

    public void takeTilesFromTable(TakeOnTable move, ArrayList<Tile> tableTiles, ArrayList<Tile> asideTiles)
    {
        // Check if the player have to take the 'first player marker'.
        if (move.isFirstToTakeFromTable())
        {
            try
            {
                if (mPlayerBoard.getInFloorLine(0) != Tile.EMPTY)
                {
                    asideTiles.add(mPlayerBoard.getInFloorLine(0)) ;
                }

                mPlayerBoard.setInFloorLine(0, Tile.takeFirstPlayerMaker()) ;
                tableTiles.set(0, Tile.EMPTY) ;
            }
            catch (Tile.FirstPlayerMarkerException | PlayerBoard.PlayerBoardException e)
            {
                e.printStackTrace() ;
            }
        }

        mTilesSelected = (ArrayList<Tile>) move.getTilesSelected().clone();
    }

    public void addTilesInPattern(ChoosePatternLine move, ArrayList<Tile> asideTiles)
    {
        try
        {
            for (Tile tile : mTilesSelected)
            {
                if (! mPlayerBoard.isPatterLineFull(move.getRow() + 1))
                {
                    // Add to pattern line.
                    mPlayerBoard.addToPatternLine(tile, move.getRow() + 1) ;
                }
                else if (! mPlayerBoard.isFloorLineFull())
                {
                    // If full, finish to add in the floor line.
                    mPlayerBoard.addToFloorLine(tile) ;
                }
                else
                {
                    // If full, according to the rules the tile should go to the box cover.
                    asideTiles.add(tile) ;
                }
            }

            mTilesSelected.clear() ;
        }
        catch (PlayerBoard.PlayerBoardException e)
        {
            e.printStackTrace() ;
        }
    }

    public void addTilesInFloor(ChooseFloorLine move, ArrayList<Tile> asideTiles)
    {
        try
        {
            for (Tile tile : mTilesSelected)
            {
                if (! mPlayerBoard.isFloorLineFull())
                {
                    mPlayerBoard.addToFloorLine(tile) ;
                }
                else
                {
                    // If full, according to the rules the tile should go to the box cover.
                    asideTiles.add(tile) ;
                }
            }

            mTilesSelected.clear() ;
        }
        catch (PlayerBoard.PlayerBoardException e)
        {
            e.printStackTrace() ;
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

    public void clearTilesSelected()
    {
        mTilesSelected.clear() ;
    }

    /**
     * Check if the game is over by checking the user's wall.
     * @return true if the game is over
     */
    public boolean checkGameOver()
    {
        return mPlayerBoard.isWallRowFull() ;
    }

    public void setTilesSelected(ArrayList<Tile> selected)
    {
        mTilesSelected = (ArrayList<Tile>) selected.clone() ;
    }

    public void setPatternLine(int row, Tile[] line)
    {
        for (int i = 0 ; i < line.length ; i ++)
        {
            try
            {
                mPlayerBoard.setInPatterLines(row - 1, i, line[i]) ;
            }
            catch (PlayerBoard.PlayerBoardException e)
            {
                e.printStackTrace() ;
            }
        }
    }

    public void setFloorLine(ArrayList<Tile> line)
    {
        for (int i = 0 ; i < line.size() ; i ++)
        {
            try
            {
                mPlayerBoard.setInFloorLine(i, line.get(i)) ;
            }
            catch (PlayerBoard.PlayerBoardException e)
            {
                e.printStackTrace() ;
            }
        }
    }

    public void setBoard(PlayerBoard board)
    {
        mPlayerBoard = board ;
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

    public Tile getInFloorLine(int i)
    {
        Tile tile = Tile.EMPTY ;

        try
        {
            tile = mPlayerBoard.getInFloorLine(i) ;
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

    public Tile[] getPatternLine(int row)
    {
        return mPlayerBoard.getPatternLine(row) ;
    }

    public ArrayList<Tile> getFloorLine()
    {
        return mPlayerBoard.getFloorLine() ;
    }

    public PlayerBoard getBoard()
    {
        return mPlayerBoard ;
    }

    /**
     * Check if a tile can be placed in this line.
     * @param row the line
     * @return true if can be placed.
     */
    public boolean isPatternLineAccessible(int row)
    {
        return ! mPlayerBoard.isPatterLineFull(row) && mTilesSelected.size() != 0
                && mPlayerBoard.canBePlacedOnPatternLine(mTilesSelected.get(0), row) ;
    }

    /**
     * Check if a tile can be placed in one of the pattern lines.
     * @return true if can be placed.
     */
    public boolean isPatternLinesAccessible()
    {
        int j = 0 ;

        for (int i = 1 ; i <= PlayerBoard.SIZE_PATTERN_LINES ; i ++)
        {
            if (isPatternLineAccessible(i))
            {
                return true ;
            }
        }

        return false ;
    }

    /**
     * Check if a tile can be placed on the floor.
     * @return true if can be placed.
     */
    public boolean isFloorLineAccessible()
    {
        return ! mPlayerBoard.isFloorLineFull() ;
    }

    @Override
    public Object clone()
    {
        Player player = new Player(mName) {} ;
        player.mTilesSelected = (ArrayList<Tile>) mTilesSelected.clone();
        player.mPlayerBoard = (PlayerBoard) mPlayerBoard.clone() ;

        return player ;
    }
}