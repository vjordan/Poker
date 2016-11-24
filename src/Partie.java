import java.util.ArrayList;
import java.util.Scanner;

public class Partie {
	
	private int nbJoueurs;
	private ArrayList<Joueur> listeJoueurs;
	private ArrayList<Joueur> listeJoueursBase;
	private Scanner sc = new Scanner(System.in);
	
	public Partie() {
		do {
			listeJoueurs = new ArrayList<Joueur>();
			listeJoueursBase = new ArrayList<Joueur>();
			
			Tour.setNbTours(0);
			demandeNbJoueurs();
			demandeNomsJoueurs();
			recapitulatifJoueurs();
			while (listeJoueurs.size() > 1) {
				new Tour(listeJoueurs, listeJoueursBase);
				recapitulatifJoueurs();
			}
			affichageVainqueur();
		} while (rejouer() == true);
	}
	
	public void demandeNbJoueurs () {
		int nbJoueurs;
		
		do {
			System.out.println("Combien de joueurs sur la table ?");
			nbJoueurs = sc.nextInt();
			sc.nextLine();
			this.nbJoueurs = nbJoueurs;
		} while (nbJoueurs < 2 || nbJoueurs > 10);
		System.out.println();
	}
	
	public void demandeNomsJoueurs() {
		String nomJoueur;
		Joueur joueur;
		boolean nomIdentique = false;
		
		for (int i=0; i<nbJoueurs; i++) {
			do {
				System.out.println("Quel est votre nom, joueur " + (i+1) + " ?");
				nomJoueur = sc.nextLine();
				nomIdentique = verifierNomJoueurs(nomJoueur, i);
			}
			while (nomJoueur.equals("") || nomIdentique == true);
			joueur = new Joueur(nomJoueur);
			listeJoueurs.add(joueur);
			listeJoueursBase.add(joueur);
		}
		listeJoueursBase.get(0).setEstDonneur(true);
		System.out.println();
	}
		
	public boolean verifierNomJoueurs(String nomJoueur, int indexListe) {
		boolean nomIdentique = false;
		
		for (int i=0; i<indexListe; i++) {
			if (nomIdentique == false) {
				if (nomJoueur.equals(listeJoueurs.get(i).getNom()))
					nomIdentique = true;
			}
		}
		
		return nomIdentique;
	}
	
	public void recapitulatifJoueurs() {
		for (int i=0; i<listeJoueurs.size(); i++)
			System.out.println(listeJoueurs.get(i).toString());
		System.out.println();
	}
	
	public void affichageVainqueur() {
		System.out.println("Vainqueur de la partie : " + listeJoueurs.get(0).getNom());
		System.out.println();
	}
	
	public boolean rejouer() {
		String repRejouer;
		
		do {			
			System.out.println("Voulez-vous rejouer ? (oui/non)");
			repRejouer = sc.nextLine();
		}
		while ((!(repRejouer.equals("oui")))&&(!(repRejouer.equals("non"))));
		System.out.println();
		if (repRejouer.equals("oui"))
			return true;
		else
		{
			System.out.println("Fin de la partie.");
			return false;
		}
	}
}
