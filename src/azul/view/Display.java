package azul.view;

import azul.controller.Mouse;
import azul.model.Game;
import azul.view.drawable.DrawingPanel;
import azul.view.resource.ResourcesLoader;
import azul.view.ui.UIPanel;

import javax.swing.*;
import java.awt.*;

public class Display implements Runnable
{
	// Menu/game states.
	public enum State { MAIN_MENU, CREDITS, IN_GAME, SETTINGS }

	// Background colors.
	public static final Color BG_MAIN_MENU = new Color(0x4A4E49) ;
	public static final Color BG_CREDITS = new Color(0x4A4E49) ;
	public static final Color BG_IN_GAME = new Color(0X4A4E49) ;
	public static final Color BG_SETTINGS = new Color(0X4A4E49) ;
	// Window title.
	public final String WINDOW_TITLE = "AZUL" ;
	// Window sizes.
	public static final int WINDOW_DEFAULT_WIDTH = 1280 ;
	public static final int WINDOW_DEFAULT_HEIGHT = 720 ;
	public final int WINDOW_MIN_WIDTH = 1280 ;  // <!> Should not be more then the
	public final int WINDOW_MIN_HEIGHT = 720 ; // min(DEFAULT_WIDTH, DEFAULT_HEIGHT). <!>

	// Game model.
	private Game mGame ;
	// Window object.
	private JFrame mFrame ;
	// Drawing canvas.
	private DrawingPanel mDrawingPanel ;
	// UI.
	private UIPanel mUIPanel ;
	// To load images.
	private ResourcesLoader mResourcesLoader;
	// Current state.
	private State mState ;

	public Display(Game game)
	{
	    mGame = game ;
		mResourcesLoader = new ResourcesLoader() ;
		mDrawingPanel = new DrawingPanel(this) ;
		mUIPanel = new UIPanel(this) ;

		mDrawingPanel.addMouseListener(new Mouse(this)) ;
	}

	@Override
	public void run()
	{
		// Initialization of the frame.
		mFrame = new JFrame(WINDOW_TITLE) ;

		mFrame.setIconImage(mResourcesLoader.getGameIcon()) ;
		mFrame.setSize(new Dimension(WINDOW_DEFAULT_WIDTH, WINDOW_DEFAULT_HEIGHT)) ;
		mFrame.setMinimumSize(new Dimension(WINDOW_MIN_WIDTH, WINDOW_MIN_HEIGHT)) ;
		mFrame.setResizable(true) ;
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		mFrame.setLocationRelativeTo(null) ;
		mFrame.setLayout(new BorderLayout(0, 0)) ;
		mFrame.add(mDrawingPanel, BorderLayout.CENTER) ;
		mFrame.add(mUIPanel, BorderLayout.SOUTH) ;

		mFrame.setVisible(true) ;

		// Start in the main menu.
		onGoMainMenu() ;
	}

	/**
	 * Called when user goes to the main menu.
	 */
	public void onGoMainMenu()
	{
		mState = State.MAIN_MENU ;

	    mUIPanel.onGoMainMenu() ;
		mDrawingPanel.onGoMainMenu() ;
		// Refresh.
		mFrame.validate() ;
	}

	/**
	 * Called when user selects the credits option.
	 */
	public void onGoCredits()
	{
		mState = State.CREDITS ;

		mUIPanel.onGoCredits() ;
		mDrawingPanel.onGoCredits() ;
		// Refresh.
		mFrame.validate() ;
	}

	/**
	 * Called when user starts a game.
	 */
	public void onGoInGame()
	{
		mState = State.IN_GAME ;

		mGame.startGame(4) ;

		mUIPanel.onGoInGame() ;
		mDrawingPanel.onGoInGame() ;
		// Refresh.
		mFrame.validate() ;
	}

	/**
	 * Called when user goes to settings.
	 */
	public void onGoSettings()
	{
		mState = State.SETTINGS ;

		mUIPanel.onGoSettings() ;
		mDrawingPanel.onGoSettings() ;
		// Refresh.
		mFrame.validate() ;
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

	public DrawingPanel getDrawingPanel()
	{
		return mDrawingPanel ;
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
}