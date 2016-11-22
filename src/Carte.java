
public class Carte implements Comparable<Carte>{
	
	private CarteCouleur couleur;
	private CarteRang rang;
	
	public Carte (CarteCouleur couleur, CarteRang rang) {
		this.couleur = couleur;
		this.rang = rang;
	}

	public String toString() {
		return rang + "/" + couleur;
	}

	public int compareTo (Carte carte) {
		int val = this.getRang().getIndex() - carte.getRang().getIndex();
		if (val == 0)
			return this.getCouleur().getIndex() - carte.getCouleur().getIndex();
		return val;
	}
	
	public CarteCouleur getCouleur() {
		return couleur;
	}

	public CarteRang getRang() {
		return rang;
	}
}
