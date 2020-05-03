package azul.view.ui;

/*
 * EXIT BUTTON : A JButton, used to quit the window when user click on it.
 * 
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ExitButton extends JButton
{
	//Setter
	public ExitButton(String name)
	{
		//Button text
		this.setText(name);
		
		//Method to quit after action
		this.addActionListener(new ActionListener() 
		{
			public void actionPerformed (ActionEvent e) 
			{
				  System.exit(0);
			}
		});
	}
}
