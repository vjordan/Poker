import java.util.ArrayList;

public class Combinaison implements Comparable<Combinaison>{

	private ArrayList<Carte> cartes = new ArrayList<Carte>();
	private CombinaisonValeur valeur;
	
	public Combinaison (Carte carte1, Carte carte2, Carte carte3, Carte carte4, Carte carte5) {
		cartes.add(carte1);
		cartes.add(carte2);
		cartes.add(carte3);
		cartes.add(carte4);
		cartes.add(carte5);
	}
	
	public int compareTo (Combinaison combinaison) {
		return this.getValeur().getIndex() - combinaison.getValeur().getIndex();
	}
	
	public String toString() {
		return "   Carte 1 : " + cartes.get(0).toString() + " | Carte 2 : " + cartes.get(1).toString() + " | Carte 3 : " + cartes.get(2).toString() + " | Carte 4 : " + cartes.get(3).toString() + " | Carte 5 : " + cartes.get(4).toString(); 
	}

	public ArrayList<Carte> getCartes() {
		return cartes;
	}

	public CombinaisonValeur getValeur() {
		return valeur;
	}

	public void setValeur(CombinaisonValeur valeur) {
		this.valeur = valeur;
	}
}
