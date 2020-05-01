package azul.view;

import java.awt.Graphics;

import javax.swing.JComponent;

import azul.model.tiles.Tile;
import azul.model.tiles.TilesFactory;

public class FactoryGraph extends JComponent {
	ImageLoader iml;
	TilesFactory tf;
	int h;
	int w;
	
	public FactoryGraph(ImageLoader il)
	{
		iml = il;
	}
	
	int resizeFactory(int x, int p,boolean b)
    {
        if(b){
            float a;
            a=(((float) x)/1300)*p;
            return (int) a;}
        else{
            float a;
            a=(((float) x)/1730)*p;
            return (int) a;
        }
    }
	public void paint(Graphics g)
    {
        h = getHeight();
        w = getWidth();
        int sx=resizeFactory(310,w,false);
        int sy=resizeFactory(310,h,true);
        tf = new TilesFactory(); //A enlever
        g.drawImage(iml.bowl.image(), 0,0,w,h,null);

        g.drawImage(iml.bowlcase.image(), resizeFactory(765,getWidth(),false), resizeFactory(170,getHeight(),true), sx, sy, null);
        g.drawImage(iml.paintIngredient(tf.getTile(0)).image(),resizeFactory(765,getWidth(),false), resizeFactory(170,getHeight(),true), sx,sy, null);
        g.drawImage(iml.bowlcase.image(), resizeFactory(765,getWidth(),false), resizeFactory(840,getHeight(),true), sx, sy, null);
        g.drawImage(iml.paintIngredient(tf.getTile(1)).image(),resizeFactory(765,getWidth(),false), resizeFactory(840,getHeight(),true), sx,sy, null);
        g.drawImage(iml.bowlcase.image(), resizeFactory(200,getWidth(),false), resizeFactory(500,getHeight(),true), sx, sy, null);
        g.drawImage(iml.paintIngredient(tf.getTile(2)).image(),resizeFactory(200,getWidth(),false), resizeFactory(500,getHeight(),true), sx,sy, null);
        g.drawImage(iml.bowlcase.image(), resizeFactory(1300,getWidth(),false), resizeFactory(500,getHeight(),true), sx,sy, null);
        g.drawImage(iml.paintIngredient(tf.getTile(3)).image(),resizeFactory(1300,getWidth(),false), resizeFactory(500,getHeight(),true), sx,sy, null);


    }
}
