package azul.model.player;

public class IAPlayer extends Player
{
    // To know if the IA is the random or minimax one.
    private Type mType ;

    /**
     * Player controlled by an IA.
     * @param playerName name printed during the game.
     */
    public IAPlayer(Type type, String playerName)
    {
        super(playerName) ;

        mType = type ;
    }

    public Type getType()
    {
        return mType ;
    }
}
