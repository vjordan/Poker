import java.util.ArrayList;

public class Table {
	
	private int pot;
	private Carte[] cartesTable = new Carte[5];
	
	public Table() {
		pot = 0;
		for (int i=0; i<5; i++)
			cartesTable[i] = null;
	}
	
	public void flop (ArrayList<Carte> paquetCartes) {
		for (int i=0; i<3; i++)
			cartesTable[i] = paquetCartes.remove(0);
		System.out.println("Flop :");
		affichageCartesTable();
		System.out.println();
	}
	
	public void turn (ArrayList<Carte> paquetCartes) {
		cartesTable[3] = paquetCartes.remove(0);
		System.out.println("Turn :");
		affichageCartesTable();
		System.out.println();
	}
	
	public void river(ArrayList<Carte> paquetCartes) {
		cartesTable[4] = paquetCartes.remove(0);
		System.out.println("River :");
		affichageCartesTable();
		System.out.println();
	}
	
	public void affichageCartesTable() {
		for (int i=0; i<5; i++) {
			if (cartesTable[i] == null)
				System.out.println("   Carte " + (i+1) + " : --------------");
			else
				System.out.println("   Carte " + (i+1) + " : " + cartesTable[i].toString());
		}
	}
	
	public String toString() {
		return "Pot = " + pot;
	}

	public int getPot() {
		return pot;
	}

	public void setPot(int pot) {
		this.pot = pot;
	}
}
