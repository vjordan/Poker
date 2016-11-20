
public enum CombinaisonValeur {
	PLUS_HAUTE_CARTE (1),
	PAIRE (2),
	DOUBLE_PAIRE (3),
	BRELAN (4),
	SUITE (5),
	COULEUR (6),
	FULL (7),
	CARRE (8),
	QUINTE_FLUSH (9),
	QUINTE_FLUSH_ROYALE (10);
	
	private int index;
	
	private CombinaisonValeur (int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
