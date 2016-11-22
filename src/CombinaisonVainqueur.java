import java.util.ArrayList;
import java.util.Collections;

public class CombinaisonVainqueur {

	private Joueur vainqueur;
	private ArrayList<Combinaison> combinaisonsJoueur;
	private ArrayList<Combinaison> listeMeilleureCombinaisonJoueurs;
	
	public CombinaisonVainqueur (ArrayList<Joueur> listeJoueurs, Table table) {
		listeMeilleureCombinaisonJoueurs = new ArrayList<Combinaison>();
		for (Joueur joueur : listeJoueurs) {
			constitutionCombinaisons(joueur, table);
			evaluationCombinaisons();
			designationMeilleureCombinaison();
		}
		designationVainqueur(listeJoueurs);
	}
	
	public void constitutionCombinaisons(Joueur joueur, Table table) {
		combinaisonsJoueur = new ArrayList<Combinaison>();
		
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[2]));
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[3]));
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[4]));
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[2],table.getCartesTable()[3]));
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[2],table.getCartesTable()[4]));
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[3],table.getCartesTable()[4]));
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[1],table.getCartesTable()[2],table.getCartesTable()[3]));
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[1],table.getCartesTable()[2],table.getCartesTable()[4]));
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[1],table.getCartesTable()[3],table.getCartesTable()[4]));
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[0],joueur.getMain()[1],table.getCartesTable()[2],table.getCartesTable()[3],table.getCartesTable()[4]));
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[0],table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[2],table.getCartesTable()[3]));
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[0],table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[2],table.getCartesTable()[4]));
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[0],table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[3],table.getCartesTable()[4]));
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[0],table.getCartesTable()[0],table.getCartesTable()[2],table.getCartesTable()[3],table.getCartesTable()[4]));
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[0],table.getCartesTable()[1],table.getCartesTable()[2],table.getCartesTable()[3],table.getCartesTable()[4]));
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[2],table.getCartesTable()[3]));
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[2],table.getCartesTable()[4]));
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[3],table.getCartesTable()[4]));
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[1],table.getCartesTable()[0],table.getCartesTable()[2],table.getCartesTable()[3],table.getCartesTable()[4]));
		combinaisonsJoueur.add(new Combinaison(joueur.getMain()[1],table.getCartesTable()[1],table.getCartesTable()[2],table.getCartesTable()[3],table.getCartesTable()[4]));
		combinaisonsJoueur.add(new Combinaison(table.getCartesTable()[0],table.getCartesTable()[1],table.getCartesTable()[2],table.getCartesTable()[3],table.getCartesTable()[4]));		
	}
	
	public void evaluationCombinaisons() {	
		for (Combinaison combinaison : combinaisonsJoueur)
			evaluerCombinaison(combinaison);
	}
	
	public void evaluerCombinaison(Combinaison combinaison) {
		boolean test;
		
		Collections.sort(combinaison.getCartes());
		Collections.reverse(combinaison.getCartes());
		
		test = testerQuinteFlushRoyale(combinaison);
		if (test == true) {
			combinaison.setValeur(CombinaisonValeur.QUINTE_FLUSH_ROYALE);
		}
		else {
			test = testerQuinteFlush(combinaison);
			if (test == true) {
				combinaison.setValeur(CombinaisonValeur.QUINTE_FLUSH);
			}
			else {
				test = testerCarre(combinaison);
				if (test == true) {
					combinaison.setValeur(CombinaisonValeur.CARRE);
				}
				else {
					test = testerFull(combinaison);
					if (test == true) {
						combinaison.setValeur(CombinaisonValeur.FULL);
					}
					else {
						test = testerCouleur(combinaison);
						if (test == true) {
							combinaison.setValeur(CombinaisonValeur.COULEUR);
						}
						else {
							test = testerSuite(combinaison);
							if (test == true) {
								combinaison.setValeur(CombinaisonValeur.SUITE);
							}
							else {
								test = testerBrelan(combinaison);
								if (test == true) {
									combinaison.setValeur(CombinaisonValeur.BRELAN);
								}
								else {
									test = testerDoublePaire(combinaison);
									if (test == true) {
										combinaison.setValeur(CombinaisonValeur.DOUBLE_PAIRE);
									}
									else {
										test = testerPaire(combinaison);
										if (test == true) {
											combinaison.setValeur(CombinaisonValeur.PAIRE);
										}
										else {
											combinaison.setValeur(CombinaisonValeur.PLUS_HAUTE_CARTE);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public boolean testerQuinteFlushRoyale (Combinaison combinaison) {
		ArrayList<Carte> cartes = combinaison.getCartes();
		for (int i=1; i<5; i++) {
			if (cartes.get(i).getCouleur().getIndex() != cartes.get(0).getCouleur().getIndex()) {
				return false;
			}
		}
		if (cartes.get(0).getRang().getIndex() != CarteRang.AS.getIndex()) {
			return false;
		}
		else {
			for (int i=1; i<5; i++) {
				if (cartes.get(i).getRang().getIndex() != CarteRang.AS.getIndex()-i) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean testerQuinteFlush (Combinaison combinaison) {
		ArrayList<Carte> cartes = combinaison.getCartes();
		for (int i=1; i<5; i++) {
			if (cartes.get(i).getCouleur().getIndex() != cartes.get(0).getCouleur().getIndex()) {
				return false;
			}
		}
		if (cartes.get(0).getRang().getIndex() == CarteRang.AS.getIndex()) {
			for (int i=1; i<5; i++) {
				if (cartes.get(i).getRang().getIndex() != CarteRang.AS.getIndex()-(8+i)) {
					return false;
				}
			}
		}
		else {
			for (int i=1; i<5; i++) {
				if (cartes.get(i).getRang().getIndex() != cartes.get(0).getRang().getIndex()-i) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean testerCarre (Combinaison combinaison) {
		ArrayList<Carte> cartes = combinaison.getCartes();
		int valRef;
		
		valRef = cartes.get(0).getRang().getIndex();
		if (cartes.get(1).getRang().getIndex() == valRef && cartes.get(2).getRang().getIndex() == valRef && cartes.get(3).getRang().getIndex() == valRef) {
			return true;
		}
		valRef = cartes.get(1).getRang().getIndex();
		if (cartes.get(2).getRang().getIndex() == valRef && cartes.get(3).getRang().getIndex() == valRef && cartes.get(4).getRang().getIndex() == valRef) {
			return true;
		}
		return false;
	}
	
	public boolean testerFull (Combinaison combinaison) {
		ArrayList<Carte> cartes = combinaison.getCartes();
		int valRef;
		
		valRef = cartes.get(0).getRang().getIndex();
		if (cartes.get(1).getRang().getIndex() == valRef && cartes.get(2).getRang().getIndex() == valRef) {
			if (cartes.get(3).getRang().getIndex() == cartes.get(4).getRang().getIndex()) {
				return true;
			}
		}
		valRef = cartes.get(2).getRang().getIndex();
		if (cartes.get(3).getRang().getIndex() == valRef && cartes.get(4).getRang().getIndex() == valRef) {
			if (cartes.get(0).getRang().getIndex() == cartes.get(1).getRang().getIndex()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean testerCouleur (Combinaison combinaison) {
		ArrayList<Carte> cartes = combinaison.getCartes();
		for (int i=1; i<5; i++) {
			if (cartes.get(i).getCouleur().getIndex() != cartes.get(0).getCouleur().getIndex()) {
				return false;
			}
		}
		return true;
	}
	
	public boolean testerSuite (Combinaison combinaison) {
		ArrayList<Carte> cartes = combinaison.getCartes();
		if (cartes.get(0).getRang().getIndex() == CarteRang.AS.getIndex() && cartes.get(1).getRang().getIndex() == CarteRang.CINQ.getIndex()) {
			for (int i=2; i<5; i++) {
				if (cartes.get(i).getRang().getIndex() != CarteRang.AS.getIndex()-(8+i)) {
					return false;
				}
			}
		}
		else {
			for (int i=1; i<5; i++) {
				if (cartes.get(i).getRang().getIndex() != cartes.get(0).getRang().getIndex()-i) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean testerBrelan (Combinaison combinaison) {
		ArrayList<Carte> cartes = combinaison.getCartes();
		int valRef;
		
		valRef = cartes.get(0).getRang().getIndex();
		if (cartes.get(1).getRang().getIndex() == valRef && cartes.get(2).getRang().getIndex() == valRef) {
			return true;
		}
		valRef = cartes.get(1).getRang().getIndex();
		if (cartes.get(2).getRang().getIndex() == valRef && cartes.get(3).getRang().getIndex() == valRef) {
			return true;
		}
		valRef = cartes.get(2).getRang().getIndex();
		if (cartes.get(3).getRang().getIndex() == valRef && cartes.get(4).getRang().getIndex() == valRef) {
			return true;
		}
		return false;
	}
	
	public boolean testerDoublePaire (Combinaison combinaison) {
		ArrayList<Carte> cartes = combinaison.getCartes();
		
		if (cartes.get(0).getRang().getIndex() == cartes.get(1).getRang().getIndex()) {
			if (cartes.get(2).getRang().getIndex() == cartes.get(3).getRang().getIndex()) {
				return true;
			}
			if (cartes.get(3).getRang().getIndex() == cartes.get(4).getRang().getIndex()) {
				return true;
			}
		}
		if (cartes.get(1).getRang().getIndex() == cartes.get(2).getRang().getIndex()) {
			if (cartes.get(3).getRang().getIndex() == cartes.get(4).getRang().getIndex()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean testerPaire (Combinaison combinaison) {
		ArrayList<Carte> cartes = combinaison.getCartes();
		
		if (cartes.get(0).getRang().getIndex() == cartes.get(1).getRang().getIndex()) {
			return true;
		}
		if (cartes.get(1).getRang().getIndex() == cartes.get(2).getRang().getIndex()) {
			return true;
		}
		if (cartes.get(2).getRang().getIndex() == cartes.get(3).getRang().getIndex()) {
			return true;
		}
		if (cartes.get(3).getRang().getIndex() == cartes.get(4).getRang().getIndex()) {
			return true;
		}
		return false;
	}

	public void designationMeilleureCombinaison() {
		Collections.sort(combinaisonsJoueur);
		listeMeilleureCombinaisonJoueurs.add(combinaisonsJoueur.get(combinaisonsJoueur.size()-1));
	}
	
	public void designationVainqueur(ArrayList<Joueur> listeJoueurs) {
		int max = 0;
		Combinaison meilleureCombinaison = null;
		
		for (Combinaison combinaison : listeMeilleureCombinaisonJoueurs) {
			if (combinaison.getValeur().getIndex() > max) {
				max = combinaison.getValeur().getIndex();
				meilleureCombinaison = combinaison;
			}
		}
		int posVainqueur = listeMeilleureCombinaisonJoueurs.indexOf(meilleureCombinaison);
		vainqueur = listeJoueurs.get(posVainqueur);
		
		System.out.println("Vainqueur du tour : " + vainqueur.getNom());
		System.out.println("Main gagnante : " + meilleureCombinaison.getValeur());
		System.out.println(meilleureCombinaison.toString());
		System.out.println();
	}
	
	public Joueur getVainqueur() {
		return vainqueur;
	}
}
