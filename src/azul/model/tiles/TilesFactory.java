package azul.model.tiles;

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
     * Prepare the factory for this game round by filling it with 4 tiles (while the bag is not empty).
     * @param remainingTiles the remaining tiles in the bag to be placed in the factories.
     * @param sideTiles the tiles in the box cover, used if the bag is empty before the factory is full.
     */
    public void prepare(ArrayList<Tile> remainingTiles, ArrayList<Tile> sideTiles)
    {
        // Place 4 tiles in the factory.
        for (int i = 1 ; i <= 4 ; i ++)
        {
            if (remainingTiles.isEmpty())
            {
                if (! sideTiles.isEmpty())
                {
                    // If the bag is empty, put the tiles in the box cover inside the bag.
                    remainingTiles.addAll(sideTiles) ;
                    sideTiles.clear() ;
                }
                else
                {
                    // The box cover is also empty.
                    // According to the rules, if there is no tiles remaining, the game start.
                    return ;
                }
            }
            // Take a tile in the bag
            Tile tile = remainingTiles.get(mRandom.nextInt(remainingTiles.size())) ;
            // Add this tile from the bag to the factory.
            mTiles.add(tile) ;
            remainingTiles.remove(tile) ;
        }
    }

    /**
     * Give all the tiles in the factory with the color of 'tile' to the player.
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
