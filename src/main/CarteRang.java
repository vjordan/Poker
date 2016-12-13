package main;

public enum CarteRang {
	DEUX (2),
	TROIS (3),
	QUATRE (4),
	CINQ (5),
	SIX (6),
	SEPT (7),
	HUIT (8),
	NEUF (9),
	DIX (10),
	VALET (11),
	DAME (12),
	ROI (13),
	AS (14);
	
	/**
	 * valeur du rang de la carte
	 */
	private int index;
	
	/**
	 * constructeur de CarteRang
	 * @param index : valeur du rang
	 */
	private CarteRang (int index) {
		this.index = index;
	}

	/**
	 * getter pour "index"
	 * @return l'index du rang de la carte
	 */
	public int getIndex() {
		return index;
	}
}
