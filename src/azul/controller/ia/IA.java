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
     * Find a move playable.
     * @return a playable move.
     */
    public abstract Move play() ;
}
