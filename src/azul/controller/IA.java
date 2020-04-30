package azul.controller;

import azul.model.Game;
import azul.model.move.Move;

public abstract class IA
{
    protected Game mGame ;

    /**
     * Initialize the IA before starting the game.
     */
    public abstract void initialize() ;

    /**
     * Called at the end of the game.
     */
    public abstract void finish() ;

    /**
     * Find a move playable.
     * @return a move.
     */
    public abstract Move play() ;
}
