package azul.view.ui.screen;

import azul.model.player.IAPlayer;
import azul.model.player.Player;
import azul.view.Display;
import azul.view.component.JHintTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Prepare extends Screen
{
    // Buttons size.
    public static final int DELETE_WIDTH = 30 ;
    public static final int DELETE_HEIGHT = 30 ;
    // Slots bg.
    public final Color BG_SLOTS = new Color(0x40443F) ;
    // Labels message.
    public final String MESSAGE_TITLE = "NEW GAME" ;
    public final String MESSAGE_SUBTITLE = "Players choice:" ;
    public final String MESSAGE_PLAYER = "Player " ;
    public final String MESSAGE_TYPE_IA_RANDOM = "EASY IA" ;
    public final String MESSAGE_TYPE_IA_MINIMAX = "HARD IA" ;
    public final String MESSAGE_TYPE_IA_EASY = "NORMAL IA" ;
    public final String MESSAGE_TYPE_HUMAN = "HUMAN" ;
    public final String MESSAGE_NAME_CHOICE = "insert a name" ;
    public final String MESSAGE_DELETE = "remove" ;
    public final String MESSAGE_ADD = "ADD A PLAYER" ;
    public final String MESSAGE_EXIT = "BACK" ;
    public final String MESSAGE_CONTINUE = "CONTINUE" ;
    // Default player name.
    public final String DEFAULT_NAME = "CPU" ;

    // Track the number of player added.
    private int mNbPlayers ;
    // Contains the screen information/instructions.
    private JPanel mHeader ;
    // Contains the player slots.
    private JPanel mSlots ;
    private JPanel mSlot1 ;
    private JPanel mSlot2 ;
    private JPanel mSlot3 ;
    private JPanel mSlot4 ;
    // Contains the navigation buttons.
    private JPanel mNavFooter ;
    // Button used to add a player.
    private JButton mAdd ;

    /**
     * Screen used to prepare a game (select player etc.).
     * @param display is the root.
     */
    public Prepare(Display display)
    {
        super(display, 3, 1) ;

        setLayout(new BorderLayout()) ;
        setBackground(Display.BG_PREPARE) ;
        setBorder(new EmptyBorder(100, 100, 100, 100)) ;
        // Create components and add them.
        createHeader() ;
        add(mHeader, BorderLayout.NORTH) ;
        createSlots() ;
        add(mSlots, BorderLayout.CENTER) ;
        createNavigationFooter() ;
        add(mNavFooter, BorderLayout.SOUTH) ;
    }

    private void createHeader()
    {
        mHeader = new JPanel(new GridLayout(2, 1)) ;
        mHeader.setBorder(new EmptyBorder(25, 100, 25, 100)) ;
        mHeader.setBackground(Display.BG_PREPARE) ;
        mHeader.add(createLabel(MESSAGE_TITLE, Display.CL_PRIMARY, 50), BorderLayout.NORTH) ;
        mHeader.add(createLabel(MESSAGE_SUBTITLE, Display.CL_TERTIARY, 30)) ;
    }

    private void createSlots()
    {
        mSlots = new JPanel(new BorderLayout()) ;
        mSlots.setBorder(BorderFactory.createLineBorder(Display.CD_PRIMARY, 3)) ;
        mSlots.setBackground(BG_SLOTS) ;
        JPanel wrapper_ = new JPanel(new GridLayout(5, 1)) ;
        wrapper_.setBorder(new EmptyBorder(25, 100, 25, 100)) ;
        wrapper_.setBackground(BG_SLOTS) ;
        // Create the slots.
        mSlot1 = createPlayerSlot(1) ;
        mSlot2 = createPlayerSlot(2) ;
        mSlot3 = createPlayerSlot(3) ;
        mSlot4 = createPlayerSlot(4) ;
        // Add player button.
        JPanel wrapper = new JPanel(new FlowLayout()) ;
        mAdd = createButton(MESSAGE_ADD, Display.CD_SECONDARY, Display.CL_PRIMARY, 20, actionEvent -> addPlayerSlot()) ;
        mAdd.setPreferredSize(new Dimension(665, 50)) ;
        wrapper.add(mAdd) ;
        wrapper.setBackground(BG_SLOTS) ;
        // Add them to the parent.
        wrapper_.add(mSlot1) ;
        wrapper_.add(mSlot2) ;
        wrapper_.add(mSlot3) ;
        wrapper_.add(mSlot4) ;
        wrapper_.add(wrapper) ;
        mSlots.add(wrapper_) ;
    }

    private void createNavigationFooter()
    {
        mNavFooter = new JPanel(new GridLayout(1, 3)) ;
        mNavFooter.setBackground(Display.BG_PREPARE) ;
        mNavFooter.setBorder(new EmptyBorder(25, 100, 0, 100)) ;
        mNavFooter.add(createButton(MESSAGE_EXIT, Display.CD_SECONDARY, Display.CL_PRIMARY, 30,
                actionEvent -> getDisplay().onGoMainMenu())) ;
        mNavFooter.add(Box.createHorizontalGlue()) ;
        mNavFooter.add(createButton(MESSAGE_CONTINUE, Display.CD_SECONDARY, Display.CL_PRIMARY, 30,
                actionEvent ->
                {
                    getDisplay().getUIPanel().getGameCanvas().startGame(mNbPlayers) ;
                    getGame().startGame(getPlayersIA(), getPlayersNames()) ;

                    if (getGame().isOnlyIAs())
                    {
                        getDisplay().onGoPrepareIAs() ;
                    }
                    else
                    {
                        getDisplay().onGoInGame() ;

                        if (getGame().getPlayer(0) instanceof IAPlayer)
                        {
                            getDisplay().getMediator().IAPlay() ;
                        }
                    }
                }
        )) ;
    }

    private JPanel createPlayerSlot(int nbPlayer)
    {
        JPanel slot = new JPanel(new FlowLayout()) ;
        slot.setBackground(BG_SLOTS) ;

        JLabel player = createLabel(MESSAGE_PLAYER + nbPlayer, Display.CL_SECONDARY, BG_SLOTS, 30) ;
        player.setPreferredSize(new Dimension(200, 50)) ;

        JHintTextField name = createTextField(MESSAGE_NAME_CHOICE, Display.CL_QUATERNARY,
                Display.CD_TERTIARY, Display.CL_PRIMARY, 20) ;
        name.setPreferredSize(new Dimension(150, 50)) ;

        Choice type = createSpinner(new String[] { MESSAGE_TYPE_HUMAN, MESSAGE_TYPE_IA_EASY, MESSAGE_TYPE_IA_RANDOM,
                        MESSAGE_TYPE_IA_MINIMAX },
                Display.CD_TERTIARY, Display.CL_PRIMARY, 20) ;
        type.setPreferredSize(new Dimension(150, 50)) ;

        JButton delete ;

        if (nbPlayer == 1 || nbPlayer == 2)
        {
            // Can't delete these players.
            delete = createButton("", BG_SLOTS, BG_SLOTS, 20, null) ;
            delete.setPreferredSize(new Dimension(150, 50)) ;
        }
        else
        {
            delete = createButton(MESSAGE_DELETE, getResourcesLoader().getDelete(), Display.CD_SECONDARY,
                    Display.CL_PRIMARY, 20,
                    actionEvent -> removePlayerSlot(nbPlayer)) ;
            delete.setPreferredSize(new Dimension(150, 50)) ;
        }

        slot.add(player) ;
        slot.add(name) ;
        slot.add(type) ;
        slot.add(delete) ;

        return slot ;
    }

    /**
     * Prepare the screen before going in.
     */
    public void prepare()
    {
        mNbPlayers = 2 ;

        // Erase the previous names.
        ((JHintTextField) mSlot1.getComponent(1)).setText("") ;
        ((JHintTextField) mSlot2.getComponent(1)).setText("") ;
        ((JHintTextField) mSlot3.getComponent(1)).setText("") ;
        ((JHintTextField) mSlot4.getComponent(1)).setText("") ;
        // Set the selection.
        ((Choice) mSlot1.getComponent(2)).select(0) ; // By default an human.
        ((Choice) mSlot2.getComponent(2)).select(1) ;
        ((Choice) mSlot3.getComponent(2)).select(1) ;
        ((Choice) mSlot4.getComponent(2)).select(1) ;
        // Start the menu with 2 players.
        mSlot3.setVisible(false) ;
        mSlot4.setVisible(false) ;
        mAdd.setVisible(true) ;
    }

    private void addPlayerSlot()
    {
        switch (mNbPlayers)
        {
            case 2 : mSlot3.setVisible(true) ; mNbPlayers ++ ; break ;
            case 3 : mSlot4.setVisible(true) ; mNbPlayers ++ ; mAdd.setVisible(false) ;
        }
    }

    private void removePlayerSlot(int player)
    {
        if (player == 3 && mNbPlayers == 4)
        {
            // Remove the third player but there is 4 players; need to slide the 4th slot.
            ((JHintTextField) mSlot3.getComponent(1)).setText(((JHintTextField) mSlot4.getComponent(1)).getText()) ;
            ((Choice) mSlot3.getComponent(2)).select(((Choice) mSlot4.getComponent(2)).getSelectedIndex()) ;
            // Refresh.
            ((JHintTextField) mSlot4.getComponent(1)).setText("") ;
            mSlot4.setVisible(false) ;
            mAdd.setVisible(true) ;
            mNbPlayers -- ;
        }
        else if (player == 3)
        {
            // Refresh.
            ((JHintTextField) mSlot3.getComponent(1)).setText("") ;
            mSlot3.setVisible(false) ;
            mAdd.setVisible(true) ;
            mNbPlayers -- ;
        }
        else if (player == 4)
        {
            // Refresh.
            ((JHintTextField) mSlot4.getComponent(1)).setText("") ;
            mSlot4.setVisible(false) ;
            mAdd.setVisible(true) ;
            mNbPlayers -- ;
        }
    }

    private Player.Type getPlayerIA(JPanel slot)
    {
        switch (((Choice) slot.getComponent(2)).getSelectedItem())
        {
            case MESSAGE_TYPE_IA_RANDOM : return Player.Type.IA_RANDOM ;
            case MESSAGE_TYPE_IA_MINIMAX : return Player.Type.IA_MINIMAX ;
            case MESSAGE_TYPE_IA_EASY : return Player.Type.IA_EASY ;
            case MESSAGE_TYPE_HUMAN : default : return Player.Type.HUMAN ;
        }
    }

    private Player.Type[] getPlayersIA()
    {
        Player.Type p1 = getPlayerIA(mSlot1) ;
        Player.Type p2 = getPlayerIA(mSlot2) ;
        Player.Type p3 = getPlayerIA(mSlot3) ;
        Player.Type p4 = getPlayerIA(mSlot4) ;

        switch (mNbPlayers)
        {
            case 2 : return new Player.Type[] { p1, p2 } ;
            case 3 : return new Player.Type[] { p1, p2, p3 } ;
            default : return new Player.Type[] { p1, p2, p3, p4 } ;
        }

    }

    private String[] getPlayersNames()
    {
        String p1 = ((JHintTextField) mSlot1.getComponent(1)).getText() ;
        String p2 = ((JHintTextField) mSlot2.getComponent(1)).getText() ;
        String p3 = ((JHintTextField) mSlot3.getComponent(1)).getText() ;
        String p4 = ((JHintTextField) mSlot4.getComponent(1)).getText() ;

        p1 = p1.isEmpty() ? DEFAULT_NAME + " 1" : p1 ;
        p2 = p2.isEmpty() ? DEFAULT_NAME + " 2" : p2 ;
        p3 = p3.isEmpty() ? DEFAULT_NAME + " 3" : p3 ;
        p4 = p4.isEmpty() ? DEFAULT_NAME + " 4" : p4 ;

        switch (mNbPlayers)
        {
            case 2 : return new String[] { p1, p2 } ;
            case 3 : return new String[] { p1, p2, p3 } ;
            default : return new String[] { p1, p2, p3, p4 } ;
        }
    }
}