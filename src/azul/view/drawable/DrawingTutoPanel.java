package azul.view.drawable;

import java.awt.Graphics;

import javax.swing.JPanel;

import azul.view.Display;
import azul.view.drawable.tutorial.Background;
import azul.view.drawable.tutorial.TutoFactory;

public class DrawingTutoPanel extends JPanel
{
    private Display mDisplay;

    private Background mBackground;
    
    private TutoFactory mFactory;
    
    //If factory need to be visible or not
    private boolean visibleFactory;

    public DrawingTutoPanel(Display display)
    {
        mDisplay = display;
        mBackground = new Background(mDisplay);
        mFactory = new TutoFactory(mDisplay);
        visibleFactory = false;
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
        if(visibleFactory) paintFactory(g) ;
    }
    
    private void paintFactory(Graphics g)
    {
    	mFactory.paint(g);
    }

    private void paintBackground(Graphics g)
    {
        mBackground.paint(g) ;
    }
    
    public void setVisibleFactory(boolean b)
    {
    	visibleFactory = b;
    }
    
    public TutoFactory getFactory()
    {
    	return mFactory;
    }
}