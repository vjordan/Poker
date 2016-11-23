
public enum CombinaisonValeur {
	PLUS_HAUTE_CARTE (1, "Plus haute carte"),
	PAIRE (2, "Paire"),
	DOUBLE_PAIRE (3, "Double paire"),
	BRELAN (4, "Brelan"),
	SUITE (5, "Suite"),
	COULEUR (6, "Couleur"),
	FULL (7, "Full"),
	CARRE (8, "Carre"),
	QUINTE_FLUSH (9, "Quinte flush"),
	QUINTE_FLUSH_ROYALE (10, "Quinte flush royale");
	
	private int index;
	private String libelle;
	
	private CombinaisonValeur (int index, String libelle) {
		this.index = index;
		this.libelle = libelle;
	}

	public int getIndex() {
		return index;
	}

	public String getLibelle() {
		return libelle;
	}
}
