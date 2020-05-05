package azul.view.ui;

import azul.view.Display;

import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Credits extends Screen
{
    // Labels message.
    public static final String MESSAGE_TITLE = "CREDITS" ;
    public static final String MESSAGE_SUBTITLE = "Made by :" ;
    public static final String[] MESSAGE_DEVELOPERS = new String[]{ "Vlad GINCU", "Charlotte BATAILLE", "Iyad ALADELY",
            "Ivan DERYABIN", "Hugo BANTIGNIES", "Nicolas BESSON", "Emilien AUFAUVRE" } ;

    /**
     * Contains the credits components <-> is the credits screen.
     * @param display is the root.
     */
    public Credits(Display display)
    {
        super(display, MESSAGE_DEVELOPERS.length + 2, 1) ;

        setBackground(Display.BG_CREDITS) ;
        setBorder(new EmptyBorder(25, 25, 25, 25)) ;
        // Create components and add them.
        add(createLabel(MESSAGE_TITLE, Color.LIGHT_GRAY, 60)) ;
        add(createLabel(MESSAGE_SUBTITLE, Color.LIGHT_GRAY, 30)) ;

        for (String messageDeveloper : MESSAGE_DEVELOPERS)
        {
            add(createLabel(messageDeveloper, Color.LIGHT_GRAY, 20)) ;
        }
    }
}