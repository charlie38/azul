package azul.view.resource;

import azul.model.tile.Tile;
import azul.view.ui.screen.InGame;
import azul.view.ui.screen.MainMenu;
import azul.view.ui.screen.Prepare;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ResourcesLoader
{
    // Game font.
    private Font mFont ;
    // Game title.
    private ImageAzul mGameTitle ;
    // Game icon.
    private ImageAzul mGameIcon ;
    // Background.
    private ImageAzul mBgTile ;
    // Tiles table.
    private ImageAzul mTable ;
    // Tiles.
    private ImageAzul mToken, mTokenS, mTokenB ;
	private ImageAzul mFlower, mFlowerS, mFlowerB ;
	private ImageAzul mClaw, mClawS, mClawB ;
	private ImageAzul mMushroom, mMushroomS, mMushroomB ;
    private ImageAzul mCrystal, mCrystalS, mCrystalB ;
    private ImageAzul mEye, mEyeS, mEyeB ;
    // Player board.
    private ImageAzul mBoard, mBoardF ;
    private ImageAzul mArrow, mArrowF;
    private ImageAzul mWallCase, mWallCaseS ;
    private ImageAzul mPatternLinesCase, mPatternLinesCaseS ;
    // Tiles factory.
    private ImageAzul mFactory, mFactoryF ;
    private ImageAzul mFactoryCase, mFactoryCaseS ;
    // In game navigation.
    private ImageAzul mPause ;
    private ImageAzul mPlay ;
    private ImageAzul mPrevious ;
    private ImageAzul mNext ;
    private ImageAzul mSettings ;
    // Prepare screen.
    private ImageAzul mDelete ;

    public ResourcesLoader()
    {
        loadFont() ;
        // Initialization of all images.
        loadImages() ;
    }

    private void loadFont()
    {
        try
        {
            mFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/liquidism_part_2.ttf")) ;
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(mFont) ;
        }
        catch (FontFormatException | IOException e)
        {
            e.printStackTrace() ;
        }
    }

    private void loadImages()
    {
        mGameTitle = new ImageAzul("res/img/title.png") ;
        mGameIcon = new ImageAzul("res/img/eye.png") ;
        mBgTile = new ImageAzul("res/img/bg.png") ;
        mTable = new ImageAzul("res/img/table.png") ;
        mToken = new ImageAzul("res/img/token.png") ;
        mTokenS = new ImageAzul("res/img/token_selected.png") ;
        mTokenB = new ImageAzul("res/img/token_blur.png") ;
        mFlower = new ImageAzul("res/img/flower.png") ;
        mFlowerS = new ImageAzul("res/img/flower_selected.png") ;
        mFlowerB = new ImageAzul("res/img/flower_blur.png") ;
        mClaw = new ImageAzul("res/img/claw.png") ;
        mClawS = new ImageAzul("res/img/claw_selected.png") ;
        mClawB = new ImageAzul("res/img/claw_blur.png") ;
        mMushroom = new ImageAzul("res/img/mushroom.png") ;
        mMushroomS = new ImageAzul("res/img/mushroom_selected.png") ;
        mMushroomB = new ImageAzul("res/img/mushroom_blur.png") ;
        mCrystal = new ImageAzul("res/img/crystal.png") ;
        mCrystalS = new ImageAzul("res/img/crystal_selected.png") ;
        mCrystalB = new ImageAzul("res/img/crystal_blur.png") ;
        mEye = new ImageAzul("res/img/eye.png") ;
        mEyeS = new ImageAzul("res/img/eye_selected.png") ;
        mEyeB = new ImageAzul("res/img/eye_blur.png") ;
        mBoard = new ImageAzul("res/img/board_borders.png") ;
        mBoardF = new ImageAzul("res/img/board_borders_focused.png") ;
        mArrow = new ImageAzul("res/img/board_arrow.png") ;
        mArrowF = new ImageAzul("res/img/board_arrow_focused.png") ;
        mWallCase = new ImageAzul("res/img/greencase.png") ;
        mWallCaseS = new ImageAzul("res/img/greencase_blur.png") ;
        mPatternLinesCase = new ImageAzul("res/img/browncase.png") ;
        mPatternLinesCaseS = new ImageAzul("res/img/browncase_blur.png") ;
        mFactory = new ImageAzul("res/img/bowl.png") ;
        mFactoryF = new ImageAzul("res/img/bowl_focused.png") ;
        mFactoryCase = new ImageAzul("res/img/bowlcase.png") ;
        mFactoryCaseS = new ImageAzul("res/img/bowlcase_blur.png") ;
        mPause = new ImageAzul("res/img/pause.png") ;
        mPlay = new ImageAzul("res/img/play.png") ;
        mPrevious = new ImageAzul("res/img/rewind.png") ;
        mNext = new ImageAzul("res/img/forward.png") ;
        mSettings = new ImageAzul("res/img/settings.png") ;
        mDelete = new ImageAzul("res/img/trash.png") ;
    }

    public Font getFont(float pt)
    {
        return mFont.deriveFont(pt) ;
    }
    
    public Image getIngredient(Tile t)
    {
    	switch (t)
    	{
    		case CRYSTAL : return mCrystal.get() ;
    		case EYE : return mEye.get() ;
    		case CLAW : return mClaw.get() ;
    		case FLOWER : return mFlower.get() ;
    		case MUSHROOM : return mMushroom.get() ;
            case FIRST_PLAYER_MAKER : return mToken.get() ;
    		default : return null ;
    	}
    }

    public Image getIngredientSelected(Tile t)
    {
        switch (t)
        {
            case CRYSTAL : return mCrystalS.get() ;
            case EYE : return mEyeS.get() ;
            case CLAW : return mClawS.get() ;
            case FLOWER : return mFlowerS.get() ;
            case MUSHROOM : return mMushroomS.get() ;
            case FIRST_PLAYER_MAKER : return mTokenS.get() ;
            default : return null ;
        }
    }

    public Image getIngredientBlurred(Tile t)
    {
        switch (t)
        {
            case CRYSTAL : return mCrystalB.get() ;
            case EYE : return mEyeB.get() ;
            case CLAW : return mClawB.get() ;
            case FLOWER : return mFlowerB.get() ;
            case MUSHROOM : return mMushroomB.get() ;
            case FIRST_PLAYER_MAKER : return mTokenB.get() ;
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

    public Image getBoardFocused()
    {
        return mBoardF.get() ;
    }

    public Image getWallCase()
    {
        return mWallCase.get() ;
    }

    public Image getWallCaseSelected()
    {
        return mWallCaseS.get() ;
    }

    public Image getPatternLinesCase()
    {
        return mPatternLinesCase.get() ;
    }

    public Image getPatternLinesCaseSelected()
    {
        return mPatternLinesCaseS.get() ;
    }

    public Image getArrow()
    {
        return mArrow.get() ;
    }

    public Image getArrowFocused()
    {
        return mArrowF.get() ;
    }

    public Image getFactory()
    {
        return mFactory.get() ;
    }

    public Image getFactoryFocused()
    {
        return mFactoryF.get() ;
    }

    public Image getFactoryCase()
    {
        return mFactoryCase.get() ;
    }

    public Image getFactoryCaseSelected()
    {
        return mFactoryCaseS.get() ;
    }

    public Image getPause()
    {
        return mPause.get().getScaledInstance(
                InGame.PAUSE_WIDTH,
                InGame.PAUSE_HEIGHT,
                Image.SCALE_SMOOTH) ;
    }

    public Image getPlay()
    {
        return mPlay.get().getScaledInstance(
                InGame.PLAY_WIDTH,
                InGame.PLAY_HEIGHT,
                Image.SCALE_SMOOTH) ;
    }

    public Image getPrevious()
    {
        return mPrevious.get().getScaledInstance(
                InGame.PREVIOUS_WIDTH,
                InGame.PREVIOUS_HEIGHT,
                Image.SCALE_SMOOTH) ;
    }

    public Image getNext()
    {
        return mNext.get().getScaledInstance(
                InGame.NEXT_WIDTH,
                InGame.NEXT_HEIGHT,
                Image.SCALE_SMOOTH) ;
    }

    public Image getSettings()
    {
        return mSettings.get().getScaledInstance(
                InGame.SETTINGS_WIDTH,
                InGame.SETTINGS_HEIGHT,
                Image.SCALE_SMOOTH) ;
    }

    public Image getDelete()
    {
        return mDelete.get().getScaledInstance(
                Prepare.DELETE_WIDTH,
                Prepare.DELETE_HEIGHT,
                Image.SCALE_SMOOTH) ;
    }
}