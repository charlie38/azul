package azul.model.tile;

import java.util.ArrayList;
import java.util.Random;

public class TilesFactory
{
    // Max nb of tiles in the factory.
    public static final int SIZE_FACTORY = 4 ;

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
        mTiles.clear() ;
        // Place 4 tiles in the factory.
        for (int i = 1 ; i <= SIZE_FACTORY ; i ++)
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
                    break ;
                }
            }
            // Take a tile in the bag
            Tile tile = remainingTiles.get(mRandom.nextInt(remainingTiles.size())) ;
            // Add this tile from the bag to the factory.
            mTiles.add(tile) ;
            remainingTiles.remove(tile) ;
        }

        for (int i = mTiles.size() ; i < SIZE_FACTORY ; i ++)
        {
            mTiles.add(Tile.EMPTY) ;
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
        for (int i = 0 ; i < SIZE_FACTORY ; i ++)
        {
            if (mTiles.get(i).equals(tile))
            {
                tiles.add(mTiles.get(i)) ;
                mTiles.set(i, Tile.EMPTY) ;
            }
        }

        return tiles ;
    }

    public void setTiles(ArrayList<Tile> tiles)
    {
        mTiles = tiles ;
    }

    public ArrayList<Tile> getTiles()
    {
        return mTiles ;
    }

    public Tile getTile(int i)
    {
        return mTiles.get(i) ;
    }
    
    public int numberOfTile(Tile tile)
    {
    	int res = 0;
    	for (Tile t : mTiles)
        {
            if (t == tile)
            {
                res++;
            }
        }
    	return res;
    }

    public boolean isEmpty()
    {
        for (Tile tile : mTiles)
        {
            if (tile != Tile.EMPTY)
            {
                return false ;
            }
        }

        return true ;
    }
    
    public boolean isTile(Tile selected)
    {
    	for (Tile tile : mTiles)
    	{
    		if(tile == selected)
    		{
    			return true;
    		}
    	}
    	
    	return false;
    }

    @Override
    public Object clone()
    {
        TilesFactory factory = new TilesFactory() ;
        factory.setTiles((ArrayList<Tile>) mTiles.clone()) ;

        return factory ;
    }
}