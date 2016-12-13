package main;

public class Carte implements Comparable<Carte>{
	
	/**
	 * couleur de la carte définie par l'énumération CarteCouleur
	 */
	private CarteCouleur couleur;
	/**
	 * rang de la carte définie par l'énumération CarteRang
	 */
	private CarteRang rang;
	
	/**
	 * une carte est définie par sa couleur et son rang
	 * @param couleur : couleur de la carte
	 * @param rang : rang de la carte
	 */
	public Carte (CarteCouleur couleur, CarteRang rang) {
		this.couleur = couleur;
		this.rang = rang;
	}

	/**
	 * Affichage de la carte, avec l'association rang/couleur
	 */
	public String toString() {
		return rang + "/" + couleur;
	}

	/**
	 * Compare 2 cartes entre elles
	 */
	public int compareTo (Carte carte) {
		// on commence par comparer les rangs des 2 cartes
		int val = this.getRang().getIndex() - carte.getRang().getIndex();
		if (val == 0)
			// si les rangs sont identiques, on compare les couleurs ( cette comparaison n'est pas vraiment importante, mais permet de respecter un certain ordre )
			return this.getCouleur().getIndex() - carte.getCouleur().getIndex();
		return val;
	}
	
	/**
	 * getter pour "couleur"
	 * @return la couleur de la carte
	 */
	public CarteCouleur getCouleur() {
		return couleur;
	}

	/**
	 * getter pour "rang"
	 * @return le rang de la carte
	 */
	public CarteRang getRang() {
		return rang;
	}
}
