package azul.model.tile;

/**
 * Tiles used in the game.
 */
public enum Tile
{
    CRYSTAL,
    EYE,
    CLAW,
    FLOWER,
    MUSHROOM,
    FIRST_PLAYER_MAKER,     // For the first player who will take tiles from the middle of the table.
    EMPTY ;                 // No tile.

    private static boolean mIsFirstMakerTaken = false ;

    /**
     * Called before the game round start.
     */
    public static void onRoundStart()
    {
        mIsFirstMakerTaken = false ;
    }

    /**
     * Give the first player marker to an user.
     * @return the first player marker.
     * @throws FirstPlayerMarkerException if the first player maker was taken.
     */
    public static Tile takeFirstPlayerMaker() throws FirstPlayerMarkerException
    {
        if (mIsFirstMakerTaken)
        {
            throw new FirstPlayerMarkerException() ;
        }

        mIsFirstMakerTaken = true ;

        return FIRST_PLAYER_MAKER ;
    }

    public static void setFirstPlayerMarkerTaken()
    {
        mIsFirstMakerTaken = true ;
    }

    public static boolean isFirstPlayerMakerTaken()
    {
        return mIsFirstMakerTaken ;
    }

    public static class FirstPlayerMarkerException extends Exception
    {
        // Messages that can be threw.
        private static final String ALREADY_TAKEN = "The marker was already taken." ;

        /**
         * To throw exception when the marker was already taken.
         */
        public FirstPlayerMarkerException()
        {
            super(ALREADY_TAKEN) ;
        }
    }
}
