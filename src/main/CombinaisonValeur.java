package main;

public enum CombinaisonValeur {
	PLUS_HAUTE_CARTE (1, "Plus haute carte"),
	PAIRE (2, "Paire"),
	DOUBLE_PAIRE (3, "Double paire"),
	BRELAN (4, "Brelan"),
	SUITE (5, "Suite"),
	COULEUR (6, "Couleur"),
	FULL (7, "Full"),
	CARRE (8, "Carré"),
	QUINTE_FLUSH (9, "Quinte flush"),
	QUINTE_FLUSH_ROYALE (10, "Quinte flush royale");
	
	/**
	 * index référençant la valeur d'une combinaison
	 */
	private int index;
	/**
	 * libelle d'une valeur ( utilisé pour l'affichage )
	 */
	private String libelle;
	
	/**
	 * constructeur de CombinaisonValeur
	 * @param index : index de la valeur de la combinaison
	 * @param libelle : libelle de la valeur de la combinaison
	 */
	private CombinaisonValeur (int index, String libelle) {
		this.index = index;
		this.libelle = libelle;
	}

	/**
	 * getter pour "index"
	 * @return l'index de la valeur de la combinaison
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * getter pour "libelle"
	 * @return le libellé de la valeur de la combinaison
	 */
	public String getLibelle() {
		return libelle;
	}
}
