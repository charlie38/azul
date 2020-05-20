package azul.view.ui;

import azul.view.Display;
import azul.view.drawable.DrawingGamePanel;
import azul.view.drawable.DrawingTutoPanel;
import azul.view.ui.screen.*;

import javax.swing.*;
import java.awt.*;

public class UIPanel extends JPanel
{
    // Root ref.
    private Display mDisplay ;
    // Screens.
    private MainMenu mMainMenu ;
    private Tutorial mTutorial ;
    private Prepare mPrepare ;
    private PrepareIAs mPrepareIAs ;
    private Credits mCredits ;
    private InGame mInGame ;
    private Settings mSettings ;
    private GameOver mGameOver ;
    // Drawing panels (canvases used to draw the game and tutorial).
    private DrawingGamePanel mGameCanvas ;
    private DrawingTutoPanel mTutoCanvas ;
    // To switch between screens.
    private CardLayout mLayout ;

    /**
     * Used to draw the UI, and mainly, changing the current screen printed.
     * @param display the window root.
     */
    public UIPanel(Display display)
    {
        mDisplay = display ;

        initialize() ;
    }

    private void initialize()
    {
        mLayout = new CardLayout(0, 0) ;
        // Canvases.
        mGameCanvas = new DrawingGamePanel(mDisplay) ;
        mTutoCanvas = new DrawingTutoPanel(mDisplay) ;
        // Create the components.
        mMainMenu = new MainMenu(mDisplay) ;
        mTutorial = new Tutorial(mDisplay, mTutoCanvas) ;
        mPrepare = new Prepare(mDisplay) ;
        mPrepareIAs = new PrepareIAs(mDisplay) ;
        mCredits = new Credits(mDisplay) ;
        mInGame = new InGame(mDisplay, mGameCanvas) ;
        mSettings = new Settings(mDisplay) ;
        mGameOver = new GameOver(mDisplay) ;
        // Add the components.
        setLayout(mLayout) ;
        add(mMainMenu, Display.State.MAIN_MENU.toString()) ;
        add(mTutorial, Display.State.TUTORIAL.toString()) ;
        add(mPrepare, Display.State.PREPARE.toString()) ;
        add(mPrepareIAs, Display.State.PREPARE_IAS.toString()) ;
        add(mCredits, Display.State.CREDITS.toString()) ;
        add(mInGame, Display.State.IN_GAME.toString()) ;
        add(mSettings, Display.State.SETTINGS.toString()) ;
        add(mGameOver, Display.State.GAME_OVER.toString()) ;
    }

    /**
     * Called when user goes to the main menu.
     */
    public void onGoMainMenu()
    {
        mLayout.show(this, Display.State.MAIN_MENU.toString()) ;
    }
    
    /**
     * Called when user goes to the tutorial.
     */
    public void onGoTutorial()
    {
        mTutorial.goToFirstStep() ;

        mLayout.show(this, Display.State.TUTORIAL.toString()) ;
    }

    /**
     * Called when user selects the "START" option.
     */
    public void onGoPrepare()
    {
        mPrepare.prepare() ;

        mLayout.show(this, Display.State.PREPARE.toString()) ;
    }

    /**
     * Called when user selects the "START" option.
     */
    public void onGoPrepareIAs()
    {
        mLayout.show(this, Display.State.PREPARE_IAS.toString()) ;
    }

    /**
     * Called when user selects the "CREDITS" option.
     */
    public void onGoCredits()
    {
        mLayout.show(this, Display.State.CREDITS.toString()) ;
    }

    /**
     * Called when user goes in game screen.
     */
    public void onGoInGame()
    {
        mLayout.show(this, Display.State.IN_GAME.toString()) ;
    }

    /**
     * Called when user goes to settings.
     */
    public void onGoSettings()
    {
        mLayout.show(this, Display.State.SETTINGS.toString()) ;
    }

    /**
     * Called when the game is over.
     */
    public void onGoGameOver()
    {
        mGameOver.refresh() ;

        mLayout.show(this, Display.State.GAME_OVER.toString()) ;
    }

    public DrawingGamePanel getGameCanvas()
    {
        return mGameCanvas ;
    }

    public DrawingTutoPanel getTutoCanvas()
    {
        return mTutoCanvas ;
    }
}