package azul.view;

import java.awt.Graphics;

import javax.swing.JComponent;

import azul.model.player.HumanPlayer;
import azul.model.player.Player;
import azul.model.player.PlayerBoard.PlayerBoardException;
import azul.model.tiles.Tile;

public class PlayerBoardGraph extends JComponent {
	ImageAzul empty,flower,flowerB,claw,clawB,mushroom,mushroomB,crystal,crystalB,eye,eyeB,board,greenCase,brownCase;
    ImageAzul list[]=new ImageAzul[5];
    Player pCurrent;

    //Setter
    public PlayerBoardGraph() {
    	
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
        greenCase = new ImageAzul("res/img/greencase.png");
        brownCase = new ImageAzul("res/img/browncase.png");
        list= new ImageAzul[]{mushroomB, crystalB, eyeB, clawB, flowerB};
    }
    
    int resizeBoard(int x, int p) 
    {
        float a;
        a=(((float) x)/1000)*p;
        return (int) a;
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
    
    public void paintEmptyWall(Graphics g,int sx,int sy,int h,int w)
    {
    	for(int i =0 ; i<5; i++){
            for(int j =0 ; j<5; j++){
                g.drawImage(greenCase.image(),resizeBoard(i*90+430,w),resizeBoard(j*100+330,h),sx,sy,null);
                g.drawImage(list[(5-i+j)%5].image(),resizeBoard(i*90+430,w),resizeBoard(j*100+330,h),sx,sy,null);
            }
        }
    }
    
    public void paintEmptyPatternLines(Graphics g,int sx,int sy,int h,int w)
    {
    	for(int i =0 ; i<5; i++){
            for(int j = 4 ;  j>3-i; j--){
                g.drawImage(brownCase.image(),resizeBoard(j*80-10,w),resizeBoard(i*100+330,h),sx,sy,null);
            }
        }
    }
    
    public void paintPlayerWall(Graphics g,int sx,int sy,int h,int w) throws PlayerBoardException {
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                Tile t = pCurrent.mPlayerBoard.getInWall(i,j);
                g.drawImage(paintIngredient(t).image(),resizeBoard(j*90+430,w),resizeBoard(i*100+330,h),sx,sy,null);
            }
        }
    }
    
    public void paintPlayerPatternLines(Graphics g,int sx,int sy,int h,int w) throws PlayerBoardException
    {
    	for(int i =0 ; i<5; i++){
            for(int j = 4 ;  j>3-i; j--){
            	Tile t = pCurrent.mPlayerBoard.getInPatternLines(i,j);
                g.drawImage(paintIngredient(Tile.EYE).image(),resizeBoard(j*80-10,w),resizeBoard(i*100+330,h),sx,sy,null);
            }
        }
    }
    
    public void initPlayerBoard(Graphics g) throws PlayerBoardException
    {
    	int h=getHeight();
        int w=getWidth();
        int sx,sy;
        
        sx=resizeBoard(70,w);
        sy=resizeBoard(70,h);

        g.drawImage(board.image(),0,0,w,h,null);
        paintEmptyWall(g,sx,sy,h,w);
        paintEmptyPatternLines(g,sx,sy,h,w);
        paintPlayerWall(g,sx,sy,h,w);
        paintPlayerPatternLines(g,sx,sy,h,w);
    }

    public void paint(Graphics g)
    {
       pCurrent = new HumanPlayer(); //A enlever
       try 
       {
		initPlayerBoard(g);
       } 
       catch (PlayerBoardException e) 
       {
		e.printStackTrace();
       }
    }
}
