package main;

import java.util.ArrayList;

public class Table {
	
	/**
	 * pot de la table
	 */
	private int pot;
	/**
	 * cartes communes de la table, qui sont au nombre de 5
	 */
	private Carte[] cartesTable = new Carte[5];
	
	/**
	 * Une nouvelle table a un pot vide et des cartes communes pas encore définies
	 */
	public Table() {
		pot = 0;
		for (int i=0; i<5; i++)
			cartesTable[i] = null;
	}
	
	/**
	 * Tire les trois premières cartes de la table pendant le flop
	 * @param paquetCartes : paquet de cartes de la table
	 */
	public void flop (ArrayList<Carte> paquetCartes) {
		for (int i=0; i<3; i++)
			cartesTable[i] = paquetCartes.remove(0); // on retire la première carte du paquet
		System.out.println("Flop :");
		// on affiche les 5 cartes de la table ( à cette étape, les 2 dernières cartes ne sont toujours pas définies )
		affichageCartesTable();
		System.out.println();
	}
	
	/**
	 * Tire la quatrième carte de la table ( la "turn" )
	 * @param paquetCartes : paquet de cartes de la table
	 */
	public void turn (ArrayList<Carte> paquetCartes) {
		cartesTable[3] = paquetCartes.remove(0);
		System.out.println("Turn :");
		// on affiche les 5 cartes de la table ( à cette étape, la dernière carte n'est toujours pas définie )
		affichageCartesTable();
		System.out.println();
	}
	
	/**
	 * Tire la cinquième et dernière carte de la table ( la "river" )
	 * @param paquetCartes : paquet de cartes de la table
	 */
	public void river(ArrayList<Carte> paquetCartes) {
		cartesTable[4] = paquetCartes.remove(0);
		System.out.println("River :");
		// on affiche les 5 cartes de la table ( cette fois, elles sont toutes présentes )
		affichageCartesTable();
		System.out.println();
	}
	
	/**
	 * Affiche les cinq cartes communes présentes sur la table
	 */
	public void affichageCartesTable() {
		for (int i=0; i<5; i++) {
			// si la carte n'a pas encore été tirée, on l'indique par des tirets, sinon on affiche sa valeur
			if (cartesTable[i] == null)
				System.out.println("   Carte " + (i+1) + " : --------------");
			else
				System.out.println("   Carte " + (i+1) + " : " + cartesTable[i].toString());
		}
	}
	
	/**
	 * Indique le montant du pot de la table
	 */
	public String toString() {
		return "Pot = " + pot;
	}

	/**
	 * getter pour "pot"
	 * @return la valeur du pot de la table
	 */
	public int getPot() {
		return pot;
	}

	/**
	 * setter pour "pot"
	 * @param pot : montant du pot de la table
	 */
	public void setPot(int pot) {
		this.pot = pot;
	}

	/**
	 * getter pour "cartesTable"
	 * @return les cinq cartes communes de la table
	 */
	public Carte[] getCartesTable() {
		return cartesTable;
	}
}
