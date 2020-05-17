package azul.controller.ia.configuration;
import azul.model.Game; 
import azul.model.move.*;
import java.util.ArrayList;
import azul.model.Couple;
public class Configuration {
	Game mGame;
	//Coordonnees de la tuile qu'on vise a remplir
	int xcible; 
	int ycible;
	//A quel joueur correspond l'IA
	int IAPlayer;
	
	
	public Configuration(Game G, int xc, int yc, int player) {
		mGame=G;
		xcible=xc;
		ycible=yc;
		IAPlayer=player;
	}
	
	public int Evaluation() {
		return mGame.getPlayer(mGame.getPlayerIndex()).getBoard().adjacents(new Couple(ycible,xcible));
	}
	
	public ArrayList<Configuration> ConfigurationsFilles(){
		//To do
		return null;
	}
	
	public Configuration choixmax() {
		
		ArrayList<Configuration> Filles=ConfigurationsFilles();
		Configuration resultat=Filles.get(0);
		
		for(Configuration C:ConfigurationsFilles()) {
			if(C.Evaluation()>resultat.Evaluation()) {
				resultat=C;
			}
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
	
}
