package azul.view.ui;

import azul.view.Display;
import azul.view.ui.screen.Credits;
import azul.view.ui.screen.InGame;
import azul.view.ui.screen.MainMenu;
import azul.view.ui.screen.Settings;

import javax.swing.*;
import java.awt.*;

public class UIPanel extends JPanel
{
    // Root ref.
    private Display mDisplay ;
    // Screens.
    private MainMenu mMainMenu ;
    private Credits mCredits ;
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
        setLayout(new BorderLayout(0, 0)) ;

        mMainMenu = new MainMenu(mDisplay) ;
        mCredits = new Credits(mDisplay) ;
        mInGame = new InGame(mDisplay) ;
        mSettings = new Settings(mDisplay) ;
    }

    /**
     * Called when user goes to the main menu.
     */
    public void onGoMainMenu()
    {
        setVisibilities() ;
    }

    /**
     * Called when user selects the credits option.
     */
    public void onGoCredits()
    {
        setVisibilities() ;
    }

    /**
     * Called when user starts a game.
     */
    public void onGoInGame()
    {
        setVisibilities() ;
    }

    /**
     * Called when user goes to settings.
     */
    public void onGoSettings()
    {
        setVisibilities() ;
    }

    private void setVisibilities()
    {
        if (getComponentCount() != 0)
        {
            removeAll() ;
        }

        switch (mDisplay.getState())
        {
            case MAIN_MENU : add(mMainMenu) ; break ;
            case CREDITS : add(mCredits) ; break ;
            case IN_GAME : add(mInGame) ; break ;
            case SETTINGS : add(mSettings) ;
        }
    }
}