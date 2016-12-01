import java.util.ArrayList;

public class Combinaison implements Comparable<Combinaison>{

	/**
	 * valeur de la combinaison d�finie par l'�num�ration CombinaisonValeur
	 */
	private CombinaisonValeur valeur;
	/**
	 * cartes constituant la combinaison de 5 cartes
	 */
	private ArrayList<Carte> cartes = new ArrayList<Carte>();
	/**
	 * cartes qui permettent de d�partager 2 combinaisons de valeur identique
	 */
	private ArrayList<Carte> cartesDepartager = new ArrayList<Carte>();
	
	/**
	 * Une nouvelle combinaison est constitu�e de 5 cartes
	 * @param carte1 : premi�re carte de la combinaison
	 * @param carte2 : seconde carte de la combinaison
	 * @param carte3 : troisi�me carte de la combinaison
	 * @param carte4 : quatri�me carte de la combinaison
	 * @param carte5 : cinqui�me carte de la combinaison
	 */
	public Combinaison (Carte carte1, Carte carte2, Carte carte3, Carte carte4, Carte carte5) {
		cartes.add(carte1);
		cartes.add(carte2);
		cartes.add(carte3);
		cartes.add(carte4);
		cartes.add(carte5);
	}
	
	/**
	 * Indique la composition de la combinaison ( on affiche les 5 cartes pr�sentes )
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
				// si les 2 combinaisons sont de m�me valeur, on compare un � un le rang des cartes qui peuvent d�partager les combinaisons
				val = this.cartesDepartager.get(i).getRang().getIndex() - combinaison.cartesDepartager.get(i).getRang().getIndex();
				if (val != 0) {
					return val;
				}
			}
		}
		// il est possible que deux combinaisons soient exactement identiques, par leur valeur, mais aussi par comparaison de leurs "cartes pour d�partager"
		// dans ce cas, on compareTo retourne 0
		return val;
	}

	public CombinaisonValeur getValeur() {
		return valeur;
	}

	public void setValeur(CombinaisonValeur valeur) {
		this.valeur = valeur;
	}
	
	public ArrayList<Carte> getCartes() {
		return cartes;
	}

	public void setCartesDepartager(ArrayList<Carte> cartesDepartager) {
		this.cartesDepartager = cartesDepartager;
	}
}
