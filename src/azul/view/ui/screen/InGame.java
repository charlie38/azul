package azul.view.ui.screen;

import azul.model.Game;
import azul.view.Display;
import azul.view.drawable.DrawingGamePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InGame extends Screen
{
    // Layout.
    public static final int SIZE_BORDER = 25 ;
    // Buttons size.
    public static final int PREVIOUS_WIDTH = 30 ;
    public static final int PREVIOUS_HEIGHT = 30 ;
    public static final int PAUSE_WIDTH = 30 ;
    public static final int PAUSE_HEIGHT = 30 ;
    public static final int SETTINGS_WIDTH = 30 ;
    public static final int SETTINGS_HEIGHT = 30 ;
    public static final int PLAY_WIDTH = 30 ;
    public static final int PLAY_HEIGHT = 30 ;
    public static final int NEXT_WIDTH = 30 ;
    public static final int NEXT_HEIGHT = 30 ;
    // Buttons message.
    public final String MESSAGE_BEGINNING = "BEGINNING" ;
    public final String MESSAGE_PAUSE = "PAUSE" ;
    public final String MESSAGE_PLAY = "PLAY" ;
    public final String MESSAGE_END = "END" ;
    public final String MESSAGE_PREVIOUS = "PREVIOUS MOVE" ;
    public final String MESSAGE_SETTINGS = "SETTINGS" ;
    public final String MESSAGE_NEXT = "NEXT MOVE" ;
    // To switch.
    public final String IA_PANEL = "IA" ;
    public final String HUMAN_PANEL = "HUMAN" ;

    // When only IAs.
    private JPanel mIAs ;
    private JButton mBeginning ;
    private JButton mPause ;
    private JButton mSettingsIAs ;
    private JButton mPlay ;
    private JButton mEnd ;
    // When at least one human.
    private JPanel mHuman ;
    private JButton mPrevious ;
    private JButton mSettingsHuman ;
    private JButton mNext ;
    // To switch.
    private JPanel mCardPanel ;
    private CardLayout mCardLayout ;

    /**
     * Contains the "in game" components <-> is the in game screen.
     * @param display is the root.
     */
    public InGame(Display display, DrawingGamePanel canvas)
    {
        super(display, 1, 1) ;

        setLayout(new BorderLayout(HGAP, VGAP)) ;
        setBorder(new EmptyBorder(SIZE_BORDER, SIZE_BORDER, SIZE_BORDER, SIZE_BORDER)) ;
        setBackground(Display.BG_IN_GAME) ;
        // Create components and add them.
        mCardLayout = new CardLayout() ;
        mCardPanel = new JPanel(mCardLayout) ;

        createIAs() ;
        createHuman() ;

        add(mCardPanel, BorderLayout.SOUTH) ;
        add(canvas, BorderLayout.CENTER) ;
    }

    private void createIAs()
    {
        mIAs = new JPanel(new GridLayout(1, 5, Screen.HGAP, Screen.VGAP)) ;
        mIAs.setBackground(Display.BG_IN_GAME) ;

        mBeginning = createButtonIconTop(MESSAGE_BEGINNING, getResourcesLoader().getPrevious(), Display.CD_SECONDARY,
                Display.CL_QUINARY, 20,
                actionEvent -> getGame().goToFirstMove()) ;
        mBeginning.setEnabled(false) ;
        mIAs.add(mBeginning) ;

        mPause = createButtonIconTop(MESSAGE_PAUSE, getResourcesLoader().getPause(), Display.CD_SECONDARY,
                Display.CL_QUINARY, 20,
                actionEvent -> getGame().interruptIAs()) ;
        mPause.setEnabled(false) ;
        mIAs.add(mPause) ;

        mSettingsIAs = createButtonIconTop(MESSAGE_SETTINGS, getResourcesLoader().getSettings(), Display.CD_SECONDARY,
                Display.CL_PRIMARY, 20,
                actionEvent -> getDisplay().onGoSettings()) ;
        mIAs.add(mSettingsIAs) ;

        mPlay = createButtonIconTop(MESSAGE_PLAY, getResourcesLoader().getPlay(), Display.CD_SECONDARY,
                Display.CL_QUINARY, 20,
                actionEvent -> getGame().continueIAs(Game.State.CONTINUE_IAS_DELAY)) ;
        mPlay.setEnabled(false) ;
        mIAs.add(mPlay) ;

        mEnd = createButtonIconTop(MESSAGE_END, getResourcesLoader().getNext(), Display.CD_SECONDARY,
                Display.CL_QUINARY, 20,
                actionEvent -> getGame().goToLastMove()) ;
        mEnd.setEnabled(false) ;
        mIAs.add(mEnd) ;

        mCardPanel.add(mIAs, IA_PANEL) ;
    }

    private void createHuman()
    {
        mHuman = new JPanel(new GridLayout(1, 3, Screen.HGAP, Screen.VGAP)) ;
        mHuman.setBackground(Display.BG_IN_GAME) ;

        mPrevious = createButtonIconTop(MESSAGE_PREVIOUS, getResourcesLoader().getPrevious(), Display.CD_SECONDARY,
                Display.CL_QUINARY, 20,
                actionEvent -> getGame().playPreviousMove()) ;
        mPrevious.setEnabled(false) ;
        mHuman.add(mPrevious) ;

        mSettingsHuman = createButtonIconTop(MESSAGE_SETTINGS, getResourcesLoader().getSettings(), Display.CD_SECONDARY,
                Display.CL_PRIMARY, 20,
                actionEvent -> getDisplay().onGoSettings()) ;
        mHuman.add(mSettingsHuman) ;

        mNext = createButtonIconTop(MESSAGE_NEXT, getResourcesLoader().getNext(), Display.CD_SECONDARY,
                Display.CL_QUINARY, 20,
                actionEvent -> getGame().playNextMove()) ;
        mNext.setEnabled(false) ;
        mHuman.add(mNext) ;

        mCardPanel.add(mHuman, HUMAN_PANEL) ;
    }

    @Override
    public void update(java.util.Observable observable, Object o)
    {
        mCardLayout.show(mCardPanel, getGame().isOnlyIAs() ? IA_PANEL : HUMAN_PANEL) ;

        if (getGame().getState() == Game.State.START)
        {
            // Switch.
            if (getGame().isOnlyIAs())
            {
                changeIAPausePlay(true) ;
            }
        }
        else if (getGame().getState() == Game.State.GAME_OVER)
        {
            if (getGame().isOnlyIAs())
            {
                changeIAPausePlay(false) ;
            }
        }

        refreshHistoryButtons() ;
        refreshIAButtons() ;
    }

    private void changeIAPausePlay(boolean start)
    {
        if (start)
        {
            mPause = createButtonIconTop(MESSAGE_PAUSE, getResourcesLoader().getPause(), Display.CD_SECONDARY,
                    Display.CL_QUINARY, 20,
                    actionEvent -> getGame().interruptIAs()) ;

            mPlay = createButtonIconTop(MESSAGE_PLAY, getResourcesLoader().getPlay(), Display.CD_SECONDARY,
                    Display.CL_QUINARY, 20,
                    actionEvent -> getGame().continueIAs(Game.State.CONTINUE_IAS_DELAY)) ;
        }
        else
        {
            mPause = createButtonIconTop(MESSAGE_PREVIOUS, getResourcesLoader().getPrevious(), Display.CD_SECONDARY,
                    Display.CL_QUINARY, 20,
                    actionEvent -> getGame().playPreviousMove()) ;

            mPlay = createButtonIconTop(MESSAGE_NEXT, getResourcesLoader().getNext(), Display.CD_SECONDARY,
                    Display.CL_QUINARY, 20,
                    actionEvent -> getGame().playNextMove()) ;
        }

        mIAs.removeAll() ;
        mIAs.add(mBeginning) ;
        mIAs.add(mPause) ;
        mIAs.add(mSettingsIAs) ;
        mIAs.add(mPlay) ;
        mIAs.add(mEnd) ;
        mIAs.revalidate() ;
    }

    public void refreshHistoryButtons()
    {
        mPrevious.setBackground(getGame().getHistory().canUndo()
                && ((getGame().isHumanVsIAs() && getGame().isGameFinished()) || ! getGame().isHumanVsIAs())
                ? Display.CL_PRIMARY : Display.CL_QUINARY) ;
        mPrevious.setEnabled(getGame().getHistory().canUndo()
                && ((getGame().isHumanVsIAs() && getGame().isGameFinished()) || ! getGame().isHumanVsIAs())) ;

        mNext.setBackground(getGame().getHistory().canRedo()
                && ((getGame().isHumanVsIAs() && getGame().isGameFinished()) || ! getGame().isHumanVsIAs())
                ? Display.CL_PRIMARY : Display.CL_QUINARY) ;
        mNext.setEnabled(getGame().getHistory().canRedo()
                && ((getGame().isHumanVsIAs() && getGame().isGameFinished()) || ! getGame().isHumanVsIAs())) ;
    }

    public void refreshIAButtons()
    {
        mBeginning.setBackground(getGame().getHistory().canUndo() ? Display.CL_PRIMARY : Display.CL_QUINARY) ;
        mBeginning.setEnabled(getGame().getHistory().canUndo()) ;

        if (getGame().isGameFinished())
        {
            mPause.setBackground(getGame().getHistory().canUndo() ? Display.CL_PRIMARY : Display.CL_QUINARY) ;
            mPause.setEnabled(getGame().getHistory().canUndo()) ;

            mPlay.setBackground(getGame().getHistory().canRedo() ? Display.CL_PRIMARY : Display.CL_QUINARY) ;
            mPlay.setEnabled(getGame().getHistory().canRedo()) ;

            mBeginning.setBackground(getGame().getHistory().canUndo() ? Display.CL_PRIMARY : Display.CL_QUINARY) ;
            mBeginning.setEnabled(getGame().getHistory().canUndo()) ;
        }
        else
        {
            mPause.setBackground((getGame().getState() == Game.State.CHOOSE_TILES
                    || getGame().getState() == Game.State.SELECT_ROW)
                    && getGame().getState() != Game.State.INTERRUPT_IAS ?
                    Display.CL_PRIMARY : Display.CL_QUINARY) ;

            mPause.setEnabled((getGame().getState() == Game.State.CHOOSE_TILES
                    || getGame().getState() == Game.State.SELECT_ROW)
                    && getGame().getState() != Game.State.INTERRUPT_IAS) ;

            mPlay.setBackground(getGame().getState() == Game.State.INTERRUPT_IAS
                    && (! getGame().isGameFinished() || getGame().getHistory().canRedo())
                    ? Display.CL_PRIMARY : Display.CL_QUINARY) ;
            mPlay.setEnabled(getGame().getState() == Game.State.INTERRUPT_IAS
                    && (! getGame().isGameFinished() || getGame().getHistory().canRedo())) ;

            mBeginning.setBackground(Display.CL_QUINARY) ;
            mBeginning.setEnabled(false) ;
        }

        mEnd.setBackground(getGame().getHistory().canRedo() || getGame().getState() == Game.State.GAME_OVER
                || ! getGame().isGameFinished() ?
                Display.CL_PRIMARY : Display.CL_QUINARY) ;
        mEnd.setEnabled(getGame().getHistory().canRedo() || getGame().getState() == Game.State.GAME_OVER
                || ! getGame().isGameFinished()) ;
    }
}