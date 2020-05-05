package azul.view.ui.screen;

import azul.view.Display;
import azul.view.ui.screen.Screen;

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
        add(createLabel(MESSAGE_TITLE, Color.LIGHT_GRAY, 40)) ;
        add(Box.createVerticalGlue()) ;
        add(Box.createVerticalGlue()) ;
        add(createButton(MESSAGE_MAIN_MENU, Color.LIGHT_GRAY, 40, (actionEvent) -> getDisplay().onGoMainMenu())) ;
        add(createButton(MESSAGE_BACK, Color.LIGHT_GRAY, 40, (actionEvent) -> getDisplay().onGoInGame())) ;
    }
}