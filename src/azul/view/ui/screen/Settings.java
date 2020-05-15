package azul.view.ui.screen;

import azul.view.Display;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Settings extends Screen
{
    // Buttons message.
    public final String MESSAGE_TITLE = "SETTINGS" ;
    public final String MESSAGE_MAIN_MENU = "GO TO THE MAIN MENU" ;
    public final String MESSAGE_BACK = "BACK" ;

    /**
     * Contains the settings components <-> is the settings screen.
     * @param display is the root.
     */
    public Settings(Display display)
    {
        super(display, 5, 1) ;

        setBackground(Display.BG_SETTINGS) ;
        setBorder(new EmptyBorder(100, 100, 100, 100)) ;
        // Create components and add them.
        add(createLabel(MESSAGE_TITLE, Display.CL_PRIMARY, 50)) ;
        add(Box.createVerticalGlue()) ;
        add(Box.createVerticalGlue()) ;
        add(createButton(MESSAGE_MAIN_MENU, Display.CD_SECONDARY, Display.CL_PRIMARY, 40,
                actionEvent -> getDisplay().onGoMainMenu())) ;
        add(createButton(MESSAGE_BACK, Display.CD_SECONDARY, Display.CL_PRIMARY, 40,
                actionEvent -> getDisplay().onGoInGame())) ;
    }
}