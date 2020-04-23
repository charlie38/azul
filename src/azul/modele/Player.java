package azul.modele;

public abstract class Player
{
    private PlayerBoard mPlayerBoard ;

    /**
     * Extended by the IA or human player.
     */
    public Player()
    {
        mPlayerBoard = new PlayerBoard() ;
    }

    /**
     * Play the player's intentions.
     * @param move contains the player's intentions.
     */
    public void play(Move move)
    {
        // TODO depending on 'Move.java'
    }
}
