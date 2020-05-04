package azul.view.ui;

import azul.view.Display;

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
        // Impossible to find a way of stacking the components...
        add(mMainMenu, BorderLayout.SOUTH) ;
        add(mCredits, BorderLayout.WEST) ;
        add(mInGame, BorderLayout.CENTER) ;
        add(mSettings, BorderLayout.NORTH) ;
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
        mMainMenu.setVisible(false) ;
        mCredits.setVisible(false) ;
        mInGame.setVisible(false) ;
        mSettings.setVisible(false) ;

        switch (mDisplay.getState())
        {
            case MAIN_MENU : mMainMenu.setVisible(true) ; break ;
            case CREDITS : mCredits.setVisible(true) ; break ;
            case IN_GAME : mInGame.setVisible(true) ; break ;
            case SETTINGS : mSettings.setVisible(true) ;
        }
    }
}