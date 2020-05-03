package azul.view;

import azul.model.Game;
import azul.view.drawables.DrawingPanel;
import azul.view.images.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class Display implements Runnable
{
	// Window title.
	public static final String WINDOW_TITLE = "AZUL" ;
	// Window sizes.
	public static final int WINDOW_WIDTH = 1280 ;
	public static final int WINDOW_HEIGHT = 720 ;
	public static final int DEFAULT_RATIO = WINDOW_WIDTH / WINDOW_HEIGHT ;
	// Component size.
	public static final float DEFAULT_COEF = 1f ;
	public static final float SIZE_COEF = DEFAULT_COEF ;
	// Background color of the window.
	public static final Color WINDOW_BG = Color.DARK_GRAY ;

	// Game model.
	private Game mGame ;
	// Window object.
	private JFrame mFrame ;
	// Drawing canvas.
	private DrawingPanel mPanel ;
	// To load images.
	private ImageLoader mImgLoader ;
	
	public Display(Game game)
	{
	    mGame = game ;
		mImgLoader = new ImageLoader() ;
		mPanel = new DrawingPanel(this) ;
	}

	@Override
	public void run()
	{
		// Initialization of components.
		mFrame = new JFrame(WINDOW_TITLE) ;

		mFrame.getContentPane().setBackground(WINDOW_BG) ;
		mFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT) ;
		mFrame.setVisible(true) ;
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		mFrame.add(mPanel) ;

		// TODO remove this call from here.
		startGame(mGame.getNbPlayers()) ;
	}

	public void startGame(int nbPlayers)
	{
	    mPanel.startGame(nbPlayers) ;
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
		return mFrame.getBounds().width ;
	}

	public int getWindowHeight()
	{
		// <!> the height also contains the pane height.
		return mFrame.getBounds().height - mFrame.getInsets().top ;
	}

	public float getResizeCoefWidth()
	{
		return (float) getWindowWidth() / WINDOW_WIDTH ;
	}

	public float getResizeCoefHeight()
	{
		return (float) getWindowHeight() / WINDOW_HEIGHT ;
	}

	public Game getGame()
	{
		return mGame ;
	}

	public ImageLoader getImageLoader()
	{
		return mImgLoader ;
	}
}
