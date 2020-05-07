package azul.model.move;

import azul.model.player.Player;
import azul.model.tile.Tile;

import java.util.ArrayList;

public class PlayerMove extends Move
{
    private Player mPlayer ;
    // If a 'take tiles' move.
    private ArrayList<Tile> mTilesSelected ;
    // If a 'take tiles from table' move.
    private boolean mIsFirstToTakeFromTable ;
    // If a 'place tile' move.
    private int mRow ;

    /**
     * Contains the player's intentions during his turn ; he selected tiles from factory.
     * @param type can only be 'Type.PLAYER_TAKE_FACTORY'.
     */
    public PlayerMove(Type type, Player player, ArrayList<Tile> tilesSelected)
    {
        super(type, Type.PLAYER_TAKE_FACTORY) ;

        mPlayer = player ;
        mTilesSelected = tilesSelected ;
    }

    /**
     * Contains the player's intentions during his turn ; he selected tiles from table.
     * @param type can only be 'Type.PLAYER_TAKE_TABLE'.
     */
    public PlayerMove(Type type, Player player, ArrayList<Tile> tilesSelected, boolean isFirstToTakeFromTable)
    {
        super(type, Type.PLAYER_TAKE_TABLE) ;

        mPlayer = player ;
        mTilesSelected = tilesSelected ;
        mIsFirstToTakeFromTable = isFirstToTakeFromTable ;
    }

    /**
     * Contains the player's intentions during his turn ; he placed his tiles on one of his pattern lines.
     * @param type can only be 'Type.PLAYER_PLACE_TILES_IN_PATTERN'.
     * @param row the pattern line on which the player wants to put his tile (the top one is 1 and bottom one 5).
     */
    public PlayerMove(Type type, Player player, int row)
    {
        super(type, Type.PLAYER_PLACE_TILES_IN_PATTERN) ;

        mPlayer = player ;
        mRow = row ;
    }

    /**
     * Contains the player's intentions during his turn ; he placed his tiles on his floor line.
     * @param type can only be 'Type.PLAYER_PLACE_TILES_IN_FLOOR'.
     */
    public PlayerMove(Type type, Player player)
    {
        super(type, Type.PLAYER_PLACE_TILES_IN_FLOOR) ;

        mPlayer = player ;
    }

    /**
     * Getters.
     */

    public Player getPlayer()
    {
        return mPlayer ;
    }

    public boolean isFirstToTakeFromTable()
    {
        return mIsFirstToTakeFromTable ;
    }

    public ArrayList<Tile> getTilesSelected()
    {
        return mTilesSelected ;
    }

    public int getRow()
    {
        return mRow ;
    }
}
