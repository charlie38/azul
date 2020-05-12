package azul.view.ui;

import azul.view.Display;
import azul.view.drawable.DrawingPanel;
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
    private Credits mCredits ;
    private InGame mInGame ;
    private Settings mSettings ;
    // Drawing panel (canvas used to draw the game).
    private DrawingPanel mCanvas ;
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
        // Create the components.
        mLayout = new CardLayout(0, 0) ;
        mMainMenu = new MainMenu(mDisplay) ;
        mTutorial = new Tutorial(mDisplay) ;
        mPrepare = new Prepare(mDisplay) ;
        mCredits = new Credits(mDisplay) ;
        mInGame = new InGame(mDisplay) ;
        mSettings = new Settings(mDisplay) ;
        mCanvas = new DrawingPanel(mDisplay) ;
        // In game screen is different. It needs a UI part and a drawing part.
        JPanel inGamePanel = new JPanel(new BorderLayout(0, 0)) ;
        inGamePanel.add(mInGame, BorderLayout.SOUTH) ;
        inGamePanel.add(mCanvas, BorderLayout.CENTER) ;
        // Add the components.
        setLayout(mLayout) ;
        add(mMainMenu, Display.State.MAIN_MENU.toString()) ;
        add(mTutorial, Display.State.TUTORIAL.toString()) ;
        add(mPrepare, Display.State.PREPARE.toString()) ;
        add(mCredits, Display.State.CREDITS.toString()) ;
        add(inGamePanel, Display.State.IN_GAME.toString()) ;
        add(mSettings, Display.State.SETTINGS.toString()) ;
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

    public DrawingPanel getDrawingPanel()
    {
        return mCanvas ;
    }
}