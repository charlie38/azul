package azul.view;

import azul.model.tiles.Tile;

public class ImageLoader {
	public ImageAzul empty,flower,flowerB,claw,clawB,mushroom,mushroomB,crystal,crystalB,eye,eyeB,board,bowl,bowlcase,greenCase,brownCase;
    public ImageAzul list[]=new ImageAzul[5];
    
    ImageLoader()
    {
    	//Initialization of all images
    	empty = new ImageAzul("res/img/empty.png");
        flower = new ImageAzul("res/img/flower.png");
        flowerB = new ImageAzul("res/img/flower_blur.png");
        claw = new ImageAzul("res/img/claw.png");
        clawB = new ImageAzul("res/img/claw_blur.png");
        mushroom = new ImageAzul("res/img/mushroom.png");
        mushroomB = new ImageAzul("res/img/mushroom_blur.png");
        crystal = new ImageAzul("res/img/crystal.png");
        crystalB = new ImageAzul("res/img/crystal_blur.png");
        eye = new ImageAzul("res/img/eye.png");
        eyeB = new ImageAzul("res/img/eye_blur.png");
        board = new ImageAzul("res/img/board.png");
        bowl = new ImageAzul("res/img/bowl.png");
        bowlcase = new ImageAzul("res/img/bowlcase.png");
        greenCase = new ImageAzul("res/img/greencase.png");
        brownCase = new ImageAzul("res/img/browncase.png");
        list= new ImageAzul[]{mushroomB, crystalB, eyeB, clawB, flowerB};
    }
    
    public ImageAzul paintIngredient(Tile t)
    {
    	switch(t)
    	{
    		case CRYSTAL:
    			return crystal;
    		case EYE:
    			return eye;
    		case CLAW:
    			return claw;
    		case FLOWER:
    			return flower;
    		case MUSHROOM:
    			return mushroom;
    		case EMPTY:
    			return empty;
    		default:
    			return null;
    	}
    }
    
}
