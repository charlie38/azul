package azul.view;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;

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
		frame.add(new PlayerBoardGraph());
		
		
		//Settings of the window
		toggleFullScreen();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Exit button
		//frame.add(new ExitButton("Quit"));
		
	}

}
