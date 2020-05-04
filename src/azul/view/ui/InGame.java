package azul.view.ui;

import azul.view.Display;

import javax.swing.*;

public class InGame extends Screen
{
    /**
     * Contains the "in game" components <-> is the in game screen.
     * @param display is the root.
     */
    public InGame(Display display)
    {
        super(display, 1, 1) ;

        setBackground(Display.BG_IN_GAME) ;
        // Create components.
        // Add them.
        add(new JButton("hey")) ;
    }
}