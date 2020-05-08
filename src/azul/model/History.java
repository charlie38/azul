package azul.model;

import azul.model.move.Move;

import java.util.Stack;

public class History
{
    private Stack<Move> mPrevious ;
    private Stack<Move> mNext ;

    public History()
    {
        mPrevious = new Stack<>() ;
        mNext = new Stack<>() ;
    }

    public Move undo()
    {
        Move move = mPrevious.pop() ;
        mNext.push(move) ;

        return move ;
    }

    public void do_(Move move)
    {
        mPrevious.push(move) ;
        mNext.clear() ;
    }

    public Move redo()
    {
        Move move = mNext.pop() ;
        mPrevious.push(move) ;

        return move ;
    }

    public Move getPrevious()
    {
        return mPrevious.peek() ;
    }

    public void removeNext(Move move)
    {
        mNext.remove(move) ;
    }

    public boolean canUndo()
    {
        return mPrevious.size() > 0 ;
    }

    public boolean canRedo()
    {
        return mNext.size() > 0 ;
    }
}
