package azul.controller;

import azul.controller.human.Human;
import azul.controller.ia.easy.IAEasy;
import azul.controller.ia.minimax.IAMinimax;
import azul.controller.ia.random.IARandom;
import azul.model.Game;
import azul.model.move.Move;
import azul.model.player.HumanPlayer;
import azul.model.player.IAPlayer;
import azul.model.player.Player;
import azul.view.drawable.Drawable;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class Mediator implements Observer
{
    // Delay btw two IA moves.
    public static final int ANIMATION_IA_DEFAULT_DELAY = 2500;
    public static final int ANIMATION_IA_NO_DELAY = 0 ;
    private int ANIMATION_IA_DELAY = ANIMATION_IA_DEFAULT_DELAY ;

    // Model part.
    private Game mGame ;
    // Human controller.
    private Human mHuman ;
    // IA controllers.
    private IARandom mIARandom ;
    private IAMinimax mIAMinimax ;
    private IAEasy mIAEasy;
    // If only IAs playing.
    private boolean mIAStarted ;
    // For delaying IAs plays.
    private Timer mIATimer ;

    /**
     * Play moves on user interactions.
     * @param game the model.
     */
    public Mediator(Game game)
    {
        mGame = game ;
        mHuman = new Human(game) ;
        mIAMinimax = new IAMinimax(game, IAMinimax.Difficulty.EASY) ;
        mIARandom = new IARandom(game) ;
        mIAEasy = new IAEasy(game) ;
        
        // Observe the game.
        mGame.addObserver(this) ;
    }

    public void onClick(Drawable selected)
    {
        // Current player for this turn.
        Player player = mGame.getPlayer() ;

        if (player instanceof HumanPlayer)
        {
            // It's an human turn.
            mHuman.onClick(selected) ;
            // Check if it's a IA turn.
            IAPlay() ;
        }
    }

    public void IAPlay()
    {
        // Current player for this turn.
        Player player = mGame.getPlayer() ;

        if (mGame.getState() == Game.State.INTERRUPT_IAS)
        {
            return ;
        }

        if (player instanceof IAPlayer)
        {
            // It's a IA turn.
            if (ANIMATION_IA_DELAY == ANIMATION_IA_NO_DELAY)
            {
                switch (((IAPlayer) player).getType())
                {
                    case IA_RANDOM : playIAMove(mIARandom.play()) ; break ;
                    case IA_MINIMAX : playIAMove(mIAMinimax.play()) ; break ;
                    case IA_EASY : playIAMove(mIAEasy.play()) ;
                }
            }
            else
            {
                mIATimer = new Timer(ANIMATION_IA_DELAY,
                        actionEvent ->
                        {
                            // Can't factorize code <!> action in mIA_.play() should also be delayed.
                            switch (((IAPlayer) player).getType())
                            {
                                case IA_RANDOM : playIAMove(mIARandom.play()) ; break ;
                                case IA_MINIMAX : playIAMove(mIAMinimax.play()) ; break ;
                                case IA_EASY : playIAMove(mIAEasy.play()) ;
                            }
                        }
                ) ;
                mIATimer.setRepeats(false) ;
                mIATimer.start() ;
            }
        }
    }

    private void playIAMove(Move move)
    {
        if (mGame.getState() == Game.State.GAME_OVER)
        {
            return ;
        }

        mGame.playMove(move) ;
        IAPlay() ;
    }

    @Override
    public void update(Observable observable, Object o)
    {
        switch (mGame.getState())
        {
            case START : onStart() ; break ;
            case INTERRUPT_IAS : onInterruptIAs() ; break ;
            case CONTINUE_IAS_DELAY : onContinueIAsWithDelay() ; break ;
            case CONTINUE_IAS_NO_DELAY : onContinueIAsWithNoDelay() ; break ;
        }
    }

    private void onStart()
    {
        mIARandom.initialize() ;
        mIAMinimax.initialize() ;
        mIAEasy.initialize();

        mIAStarted = false ;
        ANIMATION_IA_DELAY = ANIMATION_IA_DEFAULT_DELAY ;
    }

    private void onInterruptIAs()
    {
        if (mIATimer != null)
        {
            mIATimer.stop() ;
        }
    }

    private void onContinueIAsWithDelay()
    {
        onInterruptIAs() ;

        ANIMATION_IA_DELAY = ANIMATION_IA_DEFAULT_DELAY ;

        Game.State state = mGame.getPreviousState() ;
        mGame.setState(state == Game.State.START ? Game.State.CHOOSE_TILES : state) ;
        mGame.notifyUI() ;

        IAPlay() ;
    }

    private void onContinueIAsWithNoDelay()
    {
        onInterruptIAs() ;

        ANIMATION_IA_DELAY = ANIMATION_IA_NO_DELAY ;

        Game.State state = mGame.getPreviousState() ;
        mGame.setState(state == Game.State.START ? Game.State.CHOOSE_TILES : state) ;
        mGame.notifyUI() ;

        IAPlay() ;
    }
}