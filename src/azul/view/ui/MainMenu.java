package azul.view.ui;

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
    public static final String MESSAGE_START = "START" ;
    public static final String MESSAGE_CREDITS = "CREDITS" ;

    // Title.
    private JLabel mTitle ;
    // Buttons.
    private JButton mStart ;
    private JButton mCredits ;

    /**
     * Contains the main menu components <-> is the starting screen.
     * @param display is the root.
     */
    public MainMenu(Display display)
    {
        super(display, 3, 1) ;

        setBackground(Display.BG_MAIN_MENU) ;
        setBorder(new EmptyBorder(100, 100, 100, 100)) ;
        // Create components.
        createTitle() ;
        createStartButton() ;
        createCreditsButton() ;
        // Add them.
        add(mTitle) ;
        add(mStart) ;
        add(mCredits) ;
    }

    public void createTitle()
    {
        mTitle = new JLabel(new ImageIcon(getImageLoader().getGameTitle())) ;
    }

    public void createStartButton()
    {
        mStart = new JButton(MESSAGE_START) ;
        mStart.setBackground(Color.LIGHT_GRAY) ;
        mStart.setBorderPainted(false) ;
        mStart.setFocusPainted(false) ;
        mStart.addActionListener(actionEvent -> getDisplay().onGoInGame()) ;
    }

    public void createCreditsButton()
    {
        mCredits = new JButton(MESSAGE_CREDITS) ;
        mCredits.setBackground(Color.LIGHT_GRAY) ;
        mCredits.setBorderPainted(false) ;
        mCredits.setFocusPainted(false) ;
        mCredits.addActionListener(actionEvent -> getDisplay().onGoCredits()) ;
    }
}