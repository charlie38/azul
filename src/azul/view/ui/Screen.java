package azul.view.ui;

import azul.model.Game;
import azul.view.Display;
import azul.view.images.ImageLoader;

import javax.swing.*;
import java.awt.*;

public abstract class Screen extends JPanel
{
    // Layout padding.
    public static final int HGAP = 100 ;
    public static final int VGAP = 20 ;

    // Root ref.
    private Display mDisplay ;

    /**
     * Contains a UI screen.
     * @param display the root.
     * @param nbRows the layout number of rows.
     * @param nbCols the layout number of columns.
     */
    public Screen(Display display, int nbRows, int nbCols)
    {
        mDisplay = display ;

        setLayout(new GridLayout(nbRows, nbCols, HGAP, VGAP)) ;
    }

    public Display getDisplay()
    {
        return mDisplay ;
    }

    public ImageLoader getImageLoader()
    {
        return mDisplay.getImageLoader() ;
    }

    public Game getGame()
    {
        return mDisplay.getGame() ;
    }

    public UIPanel getUIPanel()
    {
        return mDisplay.getUIPanel() ;
    }
}