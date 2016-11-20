
public class Carte {
	
	private CarteCouleur couleur;
	private CarteValeur valeur;
	
	public Carte (CarteCouleur couleur, CarteValeur valeur) {
		this.couleur = couleur;
		this.valeur = valeur;
	}

	public String toString() {
		return valeur + "/" + couleur;
	}

	public CarteCouleur getCouleur() {
		return couleur;
	}

	public CarteValeur getValeur() {
		return valeur;
	}
}
