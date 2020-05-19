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
		
		if(!isTargetInTable())
		{
			return chooseFactory();
		}
		
		if(!isTargetInFactories())
		{
			return chooseTable();
		}
		
		System.out.println("Pas l'ingrédient ciblé dans le jeu !");
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
		mTargetSquare = chooseSquare();
		mTargetLine = mTargetSquare.x;
		mTargetTile = PlayerBoard.getWallTile(mTargetSquare.x, mTargetSquare.y);
		System.out.println(mTargetTile);
	}
	
	private Couple chooseSquare()
	{
		Configuration mConfig = new Configuration(mGame,0,0);
		mConfig = mConfig.choixmax();
		//Collections.sort(mConfig.Filles);
		System.out.print("Case  -->  x : "+mConfig.getXCible()+"    y : "+mConfig.getYCible()+"     ");
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
		if(mGame.getPlayer().isPatternLineAccessible(mTargetLine))
		{
			return new ChoosePatternLine(mGame.getPlayer(), mGame.getTilesAside(), mGame.getTilesRemaining(), mTargetLine - 1) ;
		}
		else
		{
			ArrayList<Integer> selectableRows = new ArrayList<>() ;

	        for (int i = 1 ; i <= PlayerBoard.SIZE_PATTERN_LINES ; i ++)
	        {
	            if (mGame.getPlayer().isPatternLineAccessible(i))
	            {
	                selectableRows.add(i) ;
	            }
	        }
	        // Get the selected row.
	        int row = selectableRows.get(mRandom.nextInt(selectableRows.size())) ;
	        // Play it.
	        return new ChoosePatternLine(mGame.getPlayer(), mGame.getTilesAside(), mGame.getTilesRemaining(), row - 1) ;
		}
	}
	
	private Move selectFloorLine()
	{
		return new ChooseFloorLine(mGame.getPlayer(), mGame.getTilesAside(), mGame.getTilesRemaining()) ;
	}
}