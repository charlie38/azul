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
import java.util.Random;

public class IAEasy extends IA {
	
	//Limit of negative points by a surplus of ingredients
	public static final int MAX_NEGATIVE_SCORE = 2;
	
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
		chooseTarget();
		
		if(mGame.isTableEmpty())
		{
			return chooseFactory();
		}
		
		if(mGame.isFactoriesEmpty())
		{
			return chooseTable();
		}
		
		return chooseFactory();
	}
	
	private void chooseTarget()
	{
		mTargetSquare = chooseCase();
		mTargetLine = mTargetSquare.x;
		mTargetTile = PlayerBoard.getWallTile(mTargetSquare.x, mTargetSquare.y);
	}
	
	private boolean isGoodLine()
	{
		return mGame.getPlayer().getBoard().canBePlacedOnPatternLine(mTargetTile, mTargetLine);
	}
	
	private Couple chooseCase()
	{
		Configuration mConfig = new Configuration(mGame,-1,-1);
		mConfig = mConfig.choixmax();
		return new Couple(mConfig.getXCible(),mConfig.getYCible());
	}
	
	private Move chooseFactory()
	{
		//Selected factory
		TilesFactory factory;
		//Factories with the type of the targeted tile
		ArrayList<Integer> targetFactories = new ArrayList<>() ;
		
		//Facoties selectable without the type of the targeted tile
		ArrayList<Integer> selectableFactories = new ArrayList<>() ;

		/*CHOICE OF FACTORY*/
        for (int i = 0 ; i < mGame.getFactories().size() ; i ++)
        {
            if (!mGame.getFactory(i).isEmpty())
            {
            	if(mGame.getFactory(i).isTile(mTargetTile))
            	{
            		targetFactories.add(i);
            	}
            	else
            	{
            		selectableFactories.add(i);
            	}
            }
        }
        
        if(targetFactories.isEmpty())
        {
        	factory = mGame.getFactory(selectableFactories.get(0)) ;
        	/*CHOICE OF TILES*/
            // Get the tiles in the factory.
            ArrayList<Tile> factoryTiles = (ArrayList<Tile>) factory.getTiles().clone() ;
            
            // And get all the factory tiles of this color.
            Tile tile = factory.getTile(mRandom.nextInt(TilesFactory.SIZE_FACTORY)) ;
            // And get all the factory tiles of this color.
            ArrayList<azul.model.tile.Tile> tilesSelected = factory.take(tile) ;
            
            // Play it.
            return new TakeInFactory(mGame.getPlayer(), tilesSelected, factory, factoryTiles) ;
        }
        else
        {
        	factory = mGame.getFactory(targetFactories.get(0)) ;
        	/*CHOICE OF TILES*/
            // Get the tiles in the factory.
            ArrayList<Tile> factoryTiles = (ArrayList<Tile>) factory.getTiles().clone() ;
            
            // And get all the factory tiles of this color.
            ArrayList<azul.model.tile.Tile> tilesSelected = factory.take(mTargetTile) ;
            
            // Play it.
            return new TakeInFactory(mGame.getPlayer(), tilesSelected, factory, factoryTiles) ;
        }
	}
	
	private Move chooseTable()
	{
		int nbTile = mGame.numberOfTileTable(mTargetTile);
		if((nbTile - mTargetLine) > MAX_NEGATIVE_SCORE)
		{	
			//Take another ingredient ( choose tile ? ) 
			return null;
		}
		
		if(nbTile == 0)
		{
			ArrayList<Integer> selectableTiles = new ArrayList<>() ;

	        for (int i = 0 ; i < Game.SIZE_TILES_TABLE ; i ++)
	        {
	            if (mGame.getInTilesTable(i) != Tile.EMPTY)
	            {
	                selectableTiles.add(i) ;
	            }
	        }
	        // Get the tiles on the table.
	        ArrayList<Tile> tableTiles = (ArrayList<Tile>) mGame.getTilesTable().clone() ;
	        // Get the selected tile.
	        Tile tile = mGame.getInTilesTable(selectableTiles.get(mRandom.nextInt(selectableTiles.size()))) ;
	        // And get all the table tiles of this color.
	        ArrayList<azul.model.tile.Tile> tilesSelected = mGame.takeOnTable(tile) ;
	        // Play it.
	        return new TakeOnTable(mGame.getPlayer(), tilesSelected,
	                ! azul.model.tile.Tile.isFirstPlayerMakerTaken(), tableTiles) ;
		}
		
		else
		{
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
		return new ChoosePatternLine(mGame.getPlayer(), mGame.getTilesAside(), mGame.getTilesRemaining(), mTargetLine - 1) ;
	}
	
	private Move selectFloorLine()
	{
		return new ChooseFloorLine(mGame.getPlayer(), mGame.getTilesAside(), mGame.getTilesRemaining()) ;
	}
}