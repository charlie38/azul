package azul.view;
/*
 * An image to keep some additional informations
 * 
 */

import java.awt.Image;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageAzul {
	Image i;
	
	//Setter
	ImageAzul(String path)
	{
		FileInputStream in;
		try 
		{
			in = new FileInputStream(path);
			
			try
			{
				i = ImageIO.read(in);
			}
			
			//Exception : The image is not accepted
			catch(Exception e)
			{
				System.err.println("***ERROR OF IMAGE***");
			}
		}
		
		//Exception : File not found or bad path
		catch(Exception e)
		{
			System.err.println("***ERROR OF PATH***");
		}
	}
	
	//Getter
	Image image()
	{
		return i;
	}
}
