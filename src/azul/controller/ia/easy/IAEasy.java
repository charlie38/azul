package azul.controller.ia.easy;

import azul.controller.ia.IA;
import azul.controller.ia.configuration.Configuration;
import azul.model.Couple;
import azul.model.Game;
import azul.model.move.*;
import azul.model.player.PlayerBoard;
import azul.model.tile.Tile;
import azul.model.tile.TilesFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class IAEasy extends IA {
	
	//Limit of negative points by a surplus of ingredients
	public static final int MAX_NEGATIVE_SCORE = 2;
	
	private ArrayList<Configuration> mPossibleSquares;
	
	private Configuration mConfig;
	
	//The target square in the wall
	private Couple mTargetSquare;
	
	//The type of the target tile
	private Tile mTargetTile;
	
	//The line number
	private int mTargetLine;
	
	private Random mRandom ;

	public IAEasy(Game game) 
	{
		super(game);
		
		mRandom = new Random() ;
	}

	@Override
	public void initialize() 
	{
		mConfig = new Configuration(mGame,0,0);
	}

	@Override
    public Move play()
    {
        switch (mGame.getState())
        {
            case CHOOSE_TILES : return chooseTiles() ;
            case SELECT_ROW : return selectRow() ;
            default : return null ;
        }
    }
	
	private Move chooseTiles()
	{	
		mPossibleSquares = mConfig.ConfigurationsFilles();
		chooseTarget();
		
		
		if(mGame.isTableEmpty())
		{
			return chooseFactory();
		}
		
		if(mGame.isFactoriesEmpty())
		{
			return chooseTable();
		}
		
		if(!isTargetInTable())
		{
			return chooseFactory();
		}
		
		if(!isTargetInFactories())
		{
			return chooseTable();
		}
		
		return chooseFactory();
	}
	
	private boolean isTargetInTable()
	{
		return mGame.numberOfTileTable(mTargetTile) != 0;
	}
	
	private boolean isTargetInFactories()
	{
		for(TilesFactory factory : mGame.getFactories())
		{
			if(factory.isTile(mTargetTile))
			{
				return true;
			}
		}
		return false;
	}
	
	private void chooseTarget()
	{
		chooseSquare();
		mTargetLine = mTargetSquare.x;
		mTargetTile = PlayerBoard.getWallTile(mTargetSquare.x, mTargetSquare.y);
		System.out.print("x : "+mTargetSquare.x+"   y : "+mTargetSquare.y+"    tile : ");
		System.out.println(mTargetTile);
	}
	
	private void chooseSquare()
	{
		mConfig = mPossibleSquares.get(0);
		mConfig = mConfig.choixmax();
		mTargetSquare = new Couple(mConfig.getXCible(),mConfig.getYCible());
		mPossibleSquares.remove(0);
	}
	
	private Move chooseFactory()
	{
		//Selected factory
		TilesFactory factory;
		//Factories with the type of the targeted tile
		ArrayList<Integer> targetFactories = new ArrayList<>() ;

		/*CHOICE OF FACTORY*/
		while(targetFactories.isEmpty())
		{
	        for (int i = 0 ; i < mGame.getFactories().size() ; i ++)
	        {
	            if (!mGame.getFactory(i).isEmpty())
	            {
	            	if(mGame.getFactory(i).isTile(mTargetTile))
	            	{
	            		targetFactories.add(i);
	            	}
	            }
	        }
	        if(targetFactories.isEmpty())
	        {
	        	chooseTarget();
	        }
		}
        
	    factory = mGame.getFactory(targetFactories.get(0)) ;
	    /*CHOICE OF TILES*/
	    // Get the tiles in the factory.
	    ArrayList<Tile> factoryTiles = (ArrayList<Tile>) factory.getTiles().clone() ;
	            
	    // And get all the factory tiles of this color.
	    ArrayList<azul.model.tile.Tile> tilesSelected = factory.take(mTargetTile) ;
	            
	    // Play it.
	    return new TakeInFactory(mGame.getPlayer(), tilesSelected, factory, factoryTiles) ;
	}
	
	private Move chooseTable()
	{
		while(mGame.numberOfTileTable(mTargetTile) == 0)
		{
			chooseTarget();
		}
		/*CHOICE OF TILES*/
		// Get the tiles on the table.
	    ArrayList<Tile> tableTiles = (ArrayList<Tile>) mGame.getTilesTable().clone() ;
	    // Get the selected tile.
	    // And get all the table tiles of this color.
	    ArrayList<azul.model.tile.Tile> tilesSelected = mGame.takeOnTable(mTargetTile) ;
	    // Play it.
	    return new TakeOnTable(mGame.getPlayer(), tilesSelected,
	           ! azul.model.tile.Tile.isFirstPlayerMakerTaken(), tableTiles) ;
		
	}
	
	private Move selectRow()
	{
	    {
	        if (! mGame.getPlayer().isPatternLinesAccessible())
	        {
	            return selectFloorLine() ;
	        }
	        // In most cases, should be in a pattern line.
	        return selectPatternLine() ;
	    }
	}
	
	private Move selectPatternLine()
	{
		while(! mGame.getPlayer().isPatternLineAccessible(mTargetLine))
		{
			chooseTarget();
		}
		return new ChoosePatternLine(mGame.getPlayer(), mGame.getTilesAside(), mGame.getTilesRemaining(), mTargetLine - 1) ;
			
	}
	
	private Move selectFloorLine()
	{
		return new ChooseFloorLine(mGame.getPlayer(), mGame.getTilesAside(), mGame.getTilesRemaining()) ;
	}
}