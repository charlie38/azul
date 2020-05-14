package azul.controller;

import azul.controller.human.Human;
import azul.controller.ia.IA;
import azul.controller.ia.minimax.IAMinimax;
import azul.controller.ia.random.IARandom;
import azul.model.Game;
import azul.model.move.*;
import azul.model.player.HumanPlayer;
import azul.model.player.IAPlayer;
import azul.model.player.Player;
import azul.model.tile.Tile;
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
    // Human controller.
    private Human mHuman ;
    // IA controllers.
    private IARandom mIARandom ;
    private IAMinimax mIAMinimax ;

    /**
     * Play moves on user interactions.
     * @param game the model.
     */
    public Mediator(Game game)
    {
        mGame = game ;
        mHuman = new Human(game) ;
        mIAMinimax = new IAMinimax(game, IAMinimax.Difficulty.EASY) ;
        mIARandom = new IARandom(game) ;
    }

    public void onClick(Drawable selected)
    {
        // Current player for this turn.
        Player player = mGame.getPlayer() ;

        if (player instanceof HumanPlayer)
        {
            // It's an human turn.
            mHuman.onClick(selected) ;
            // Check if it's a IA turn.
            IAPlay() ;
        }
    }

    private void IAPlay()
    {
        // Current player for this turn.
        Player player = mGame.getPlayer() ;

        if (player instanceof IAPlayer)
        {
            // It's a IA turn.
            switch (((IAPlayer) player).getType())
            {
                case IA_RANDOM : mGame.playMove(mIARandom.play()) ; IAPlay() ; break ;
                case IA_MINIMAX : mGame.playMove(mIAMinimax.play()) ; IAPlay() ;
            }
        }
    }
}
