package azul.model.move;

import azul.model.player.Player;
import azul.model.tile.Tile;
import azul.model.tile.TilesFactory;

import java.util.ArrayList;

public class PlayerMove extends Move
{
    private Player mPlayer ;
    // If a 'take tiles' move.
    private ArrayList<Tile> mTilesSelected ;
    // If a 'take tile in factory' move.
    private TilesFactory mFactory ;
    private ArrayList<Tile> mFactoryTiles ;
    // If a 'take tile in table' move.
    private ArrayList<Tile> mTableTiles ;
    // If a 'take tiles from table' move.
    private boolean mIsFirstToTakeFromTable ;
    // If a 'place tile' move.
    private int mRow ;
    // If a 'place tile in pattern line' move.
    private Tile[] mPatternLine ;
    // If a 'place tile in floor line' move.
    private ArrayList<Tile> mFloorLine ;


    /**
     * Contains the player's intentions during his turn ; he selected tiles from factory.
     * @param type can only be 'Type.PLAYER_TAKE_FACTORY'.
     */
    public PlayerMove(Type type, Player player, ArrayList<Tile> tilesSelected, TilesFactory factory,
                      ArrayList<Tile> factoryTiles)
    {
        super(type, Type.PLAYER_TAKE_FACTORY) ;

        mPlayer = player ;
        mTilesSelected = tilesSelected ;
        mFactory = factory ;
        mFactoryTiles = factoryTiles ;
    }

    /**
     * Contains the player's intentions during his turn ; he selected tiles from table.
     * @param type can only be 'Type.PLAYER_TAKE_TABLE'.
     */
    public PlayerMove(Type type, Player player, ArrayList<Tile> tilesSelected, boolean isFirstToTakeFromTable,
                      ArrayList<Tile> tableTiles)
    {
        super(type, Type.PLAYER_TAKE_TABLE) ;

        mPlayer = player ;
        mTilesSelected = tilesSelected ;
        mIsFirstToTakeFromTable = isFirstToTakeFromTable ;
        mTableTiles = (ArrayList<Tile>) tableTiles.clone();
        mFloorLine = (ArrayList<Tile>) mPlayer.getFloorLine().clone() ;
    }

    /**
     * Contains the player's intentions during his turn ; he placed his tiles on one of his pattern lines.
     * @param type can only be 'Type.PLAYER_PLACE_TILES_IN_PATTERN'.
     * @param rowIndex the pattern line on which the player wants to put his tile (the top one is 1 and bottom one 5) - 1.
     */
    public PlayerMove(Type type, Player player, int rowIndex)
    {
        super(type, Type.PLAYER_PLACE_TILES_IN_PATTERN) ;

        mPlayer = player ;
        mRow = rowIndex ;
        mPatternLine = player.getPatternLine(rowIndex + 1).clone() ;
    }

    /**
     * Contains the player's intentions during his turn ; he placed his tiles on his floor line.
     * @param type can only be 'Type.PLAYER_PLACE_TILES_IN_FLOOR'.
     */
    public PlayerMove(Type type, Player player)
    {
        super(type, Type.PLAYER_PLACE_TILES_IN_FLOOR) ;

        mPlayer = player ;
        mFloorLine = (ArrayList<Tile>) mPlayer.getFloorLine().clone() ;
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

    public ArrayList<Tile> getFactoryTiles()
    {
        return mFactoryTiles ;
    }

    public ArrayList<Tile> getTableTiles()
    {
        return mTableTiles ;
    }

    public Tile[] getPatternLine()
    {
        return mPatternLine ;
    }

    public ArrayList<Tile> getFloorLine()
    {
        return mFloorLine ;
    }

    public int getRow()
    {
        return mRow ;
    }

    public TilesFactory getFactory()
    {
        return mFactory ;
    }
}
