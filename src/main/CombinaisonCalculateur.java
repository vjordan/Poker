package main;

import java.util.ArrayList;
import java.util.Collections;

public class CombinaisonCalculateur {

	/**
	 * instance unique de la classe ( design pattern singleton )
	 */
	private static CombinaisonCalculateur COMBI_CALC_TEST = new CombinaisonCalculateur();
	
	/**
	 * liste avec le ou les vainqueurs
	 */
	private ArrayList<Joueur> vainqueur = new ArrayList<Joueur>();
	/**
	 * liste contenant toutes les combinaisons possibles de 5 cartes parmi les 7 pr�sentes pour 1 joueur
	 */
	// 7 cartes = 5 cartes communes de la table + 2 cartes privatives du joueur
	private ArrayList<Combinaison> combinaisonsJoueur;
	/**
	 * liste contenant la meilleure combinaison de chaque joueur
	 */
	private ArrayList<Combinaison> listeMeilleureCombinaisonJoueurs = new ArrayList<Combinaison>();
	
	/**
	 * D�roulement des proc�dures pour d�finir la meilleure combinaison
	 * @param listeJoueurs : liste des joueurs qui s'affrontent pour gagner le pot de la table
	 * @param table : table contenant notamment les 5 cartes communes
	 */
	public CombinaisonCalculateur (ArrayList<Joueur> listeJoueurs, Table table) {
		for (Joueur joueur : listeJoueurs) {
			constitutionCombinaisons(joueur, table);
			evaluationCombinaisons();
			designationMeilleureCombinaison();
		}
		designationVainqueur(listeJoueurs);
	}
	
	/**
	 * Constructeur priv� pour tester les combinaisons de cartes
	 */
	private CombinaisonCalculateur () {}
	
	/**
	 * Constitue les 21 combinaisons de 5 cartes possibles pour un joueur
	 * @param joueur : joueur
	 * @param table : table
	 */
	public void constitutionCombinaisons(Joueur joueur, Table table) {
		combinaisonsJoueur = new ArrayList<Combinaison>();
		
		// on rassemble les 2 cartes du joueur et les 5 cartes de la table dans une liste
		ArrayList<Carte> cartesEnsemble = new ArrayList<Carte>();
		for (int i=0; i<5; i++) {
			if (i<2)
				cartesEnsemble.add(joueur.getMain()[i]);
			cartesEnsemble.add(table.getCartesTable()[i]);
		}
		
		// constitution de chacune des combinaisons pour un joueur
		for (int i=0; i<6; i++) {
			for (int j=i; j<7; j++) {
				if (i != j) {
					ArrayList<Carte> cartesCombinaison = new ArrayList<Carte>();
					for (Carte carte : cartesEnsemble)
						cartesCombinaison.add(carte);
					cartesCombinaison.remove(j);
					cartesCombinaison.remove(i);
					combinaisonsJoueur.add(new Combinaison(cartesCombinaison));
				}
			}
		}
	}
	
	/**
	 * Evalue toutes les combinaisons pour conna�tre leur valeur
	 */
	public void evaluationCombinaisons() {	
		for (Combinaison combinaison : combinaisonsJoueur)
			// pour chaque combinaison, on �value sa valeur
			evaluerCombinaison(combinaison);
	}
	
	/**
	 * Evalue la valeur d'une combinaison
	 * @param combinaison : combinaison �valu�e
	 */
	public void evaluerCombinaison(Combinaison combinaison) {
		// on trie les cartes de la combinaison de fa�on d�croissant, pour faciliter grandement son �valuation
		Collections.sort(combinaison.getCartes());
		Collections.reverse(combinaison.getCartes());
		
		//on teste chaque valeur pour voir si elle correspond � celle de la combinaison
		//d�s que la plus haute valeur est trouv�e, on arr�te l'�valuation et on indique � la combinaison sa valeur
		if (testerQuinteFlushRoyale(combinaison))
			combinaison.setValeur(CombinaisonValeur.QUINTE_FLUSH_ROYALE);
		else if (testerQuinteFlush(combinaison))	
			combinaison.setValeur(CombinaisonValeur.QUINTE_FLUSH);
		else if (testerCarre(combinaison))			
			combinaison.setValeur(CombinaisonValeur.CARRE);
		else if (testerFull(combinaison))			
			combinaison.setValeur(CombinaisonValeur.FULL);
		else if (testerCouleur(combinaison))
			combinaison.setValeur(CombinaisonValeur.COULEUR);
		else if (testerSuite(combinaison))
			combinaison.setValeur(CombinaisonValeur.SUITE);
		else if (testerBrelan(combinaison))
			combinaison.setValeur(CombinaisonValeur.BRELAN);
		else if (testerDoublePaire(combinaison))
			combinaison.setValeur(CombinaisonValeur.DOUBLE_PAIRE);
		else if (testerPaire(combinaison))
			combinaison.setValeur(CombinaisonValeur.PAIRE);
		else {
			testerPlusHauteCarte(combinaison);
			combinaison.setValeur(CombinaisonValeur.PLUS_HAUTE_CARTE);
		}
	}
	
