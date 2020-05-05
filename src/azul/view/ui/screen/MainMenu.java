package azul.view.ui.screen;

import azul.view.Display;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainMenu extends Screen
{
    // Title size.
    public static final int GAME_TITLE_WIDTH = 150 ;
    public static final int GAME_TITLE_HEIGHT = 150 ;
    // Buttons message.
    public final String MESSAGE_START = "START" ;
    public final String MESSAGE_CREDITS = "CREDITS" ;

    /**
     * Contains the main menu components <-> is the starting screen.
     * @param display is the root.
     */
    public MainMenu(Display display)
    {
        super(display, 3, 1) ;

        setBackground(Display.BG_MAIN_MENU) ;
        setBorder(new EmptyBorder(100, 100, 100, 100)) ;
        // Create components and add them.
        add(new JLabel(new ImageIcon(getResourcesLoader().getGameTitle()))) ;
        add(createButton(MESSAGE_START, Color.LIGHT_GRAY, 40, (actionEvent) -> getDisplay().onGoInGame())) ;
        add(createButton(MESSAGE_CREDITS, Color.LIGHT_GRAY, 40, (actionEvent) -> getDisplay().onGoCredits())) ;
    }
}