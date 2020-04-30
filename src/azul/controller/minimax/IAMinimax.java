package azul.controller.minimax;

import azul.controller.IA;
import azul.model.move.Move;

import java.util.Collection;

public class IAMinimax extends IA
{
    private final String START_MESSAGE = "IA minimax starts" ;
    private final String END_MESSAGE = "IA minimax ends" ;

    private final float NAN_EVALUATION = 1f / 1000f ;

    /**
     * IA using the 'minimax' algorithm.
     */
    public IAMinimax()
    {
    }

    @Override
    public void initialize()
    {
        System.out.println(START_MESSAGE) ;
    }

    @Override
    public void finish()
    {
        System.out.println(END_MESSAGE) ;
    }

    /**
     * @return a move calculated with the 'minimax' algorithm.
     */
    @Override
    public Move play()
    {
        Position current = getCurrentPosition() ;

        float eval = minimax(current, current.getDepth(), true) ;

        return getMove(eval) ;
    }

    /**
     * Return an evaluation calculated by the 'minimax' algorithm.
     * @param position current 'tree' state.
     * @param depth current 'tree' depth.
     * @param isMaximizingPlayer
     * @return 'NAN_EVALUATION' if can't play/game is over, else return an evaluation.
     */
    public float minimax(Position position, int depth, boolean isMaximizingPlayer)
    {
        // TODO Minimax for more than 2 players...
        
        if (depth == 0 || mGame.isGameOver())
        {
            return NAN_EVALUATION ;
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

    private Move getMove(float eval)
    {
        if (eval == NAN_EVALUATION)
        {
            return getMoveNAN() ;
        }

        Move move = null ;
        // Todo
        return move ;
    }

    private Move getMoveNAN()
    {
        Move move = null ;
        // Todo
        return move ;
    }

    private Position getCurrentPosition()
    {
        Position position = null ;
        // TODO
        return position ;
    }
}