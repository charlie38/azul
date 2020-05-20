package azul.view.drawable.tutorial;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import azul.model.tile.Tile;
import azul.model.tile.TilesFactory;
import azul.view.Display;
import azul.view.drawable.Drawable;
import azul.view.drawable.game.factory.FactoryTile;

public class TutoFactory extends Drawable {
	
	// Request a focus animation.
    private static final int ANIMATION_DELAY = 100 ;
    private final boolean[] ANIMATION_PATTERN = { true, false, true,
            false, false, false, false, false, false, false, false, false, false, false } ;
    // /!\ Change all factory components size.
    public static final float SIZE_COEF = 1.2f ;
    // Factory bg.
    public static final int WIDTH_FACTORY = (int) (340 * SIZE_COEF) ;
    public static final int HEIGHT_FACTORY = (int) (200 * SIZE_COEF) ;
    // Cases and tiles.
    public final int DISTANCE_LEFT_TO_TILE = (int) (15 * SIZE_COEF) ;
    public final int DISTANCE_TOP_TO_TILE = (int) (30 * SIZE_COEF) ;
    public static final int WIDTH_TILE = (int) (60 * SIZE_COEF) ;
    public static final int HEIGHT_TILE = (int) (60 * SIZE_COEF) ;
    public final int SPACE_H_TILE = (WIDTH_FACTORY - (4 * WIDTH_TILE) - (4 * DISTANCE_LEFT_TO_TILE)) ;
    public final int SPACE_V_TILE = (HEIGHT_FACTORY - (4 * HEIGHT_TILE) - (4 * DISTANCE_TOP_TO_TILE)) ;
    
    // Current part of the animation.
    private int mAnimationIndex ;
	
    //The tutorial tileFactory
	private TilesFactory mTileFactory;
	
	private Display mDisplay;
	
	//List of Tile
	private ArrayList<Tile> mTiles;
	
	//List of Tile ( graphic )
	private ArrayList<TutoTiles> mTutoTiles;

	public TutoFactory(Display display) 
	{
		super(display, -Display.WINDOW_DEFAULT_WIDTH/3, -Display.WINDOW_DEFAULT_HEIGHT/4, WIDTH_FACTORY,HEIGHT_FACTORY, Drawable.DEFAULT_ANIMATION_DELAY);
		mDisplay = display;
		initFactory();	
	}
	
	public void initFactory()
	{
		mAnimationIndex = 0;
		mTileFactory = new TilesFactory();
		
		mTiles = new ArrayList<Tile>();
		
		mTiles.add(Tile.MUSHROOM);
		mTiles.add(Tile.EYE);
		mTiles.add(Tile.FLOWER);
		mTiles.add(Tile.MUSHROOM);
		
		mTileFactory.setTiles(mTiles);
		
		createFactoryTileList();
	}
	
	private void createFactoryTileList()
    {
        mTutoTiles = new ArrayList<>() ;

        int tileIndex = 0 ;

        for (int i = 0 ; i < 2 ; i ++)
        {
            for (int j = 0 ; j < 2 ; j ++)
            {
                mTutoTiles.add(new TutoTiles(
                		mDisplay,
                		mOriginalX + DISTANCE_LEFT_TO_TILE + j * (WIDTH_TILE + SPACE_H_TILE),
                        mOriginalY + DISTANCE_TOP_TO_TILE + i * (HEIGHT_TILE + SPACE_V_TILE),
                        mTiles.get(tileIndex++)));
            }
        }
    }
	
	public Drawable onClick(int x, int y)
    {
        for (TutoTiles tile : mTutoTiles)
        {
            if (tile.isClicked(x, y))
            {
            	System.out.println(tile.getTile());
                return tile ;
            }
        }

        return null ;
    }
	
	public void paint(Graphics g)
    {
		super.paint(g);
		Point point = computeCoef() ;
		paintFactory(g,point.x,point.y);
		paintTile(g,point.x,point.y);
    }
	
	private void paintFactory(Graphics g, int x, int y)
    {
		int width = (int) (WIDTH_FACTORY * mCoef) ;
        int height = (int) (HEIGHT_FACTORY * mCoef) ;
        g.drawImage(getResourcesLoader().getFactory(), x,y,width,height,null);
    }
	
	private void paintTile(Graphics g,int x, int y)
	{

	     for (TutoTiles tile : mTutoTiles)
	     {
	    	 tile.paint(g);
	     }
	}

	@Override
	protected ActionListener onAnimationChanged() 
	{
		return null;
	}

	@Override
	protected void onAnimationEnds() 
	{	
	}

}
