package azul.modele.player;

import azul.modele.move.PlayerMove;
import azul.modele.tiles.Tile;

public abstract class Player
{
    private PlayerBoard mPlayerBoard ;

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
     */
    public void play(PlayerMove move)
    {
        switch (move.getType())
        {
            case PLAYER_TAKE_FACTORY : takeTilesFromFactory(move) ; break ;
            case PLAYER_TAKE_TABLE : takeTilesFromTable(move) ; break ;
            case PLAYER_PLACE_TILE_IN_PATTERN : addTileInPattern(move) ; break ;
            case PLAYER_PLACE_TILE_IN_WALL: addTileInWall(move) ; break ;
            case PLAYER_PLACE_TILE_IN_FLOOR : addTileInFloor(move) ;
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
            mPlayerBoard.addToPatternLine(move.getTileSelected(), move.getLine()) ;
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
            mPlayerBoard.addToWall(move.getTileSelected(), move.getLine(), move.getColumn()) ;
        }
        catch (PlayerBoard.PlayerBoardException e)
        {
            e.printStackTrace() ;
        }
    }

    private void addTileInFloor(PlayerMove move)
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
