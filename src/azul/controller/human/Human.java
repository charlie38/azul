package azul.controller.human;

import azul.model.Game;
import azul.model.move.ChooseFloorLine;
import azul.model.move.ChoosePatternLine;
import azul.model.move.TakeInFactory;
import azul.model.move.TakeOnTable;
import azul.model.tile.Tile;
import azul.view.drawable.Drawable;
import azul.view.drawable.game.board.FloorLineArrow;
import azul.view.drawable.game.board.PatternLineArrow;
import azul.view.drawable.game.factory.FactoryTile;
import azul.view.drawable.game.table.TableTile;
import azul.controller.human.sound.SoundEffect;

import java.util.ArrayList;

public class Human
{
    // Model part.
    protected Game mGame ;

    public Human(Game game)
    {
        mGame = game ;
    }

    public void onClick(Drawable selected)
    {
        switch (mGame.getState())
        {
            case CHOOSE_TILES :

                // Check if user choose tiles in a factory/table.
                if (selected instanceof FactoryTile
                        && ! mGame.getFactory(((FactoryTile) selected).getFactoryIndex()).isEmpty())
                {
                  try {
                    SoundEffect audioPlayer = new SoundEffect();
                        audioPlayer.play();
                  }
                  catch (Exception e){
                      System.out.println(e);
                    }
                    chooseInFactory((FactoryTile) selected) ;
                }
                else if (selected instanceof TableTile
                        && ! mGame.isTableEmpty())
                {
                  try {
                    SoundEffect audioPlayer = new SoundEffect();
                        audioPlayer.play();
                  }
                  catch (Exception e){
                      System.out.println(e);
                    }
                    chooseOnTable((TableTile) selected) ;
                }

                break ;

            case SELECT_ROW :

                // Check if user select a pattern/floor line to put his selected tiles.
                if (selected instanceof PatternLineArrow
                        && ((PatternLineArrow) selected).getPlayerIndex() == mGame.getPlayerIndex())
                {
                  try {
                    SoundEffect audioPlayer = new SoundEffect();
                        audioPlayer.play();
                  }
                  catch (Exception e){
                      System.out.println(e);
                    }
                    selectPatternLine((PatternLineArrow) selected) ;
                }
                else if (selected instanceof FloorLineArrow
                        && ((FloorLineArrow) selected).getPlayerIndex() == mGame.getPlayerIndex())
                {
                  try {
                    SoundEffect audioPlayer = new SoundEffect();
                        audioPlayer.play();
                  }
                  catch (Exception e){
                      System.out.println(e);
                    }
                    selectFloorLine() ;
                }
        }
    }

    private void chooseInFactory(FactoryTile selected)
    {
        int factory = selected.getFactoryIndex() ;
        int tile = selected.getTileIndex() ;
        // Get the tiles in the factory.
        ArrayList<Tile> factoryTiles = (ArrayList<Tile>) mGame.getFactory(factory).getTiles().clone() ;
        // Get the tile selected by the user.
        azul.model.tile.Tile tileSelected = mGame.getFactory(factory).getTile(tile) ;
        // And get all the factory tiles of this color.
        ArrayList<azul.model.tile.Tile> tilesSelected = mGame.getFactory(factory).take(tileSelected) ;
        // Play it.
        mGame.playMove(new TakeInFactory(mGame.getPlayer(), tilesSelected,
                mGame.getFactory(factory), factoryTiles)) ;
    }

    private void chooseOnTable(TableTile selected)
    {
        int tile = selected.getTileIndex() ;
        // Get the tiles on the table.
        ArrayList<Tile> tableTiles = (ArrayList<Tile>) mGame.getTilesTable().clone() ;
        // Get the tile selected by the user.
        azul.model.tile.Tile tileSelected = mGame.getInTilesTable(tile) ;
        // And get all the table tiles of this color.
        ArrayList<azul.model.tile.Tile> tilesSelected = mGame.takeOnTable(tileSelected) ;
        // Play it.
        mGame.playMove(new TakeOnTable(mGame.getPlayer(), tilesSelected,
                ! azul.model.tile.Tile.isFirstPlayerMakerTaken(), tableTiles)) ;
    }

    private void selectPatternLine(PatternLineArrow selected)
    {
        // Get the selected row.
        int row = selected.getRowIndex() ;
        // Play it.
        mGame.playMove(new ChoosePatternLine(mGame.getPlayer(), mGame.getTilesAside(), mGame.getTilesRemaining(), row)) ;
    }

    private void selectFloorLine()
    {
        // Play it.
        mGame.playMove(new ChooseFloorLine(mGame.getPlayer(), mGame.getTilesAside(), mGame.getTilesRemaining())) ;
    }
}
