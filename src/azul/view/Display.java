package azul.view;

import azul.model.Game;
import azul.view.drawables.DrawingPanel;
import azul.view.images.ImageLoader;
import azul.view.ui.UIPanel;

import javax.swing.*;
import java.awt.*;

public class Display implements Runnable
{
	// Window title.
	public static final String WINDOW_TITLE = "AZUL" ;
	// Window sizes.
	public static final int WINDOW_DEFAULT_WIDTH = 1280 ;
	public static final int WINDOW_DEFAULT_HEIGHT = 720 ;
	public static final int WINDOW_MIN_WIDTH = 720 ;  // <!> Should not be more then the
	public static final int WINDOW_MIN_HEIGHT = 720 ; // min(DEFAULT_WIDTH, DEFAULT_HEIGHT). <!>
	// Component size.
	public static final float DEFAULT_COEF = 1f ;
	public static final float SIZE_COEF = DEFAULT_COEF ;

	// Game model.
	private Game mGame ;
	// Window object.
	private JFrame mFrame ;
	// Drawing canvas.
	private DrawingPanel mDrawingPanel ;
	// UI.
	private UIPanel mUIPanel ;
	// To load images.
	private ImageLoader mImgLoader ;
	
	public Display(Game game)
	{
	    mGame = game ;
		mImgLoader = new ImageLoader() ;
		mDrawingPanel = new DrawingPanel(this) ;
		mUIPanel = new UIPanel(this) ;
	}

	@Override
	public void run()
	{
		// Initialization of the frame.
		mFrame = new JFrame(WINDOW_TITLE) ;

		mFrame.setIconImage(mImgLoader.getGameIcon()) ;
		mFrame.setSize(new Dimension(WINDOW_DEFAULT_WIDTH, WINDOW_DEFAULT_HEIGHT)) ;
		mFrame.setMinimumSize(new Dimension(WINDOW_MIN_WIDTH, WINDOW_MIN_HEIGHT)) ;
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		mFrame.setLocationRelativeTo(null) ;
		mFrame.setContentPane(mDrawingPanel) ;
		mFrame.add(mUIPanel) ;
		mFrame.setVisible(true) ;
	}

	public void startGame(int nbPlayers)
	{
	    SwingUtilities.invokeLater(
	    		() ->
				{
					mGame.startGame(nbPlayers) ;
					mDrawingPanel.startGame(nbPlayers) ;
				}
		) ;
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

	public float getResizeCoefWidth()
	{
		return (float) getWindowWidth() / WINDOW_DEFAULT_WIDTH ;
	}

	public float getResizeCoefHeight()
	{
		return (float) getWindowHeight() / WINDOW_DEFAULT_HEIGHT ;
	}

	public Game getGame()
	{
		return mGame ;
	}

	public ImageLoader getImageLoader()
	{
		return mImgLoader ;
	}

	public DrawingPanel getDrawingPanel()
	{
		return mDrawingPanel ;
	}

	public UIPanel getUIPanel()
	{
		return mUIPanel ;
	}
}