package azul.controller.minimax;

import azul.model.Game;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Position
{
    // The value assigned.
    private final int mState ;
    // Player turn.
    private final int mPlayer ;

    /**
     * Contains
     * @param state the value assigned to this node.
     * @param player the player who it's the turn.
     */
    public Position(int state, int player)
    {
        mState = state;
        mPlayer = player ;
    }

    public Collection<Position> getAll(Game game)
    {
        List<Position> positions = new LinkedList<>() ;

        // TODO assign
        // Here we must give the appropriate possible players moves. (limited in nb)

        return positions ;
    }

    public int getDepth()
    {
        // TODO
        return 0 ;
    }
}
