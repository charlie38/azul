package azul.view;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainDisplay implements Runnable 
{
	JFrame frame;
	
	
	public MainDisplay()
	{
		
	}
	
	/*Method for full screen*/
	public void toggleFullScreen()
	{
		GraphicsEnvironment env =  GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice dev = env.getDefaultScreenDevice();
		dev.setFullScreenWindow(frame);
	}

	@Override
	public void run()
	{
		//Initialization of components
		frame = new JFrame("Azul");
		ImageLoader iml = new ImageLoader();
		PlayerBoardGraph p1 = new PlayerBoardGraph(iml);
		PlayerBoardGraph p2 = new PlayerBoardGraph(iml);
		PlayerBoardGraph p3 = new PlayerBoardGraph(iml);
		PlayerBoardGraph p4 = new PlayerBoardGraph(iml);
		
		JPanel pan= new JPanel(new GridLayout(3,3));
		pan.add(p1);
		pan.add(new FactoryGraph(iml));
		pan.add(p2);
		pan.add(new FactoryGraph(iml));pan.add(new FactoryGraph(iml));pan.add(new FactoryGraph(iml));
		pan.add(p3);
		pan.add(new FactoryGraph(iml));
		pan.add(p4);
		
		
		//Settings of the window
		//toggleFullScreen();
		frame.setSize(1000,1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//Building of interface
		frame.add(pan);
		
	}

}
