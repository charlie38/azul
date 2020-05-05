package azul.view.ui.screen;

import azul.view.Display;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InGame extends Screen
{
    // Buttons size.
    public static final int PREVIOUS_WIDTH = 30 ;
    public static final int PREVIOUS_HEIGHT = 30 ;
    public static final int SETTINGS_WIDTH = 30 ;
    public static final int SETTINGS_HEIGHT = 30 ;
    public static final int NEXT_WIDTH = 30 ;
    public static final int NEXT_HEIGHT = 30 ;
    // Buttons message.
    public final String MESSAGE_PREVIOUS = "PREVIOUS MOVE" ;
    public final String MESSAGE_SETTINGS = "SETTINGS" ;
    public final String MESSAGE_NEXT = "NEXT MOVE" ;

    // Buttons.
    private JButton mPrevious ;
    private JButton mNext ;

    /**
     * Contains the "in game" components <-> is the in game screen.
     * @param display is the root.
     */
    public InGame(Display display)
    {
        super(display, 1, 3) ;

        setBackground(Display.BG_IN_GAME) ;
        setBorder(new EmptyBorder(25, 25, 25, 25)) ;
        // Create components and add them.
        mPrevious = createButtonIconTop(MESSAGE_PREVIOUS, new ImageIcon(getResourcesLoader().getPrevious()),
                Color.lightGray, 20,
                actionEvent -> getGame().goToPreviousMove()) ;
        add(mPrevious) ;
        add(createButtonIconTop(MESSAGE_SETTINGS, new ImageIcon(getResourcesLoader().getSettings()),
                Color.lightGray, 20,
                actionEvent -> getDisplay().onGoSettings())) ;
        mNext = createButtonIconTop(MESSAGE_NEXT, new ImageIcon(getResourcesLoader().getNext()),
                Color.lightGray, 20,
                actionEvent -> getGame().goToNextMove()) ;
        add(mNext) ;
    }
}