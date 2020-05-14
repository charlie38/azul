package azul.controller.ia.minimax;

import azul.controller.ia.IA;
import azul.model.Game;
import azul.model.move.Move;
import azul.model.player.Player;

import java.util.Collection;

public class IAMinimax extends IA
{
    // The algorithm starting depth.
    private final int EASY_STARTING_DEPTH = 3 ; // TODO Determinate (for efficiency, and IA difficulty)
    private final int MIDDLE_STARTING_DEPTH = 4 ; // TODO Determinate (for efficiency, and IA difficulty)
    private final int HARD_STARTING_DEPTH = 5 ; // TODO Determinate (for efficiency, and IA difficulty)
    // Difficulty of the IA.
    public enum Difficulty { EASY, MIDDLE, HARD }

    // Difficulty of the IA.
    private Difficulty mDifficulty ;
    // Starting depth, depends on the difficulty.
    private int mStartingDepth ;

    /**
     * IA using the 'minimax' algorithm.
     */
    public IAMinimax(Game game, Difficulty difficulty)
    {
        super(game) ;

        mDifficulty = difficulty ;

        switch (mDifficulty)
        {
            case EASY : mStartingDepth = EASY_STARTING_DEPTH ; break ;
            case MIDDLE : mStartingDepth = MIDDLE_STARTING_DEPTH ; break ;
            case HARD : mStartingDepth = HARD_STARTING_DEPTH ;
        }
    }

    @Override
    public void initialize()
    {
    }

    /**
     * @return a move calculated with the 'minimax' algorithm.
     */
    @Override
    public Move play()
    {
        Position current = getCurrentPosition() ;

        float eval = minimax(current, mStartingDepth, true) ;

        return getMove(eval) ;
    }

    /**
     * Return an evaluation calculated by the 'minimax' algorithm.
     * @param position current 'tree' node/state/value.
     * @param depth current 'tree' depth <-> the number of moves ahead we want to search.
     * @param isMaximizingPlayer true if it's the player's turn whose value we want to maximize (this IA).
     * @return an evaluation used to determinate the next player move (for this IA).
     */
    public float minimax(Position position, int depth, boolean isMaximizingPlayer)
    {
        if (depth == 0 || mGame.isGameOver())
        {
            return getStaticEvaluation(position) ;
        }

        Collection<Position> nodes = position.getAll(mGame) ;

        if (isMaximizingPlayer)
        {
            float maxEval = Float.NEGATIVE_INFINITY ;

            for (Position child : nodes)
            {
                float eval = minimax(child, depth - 1, false) ;
                maxEval = Math.max(maxEval, eval) ;
            }

            return maxEval ;
        }
        else
        {
            float minEval = Float.POSITIVE_INFINITY ;

            for (Position child : nodes)
            {
                float eval = minimax(child, depth - 1, true) ;
                minEval = Math.min(minEval, eval) ;
            }

            return minEval ;
        }
    }

    /**
     * Return a static evaluation of the current position in the 'tree' (because can't be evaluated).
     * @param position the current 'tree' node.
     * @return the node value.
     */
    private float getStaticEvaluation(Position position)
    {
        // TODO
        return 0f ;
    }

    private Move getMove(float eval)
    {
        Move move = null ;
        // Todo
        return move ;
    }

    private Position getCurrentPosition()
    {
        return new Position(evaluateValueFromPlayer(mGame.getPlayer()), true) ;
    }

    private int evaluateValueFromPlayer(Player player)
    {
        // TODO evaluate the current player value.
        return 0 ;
    }
}