package azul.view.drawables;

import azul.view.Display;
import azul.view.ui.UIPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawingPanel extends JPanel
{
    // Root ref.
    private Display mDisplay ;
    // Game background.
    private Background mBackground ;
    // Bowls table
    private Table mTable ;
    // Players board.
    private ArrayList<PlayerBoard> mBoards ;
    // Tiles factories.
    private ArrayList<TilesFactory> mFactories ;

    /**
     * Used to draw the game objects.
     * @param display the current window.
     */
    public DrawingPanel(Display display)
    {
        mDisplay = display ;
        mBoards = new ArrayList<>() ;
        mFactories = new ArrayList<>() ;

        createBackground() ;
        createTable() ;
    }

    /**
     * Called when user goes to the main menu.
     */
    public void onGoMainMenu()
    {
        // Refresh.
        repaint() ;
    }

    /**
     * Called when user selects the credits option.
     */
    public void onGoCredits()
    {
        // Refresh.
        repaint() ;
    }

    /**
     * Called when user starts a game.
     */
    public void onGoInGame()
    {
        startGame(mDisplay.getGame().getNbPlayers()) ;
        // Refresh.
        repaint() ;
    }

    /**
     * Called when user goes to settings.
     */
    public void onGoSettings()
    {
        // Refresh.
        repaint() ;
    }

    public void startGame(int nbPlayers)
    {
        mBoards.clear() ;
        mFactories.clear() ;
        // Create new game objects.
        createPlayers(nbPlayers) ;
        createFactories(mDisplay.getGame().getNbTilesFactories(nbPlayers)) ;
    }

    private void createBackground()
    {
        mBackground = new Background(mDisplay) ;
    }

    private void createTable()
    {
        mTable = new Table(mDisplay,
                - Table.WIDTH_TABLE / 2,
                Display.WINDOW_DEFAULT_HEIGHT / 2 - Table.HEIGHT_TABLE) ;
    }

    private void createPlayers(int nbPlayers)
    {
        for (int i = 0 ; i < nbPlayers ; i ++)
        {
            mBoards.add(new PlayerBoard(mDisplay,
                    (i % 2 != 0 ? -1 : 1) * Display.WINDOW_DEFAULT_WIDTH / 2
                            - (i % 2 != 0 ? 0 : PlayerBoard.WIDTH_BOARD),
                    (i < 2 ? -1 : 1) * Display.WINDOW_DEFAULT_HEIGHT / 2
                            - (i < 2 ? 0 : PlayerBoard.HEIGHT_BOARD))) ;
        }
    }

    private void createFactories(int nbFactories)
    {
        int nbRows = nbFactories / 3 + nbFactories % 3 ;
        int factory = 0 ;

        for (int i = 0 ; i < nbRows ; i ++)
        {
            for (int j = 0 ; j < ((i == nbRows - 1 && nbFactories % 3 != 0 )? nbFactories % 3 : 3) ; j ++)
            {
                float deltaX = 0f ;

                switch (j)
                {
                    case 0 : deltaX = -2f ; break ;
                    case 1 : deltaX = -.5f ; break ;
                    case 2 : deltaX = 1f ; break ;
                }

                mFactories.add(new TilesFactory(mDisplay,
                        (int) (TilesFactory.WIDTH_FACTORY * deltaX),
                        (int) (TilesFactory.HEIGHT_FACTORY * (1.f + 1.45f * i)),
                        factory ++)) ;
            }
        }
    }

    protected float getResizeCoefWidth()
    {
        return (float) getWidth() / Display.WINDOW_DEFAULT_WIDTH ;
    }

    protected float getResizeCoefHeight()
    {
        return (float) getHeight() / Display.WINDOW_DEFAULT_HEIGHT ;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g) ;

        switch (mDisplay.getState())
        {
            case MAIN_MENU : paintMainMenu(g) ; break ;
            case CREDITS : paintCredits(g) ; break ;
            case IN_GAME : paintInGame(g) ; break ;
            case SETTINGS : paintSettings(g) ;
        }
    }

    private void paintMainMenu(Graphics g)
    {
        g.setColor(Display.BG_MAIN_MENU) ;
        g.fillRect(0, 0, getWidth(), getHeight()) ;
    }

    private void paintCredits(Graphics g)
    {
        g.setColor(Display.BG_CREDITS) ;
        g.fillRect(0, 0, getWidth(), getHeight()) ;
    }

    private void paintInGame(Graphics g)
    {
        paintBackground(g) ;
        paintTable(g) ;
        paintPlayersBoard(g) ;
        paintTilesFactories(g) ;
    }

    private void paintSettings(Graphics g)
    {
        g.setColor(Display.BG_SETTINGS) ;
        g.fillRect(0, 0, getWidth(), getHeight()) ;
    }

    private void paintBackground(Graphics g)
    {
        mBackground.paint(g) ;
    }

    private void paintTable(Graphics g)
    {
        mTable.paint(g) ;
    }

    private void paintPlayersBoard(Graphics g)
    {
        for (PlayerBoard board : mBoards)
        {
            board.paint(g) ;
        }
    }

    private void paintTilesFactories(Graphics g)
    {
        for (TilesFactory factory : mFactories)
        {
            factory.paint(g) ;
        }
    }
}