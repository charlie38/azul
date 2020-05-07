package azul.controller;

import azul.model.Game;
import azul.model.move.PlayerMove;
import azul.model.move.Type;
import azul.view.drawable.Drawable;
import azul.view.drawable.board.PatternLineArrow;
import azul.view.drawable.factory.Tile;

import java.util.ArrayList;

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
        switch (mGame.getState())
        {
            case CHOOSE_TILES :

                // Check if user choose tiles in a factory.
                if (selected instanceof Tile)
                {
                    int factory = ((Tile) selected).getFactoryIndex() ;
                    int tile = ((Tile) selected).getTileIndex() ;
                    // Get the tile selected by the user.
                    azul.model.tile.Tile tileSelected = mGame.getFactory(factory).getTile(tile) ;
                    // And get all the factory tiles of this color.
                    ArrayList<azul.model.tile.Tile> tilesSelected = mGame.getFactory(factory).take(tileSelected) ;
                    // Play it.
                    mGame.playMove(new PlayerMove(Type.PLAYER_TAKE_FACTORY, mGame.getPlayer(), tilesSelected)) ;
                }

                break ;

            case SELECT_ROW :

                // Check if user select a pattern line to put his selected tiles.
                if (selected instanceof PatternLineArrow
                        && ((PatternLineArrow) selected).getPlayerIndex() == mGame.getPlayerIndex())
                {
                    // Get the selected row.
                    int row = ((PatternLineArrow) selected).getRowIndex() ;
                    // Play it.
                    mGame.playMove(new PlayerMove(Type.PLAYER_PLACE_TILES_IN_PATTERN, mGame.getPlayer(), row)) ;
                    mGame.changePlayer() ;
                }
        }
    }
}
