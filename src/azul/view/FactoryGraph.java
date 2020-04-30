package azul.view;

import java.awt.Graphics;

import javax.swing.JComponent;

public class FactoryGraph extends JComponent {
	ImageLoader iml;
	
	public FactoryGraph(ImageLoader il)
	{
		iml = il;
	}
	
	public void paint(Graphics g)
    {
       g.drawImage(iml.bowl.image(),100,100,300,300,null);
    }
}
