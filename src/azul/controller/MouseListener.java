package azul.controller;

import azul.view.Display;
import azul.view.drawable.Drawable;
import azul.view.drawable.board.PatternLineTile;
import azul.view.drawable.board.WallTile;
import azul.view.drawable.factory.Tile;

import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener
{
    private Display mDisplay ;

    public MouseListener(Display display)
    {
        mDisplay = display ;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        Drawable selected = mDisplay.getUIPanel().getDrawingPanel().onClick(e.getX(), e.getY()) ;

        if (selected == null)
        {
            return ;
        }

        if (selected instanceof Tile)
        {
            // On click, the factory tile start blinking.
            ((Tile) selected).setIsAnimated(true) ;
        }
        else if (selected instanceof WallTile)
        {
            // On click, the wall tile start blinking.
            ((WallTile) selected).setIsAnimated(true) ;
        }
        else if (selected instanceof PatternLineTile)
        {
            // On click, the pattern line tile start blinking.
            ((PatternLineTile) selected).setIsAnimated(true) ;
        }
    }

    /** Useless. **/

    @Override
    public void mousePressed(MouseEvent mouseEvent) { }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) { }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) { }

    @Override
    public void mouseExited(MouseEvent mouseEvent) { }
}