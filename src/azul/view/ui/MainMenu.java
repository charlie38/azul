package azul.view.ui;

import azul.view.Display;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends Screen
{
    // Buttons message.
    public static final String MESSAGE_START = "START" ;

    // Start button.
    private JButton mStart ;

    /**
     * Contains the main menu components <-> is the starting screen.
     * @param display is the root.
     */
    public MainMenu(Display display)
    {
        super(display) ;

        setBackground(UIPanel.BG_MAIN_MENU) ;
        // Create components.
        createStartButton() ;
        // Add them.
        add(mStart) ;
    }

    public void createStartButton()
    {
        mStart = new JButton(MESSAGE_START) ;
        mStart.setBackground(Color.GREEN) ;
        mStart.addActionListener(actionEvent -> getUIPanel().onGoInGame()) ;
    }
}