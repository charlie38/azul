package azul.view.resource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;

public class ImageAzul
{
	// Error messages that can be threw.
	private final String ERROR_PATH = "\nCan't load the image: path error." ;
	private final String ERROR_FORMAT = "\nCan't load the image: format error." ;

	private Image mImage ;

	/**
	 * An image to keep additional information.
	 */
	public ImageAzul(String path)
	{
	    loadImage(path) ;
	}

	private void loadImage(String path)
	{
		FileInputStream stream ;

		try
		{
			stream = new FileInputStream(path) ;

			try
			{
				mImage = ImageIO.read(stream) ;
			}
			catch (Exception e)
			{
				System.err.println(path.concat(ERROR_FORMAT)) ;
			}
		}
		catch(Exception e)
		{
			System.err.println(path.concat(ERROR_PATH)) ;
		}
	}

	public Image get()
	{
		return mImage ;
	}
}
