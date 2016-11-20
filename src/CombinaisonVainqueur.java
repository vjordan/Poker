import java.util.ArrayList;

public class CombinaisonVainqueur {

	private Joueur vainqueur;
	private Combinaison[] combinaisonsJoueur;
	
	public CombinaisonVainqueur (ArrayList<Joueur> listeJoueurs, Table table) {
		for (Joueur joueur : listeJoueurs) {
			constitutionCombinaisons(joueur, table);
		}
	}
	
	public void constitutionCombinaisons(Joueur joueur, Table table) {
		combinaisonsJoueur = new Combinaison[21];
		combinaisonsJoueur[0] = new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[2]);
		combinaisonsJoueur[1] = new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[3]);
		combinaisonsJoueur[2] = new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[4]);
		combinaisonsJoueur[3] = new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[2],table.getCartesTable()[3]);
		combinaisonsJoueur[4] = new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[2],table.getCartesTable()[4]);
		combinaisonsJoueur[5] = new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[3],table.getCartesTable()[4]);
		combinaisonsJoueur[6] = new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[1],table.getCartesTable()[2],table.getCartesTable()[3]);
		combinaisonsJoueur[7] = new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[1],table.getCartesTable()[2],table.getCartesTable()[4]);
		combinaisonsJoueur[8] = new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[1],table.getCartesTable()[3],table.getCartesTable()[4]);
		combinaisonsJoueur[9] = new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[2],table.getCartesTable()[3],table.getCartesTable()[4]);
		combinaisonsJoueur[10] = new Combinaison(joueur.getMain()[0],table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[2],table.getCartesTable()[3]);
		combinaisonsJoueur[11] = new Combinaison(joueur.getMain()[0],table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[2],table.getCartesTable()[4]);
		combinaisonsJoueur[12] = new Combinaison(joueur.getMain()[0],table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[3],table.getCartesTable()[4]);
		combinaisonsJoueur[13] = new Combinaison(joueur.getMain()[0],table.getCartesTable()[0],table.getCartesTable()[2],table.getCartesTable()[3],table.getCartesTable()[4]);
		combinaisonsJoueur[14] = new Combinaison(joueur.getMain()[0],table.getCartesTable()[1],table.getCartesTable()[2],table.getCartesTable()[3],table.getCartesTable()[4]);
		combinaisonsJoueur[15] = new Combinaison(joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[2],table.getCartesTable()[3]);
		combinaisonsJoueur[16] = new Combinaison(joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[2],table.getCartesTable()[4]);
		combinaisonsJoueur[17] = new Combinaison(joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[3],table.getCartesTable()[4]);
		combinaisonsJoueur[18] = new Combinaison(joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[2],table.getCartesTable()[3],table.getCartesTable()[4]);
		combinaisonsJoueur[19] = new Combinaison(joueur.getMain()[1],table.getCartesTable()[1],table.getCartesTable()[2],table.getCartesTable()[3],table.getCartesTable()[4]);
		combinaisonsJoueur[20] = new Combinaison(table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[2],table.getCartesTable()[3],table.getCartesTable()[4]);		
	}

	public Joueur getVainqueur() {
		return vainqueur;
	}
}
