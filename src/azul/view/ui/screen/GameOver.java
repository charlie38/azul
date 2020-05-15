package azul.view.ui.screen;

import azul.view.Display;

import javax.swing.border.EmptyBorder;

public class GameOver extends Screen
{
    // Buttons message.
    public final String MESSAGE_GAME_OVER = "GAME OVER" ;
    public final String MESSAGE_WINNER = "The winner is " ;
    public final String MESSAGE_POINTS = " points" ;


    /**
     * Contains the "game over" components <-> is the in game over screen.
     * @param display is the root.
     */
    public GameOver(Display display)
    {
        super(display, 1, 1) ;

        setBackground(Display.BG_GAME_OVER) ;
        setBorder(new EmptyBorder(25, 25, 25, 25)) ;
        // Create components and add them.
        createGameOverLabel() ;
    }

    private void createGameOverLabel()
    {
        add(createLabel(MESSAGE_GAME_OVER, Display.CL_PRIMARY, 40)) ;
    }

    public void refresh()
    {
    }
}