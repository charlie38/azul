package azul.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import azul.view.Display;
import azul.view.drawable.*;
import azul.view.drawable.factory.Tile;

public class Mouse implements MouseListener
{
    private Display mDisplay ;

    public Mouse(Display display)
    {
        mDisplay = display ;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        Drawable selected = mDisplay.getDrawingPanel().onClick(e.getX(), e.getY()) ;

        if (selected == null)
        {
            return ;
        }

        if (selected instanceof Tile)
        {
            // On click, the factory tile start blinking.
            ((Tile) selected).setIsAnimated(true) ;
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}