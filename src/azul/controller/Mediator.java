package azul.controller;

import azul.model.Game;
import azul.view.drawable.Drawable;
import azul.view.drawable.board.PatternLineTile;
import azul.view.drawable.board.WallTile;
import azul.view.drawable.factory.Tile;

public class Mediator
{
    // Model part.
    private Game mGame ;

    public Mediator(Game game)
    {
        mGame = game ;
    }

    public void onClick(Drawable selected)
    {
        mGame.changePlayer() ;

        if (selected instanceof Tile)
        {
            // On click, the factory tile start blinking.
            ((Tile) selected).setIsAnimated(true) ;
        }
        else if (selected instanceof WallTile)
        {
            // On click, the wall tile start blinking.
            ((WallTile) selected).setIsAnimated(true) ;
        }
        else if (selected instanceof PatternLineTile)
        {
            // On click, the pattern line tile start blinking.
            ((PatternLineTile) selected).setIsAnimated(true) ;
        }
    }
}
