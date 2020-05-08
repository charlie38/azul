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
        mPrevious = createButtonIconTop(MESSAGE_PREVIOUS, getResourcesLoader().getPrevious(), Display.CD_SECONDARY,
                Display.CL_QUINARY, 20,
                actionEvent -> getGame().playPreviousMove()) ;
        mPrevious.setEnabled(false) ;
        add(mPrevious) ;

        add(createButtonIconTop(MESSAGE_SETTINGS, getResourcesLoader().getSettings(), Display.CD_SECONDARY,
                Display.CL_PRIMARY, 20,
                actionEvent -> getDisplay().onGoSettings())) ;

        mNext = createButtonIconTop(MESSAGE_NEXT, getResourcesLoader().getNext(), Display.CD_SECONDARY,
                Display.CL_QUINARY, 20,
                actionEvent -> getGame().playNextMove()) ;
        mNext.setEnabled(false) ;
        add(mNext) ;
    }

    @Override
    public void update(java.util.Observable observable, Object o)
    {
        refreshHistoryButtons() ;
    }

    public void refreshHistoryButtons()
    {
        mPrevious.setBackground(getGame().getHistory().canUndo() ? Display.CL_PRIMARY : Display.CL_QUINARY) ;
        mPrevious.setEnabled(getGame().getHistory().canUndo()) ;

        mNext.setBackground(getGame().getHistory().canRedo() ? Display.CL_PRIMARY : Display.CL_QUINARY) ;
        mNext.setEnabled(getGame().getHistory().canRedo()) ;
    }
}