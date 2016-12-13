package main;

import java.util.ArrayList;

public class Joueur {
	
	/**
	 * nom du joueur
	 */
	private String nom;
	/**
	 * montant du tapis du joueur
	 */
	private int compte;
	/**
	 * mise du joueur pendant un tour de mises ( c'est-à-dire préflop, flop, turn ou river )
	 */
	private int mise;
	/**
	 * mise totale jouée par le joueur pendant un tour de jeu complet ( un tour de jeu est composé des quatre tours de mises énoncés ci-dessus )
	 */
	private int miseTotale;
	/**
	 * permet de savoir si le joueur est le donneur de la partie actuel
	 */
	private boolean estDonneur;
	/**
	 * les 2 cartes privatives du joueur
	 */
	private Carte[] main = new Carte[2];
	
	/**
	 * Un nouveau joueur a un compte de 2000, et n'est pas donneur
	 * @param nom : nom qui définit un joueur
	 */
	public Joueur(String nom) {
		this.nom = nom;
		compte = 2000;
		estDonneur = false;
	}
	
	/**
	 * Distribution des cartes pour le joueur
	 * @param paquetCartes : paquet de cartes de la table
	 */
	public void main(ArrayList<Carte> paquetCartes) {
		for (int i=0; i<2 ;i++)
			main[i] = paquetCartes.remove(0); // on retire la première carte du paquet
	}

	/**
	 * Affiche le nom du joueur et ses deux cartes qui constituent sa main
	 */
	public void affichageMain() {
		System.out.println(nom + " --> " + "Carte 1 : " + main[0].toString() + " | Carte 2 : " + main[1].toString());
	}
	
	/**
	 * Indique le nom du joueur et le montant de son tapis
	 */
	public String toString() {
		return nom + " : Tapis = " + compte;
	}

	/**
	 * getter pour "nom"
	 * @return le nom du joueur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * getter pour "compte"
	 * @return le tapis du joueur
	 */
	public int getCompte() {
		return compte;
	}

	/**
	 * setter pour "compte"
	 * @param compte : tapis du joueur
	 */
	public void setCompte(int compte) {
		this.compte = compte;
	}

	/**
	 * getter pour "mise"
	 * @return la mise du joueur pendant un tour de mises
	 */
	public int getMise() {
		return mise;
	}
	
	/**
	 * setter pour "mise"
	 * @param mise : mise du joueur pendant un tour de mises
	 */
	public void setMise(int mise) {
		this.mise = mise;
	}

	/**
	 * getter pour "miseTotale"
	 * @return la mise du joueur pendant un tour entier
	 */
	public int getMiseTotale() {
		return miseTotale;
	}

	/**
	 * setter pour "miseTotale"
	 * @param miseTotale : mise du joueur pendant un tour entier
	 */
	public void setMiseTotale(int miseTotale) {
		this.miseTotale = miseTotale;
	}

	/**
	 * getter pour "estDonneur"
	 * @return un booléen pour savoir si le joueur est le donneur de la partie ou pas
	 */
	public boolean isEstDonneur() {
		return estDonneur;
	}

	/**
	 * setter pour "estDonneur"
	 * @param estDonneur : valeur booléenne qui indique si le joueur est donneur de la partie ou pas
	 */
	public void setEstDonneur(boolean estDonneur) {
		this.estDonneur = estDonneur;
	}

	/**
	 * getter pour "main"
	 * @return les deux cartes privées du joueur
	 */
	public Carte[] getMain() {
		return main;
	}
}
