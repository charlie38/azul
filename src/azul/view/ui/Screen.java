package azul.view.ui;

import azul.model.Game;
import azul.view.Display;

import javax.swing.*;

public abstract class Screen extends JPanel
{
    // Root ref.
    private Display mDisplay ;

    /**
     * Contains a UI screen.
     * @param display the root.
     */
    public Screen(Display display)
    {
        mDisplay = display ;
    }

    public Display getDisplay()
    {
        return mDisplay ;
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