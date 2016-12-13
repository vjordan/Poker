package main;

import java.util.ArrayList;

public class Combinaison implements Comparable<Combinaison>{

	/**
	 * valeur de la combinaison définie par l'énumération CombinaisonValeur
	 */
	private CombinaisonValeur valeur;
	/**
	 * cartes constituant la combinaison de 5 cartes
	 */
	private ArrayList<Carte> cartes = new ArrayList<Carte>();
	/**
	 * cartes qui permettent de départager 2 combinaisons de valeur identique
	 */
	private ArrayList<Carte> cartesDepartager = new ArrayList<Carte>();
	
	/**
	 * Une nouvelle combinaison est constituée de 5 cartes
	 * @param cartes : liste des cartes pour la combinaison
	 */
	public Combinaison (ArrayList<Carte> cartes) {
		this.cartes = cartes;
	}
	
	/**
	 * Indique la composition de la combinaison ( on affiche les 5 cartes présentes )
	 */
	public String toString() {
		return "      Carte 1 : " + cartes.get(0).toString() + " | Carte 2 : " + cartes.get(1).toString() + " | Carte 3 : " + cartes.get(2).toString() + " | Carte 4 : " + cartes.get(3).toString() + " | Carte 5 : " + cartes.get(4).toString(); 
	}
	
	/**
	 * Compare 2 combinaisons entre elles
	 */
	public int compareTo (Combinaison combinaison) {
		// on commence par comparer les valeurs des 2 combinaisons
		int val = this.getValeur().getIndex() - combinaison.getValeur().getIndex();
		if (val == 0) {
			for (int i=0; i<this.cartesDepartager.size(); i++) {
				// si les 2 combinaisons sont de même valeur, on compare un à un le rang des cartes qui peuvent départager les combinaisons
				val = this.cartesDepartager.get(i).getRang().getIndex() - combinaison.cartesDepartager.get(i).getRang().getIndex();
				if (val != 0) {
					return val;
				}
			}
		}
		// il est possible que deux combinaisons soient exactement identiques, par leur valeur, mais aussi par comparaison de leurs "cartes pour départager"
		// dans ce cas, on compareTo retourne 0
		return val;
	}

	/**
	 * getter pour "valeur"
	 * @return la valeur de la combinaison
	 */
	public CombinaisonValeur getValeur() {
		return valeur;
	}

	/**
	 * setter pour "valeur"
	 * @param valeur : valeur de la combinaison
	 */
	public void setValeur(CombinaisonValeur valeur) {
		this.valeur = valeur;
	}
	
	/**
	 * getter pour "cartes"
	 * @return les cinq cartes constituant la combinaison
	 */
	public ArrayList<Carte> getCartes() {
		return cartes;
	}

	/**
	 * getter pour "cartesDepartager"
	 * @param cartesDepartager : liste des cartes qui permettent de départager deux combinaisons de même valeur
	 */
	public void setCartesDepartager(ArrayList<Carte> cartesDepartager) {
		this.cartesDepartager = cartesDepartager;
	}
}
