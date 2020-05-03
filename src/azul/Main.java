package azul;

import javax.swing.SwingUtilities;

import azul.model.Game;
import azul.view.Display;

public class Main
{
    public static void main(String[] args)
    {
        int nbPlayer = 4 ;

        Game game = new Game() ;
        game.startGame(nbPlayer) ;

        Display display = new Display(game) ;
    	SwingUtilities.invokeLater(display) ;
    }
}
