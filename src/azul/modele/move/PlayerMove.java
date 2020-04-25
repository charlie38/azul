package azul.modele.move;

import azul.modele.player.Player;
import azul.modele.tiles.Tile;

import java.util.ArrayList;

public class PlayerMove extends Move
{
    private Player mPlayer ;
    // If a 'take tiles' move.
    private ArrayList<Tile> mTilesSelected ;
    // If a 'take tiles from table' move.
    private boolean mIsFirstToTakeFromTable ;
    // If a 'place tile' move.
    private Tile mTileSelected ;
    private int mRow ;
    private int mColumn ;

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
     * Contains the player's intentions during his turn ; he placed his tile on one of his pattern lines.
     * @param type can only be 'Type.PLAYER_PLACE_TILE_IN_PATTERN'.
     * @param row the pattern line on which the player wants to put his tile (the top one is 1 and bottom one 5).
     */
    public PlayerMove(Type type, Player player, Tile tileSelected, int row)
    {
        super(type, Type.PLAYER_PLACE_TILE_IN_PATTERN) ;

        mPlayer = player ;
        mTileSelected = tileSelected ;
        mRow = row ;
    }

    // TODO may be useless, depending if we use 'Player.decorateWall() -> automatised decoration'
    /**
     * Contains the player's intentions during his turn ; he placed his tile on the wall.
     * @param type can only be 'Type.PLAYER_PLACE_TILE_IN_WALL'.
     * @param row the wall line on which the player wants to put his tile (the top one is 1 and bottom one 5).
     * @param column the wall column on which the player wants to put his tile (the left one is 1 and right one 5).
     */
    public PlayerMove(Type type, Player player, Tile tileSelected, int row, int column)
    {
        super(type, Type.PLAYER_PLACE_TILE_IN_WALL) ;

        mPlayer = player ;
        mTileSelected = tileSelected ;
        mRow = row ;
        mColumn = column ;
    }

    /**
     * Contains the player's intentions during his turn ; he placed his tile on his floor line.
     * @param type can only be 'Type.PLAYER_PLACE_TILE_IN_FLOOR'.
     */
    public PlayerMove(Type type, Player player, Tile tileSelected)
    {
        super(type, Type.PLAYER_PLACE_TILE_IN_FLOOR) ;

        mPlayer = player ;
        mTileSelected = tileSelected ;
    }

    /**
     * Getters.
     */

    public Player getPlayer()
    {
        return mPlayer ;
    }

    public Tile getTileSelected()
    {
        return mTileSelected ;
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

    public int getColumn()
    {
        return mColumn ;
    }
}
