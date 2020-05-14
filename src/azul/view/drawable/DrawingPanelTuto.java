package azul.view.drawable;

import java.awt.Graphics;

import javax.swing.JPanel;

import azul.view.Display;
import azul.view.drawable.factory.TilesFactory;

public class DrawingPanelTuto extends JPanel
{
    private Display mDisplay;

    private Background mBackground;

    public DrawingPanelTuto(Display display)
    {
        mDisplay = display;
        mBackground = new Background(mDisplay);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g) ;

        paintTuto(g) ;
    }

    private void paintTuto(Graphics g)
    {
        paintBackground(g) ;
    }

    private void paintBackground(Graphics g)
    {
        mBackground.paint(g) ;
    }
}