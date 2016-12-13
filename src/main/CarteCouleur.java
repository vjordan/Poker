package main;

public enum CarteCouleur {
	TREFLE (1),
	PIQUE (2),
	CARREAU (3),
	COEUR (4);
	
	/**
	 * valeur de la couleur de la carte
	 */
	private int index;
	
	/**
	 * constructeur pour CarteCouleur
	 * @param index : valeur de la couleur
	 */
	private CarteCouleur (int index) {
		this.index = index;
	}

	/**
	 * getter pour "index"
	 * @return l'index de la couleur de la carte
	 */
	public int getIndex() {
		return index; 
	}
}