package azul;

import javax.swing.SwingUtilities;

import azul.controller.Mediator;
import azul.model.Game;
import azul.view.Display;

public class Main
{
    public static void main(String[] args)
    {
        // Model.
        Game game = new Game() ;                        // -> No dependencies, but use Observable/Observer pattern.
        // Controller.
        Mediator mediator = new Mediator(game) ;        // -> "Model" dependency.
        // View.
        Display display = new Display(game, mediator) ; // -> "Model" and "Controller" dependencies.

        // Start.
        SwingUtilities.invokeLater(display) ;
    }
}
