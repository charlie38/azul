package azul.model.move;

public abstract class Move
{
    // The type of this move.
    private Type mType ;

    /**
     * Contains game changes.
     */
    protected Move(Type type, Type... expected)
    {
        try
        {
            checkType(type, expected) ;
        }
        catch (MoveException e)
        {
            e.printStackTrace() ;
        }

        mType = type ;
    }

    /**
     * Throws an exception if the type used is not corresponding to the move constructor.
     * @param current type used.
     * @param expected types expected with this constructor.
     * @throws MoveException if the type is not in 'expected'.
     */
    private void checkType(Type current, Type... expected) throws MoveException
    {
        for (int i = 0 ; expected != null && i < expected.length ; i ++)
        {
            if (current == expected[i])
            {
                return ;
            }
        }

        throw new MoveException() ;
    }

    public Type getType()
    {
        return mType ;
    }

    private static class MoveException extends Exception
    {
        // Messages that can be threw.
        private static final String TYPE_CONSTRUCTOR = "Your using the wrong type of move for this 'Move' constructor." ;

        /**
         * To throw exception when using wrong type of move with a constructor of 'Move'.
         */
        public MoveException()
        {
            super(TYPE_CONSTRUCTOR) ;
        }
    }
}
