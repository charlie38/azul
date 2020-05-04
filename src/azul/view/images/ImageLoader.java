package azul.view.images;

import azul.model.tiles.Tile;
import azul.view.ui.MainMenu;

import java.awt.*;

public class ImageLoader
{
    // Game title.
    private ImageAzul mGameTitle ;
    // Game icon.
    private ImageAzul mGameIcon ;
    // Background.
    private ImageAzul mBgTile ;
    // Bowls table.
    private ImageAzul mTable ;
    // Tiles.
	private ImageAzul mEmpty ;
	private ImageAzul mFlower, mFlowerB ;
	private ImageAzul mClaw, mClawB ;
	private ImageAzul mMushroom, mMushroomB ;
    private ImageAzul mCrystal, mCrystalB ;
    private ImageAzul mEye, mEyeB ;
    // Player board.
    private ImageAzul mBoard ;
    private ImageAzul mWallCase ;
    private ImageAzul mPatternLineCase ;
    // Tiles factory.
    private ImageAzul mFactory ;
    private ImageAzul mFactoryCase ;

    public ImageLoader()
    {
        // Initialization of all images.
        loadImages() ;
    }

    private void loadImages()
    {
        mGameTitle = new ImageAzul("res/img/title.png") ;
        mGameIcon = new ImageAzul("res/img/eye.png") ;
        mBgTile = new ImageAzul("res/img/bg.png") ;
        mTable = new ImageAzul("res/img/table.png") ;
        mEmpty = new ImageAzul("res/img/empty.png") ;
        mFlower = new ImageAzul("res/img/flower.png") ;
        mFlowerB = new ImageAzul("res/img/flower_blur.png") ;
        mClaw = new ImageAzul("res/img/claw.png") ;
        mClawB = new ImageAzul("res/img/claw_blur.png") ;
        mMushroom = new ImageAzul("res/img/mushroom.png") ;
        mMushroomB = new ImageAzul("res/img/mushroom_blur.png") ;
        mCrystal = new ImageAzul("res/img/crystal.png") ;
        mCrystalB = new ImageAzul("res/img/crystal_blur.png") ;
        mEye = new ImageAzul("res/img/eye.png") ;
        mEyeB = new ImageAzul("res/img/eye_blur.png") ;
        mBoard = new ImageAzul("res/img/board_borders.png") ;
        mWallCase = new ImageAzul("res/img/greencase.png") ;
        mPatternLineCase = new ImageAzul("res/img/browncase.png") ;
        mFactory = new ImageAzul("res/img/bowl.png") ;
        mFactoryCase = new ImageAzul("res/img/bowlcase.png") ;
    }
    
    public Image getIngredient(Tile t)
    {
    	switch(t)
    	{
    		case CRYSTAL : return mCrystal.get() ;
    		case EYE : return mEye.get() ;
    		case CLAW : return mClaw.get() ;
    		case FLOWER : return mFlower.get() ;
    		case MUSHROOM : return mMushroom.get() ;
    		case EMPTY : return mEmpty.get() ;
    		default : return null ;
    	}
    }

    public Image getGameTitle()
    {
        return mGameTitle.get().getScaledInstance(
                MainMenu.GAME_TITLE_WIDTH,
                MainMenu.GAME_TITLE_HEIGHT,
                Image.SCALE_SMOOTH) ;
    }

    public Image getGameIcon()
    {
        return mGameIcon.get() ;
    }

    public Image getBgTile()
    {
        return mBgTile.get() ;
    }

    public Image getTable()
    {
        return mTable.get() ;
    }

    public Image getBoard()
    {
        return mBoard.get() ;
    }

    public Image getWallCase()
    {
        return mWallCase.get() ;
    }

    public Image getPatternLinesCase()
    {
        return mPatternLineCase.get() ;
    }

    public Image getFactory()
    {
        return mFactory.get() ;
    }

    public Image getFactoryCase()
    {
        return mFactoryCase.get() ;
    }
}