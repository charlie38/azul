package azul.view;

import azul.controller.Mediator;
import azul.model.Game;
import azul.view.adapter.MouseAdapter;
import azul.view.adapter.MouseTutoAdapter;
import azul.view.resource.ResourcesLoader;
import azul.view.ui.UIPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Display implements Runnable, Observer
{
	// Menu/game states.
	public enum State { MAIN_MENU, TUTORIAL, PREPARE, PREPARE_IAS, CREDITS, IN_GAME, SETTINGS, GAME_OVER }

	// Background colors.
	public static final Color BG_MAIN_MENU = new Color(0x4A4E49) ;
	public static final Color BG_TUTORIAL = new Color(0x4A4E49);
	public static final Color BG_PREPARE = new Color(0x4A4E49) ;
	public static final Color BG_PREPARE_IAS = new Color(0x4A4E49) ;
	public static final Color BG_CREDITS = new Color(0x4A4E49) ;
	public static final Color BG_IN_GAME = new Color(0X4A4E49) ;
	public static final Color BG_SETTINGS = new Color(0X4A4E49) ;
	public static final Color BG_GAME_OVER = new Color(0X4A4E49) ;
	// Text/component bg colors.
	public static final Color CL_PRIMARY = new Color(0xB0B0B0) ;
	public static final Color CL_SECONDARY = new Color(0xA0A0A0) ;
	public static final Color CL_TERTIARY = new Color(0x909090) ;
	public static final Color CL_QUATERNARY = new Color(0x808080) ;
	public static final Color CL_QUINARY = new Color(0x707070) ;
	public static final Color CD_PRIMARY = new Color(0x202020) ;
	public static final Color CD_SECONDARY = new Color(0x303030) ;
	public static final Color CD_TERTIARY = new Color(0x404040) ;
	// Window title.
	public final String WINDOW_TITLE = "AZUL" ;
	// Window sizes.
	public static final int WINDOW_DEFAULT_WIDTH = 1280 ;
	public static final int WINDOW_DEFAULT_HEIGHT = 720 ;
	public final int WINDOW_MIN_WIDTH = 1280 ;  // <!> Should not be more then the
	public final int WINDOW_MIN_HEIGHT = 720 ; // min(DEFAULT_WIDTH, DEFAULT_HEIGHT). <!>

	// Game model.
	private Game mGame ;
	// Controller part.
	private Mediator mMediator ;
	// Window object.
	private JFrame mFrame ;
	// UI.
	private UIPanel mUIPanel ;
	// To load images.
	private ResourcesLoader mResourcesLoader;
	// Current state.
	private State mState ;

	public Display(Game game, Mediator mediator)
	{
	    mGame = game ;
	    mMediator = mediator ;
		mResourcesLoader = new ResourcesLoader() ;
		// Observe the game.
		game.addObserver(this) ;

		initializeUI() ;
	}

	private void initializeUI()
	{
		// The panel.
		mUIPanel = new UIPanel(this) ;
		// Adapters.
		mUIPanel.addMouseListener(new MouseAdapter(this, mMediator)) ;
		mUIPanel.addMouseListener(new MouseTutoAdapter(this, mMediator)) ;
	}

	@Override
	public void run()
	{
		// Initialization of the frame.
		mFrame = new JFrame(WINDOW_TITLE) ;
		// Settings.
		mFrame.setIconImage(mResourcesLoader.getGameIcon()) ;
		mFrame.setSize(new Dimension(WINDOW_DEFAULT_WIDTH, WINDOW_DEFAULT_HEIGHT)) ;
		mFrame.setMinimumSize(new Dimension(WINDOW_MIN_WIDTH, WINDOW_MIN_HEIGHT)) ;
		mFrame.setResizable(true) ;
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		mFrame.setLocationRelativeTo(null) ;
		mFrame.add(mUIPanel) ;
		mFrame.setVisible(true) ;
		// Start in the main menu.
		onGoMainMenu() ;
	}
	@Override
	public void update(Observable observable, Object o)
	{
		if (mGame.getState() == Game.State.GAME_OVER)
		{
			onGoGameOver() ;
		}
	}

	/**
	 * Called when user goes to the main menu.
	 */
	public void onGoMainMenu()
	{
		mState = State.MAIN_MENU ;
	    mUIPanel.onGoMainMenu() ;
	}
	
	/**
	 * Called when user goes to the tutorial.
	 */
	public void onGoTutorial()
	{
		mState = State.TUTORIAL ;
	    mUIPanel.onGoTutorial() ;
	}

	/**
	 * Called when user selects the "START" option.
	 */
	public void onGoPrepare()
	{
		mState = State.PREPARE ;
		mUIPanel.onGoPrepare() ;
	}

	/**
	 * Called when user wants to play only with IAs.
	 */
	public void onGoPrepareIAs()
	{
		mState = State.PREPARE_IAS ;
		mUIPanel.onGoPrepareIAs() ;
	}

	/**
	 * Called when user selects the "CREDITS" option.
	 */
	public void onGoCredits()
	{
		mState = State.CREDITS ;
		mUIPanel.onGoCredits() ;
	}

	/**
	 * Called when user goes in the game screen.
	 */
	public void onGoInGame()
	{
		mState = State.IN_GAME ;
		mUIPanel.onGoInGame() ;
	}

	/**
	 * Called when user goes to settings.
	 */
	public void onGoSettings()
	{
		mState = State.SETTINGS ;
		mUIPanel.onGoSettings() ;
	}

	/**
	 * Called when the game is over.
	 */
	public void onGoGameOver()
	{
		mState = State.GAME_OVER ;
		mUIPanel.onGoGameOver() ;
	}

	/**
	 * Active full screen if called.
	 */
	public void toggleFullScreen()
	{
		GraphicsEnvironment env =  GraphicsEnvironment.getLocalGraphicsEnvironment() ;
		GraphicsDevice dev = env.getDefaultScreenDevice() ;
		dev.setFullScreenWindow(mFrame) ;
	}

	public int getWindowWidth()
	{
		// <!> the height also contains the pane width (window borders).
		return mFrame.getBounds().width - mFrame.getInsets().right - mFrame.getInsets().left ;
	}

	public int getWindowHeight()
	{
		// <!> the height also contains the pane height (window title bar and bottom border).
		return mFrame.getBounds().height - mFrame.getInsets().top - mFrame.getInsets().bottom ;
	}

	public Game getGame()
	{
		return mGame ;
	}

	public ResourcesLoader getResourcesLoader()
	{
		return mResourcesLoader;
	}

	public UIPanel getUIPanel()
	{
		return mUIPanel ;
	}

	public State getState()
	{
		return mState ;
	}

	public Frame getFrame()
	{
		return mFrame ;
	}

	public Mediator getMediator()
	{
		return mMediator ;
	}
}