package azul.controller.ia.minimax;

import azul.model.Game;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Position
{
    // TODO maybe remove the value , ect :
    // TODO this tree is about the minimax tree, and should be empty at the start
    // TODO value can be usefull to know how many child ect...
    // The value assigned.
    private final int mValue ;
    // Player turn.
    private final boolean mIsPlayerMaximizing ;

    /**
     * Contains the users 'tree' values.
     * @param value the value assigned to this node.
     * @param isPlayerMaximizing the player whose turn it's.
     */
    public Position(int value, boolean isPlayerMaximizing)
    {
        mValue = value ;
        mIsPlayerMaximizing = isPlayerMaximizing ;
    }

    public Collection<Position> getAll(Game game)
    {
        List<Position> positions = new LinkedList<>() ;
        // Here we must give the appropriate possible players moves (limited for efficiency).
        switch (mValue)
        {
            // TODO assign, and find a stop condition for the recursion
            case 1 : positions.add(new Position(0, ! mIsPlayerMaximizing)) ; break ;
            case 2 : positions.add(new Position(0, ! mIsPlayerMaximizing)) ; break ;
            case 3 : positions.add(new Position(0, ! mIsPlayerMaximizing)) ; break ;
            case 4 : positions.add(new Position(0, ! mIsPlayerMaximizing)) ; break ;
        }

        return positions ;
    }
}
