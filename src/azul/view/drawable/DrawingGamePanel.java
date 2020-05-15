package azul.view.drawable;

import azul.view.Display;
import azul.view.drawable.game.board.PlayerBoard;
import azul.view.drawable.game.factory.TilesFactory;
import azul.view.drawable.game.table.Table;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawingGamePanel extends JPanel
{
    // /!\ Animation minimum delay for update <~> game loop.
    private final int ANIMATION_MIN_DELAY = 1000 / 60 ; // 60FPS

    // Root ref.
    private Display mDisplay ;
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
    public DrawingGamePanel(Display display)
    {
        mDisplay = display ;
        mBoards = new ArrayList<>() ;
        mFactories = new ArrayList<>() ;

        createTable() ;
        // To update all components animations, by redrawing the canvas once.
        // (avoid calling "DrawingPanel.repaint()" each time a component animation should refresh UI <~> like a game loop)
        new Timer(ANIMATION_MIN_DELAY, actionEvent -> repaint()).start() ;
    }

    public void startGame(int nbPlayers)
    {
        // Create new game objects.
        createPlayers(nbPlayers) ;
        createFactories(mDisplay.getGame().getNbTilesFactories(nbPlayers)) ;
    }

    private void createTable()
    {
        mTable = new Table(mDisplay,
                - Table.WIDTH_TABLE / 2,
                -Display.WINDOW_DEFAULT_HEIGHT / 2 + 10) ;
    }

    private void createPlayers(int nbPlayers)
    {
        for (PlayerBoard board : mBoards)
        {
            // /!\ Important ! Remove the observers, because can be notified before the GC take them in charge.
            mDisplay.getGame().deleteObserver(board) ;
        }

        mBoards.clear() ;

        for (int i = 0 ; i < nbPlayers ; i ++)
        {
            mBoards.add(new PlayerBoard(mDisplay,
                    (i % 2 != 0 ? -1 : 1) * Display.WINDOW_DEFAULT_WIDTH / 2
                            - (i % 2 != 0 ? 0 : PlayerBoard.WIDTH_BOARD),
                    (i < 2 ? -1 : 1) * Display.WINDOW_DEFAULT_HEIGHT / 2
                            - (i < 2 ? 0 : PlayerBoard.HEIGHT_BOARD),
                    i)) ;
        }
    }

    private void createFactories(int nbFactories)
    {
        for (TilesFactory factory : mFactories)
        {
            // /!\ Important ! Remove the observers, because can be notified before the GC take them in charge.
            mDisplay.getGame().deleteObserver(factory) ;
        }

        mFactories.clear() ;

        int nbRows = (nbFactories == 5) ? 2 : nbFactories / 3 + nbFactories % 3 ;
        int factory = 0 ;

        for (int i = 0 ; i < nbRows ; i ++)
        {
            for (int j = 0 ; j < ((i == nbRows - 1 && nbFactories % 3 != 0 ) ? nbFactories % 3 : 3) ; j ++)
            {
                float deltaX = 0f ;

                switch (j)
                {
                    case 0 : deltaX = -1.5f ; break ;
                    case 1 : deltaX = -.5f ; break ;
                    case 2 : deltaX = .5f ; break ;
                }

                mFactories.add(new TilesFactory(mDisplay,
                        (int) (TilesFactory.WIDTH_FACTORY * deltaX),
                        Display.WINDOW_DEFAULT_HEIGHT / 2 - ((TilesFactory.HEIGHT_FACTORY + 10) * (i + 1)),
                        factory ++)) ;
            }
        }
    }

    /**
     * Called by the controller.
     * @param x the click x-coordinate on the Swing coordinate system.
     * @param y the click y-coordinate on the Swing coordinate system.
     * @return the drawable hit (if exists).
     */
    public Drawable onClick(int x, int y)
    {
        Drawable clicked ;

        for (PlayerBoard board : mBoards)
        {
            if ((clicked = board.onClick(x, y)) != null)
            {
                return clicked ;
            }
        }

        for (TilesFactory factory : mFactories)
        {
            if ((clicked = factory.onClick(x, y)) != null)
            {
                return clicked ;
            }
        }

        if ((clicked = mTable.onClick(x, y)) != null)
        {
            return clicked ;
        }

        return null ;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g) ;

        paintGame(g) ;
    }

    private void paintGame(Graphics g)
    {
        paintBackground(g) ;
        paintTable(g) ;
        paintPlayersBoard(g) ;
        paintTilesFactories(g) ;
    }

    private void paintBackground(Graphics g)
    {
        g.setColor(Display.BG_IN_GAME) ;
        g.fillRect(0, 0, getWidth(), getHeight()) ;
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

    protected float getResizeWidthCoef()
    {
        return (float) getWidth() / Display.WINDOW_DEFAULT_WIDTH ;
    }

    protected float getResizeHeightCoef()
    {
        return (float) getHeight() / Display.WINDOW_DEFAULT_HEIGHT ;
    }
}