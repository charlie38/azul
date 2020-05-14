package azul.controller.ia;

import azul.model.Game;
import azul.model.move.Move;

public abstract class IA
{
    // Model part.
    protected Game mGame ;

    public IA(Game game)
    {
        mGame = game ;
    }

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
     * @return a playable move.
     */
    public abstract Move play() ;
}
