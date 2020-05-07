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
        Game game = new Game() ; // No dependencies.
        // Controller.
        Mediator mediator = new Mediator(game) ; // The "Model" dependency.
        // View.
        Display display = new Display(game, mediator) ; // The "Model Controller" dependencies.

        SwingUtilities.invokeLater(display) ;
    }
}
