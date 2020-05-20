package azul.view.adapter;

import azul.controller.Mediator;
import azul.view.Display;
import azul.view.drawable.Drawable;
import azul.view.ui.screen.InGame;

import java.awt.event.MouseEvent;

public class MouseAdapter extends java.awt.event.MouseAdapter
{
    // View part.
    private Display mDisplay ;
    // Controller part.
    private Mediator mMediator ;

    public MouseAdapter(Display display, Mediator mediator)
    {
        mDisplay = display ;
        mMediator = mediator ;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        int x = e.getX() ;
        int y = e.getY() ;
        // Canvas padding.
        x -= InGame.SIZE_BORDER ;
        y -= InGame.SIZE_BORDER ;
        // Search for a drawable.
        Drawable selected = mDisplay.getUIPanel().getGameCanvas().onClick(x, y) ;

        if (selected != null)
        {
            mMediator.onClick(selected) ;
        }
    }
}