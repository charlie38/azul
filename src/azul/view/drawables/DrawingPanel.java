package azul.view.drawables;

import azul.view.Display;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawingPanel extends JPanel
{
    // Root ref.
    private Display mDisplay ;
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

        setBackground(Display.WINDOW_BG) ;
    }

    public void startGame(int nbPlayers)
    {
        mBoards.clear() ;
        mFactories.clear() ;

        switch (nbPlayers)
        {
            case 2 :
                createTwoPlayers() ;
                createFiveFactories() ;
                break ;
            case 3 :
                createThreePlayers() ;
                createSevenFactories() ;
                break ;
            case 4 :
                createFourPlayers() ;
                createNineFactories() ;
        }
    }

    private void createTwoPlayers()
    {
        mBoards.add(new PlayerBoard(mDisplay,
                - mDisplay.getWindowWidth() / 2,
                - PlayerBoard.HEIGHT_BOARD / 2)) ;
        mBoards.add(new PlayerBoard(mDisplay,
                mDisplay.getWindowWidth() / 2 - PlayerBoard.WIDTH_BOARD,
                - PlayerBoard.HEIGHT_BOARD / 2)) ;
    }

    private void createThreePlayers()
    {
        mBoards.add(new PlayerBoard(mDisplay,
                - PlayerBoard.WIDTH_BOARD / 2,
                mDisplay.getWindowHeight() / 2 - PlayerBoard.HEIGHT_BOARD)) ;
        mBoards.add(new PlayerBoard(mDisplay,
                - mDisplay.getWindowWidth() / 2,
                - mDisplay.getWindowHeight() / 2)) ;
        mBoards.add(new PlayerBoard(mDisplay,
                mDisplay.getWindowWidth() / 2 - PlayerBoard.WIDTH_BOARD,
                - mDisplay.getWindowHeight() / 2)) ;
    }

    private void createFourPlayers()
    {
        mBoards.add(new PlayerBoard(mDisplay,
                - mDisplay.getWindowWidth() / 2,
                mDisplay.getWindowHeight() / 2 - PlayerBoard.HEIGHT_BOARD)) ;
        mBoards.add(new PlayerBoard(mDisplay,
                mDisplay.getWindowWidth() / 2 - PlayerBoard.WIDTH_BOARD,
                mDisplay.getWindowHeight() / 2 - PlayerBoard.HEIGHT_BOARD)) ;
        mBoards.add(new PlayerBoard(mDisplay,
                - mDisplay.getWindowWidth() / 2,
                - mDisplay.getWindowHeight() / 2)) ;
        mBoards.add(new PlayerBoard(mDisplay,
                mDisplay.getWindowWidth() / 2 - PlayerBoard.WIDTH_BOARD,
                - mDisplay.getWindowHeight() / 2)) ;
    }

    private void createFiveFactories()
    {
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * -2.5),
                (int) (TilesFactory.HEIGHT_FACTORY * 1.25), 0)) ;
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * -.5),
                (int) (TilesFactory.HEIGHT_FACTORY * 1.25), 1)) ;
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * 1.5),
                (int) (TilesFactory.HEIGHT_FACTORY * 1.25), 2)) ;
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * -1.5),
                (int) (TilesFactory.HEIGHT_FACTORY * -2.25), 3)) ;
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * .5),
                (int) (TilesFactory.HEIGHT_FACTORY * -2.25), 4)) ;
    }

    private void createSevenFactories()
    {
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * -2.5),
                (int) (TilesFactory.HEIGHT_FACTORY * -4.), 0)) ;
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * -2.5),
                (int) (TilesFactory.HEIGHT_FACTORY * -2.75), 1)) ;
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * -2.5),
                (int) (TilesFactory.HEIGHT_FACTORY * -1.5), 2)) ;
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * -.5),
                (int) (TilesFactory.HEIGHT_FACTORY * -.5), 3)) ;
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * 1.5),
                (int) (TilesFactory.HEIGHT_FACTORY * -4.), 4)) ;
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * 1.5),
                (int) (TilesFactory.HEIGHT_FACTORY * -2.75), 5)) ;
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * 1.5),
                (int) (TilesFactory.HEIGHT_FACTORY * -1.5), 6)) ;
    }

    private void createNineFactories()
    {
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * -2.5),
                (int) (TilesFactory.HEIGHT_FACTORY * 1.), 0)) ;
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * -.5),
                (int) (TilesFactory.HEIGHT_FACTORY * 1.), 1)) ;
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * 1.5),
                (int) (TilesFactory.HEIGHT_FACTORY * 1.), 2)) ;
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * -2.5),
                (int) (TilesFactory.HEIGHT_FACTORY * 2.375), 3)) ;
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * -.5),
                (int) (TilesFactory.HEIGHT_FACTORY * 2.375), 4)) ;
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * 1.5),
                (int) (TilesFactory.HEIGHT_FACTORY * 2.375), 5)) ;
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * -2.5),
                (int) (TilesFactory.HEIGHT_FACTORY * 3.75), 6)) ;
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * -.5),
                (int) (TilesFactory.HEIGHT_FACTORY * 3.75), 7)) ;
        mFactories.add(new TilesFactory(mDisplay,
                (int) (TilesFactory.WIDTH_FACTORY * 1.5),
                (int) (TilesFactory.HEIGHT_FACTORY * 3.75), 8)) ;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g) ;

        paintPlayersBoard(g) ;
        paintTilesFactories(g) ;
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