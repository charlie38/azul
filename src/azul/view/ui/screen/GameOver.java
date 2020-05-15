package azul.view.ui.screen;

import azul.model.player.Player;
import azul.view.Display;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class GameOver extends Screen
{
    // Buttons message.
    public final String MESSAGE_GAME_OVER = "GAME OVER" ;
    public final String MESSAGE_POINTS = " points" ;
    public final String MESSAGE_BACK = "BACK IN GAME" ;
    public final String MESSAGE_MAIN_MENU = "GO TO THE MAIN MENU" ;

    // Components.
    private JLabel mTitle ;
    private JButton mBack ;
    private JButton mMainMenu ;

    /**
     * Contains the "game over" components <-> is the in game over screen.
     * @param display is the root.
     */
    public GameOver(Display display)
    {
        super(display, 7, 1) ;

        setBackground(Display.BG_GAME_OVER) ;
        setBorder(new EmptyBorder(100, 25, 100, 25)) ;
        // Create components.
        createComponents() ;
    }

    private void createComponents()
    {
        mTitle = createLabel(MESSAGE_GAME_OVER, Display.CL_PRIMARY, 50) ;
        mBack = createButton(MESSAGE_BACK, Display.CD_SECONDARY, Display.CL_PRIMARY, 30,
                actionEvent -> getDisplay().onGoInGame()) ;
        mMainMenu = createButton(MESSAGE_MAIN_MENU, Display.CD_SECONDARY, Display.CL_PRIMARY, 30,
                actionEvent -> getDisplay().onGoMainMenu()) ;
    }

    private void createPlayers()
    {
        int i ;

        ArrayList<Player> players = new ArrayList<>() ;
        // Sort players with score.
        for (i = 0 ; i < getGame().getNbPlayers() ; i ++)
        {
            Player player = getGame().getPlayer(i) ;
            int score = player.getScore() ;

            int j = 0 ;

            for (Player player_ : players)
            {
                if (player_.getScore() < score)
                {
                    break ;
                }

                j ++ ;
            }

            players.add(j, player) ;
        }

        i = 0 ;

        for (Player player : players)
        {
            JPanel slot = new JPanel(new GridLayout(1, 3)) ;
            slot.setBackground(Display.CL_QUINARY) ;
            slot.add(createLabel(String.valueOf(++ i),
                    i == 0 ? Display.CD_SECONDARY : Display.CD_TERTIARY,
                    i == 0 ? 30 : 25)) ;
            slot.add(createLabel(player.getName(),
                    i == 0 ? Display.CD_SECONDARY : Display.CD_TERTIARY,
                    i == 0 ? 30 : 25)) ;
            slot.add(createLabel(String.valueOf(player.getScore()) + MESSAGE_POINTS,
                    i == 0 ? Display.CD_SECONDARY : Display.CD_TERTIARY,
                    i == 0 ? 30 : 25)) ;
            add(slot) ;
        }

        for ( ; i < 4 ; i ++)
        {
            add(Box.createVerticalGlue()) ;
        }
    }

    public void refresh()
    {
        removeAll() ;
        add(mTitle) ;
        createPlayers() ;
        add(mBack) ;
        add(mMainMenu) ;
    }
}