package azul.controller;

import azul.model.Game;
import azul.model.move.PlayerMove;
import azul.model.move.Type;
import azul.view.drawable.Drawable;
import azul.view.drawable.board.FloorLineArrow;
import azul.view.drawable.board.PatternLineArrow;
import azul.view.drawable.table.TableTile;
import azul.view.drawable.factory.FactoryTile;

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

                // Check if user choose tiles in a factory/table.
                if (selected instanceof FactoryTile)
                {
                    chooseInFactory((FactoryTile) selected) ;
                }
                else if (selected instanceof TableTile)
                {
                    chooseOnTable((TableTile) selected) ;
                }

                break ;

            case SELECT_ROW :

                // Check if user select a pattern/floor line to put his selected tiles.
                if (selected instanceof PatternLineArrow
                        && ((PatternLineArrow) selected).getPlayerIndex() == mGame.getPlayerIndex())
                {
                    selectPatternLine((PatternLineArrow) selected) ;
                }
                else if (selected instanceof FloorLineArrow
                        && ((FloorLineArrow) selected).getPlayerIndex() == mGame.getPlayerIndex())
                {
                    selectFloorLine() ;
                }
        }
    }

    private void chooseInFactory(FactoryTile selected)
    {
        int factory = selected.getFactoryIndex() ;
        int tile = selected.getTileIndex() ;
        // Get the tile selected by the user.
        azul.model.tile.Tile tileSelected = mGame.getFactory(factory).getTile(tile) ;
        // And get all the factory tiles of this color.
        ArrayList<azul.model.tile.Tile> tilesSelected = mGame.getFactory(factory).take(tileSelected) ;
        // Play it.
        mGame.playMove(new PlayerMove(Type.PLAYER_TAKE_FACTORY, mGame.getPlayer(), tilesSelected,
                mGame.getFactory(factory))) ;
    }

    private void chooseOnTable(TableTile selected)
    {
        int tile = selected.getTileIndex() ;
        // Get the tile selected by the user.
        azul.model.tile.Tile tileSelected = mGame.getInTilesTable(tile) ;
        // And get all the table tiles of this color.
        ArrayList<azul.model.tile.Tile> tilesSelected = mGame.takeOnTable(tileSelected) ;
        // Play it.
        mGame.playMove(new PlayerMove(Type.PLAYER_TAKE_TABLE, mGame.getPlayer(), tilesSelected,
                ! azul.model.tile.Tile.isFirstPlayerMakerTaken())) ;
    }

    private void selectPatternLine(PatternLineArrow selected)
    {
        // Get the selected row.
        int row = selected.getRowIndex() ;
        // Play it.
        mGame.playMove(new PlayerMove(Type.PLAYER_PLACE_TILES_IN_PATTERN, mGame.getPlayer(), row)) ;
        mGame.changePlayer() ;
    }

    private void selectFloorLine()
    {
        // Play it.
        mGame.playMove(new PlayerMove(Type.PLAYER_PLACE_TILES_IN_FLOOR, mGame.getPlayer())) ;
        mGame.changePlayer() ;
    }
}
