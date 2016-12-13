package main;

import java.util.ArrayList;
import java.util.Scanner;

public class Partie {
	
	/**
	 * nombre de joueurs de la partie
	 */
	private int nbJoueurs;
	/**
	 * liste des joueurs qui va évoluer au cours de la partie au fur et à mesure des éliminations
	 */
	private ArrayList<Joueur> listeJoueurs;
	/**
	 * liste des joueurs au début de la partie : cette liste reste inchangée pendant la partie
	 */
	private ArrayList<Joueur> listeJoueursBase;
	/**
	 * scanner pour lire les entrées clavier
	 */
	private Scanner sc = new Scanner(System.in);
	
	/**
	 * Déroulement d'une partie
	 */
	public Partie() {
		do {
			listeJoueurs = new ArrayList<Joueur>();
			listeJoueursBase = new ArrayList<Joueur>();
			
			Tour.setNbTours(0);
			demandeNbJoueurs();
			demandeNomsJoueurs();
			recapitulatifJoueurs();
			// tant qu'il reste plusieurs joueurs dans la partie, on rejoue un tour
			while (listeJoueurs.size() > 1) {
				new Tour(listeJoueurs, listeJoueursBase);
				recapitulatifJoueurs();
			}
			affichageVainqueur();
		} while (rejouer() == true);
	}
	
	/**
	 * Demande combien de joueurs seront présents dans la partie initialement
	 */
	public void demandeNbJoueurs () {
		int nbJoueurs;
		
		do {
			System.out.println("Combien de joueurs sur la table ?");
			nbJoueurs = sc.nextInt();
			sc.nextLine();
			this.nbJoueurs = nbJoueurs;
		} while (nbJoueurs < 2 || nbJoueurs > 10); // le nombre de joueurs doit être compris entre 2 et 10
		System.out.println();
	}
	
	/**
	 * Demande le nom de chaque joueur
	 */
	public void demandeNomsJoueurs() {
		String nomJoueur;
		Joueur joueur;
		boolean nomIdentique = false;
		
		for (int i=0; i<nbJoueurs; i++) {
			do {
				System.out.println("Quel est votre nom, joueur " + (i+1) + " ?");
				nomJoueur = sc.nextLine();
				// on vérifie que le nom entré n'a pas déjà été donné pour un joueur antérieur
				nomIdentique = verifierNomJoueurs(nomJoueur, i);
			}
			while (nomJoueur.equals("") || nomIdentique); // si nom vide ou déjà présent, on redemande le nom
			joueur = new Joueur(nomJoueur);
			listeJoueurs.add(joueur);
			listeJoueursBase.add(joueur);
		}
		listeJoueursBase.get(0).setEstDonneur(true);
		System.out.println();
	}
	
	/**
	 * Permet de vérifier si le nom d'un joueur a déjà été donné pour un autre
	 * @param nomJoueur : nom du joueur vérifié
	 * @param indexListe : nombre de joueur ajouté à la partie à un moment donné ( un joueur n'est ajouté à la partie qu'après confirmation de son nom )
	 * @return true si le nom a déjà été donné
	 */
	public boolean verifierNomJoueurs(String nomJoueur, int indexListe) {
		for (int i=0; i<indexListe; i++) {
			if (nomJoueur.equals(listeJoueurs.get(i).getNom())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Recapitule tous les joueurs de la partie en retournant leur nom et le montant de leur tapis
	 */
	public void recapitulatifJoueurs() {
		for (int i=0; i<listeJoueurs.size(); i++)
			System.out.println(listeJoueurs.get(i).toString());
		System.out.println();
	}
	
	/**
	 * Affiche le vainqueur en fin de partie
	 */
	public void affichageVainqueur() {
		System.out.println("Vainqueur de la partie : " + listeJoueurs.get(0).getNom());
		System.out.println();
	}
	
	/**
	 * Demande pour rejouer une partie
	 * @return true si l'utilisateur souhaite relancer une partie
	 */
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
