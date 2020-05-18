package azul.view.ui.screen;

import azul.view.Display;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PrepareIAs extends Screen
{
    // Buttons message.
    public final String MESSAGE_TITLE = "<html>You are going to watch IAs playing<br/>want to continue?</html>" ;
    public final String MESSAGE_CONTINUE = "CONTINUE" ;
    public final String MESSAGE_BACK = "BACK" ;

    /**
     * When user wants to play only with IAs.
     * @param display is the root.
     */
    public PrepareIAs(Display display)
    {
        super(display, 4, 1) ;

        setBackground(Display.BG_PREPARE_IAS) ;
        setBorder(new EmptyBorder(100, 100, 100, 100)) ;
        // Create components and add them.
        add(createLabel(MESSAGE_TITLE, Display.CL_PRIMARY, 50)) ;
        add(Box.createVerticalGlue()) ;
        add(createButton(MESSAGE_CONTINUE, Display.CD_SECONDARY, Display.CL_PRIMARY, 30,
                actionEvent ->
                {
                    getDisplay().onGoInGame() ;
                    getDisplay().getMediator().IAPlay() ;
                }
        )) ;
        add(createButton(MESSAGE_BACK, Display.CD_SECONDARY, Display.CL_PRIMARY, 30,
                actionEvent -> getDisplay().onGoPrepare())) ;
    }
}