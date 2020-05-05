package azul.view.ui.screen;

import azul.view.Display;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Credits extends Screen
{
    // Labels message.
    public final String MESSAGE_TITLE = "CREDITS" ;
    public final String MESSAGE_SUBTITLE = "Made by :" ;
    public final String MESSAGE_EXIT = "EXIT" ;
    public final String[] MESSAGE_DEVELOPERS = new String[]{ "Vlad GINCU", "Charlotte BATAILLE", "Iyad ALADELY",
            "Ivan DERYABIN", "Hugo BANTIGNIES", "Nicolas BESSON", "Emilien AUFAUVRE" } ;

    /**
     * Contains the credits components <-> is the credits screen.
     * @param display is the root.
     */
    public Credits(Display display)
    {
        super(display, 6, 1) ;

        setBackground(Display.BG_CREDITS) ;
        setBorder(new EmptyBorder(100, 100, 100, 100)) ;
        // Create components and add them.
        JPanel content = new JPanel(new GridLayout(2, MESSAGE_DEVELOPERS.length / 2)) ;
        content.setBackground(Display.BG_CREDITS) ;
        add(createLabel(MESSAGE_TITLE, Color.LIGHT_GRAY, 40)) ;
        add(Box.createVerticalGlue()) ;
        add(Box.createVerticalGlue()) ;
        add(createLabel(MESSAGE_SUBTITLE, Color.LIGHT_GRAY, 30)) ;
        add(content) ;

        for (String messageDeveloper : MESSAGE_DEVELOPERS)
        {
            content.add(createLabel(messageDeveloper, Color.LIGHT_GRAY, 20)) ;
        }

        add(createButton(MESSAGE_EXIT, Color.LIGHT_GRAY, 40, actionEvent -> getDisplay().onGoMainMenu())) ;
    }
}