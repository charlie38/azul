package azul.model.move;

import azul.model.Game;

public abstract class Move
{
    /**
     * Contains game changes.
     */
    protected Move() { }

    public abstract void undo(Game game) ;

    public abstract void do_(Game game) ;

    public abstract void redo(Game game) ;
}