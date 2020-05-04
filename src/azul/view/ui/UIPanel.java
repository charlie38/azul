package azul.view.ui;

import azul.view.Display;

import javax.swing.*;
import java.awt.*;

public class UIPanel extends JPanel
{
    // UI states.
    public enum State { MAIN_MENU, IN_GAME, SETTINGS }

    // Background colors.
    public static final Color BG_MAIN_MENU = Color.DARK_GRAY ;
    public static final Color BG_IN_GAME = new Color(0, 0, 0, 0) ;
    public static final Color BG_SETTINGS = Color.DARK_GRAY ;

    // Root ref.
    private Display mDisplay ;
    // Current state.
    private State mState ;
    // Screens.
    private MainMenu mMainMenu ;
    private InGame mInGame ;
    private Settings mSettings ;

    /**
     * Used to draw the UI.
     * @param display the window.
     */
    public UIPanel(Display display)
    {
        mDisplay = display ;

        initialize() ;
    }

    private void initialize()
    {
        mMainMenu = new MainMenu(mDisplay) ;
        mInGame = new InGame(mDisplay) ;
        mSettings = new Settings(mDisplay) ;

        add(mMainMenu) ;
        add(mInGame) ;
        add(mSettings) ;

        // Start in the main menu.
        onGoMainMenu() ;
    }

    /**
     * Called when user goes to the main menu.
     */
    public void onGoMainMenu()
    {
        mState = State.MAIN_MENU ;
        setVisibilities() ;
    }

    /**
     * Called when user starts a game.
     */
    public void onGoInGame()
    {
        mState = State.IN_GAME ;
        setVisibilities() ;

        mDisplay.startGame(4) ;
    }

    /**
     * Called when user goes to settings.
     */
    public void onGoSettings()
    {
        mState = State.SETTINGS ;
        setVisibilities() ;
    }

    private void setVisibilities()
    {
        mMainMenu.setVisible(false) ;
        mInGame.setVisible(false) ;
        mSettings.setVisible(false) ;

        switch (mState)
        {
            case MAIN_MENU : mMainMenu.setVisible(true) ; break ;
            case IN_GAME : mInGame.setVisible(true) ; break ;
            case SETTINGS : mSettings.setVisible(true) ;
        }
    }

    public State getState()
    {
        return mState ;
    }
}