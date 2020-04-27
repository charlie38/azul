package azul;

import javax.swing.SwingUtilities;

import azul.view.MainDisplay;

public class Main
{
    public static void main(String[] args)
    {
    	SwingUtilities.invokeLater(new MainDisplay());
    }
}
