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

    public void paint(Graphics g){
        g.drawImage(board.image(),0,0,1000,1000,null);
        for(int i =0 ; i<5; i++){
            for(int j =0 ; j<5; j++){
                g.drawImage(greenCase.image(),i*90+430,j*100+330,85,85,null);
                g.drawImage(list[(5-i+j)%5].image(),i*90+430,j*100+330,85,85,null);
            }
        }
    }
}