	/**
	 * Teste si la combinaison est une quinte flush royale
	 * @param combinaison : combinaison �valu�e
	 * @return true si la combinaison est une quinte flush royale
	 */
	public boolean testerQuinteFlushRoyale (Combinaison combinaison) {
		ArrayList<Carte> cartes = combinaison.getCartes();
		
		// si toutes les cartes ne sont pas de la m�me couleur, return false
		for (int i=1; i<5; i++) {
			if (cartes.get(i).getCouleur().getIndex() != cartes.get(0).getCouleur().getIndex()) {
				return false;
			}
		}
		// si la premi�re carte n'est pas un as, return false
		if (cartes.get(0).getRang().getIndex() != CarteRang.AS.getIndex()) {
			return false;
		}
		else {
			for (int i=1; i<5; i++) {
				// si les rangs des cartes ne se suivent pas mutuellement ( AS ROI DAME VALET DIX ), return false
				if (cartes.get(i).getRang().getIndex() != CarteRang.AS.getIndex()-i) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Teste si la combinaison est une quinte flush
	 * @param combinaison : combinaison �valu�e
	 * @return true si la combinaison est une quinte flush
	 */
	public boolean testerQuinteFlush (Combinaison combinaison) {
		ArrayList<Carte> cartes = combinaison.getCartes();
		ArrayList<Carte> cartesDepartager = new ArrayList<Carte>();
		
		// si toutes les cartes ne sont pas de la m�me couleur, return false
		for (int i=1; i<5; i++) {
			if (cartes.get(i).getCouleur().getIndex() != cartes.get(0).getCouleur().getIndex()) {
				return false;
			}
		}
		// cas sp�cial : 1�re carte = AS, 2�me carte = CINQ
		if (cartes.get(0).getRang().getIndex() == CarteRang.AS.getIndex() && cartes.get(1).getRang().getIndex() == CarteRang.CINQ.getIndex()) {
			for (int i=2; i<5; i++) {
				// si les rangs des cartes ne se suivent pas mutuellement, return false
				if (cartes.get(i).getRang().getIndex() != CarteRang.AS.getIndex()-(8+i)) {
					return false;
				}
			}
		}
		// pour tous les autres cas ...
		else {
			for (int i=1; i<5; i++) {
				// si les rangs des cartes ne se suivent pas mutuellement, return false
				if (cartes.get(i).getRang().getIndex() != cartes.get(0).getRang().getIndex()-i) {
					return false;
				}
			}
		}
		if (cartes.get(0).getRang().getIndex() == CarteRang.AS.getIndex() && cartes.get(1).getRang().getIndex() == CarteRang.CINQ.getIndex()) {
			cartesDepartager.add(cartes.get(1));
			combinaison.setCartesDepartager(cartesDepartager);
		}
		else {
			cartesDepartager.add(cartes.get(0));
			combinaison.setCartesDepartager(cartesDepartager);
		}
		return true;
	}
	
	/**
	 * Teste si la combinaison est un carr�
	 * @param combinaison : combinaison �valu�e
	 * @return true si la combinaison est un carr�
	 */
	public boolean testerCarre (Combinaison combinaison) {
		ArrayList<Carte> cartes = combinaison.getCartes();
		ArrayList<Carte> cartesDepartager = new ArrayList<Carte>();
		int valRef;
		
		//si 4 cartes de la combinaison ont le m�me rang, return true
		valRef = cartes.get(0).getRang().getIndex();
		if (cartes.get(1).getRang().getIndex() == valRef && cartes.get(2).getRang().getIndex() == valRef && cartes.get(3).getRang().getIndex() == valRef) {
			cartesDepartager.add(cartes.get(0));
			cartesDepartager.add(cartes.get(4));
			combinaison.setCartesDepartager(cartesDepartager);
			return true;
		}
		valRef = cartes.get(1).getRang().getIndex();
		if (cartes.get(2).getRang().getIndex() == valRef && cartes.get(3).getRang().getIndex() == valRef && cartes.get(4).getRang().getIndex() == valRef) {
			cartesDepartager.add(cartes.get(1));
			cartesDepartager.add(cartes.get(0));
			combinaison.setCartesDepartager(cartesDepartager);
			return true;
		}
		return false;
	}
	
	/**
	 * Teste si la combinaison est un full
	 * @param combinaison : combinaison �valu�e
	 * @return true si la combinaison est un full
	 */
	public boolean testerFull (Combinaison combinaison) {
		ArrayList<Carte> cartes = combinaison.getCartes();
		ArrayList<Carte> cartesDepartager = new ArrayList<Carte>();
		int valRef;
		
		// si la combinaison poss�de 3 cartes de rang identique et 2 autres cartes de rang identique entre elles, return true
		valRef = cartes.get(0).getRang().getIndex();
		if (cartes.get(1).getRang().getIndex() == valRef && cartes.get(2).getRang().getIndex() == valRef) {
			if (cartes.get(3).getRang().getIndex() == cartes.get(4).getRang().getIndex()) {
				cartesDepartager.add(cartes.get(0));
				cartesDepartager.add(cartes.get(3));
				combinaison.setCartesDepartager(cartesDepartager);
				return true;
			}
		}
		valRef = cartes.get(2).getRang().getIndex();
		if (cartes.get(3).getRang().getIndex() == valRef && cartes.get(4).getRang().getIndex() == valRef) {
			if (cartes.get(0).getRang().getIndex() == cartes.get(1).getRang().getIndex()) {
				cartesDepartager.add(cartes.get(2));
				cartesDepartager.add(cartes.get(0));
				combinaison.setCartesDepartager(cartesDepartager);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Teste si la combinaison est une couleur
	 * @param combinaison : combinaison �valu�e
	 * @return true si la combinaison est une couleur
	 */
	public boolean testerCouleur (Combinaison combinaison) {
		ArrayList<Carte> cartes = combinaison.getCartes();
		ArrayList<Carte> cartesDepartager = new ArrayList<Carte>();
		
		// si toutes les cartes ne sont pas de la m�me couleur, return false
		for (int i=1; i<5; i++) {
			if (cartes.get(i).getCouleur().getIndex() != cartes.get(0).getCouleur().getIndex()) {
				return false;
			}
		}
		cartesDepartager.add(cartes.get(0));
		cartesDepartager.add(cartes.get(1));
		cartesDepartager.add(cartes.get(2));
		cartesDepartager.add(cartes.get(3));
		cartesDepartager.add(cartes.get(4));
		combinaison.setCartesDepartager(cartesDepartager);
		return true;
	}
	
	/**
	 * Teste si la combinaison est une suite
	 * @param combinaison : combinaison �valu�e
	 * @return true si la combinaison est une suite
	 */
	public boolean testerSuite (Combinaison combinaison) {
		ArrayList<Carte> cartes = combinaison.getCartes();
		ArrayList<Carte> cartesDepartager = new ArrayList<Carte>();
		
		// cas sp�cial : 1�re carte = AS, 2�me carte = CINQ
		if (cartes.get(0).getRang().getIndex() == CarteRang.AS.getIndex() && cartes.get(1).getRang().getIndex() == CarteRang.CINQ.getIndex()) {
			for (int i=2; i<5; i++) {
				// si les rangs des cartes ne se suivent pas mutuellement, return false
				if (cartes.get(i).getRang().getIndex() != CarteRang.AS.getIndex()-(8+i)) {
					return false;
				}
			}
		}
		// pour tous les autres cas ...
		else {
			for (int i=1; i<5; i++) {
				// si les rangs des cartes ne se suivent pas mutuellement, return false
				if (cartes.get(i).getRang().getIndex() != cartes.get(0).getRang().getIndex()-i) {
					return false;
				}
			}
		}
		if (cartes.get(0).getRang().getIndex() == CarteRang.AS.getIndex() && cartes.get(1).getRang().getIndex() == CarteRang.CINQ.getIndex()) {
			cartesDepartager.add(cartes.get(1));
			combinaison.setCartesDepartager(cartesDepartager);
		}
		else {
			cartesDepartager.add(cartes.get(0));
			combinaison.setCartesDepartager(cartesDepartager);
		}
		return true;
	}
	
	/**
	 * Teste si la combinaison est un brelan
	 * @param combinaison : combinaison �valu�e
	 * @return true si la combinaison est un brelan
	 */
	public boolean testerBrelan (Combinaison combinaison) {
		ArrayList<Carte> cartes = combinaison.getCartes();
		ArrayList<Carte> cartesDepartager = new ArrayList<Carte>();
		int valRef;
		
		// si la combinaison poss�de 3 cartes de rang identique, return true
		valRef = cartes.get(0).getRang().getIndex();
		if (cartes.get(1).getRang().getIndex() == valRef && cartes.get(2).getRang().getIndex() == valRef) {
			cartesDepartager.add(cartes.get(0));
			cartesDepartager.add(cartes.get(3));
			cartesDepartager.add(cartes.get(4));
			combinaison.setCartesDepartager(cartesDepartager);
			return true;
		}
		valRef = cartes.get(1).getRang().getIndex();
		if (cartes.get(2).getRang().getIndex() == valRef && cartes.get(3).getRang().getIndex() == valRef) {
			cartesDepartager.add(cartes.get(1));
			cartesDepartager.add(cartes.get(0));
			cartesDepartager.add(cartes.get(4));
			combinaison.setCartesDepartager(cartesDepartager);
			return true;
		}
		valRef = cartes.get(2).getRang().getIndex();
		if (cartes.get(3).getRang().getIndex() == valRef && cartes.get(4).getRang().getIndex() == valRef) {
			cartesDepartager.add(cartes.get(2));
			cartesDepartager.add(cartes.get(0));
			cartesDepartager.add(cartes.get(1));
			combinaison.setCartesDepartager(cartesDepartager);
			return true;
		}
		return false;
	}
	
	/**
	 * Teste si la combinaison est une double paire
	 * @param combinaison : combinaison �valu�e
	 * @return true si la combinaison est une double paire
	 */
	public boolean testerDoublePaire (Combinaison combinaison) {
		ArrayList<Carte> cartes = combinaison.getCartes();
		ArrayList<Carte> cartesDepartager = new ArrayList<Carte>();
		
		// si la combinaison poss�de 2 cartes de rang identique, et 2 autres de rang identique entre elles, return true
		if (cartes.get(0).getRang().getIndex() == cartes.get(1).getRang().getIndex()) {
			if (cartes.get(2).getRang().getIndex() == cartes.get(3).getRang().getIndex()) {
				cartesDepartager.add(cartes.get(0));
				cartesDepartager.add(cartes.get(2));
				cartesDepartager.add(cartes.get(4));
				combinaison.setCartesDepartager(cartesDepartager);
				return true;
			}
			if (cartes.get(3).getRang().getIndex() == cartes.get(4).getRang().getIndex()) {
				cartesDepartager.add(cartes.get(0));
				cartesDepartager.add(cartes.get(3));
				cartesDepartager.add(cartes.get(2));
				combinaison.setCartesDepartager(cartesDepartager);
				return true;
			}
		}
		if (cartes.get(1).getRang().getIndex() == cartes.get(2).getRang().getIndex()) {
			if (cartes.get(3).getRang().getIndex() == cartes.get(4).getRang().getIndex()) {
				cartesDepartager.add(cartes.get(1));
				cartesDepartager.add(cartes.get(3));
				cartesDepartager.add(cartes.get(0));
				combinaison.setCartesDepartager(cartesDepartager);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Teste si la combinaison est une paire
	 * @param combinaison : combinaison �valu�e
	 * @return true si la combinaison est une paire
	 */
	public boolean testerPaire (Combinaison combinaison) {
		ArrayList<Carte> cartes = combinaison.getCartes();
		ArrayList<Carte> cartesDepartager = new ArrayList<Carte>();
		
		// si la combinaison poss�de 2 cartes identiques, return true
		if (cartes.get(0).getRang().getIndex() == cartes.get(1).getRang().getIndex()) {
			cartesDepartager.add(cartes.get(0));
			cartesDepartager.add(cartes.get(2));
			cartesDepartager.add(cartes.get(3));
			cartesDepartager.add(cartes.get(4));
			combinaison.setCartesDepartager(cartesDepartager);
			return true;
		}
		if (cartes.get(1).getRang().getIndex() == cartes.get(2).getRang().getIndex()) {
			cartesDepartager.add(cartes.get(1));
			cartesDepartager.add(cartes.get(0));
			cartesDepartager.add(cartes.get(3));
			cartesDepartager.add(cartes.get(4));
			combinaison.setCartesDepartager(cartesDepartager);
			return true;
		}
		if (cartes.get(2).getRang().getIndex() == cartes.get(3).getRang().getIndex()) {
			cartesDepartager.add(cartes.get(2));
			cartesDepartager.add(cartes.get(0));
			cartesDepartager.add(cartes.get(1));
			cartesDepartager.add(cartes.get(4));
			combinaison.setCartesDepartager(cartesDepartager);
			return true;
		}
		if (cartes.get(3).getRang().getIndex() == cartes.get(4).getRang().getIndex()) {
			cartesDepartager.add(cartes.get(3));
			cartesDepartager.add(cartes.get(0));
			cartesDepartager.add(cartes.get(1));
			cartesDepartager.add(cartes.get(2));
			combinaison.setCartesDepartager(cartesDepartager);
			return true;
		}
		return false;
	}
	
	/**
	 * La valeur de la combinaison est d�finie par sa plus haute carte
	 * @param combinaison : combinaison �valu�e
	 */
	public void testerPlusHauteCarte (Combinaison combinaison) {
		ArrayList<Carte> cartes = combinaison.getCartes();
		ArrayList<Carte> cartesDepartager = new ArrayList<Carte>();
		
		//on pourra d�partager les combinaisons de cette valeur avec la carte de meilleur rang
		cartesDepartager.add(cartes.get(0));
		cartesDepartager.add(cartes.get(1));
		cartesDepartager.add(cartes.get(2));
		cartesDepartager.add(cartes.get(3));
		cartesDepartager.add(cartes.get(4));
		combinaison.setCartesDepartager(cartesDepartager);
	}

	/**
	 * D�signe la meilleure combinaison pour un joueur
	 */
	public void designationMeilleureCombinaison() {
		// on trie les combinaisons selon leur valeur et on prend la premi�re qui correspond � la meilleure
		Collections.sort(combinaisonsJoueur);
		Collections.reverse(combinaisonsJoueur);
		listeMeilleureCombinaisonJoueurs.add(combinaisonsJoueur.get(0));
	}
	
	/**
	 * D�signe le vainqueur du tour
	 * @param listeJoueurs : liste des joueurs en comp�tition
	 */
	public void designationVainqueur(ArrayList<Joueur> listeJoueurs) {
		// liste r�capitulant la meilleure combinaison de chaque joueur
		ArrayList<Combinaison> listeCombinaisons = new ArrayList<Combinaison>();
		// liste indiquant la meilleure combinaison de toutes parmi les meilleures combinaisons de chaque joueur
		// il peut y avoir plusieurs combinaisons gagnantes, si elles sont exactement identiques
		ArrayList<Combinaison> listeMeilleuresCombinaisons = new ArrayList<Combinaison>();
		
		for (int i=0; i<listeMeilleureCombinaisonJoueurs.size(); i++) {
			listeCombinaisons.add(listeMeilleureCombinaisonJoueurs.get(i));
		}
		
		// on trie les combinaisons
		Collections.sort(listeCombinaisons);
		Collections.reverse(listeCombinaisons);
		
		int posVainqueur = listeMeilleureCombinaisonJoueurs.indexOf(listeCombinaisons.get(0));
		listeMeilleuresCombinaisons.add(listeMeilleureCombinaisonJoueurs.get(posVainqueur));
		vainqueur.add(listeJoueurs.get(posVainqueur));
		for (int i=1; i<listeCombinaisons.size(); i++) {
			// on v�rifie si les combinaisons qui suivent la premi�re de la liste ne sont pas identiques � elles
			if (listeCombinaisons.get(i).compareTo(listeCombinaisons.get(0)) == 0) {
				posVainqueur = listeMeilleureCombinaisonJoueurs.indexOf(listeCombinaisons.get(i));
				listeMeilleuresCombinaisons.add(listeMeilleureCombinaisonJoueurs.get(posVainqueur));
				vainqueur.add(listeJoueurs.get(posVainqueur));
			}
			// d�s qu'une combinaison n'est pas identique � la premi�re, pas besoin de continuer � v�rifier puisque les suivantes seront forc�ment inf�rieures
			else
				break;
		}
		
		// on affiche le vainqueur du tour en pr�cisant la valeur de la main gagnante, avec la combinaison correspondante
		for (int i=0; i<vainqueur.size(); i++) {
			System.out.println("Vainqueur du tour : " + vainqueur.get(i).getNom());
			System.out.println("   Main gagnante : " + listeMeilleuresCombinaisons.get(i).getValeur().getLibelle());
			System.out.println(listeMeilleuresCombinaisons.get(i).toString());
		}
		System.out.println();
	}

	/**
	 * getter pour "vainqueur"
	 * @return la liste du ou des vainqueur(s) du tour
	 */
	public ArrayList<Joueur> getVainqueur() {
		return vainqueur;
	}
	
	/**
	 * getter pour "COMBI_CALC_TEST"
	 * @return l'instance unique du singleton
	 */
	public static CombinaisonCalculateur getInstance() {
		return COMBI_CALC_TEST;
	}
}
