package azul.model.player;

import azul.model.move.PlayerMove;
import azul.model.tiles.Tile;

import java.util.ArrayList;

public abstract class Player
{
    public PlayerBoard mPlayerBoard ;

    /**
     * Extended by the IA or human player.
     */
    public Player()
    {
        mPlayerBoard = new PlayerBoard() ;
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
            case PLAYER_PLACE_TILE_IN_PATTERN : addTileInPattern(move) ; break ;
            case PLAYER_PLACE_TILE_IN_WALL: addTileInWall(move) ; break ;
            case PLAYER_PLACE_TILE_IN_FLOOR : addTileInFloor(move, asideTiles) ;
        }
    }

    private void takeTilesFromFactory(PlayerMove move)
    {
        // Nothing to do here, this move is just to update the UI and factory.
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

    private void addTileInPattern(PlayerMove move)
    {
        try
        {
            mPlayerBoard.addToPatternLine(move.getTileSelected(), move.getRow()) ;
        }
        catch (PlayerBoard.PlayerBoardException e)
        {
            e.printStackTrace() ;
        }
    }

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
    }

    private void addTileInFloor(PlayerMove move, ArrayList<Tile> asideTiles)
    {
        if (mPlayerBoard.isFloorLineFull())
        {
            // If full, according to the rules the tile should go to the box cover.
            asideTiles.add(move.getTileSelected()) ;
        }
        else
        {
            try
            {
                mPlayerBoard.addToFloorLine(move.getTileSelected()) ;
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
}
