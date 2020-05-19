package azul.controller.ia.configuration;
import azul.model.Game; 
import azul.model.player.PlayerBoard;
import azul.model.tile.Tile;
import azul.model.move.*;
import java.util.ArrayList;
import java.util.Random;

import azul.model.Couple;
public class Configuration {
	Game mGame;
	//Coordonnees de la tuile qu'on vise a remplir
	private int xcible; 
	private int ycible;

	
	
	public Configuration(Game G, int xc, int yc) {
		mGame=G;
		xcible=xc;
		ycible=yc;

	}
	
	public int Evaluation() {
		return mGame.getPlayer(mGame.getPlayerIndex()).getBoard().pointsPotentiels(new Couple(ycible,xcible));
	}
	
	public ArrayList<Configuration> ConfigurationsFilles(){
		//To do
		PlayerBoard board=mGame.getPlayer(mGame.getPlayerIndex()).getBoard();
		ArrayList<Configuration> ConfigFilles=new ArrayList<Configuration>();
		for(int i=1; i<6;i++) {
			for(int j=1; j<6;j++) {
				if(!board.isWallCaseNotEmpty(i,j) && (board.getPatternLine(i)[0]==Tile.EMPTY || board.getPatternLine(i)[0]==PlayerBoard.getWallTile(i, j)) && !(board.isPatterLineFull(i))) {
					ConfigFilles.add(new Configuration(mGame, j, i));
				}
			}
		}
		return ConfigFilles;
	}
	
	public Move meilleureAction() {
		Move meilleurMalus;
		
		
		return null;
	}
	
	
	public Configuration choixmax() {
        Random r=new Random();
        boolean pass=r.nextBoolean();
        ArrayList<Configuration> Filles=ConfigurationsFilles();
        if(Filles==null) return null;
        Configuration resultat=Filles.get(0);
        
        for(Configuration C:ConfigurationsFilles()) {
            if(C.Evaluation()>resultat.Evaluation()) {
                resultat=C;
            }
            if(C.Evaluation()==resultat.Evaluation() && pass){
                resultat=C;
            }
            pass=r.nextBoolean();
        }
        return resultat;
        
    }
	
	public Configuration choixmin() {
		
		ArrayList<Configuration> Filles=ConfigurationsFilles();
		Configuration resultat=Filles.get(0);
		
		for(Configuration C:ConfigurationsFilles()) {
			if(C.Evaluation()<resultat.Evaluation()) {
				resultat=C;
			}
		}
		return resultat;
		
	}
	
	public int getXCible()
	{
		return xcible;
	}
	
	public int getYCible()
	{
		return ycible;
	}
	
}

