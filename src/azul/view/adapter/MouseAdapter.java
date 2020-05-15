package azul.view.adapter;

import azul.controller.Mediator;
import azul.view.Display;
import azul.view.drawable.Drawable;

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
        Drawable selected = mDisplay.getUIPanel().getGameCanvas().onClick(e.getX(), e.getY()) ;

        if (selected != null)
        {
            mMediator.onClick(selected) ;
        }
    }
}