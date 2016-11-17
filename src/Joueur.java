import java.util.ArrayList;

public class Joueur {
	
	private String nom;
	private int compte, mise, miseTour;
	private boolean estDonneur;
	private Carte[] main = new Carte[2];
	
	public Joueur(String nom) {
		this.nom = nom;
		compte = 2000;
		this.estDonneur = false;
	}
	
	public void main(ArrayList<Carte> paquetCartes) {
		for (int i=0; i<2 ;i++)
			main[i] = paquetCartes.remove(0);
		affichageMain();
	}

	public void affichageMain() {
		System.out.println(nom + " --> " + "Carte 1 : " + main[0].toString() + " | Carte 2 : " + main[1].toString());
	}
	
	public String toString() {
		return nom + " : Tapis = " + compte;
	}

	public String getNom() {
		return nom;
	}

	public int getCompte() {
		return compte;
	}

	public void setCompte(int compte) {
		this.compte = compte;
	}

	public int getMise() {
		return mise;
	}

	public int getMiseTour() {
		return miseTour;
	}

	public void setMiseTour(int miseTour) {
		this.miseTour = miseTour;
	}

	public void setMise(int mise) {
		this.mise = mise;
	}

	public boolean isEstDonneur() {
		return estDonneur;
	}

	public void setEstDonneur(boolean estDonneur) {
		this.estDonneur = estDonneur;
	}

	public Carte[] getMain() {
		return main;
	}
}
