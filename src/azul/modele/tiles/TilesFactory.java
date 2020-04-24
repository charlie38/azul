package azul.modele.tiles;

import azul.modele.tiles.Tile;

import java.util.ArrayList;
import java.util.Random;

public class TilesFactory
{
    // Current tiles in the factory.
    private ArrayList<Tile> mTiles ;
    // To choose randomly the tiles in the bag.
    private Random mRandom ;

    public TilesFactory()
    {
        mTiles = new ArrayList<>() ;
        mRandom = new Random() ;
    }

    /**
     * Prepare the factory for this game round by filling it with tiles.
     * @param remainingTiles the remaining tiles in the bag to be placed in the factories.
     */
    public void prepare(ArrayList<Tile> remainingTiles)
    {
        // Place 4 tiles in the factory.
        for (int i = 1 ; i <= 4 && ! remainingTiles.isEmpty() ; i ++)
        {
            // According to the rules, if there is no tiles remaining, the game start.
            mTiles.add(remainingTiles.get(mRandom.nextInt(remainingTiles.size()))) ;
        }
    }

    /**
     * Give all the tiles in the factory with the color of 'tile'.
     * @param tile used to get the requested color.
     * @return the tiles in the factory.
     */
    public ArrayList<Tile> take(Tile tile)
    {
        ArrayList<Tile> tiles = new ArrayList<>() ;
        // Get the tiles in the factory.
        for (Tile tile_ : mTiles)
        {
            if (tile_.equals(tile))
            {
                tiles.add(tile_) ;
            }
        }
        // Remove them from the factory.
        mTiles.removeAll(tiles) ;

        return tiles ;
    }

    public ArrayList<Tile> getTiles()
    {
        return mTiles ;
    }
}
