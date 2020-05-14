package azul.controller;

import azul.controller.human.Human;
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
    private final int ANIMATION_IA_DELAY = 1000 ;

    // Model part.
    private Game mGame ;
    // Human controller.
    private Human mHuman ;
    // IA controllers.
    private IARandom mIARandom ;
    private IAMinimax mIAMinimax ;
    // If only IAs playing.
    private boolean mIAStarted ;

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
        // Observe the game.
        mGame.addObserver(this) ;
    }

    public void onClick(Drawable selected)
    {
        if (mGame.isOnlyIAs() && ! mIAStarted)
        {
            IAPlay() ;
            mIAStarted = true ;
        }
        else
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
    }

    private void IAPlay()
    {
        // Current player for this turn.
        Player player = mGame.getPlayer() ;

        if (player instanceof IAPlayer)
        {
            // It's a IA turn.
            switch (((IAPlayer) player).getType())
            {
                case IA_RANDOM : playIAMove(mIARandom.play()) ; break ;
                case IA_MINIMAX : playIAMove(mIAMinimax.play()) ;
            }
        }
    }

    private void playIAMove(Move move)
    {
        Timer timer = new Timer(ANIMATION_IA_DELAY,
                actionEvent ->
                {
                    mGame.playMove(move) ;
                    IAPlay() ;
                }
        ) ;
        timer.setRepeats(false) ;
        timer.start() ;
    }

    @Override
    public void update(Observable observable, Object o)
    {
        if (mGame.getState() == Game.State.START)
        {
            mIARandom.initialize() ;
            mIAMinimax.initialize() ;

            mIAStarted = false ;
        }
    }
}