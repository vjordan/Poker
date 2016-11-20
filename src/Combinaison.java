
public class Combinaison {

	private Carte[] cartes = new Carte[5];
	
	public Combinaison (Carte carte1, Carte carte2, Carte carte3, Carte carte4, Carte carte5) {
		cartes[0] = carte1;
		cartes[1] = carte2;
		cartes[2] = carte3;
		cartes[3] = carte4;
		cartes[4] = carte5;
	}

	public Carte[] getCartes() {
		return cartes;
	}
}
