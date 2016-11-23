
public enum CarteCouleur {
	TREFLE (1),
	PIQUE (2),
	CARREAU (3),
	COEUR (4);
	
	private int index;
	
	private CarteCouleur (int index) {
		this.index = index;
	}

	public int getIndex() {
		return index; 
	}
}