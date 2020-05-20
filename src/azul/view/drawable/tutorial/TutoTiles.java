package azul.view.drawable.tutorial;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionListener;

import azul.model.tile.Tile;
import azul.view.Display;
import azul.view.drawable.Drawable;

public class TutoTiles extends Drawable {
	
	// Request a focus animation.
    private static final int ANIMATION_DELAY = 100 ;
    private final boolean[] ANIMATION_PATTERN = { true, false, true,
            false, false, false, false, false, false, false, false, false, false, false } ;

    // Tile index in model representations.
    private int mFactoryIndex ;
    private int mIndex ;
    // Current part of the animation.
    private int mAnimationIndex ;
    
    //Type of tile.
    private Tile mTile;
    
	public TutoTiles(Display display,int x,int y,Tile t)
	{
		super(display, x, y, TutoFactory.WIDTH_TILE, TutoFactory.HEIGHT_TILE, ANIMATION_DELAY);
		mTile = t;
	}
	
	@Override
    public void paint(Graphics g)
    {
        super.paint(g) ;

        Point point = computeCoef() ;

        paintTile(g, point.x, point.y) ;
    }
	
	private void paintTile(Graphics g, int x,int y)
	{
		int width = (int) (mOriginalWidth * mCoef) ;
        int height = (int) (mOriginalHeight * mCoef) ;
        
        g.drawImage(getResourcesLoader().getFactoryCase(), x, y, width, height, null) ;
        g.drawImage(getResourcesLoader().getIngredient(mTile), x, y, width, height, null) ;
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
	
	public Tile getTile()
	{
		return mTile;
	}
	

}
