package azul.view;

import java.awt.Graphics;

import javax.swing.JComponent;

import azul.model.player.HumanPlayer;
import azul.model.player.Player;
import azul.model.player.PlayerBoard.PlayerBoardException;
import azul.model.tiles.Tile;

public class PlayerBoardGraph extends JComponent {
    Player pCurrent;
    ImageLoader iml;

    //Setter
    public PlayerBoardGraph(ImageLoader il) {
    	
    	iml = il;
    }
    
    int resizeBoard(int x, int p) 
    {
        float a;
        a=(((float) x)/1000)*p;
        return (int) a;
    }
    
    public void paintEmptyWall(Graphics g,int sx,int sy,int h,int w)
    {
    	for(int i =0 ; i<5; i++){
            for(int j =0 ; j<5; j++){
                g.drawImage(iml.greenCase.image(),resizeBoard(i*90+440,w),resizeBoard(j*132+130,h),sx,sy,null);
                g.drawImage(iml.list[(5-i+j)%5].image(),resizeBoard(i*90+440,w),resizeBoard(j*132+130,h),sx,sy,null);
            }
        }
    }
    
    public void paintEmptyPatternLines(Graphics g,int sx,int sy,int h,int w)
    {
    	for(int i =0 ; i<5; i++){
            for(int j = 4 ;  j>3-i; j--){
                g.drawImage(iml.brownCase.image(),resizeBoard(j*75+10,w),resizeBoard(i*132+130,h),sx,sy,null);
            }
        }
    }
    
    public void paintPlayerWall(Graphics g,int sx,int sy,int h,int w) throws PlayerBoardException {
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                Tile t = pCurrent.mPlayerBoard.getInWall(i,j);
                g.drawImage(iml.paintIngredient(t).image(),resizeBoard(j*90+440,w),resizeBoard(i*150+110,h),sx,sy,null);
            }
        }
    }
    
    public void paintPlayerPatternLines(Graphics g,int sx,int sy,int h,int w) throws PlayerBoardException
    {
    	for(int i =0 ; i<5; i++){
            for(int j = 4 ;  j>3-i; j--){
            	Tile t = pCurrent.mPlayerBoard.getInPatternLines(i,j);
            }
        }
    }
    
    public void initPlayerBoard(Graphics g) throws PlayerBoardException
    {
    	int h=getHeight();
        int w=getWidth();
        int sx,sy;
        
        sx=resizeBoard(70,w);
        sy=resizeBoard(100,h);

        g.drawImage(iml.board.image(),0,0,w,h,null);
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
