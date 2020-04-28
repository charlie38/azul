package azul.view;

import java.awt.Graphics;

import javax.swing.JComponent;

public class PlayerBoardGraph extends JComponent {
	ImageAzul flower,flowerB,claw,clawB,mushroom,mushroomB,crystal,crystalB,eye,eyeB,board,greenCase,brownCase;
    ImageAzul list[]=new ImageAzul[5];

    //Setter
    public PlayerBoardGraph() {
    	
    	//Initialization of all images
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
    
    int Redimensionne(int x, int p) {
        float A;
        A=(((float) x)/1000)*p;
        return (int) A;
    }

    public void paint(Graphics g){
        int h=getHeight();
        int w=getWidth();
        double px,py;
        int x,y,sx,sy;
        sx=Redimensionne(70,w);
        sy=Redimensionne(70,h);

        g.drawImage(board.image(),0,0,w,h,null);
        for(int i =0 ; i<5; i++){
            for(int j =0 ; j<5; j++){
                g.drawImage(greenCase.image(),Redimensionne(i*90+430,w),Redimensionne(j*100+330,h),sx,sy,null);
                g.drawImage(list[(5-i+j)%5].image(),Redimensionne(i*90+430,w),Redimensionne(j*100+330,h),sx,sy,null);
            }
        }
        for(int i =0 ; i<5; i++){
            for(int j = 4 ;  j>3-i; j--){
                g.drawImage(brownCase.image(),Redimensionne(j*80-10,w),Redimensionne(i*100+330,h),sx,sy,null);
            }
        }
    }
}
