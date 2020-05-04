package azul.view.drawables;

import azul.view.Display;

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
    }

    public void startGame(int nbPlayers)
    {
        mBoards.clear() ;
        mFactories.clear() ;
        // Create new game objects.
        createBackground() ;
        createTable() ;
        createPlayers(nbPlayers) ;
        createFactories(mDisplay.getGame().getNbTilesFactories(nbPlayers)) ;
        // Refresh.
        repaint() ;
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

    @Override
    public void paint(Graphics g)
    {
        super.paint(g) ;

        if (mDisplay.getGame().isGameRunning())
        {
            paintBackground(g) ;
            paintTable(g) ;
            paintPlayersBoard(g) ;
            paintTilesFactories(g) ;
        }
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