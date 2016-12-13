package test;

import java.util.ArrayList;
import junit.framework.TestCase;
import main.*;

public class CombinaisonCalculateurTest extends TestCase {

	public void testEvaluerCombinaison() {
		CombinaisonCalculateur combiCalc = CombinaisonCalculateur.getInstance();
		Carte carte1, carte2, carte3, carte4, carte5;
		ArrayList<Carte> listeCartes;
		Combinaison combinaison;
		
		// QUINTE FLUSH ROYALE
		carte1 = new Carte(CarteCouleur.COEUR,CarteRang.DAME);
		carte2 = new Carte(CarteCouleur.COEUR,CarteRang.ROI);
		carte3 = new Carte(CarteCouleur.COEUR,CarteRang.VALET);
		carte4 = new Carte(CarteCouleur.COEUR,CarteRang.AS);
		carte5 = new Carte(CarteCouleur.COEUR,CarteRang.DIX);
		
		listeCartes = new ArrayList<Carte>();
		listeCartes.add(carte1);listeCartes.add(carte2);listeCartes.add(carte3);listeCartes.add(carte4);listeCartes.add(carte5);
		combinaison = new Combinaison(listeCartes);
		combiCalc.evaluerCombinaison(combinaison);
		assertTrue("quinte flush royale", combinaison.getValeur() == CombinaisonValeur.QUINTE_FLUSH_ROYALE);
		
		// QUINTE FLUSH
		carte1 = new Carte(CarteCouleur.COEUR,CarteRang.SEPT);
		carte2 = new Carte(CarteCouleur.COEUR,CarteRang.HUIT);
		carte3 = new Carte(CarteCouleur.COEUR,CarteRang.SIX);
		carte4 = new Carte(CarteCouleur.COEUR,CarteRang.NEUF);
		carte5 = new Carte(CarteCouleur.COEUR,CarteRang.CINQ);
		
		listeCartes = new ArrayList<Carte>();
		listeCartes.add(carte1);listeCartes.add(carte2);listeCartes.add(carte3);listeCartes.add(carte4);listeCartes.add(carte5);
		combinaison = new Combinaison(listeCartes);
		combiCalc.evaluerCombinaison(combinaison);
		assertTrue("quinte flush", combinaison.getValeur() == CombinaisonValeur.QUINTE_FLUSH);
		
		// CARRE
		carte1 = new Carte(CarteCouleur.CARREAU,CarteRang.AS);
		carte2 = new Carte(CarteCouleur.TREFLE,CarteRang.AS);
		carte3 = new Carte(CarteCouleur.PIQUE,CarteRang.AS);
		carte4 = new Carte(CarteCouleur.COEUR,CarteRang.AS);
		carte5 = new Carte(CarteCouleur.COEUR,CarteRang.DIX);
		
		listeCartes = new ArrayList<Carte>();
		listeCartes.add(carte1);listeCartes.add(carte2);listeCartes.add(carte3);listeCartes.add(carte4);listeCartes.add(carte5);
		combinaison = new Combinaison(listeCartes);
		combiCalc.evaluerCombinaison(combinaison);
		assertTrue("carre", combinaison.getValeur() == CombinaisonValeur.CARRE);
		
		// FULL
		carte1 = new Carte(CarteCouleur.CARREAU,CarteRang.AS);
		carte2 = new Carte(CarteCouleur.TREFLE,CarteRang.AS);
		carte3 = new Carte(CarteCouleur.COEUR,CarteRang.ROI);
		carte4 = new Carte(CarteCouleur.COEUR,CarteRang.AS);
		carte5 = new Carte(CarteCouleur.TREFLE,CarteRang.ROI);
		
		listeCartes = new ArrayList<Carte>();
		listeCartes.add(carte1);listeCartes.add(carte2);listeCartes.add(carte3);listeCartes.add(carte4);listeCartes.add(carte5);
		combinaison = new Combinaison(listeCartes);
		combiCalc.evaluerCombinaison(combinaison);
		assertTrue("full", combinaison.getValeur() == CombinaisonValeur.FULL);
		
		// COULEUR
		carte1 = new Carte(CarteCouleur.COEUR,CarteRang.HUIT);
		carte2 = new Carte(CarteCouleur.COEUR,CarteRang.DIX);
		carte3 = new Carte(CarteCouleur.COEUR,CarteRang.CINQ);
		carte4 = new Carte(CarteCouleur.COEUR,CarteRang.AS);
		carte5 = new Carte(CarteCouleur.COEUR,CarteRang.DEUX);
		
		listeCartes = new ArrayList<Carte>();
		listeCartes.add(carte1);listeCartes.add(carte2);listeCartes.add(carte3);listeCartes.add(carte4);listeCartes.add(carte5);
		combinaison = new Combinaison(listeCartes);
		combiCalc.evaluerCombinaison(combinaison);
		assertTrue("couleur", combinaison.getValeur() == CombinaisonValeur.COULEUR);
		
		// SUITE
		carte1 = new Carte(CarteCouleur.CARREAU,CarteRang.TROIS);
		carte2 = new Carte(CarteCouleur.TREFLE,CarteRang.QUATRE);
		carte3 = new Carte(CarteCouleur.PIQUE,CarteRang.DEUX);
		carte4 = new Carte(CarteCouleur.COEUR,CarteRang.CINQ);
		carte5 = new Carte(CarteCouleur.COEUR,CarteRang.AS);
		
		listeCartes = new ArrayList<Carte>();
		listeCartes.add(carte1);listeCartes.add(carte2);listeCartes.add(carte3);listeCartes.add(carte4);listeCartes.add(carte5);
		combinaison = new Combinaison(listeCartes);
		combiCalc.evaluerCombinaison(combinaison);
		assertTrue("suite", combinaison.getValeur() == CombinaisonValeur.SUITE);
		
		// BRELAN
		carte1 = new Carte(CarteCouleur.CARREAU,CarteRang.DIX);
		carte2 = new Carte(CarteCouleur.TREFLE,CarteRang.DIX);
		carte3 = new Carte(CarteCouleur.COEUR,CarteRang.CINQ);
		carte4 = new Carte(CarteCouleur.COEUR,CarteRang.DIX);
		carte5 = new Carte(CarteCouleur.TREFLE,CarteRang.TROIS);
		
		listeCartes = new ArrayList<Carte>();
		listeCartes.add(carte1);listeCartes.add(carte2);listeCartes.add(carte3);listeCartes.add(carte4);listeCartes.add(carte5);
		combinaison = new Combinaison(listeCartes);
		combiCalc.evaluerCombinaison(combinaison);
		assertTrue("brelan", combinaison.getValeur() == CombinaisonValeur.BRELAN);
		
		// DOUBLE PAIRE
		carte1 = new Carte(CarteCouleur.COEUR,CarteRang.ROI);
		carte2 = new Carte(CarteCouleur.TREFLE,CarteRang.AS);
		carte3 = new Carte(CarteCouleur.TREFLE,CarteRang.ROI);
		carte4 = new Carte(CarteCouleur.COEUR,CarteRang.AS);
		carte5 = new Carte(CarteCouleur.TREFLE,CarteRang.CINQ);
		
		listeCartes = new ArrayList<Carte>();
		listeCartes.add(carte1);listeCartes.add(carte2);listeCartes.add(carte3);listeCartes.add(carte4);listeCartes.add(carte5);
		combinaison = new Combinaison(listeCartes);
		combiCalc.evaluerCombinaison(combinaison);
		assertTrue("double paire", combinaison.getValeur() == CombinaisonValeur.DOUBLE_PAIRE);
		
		// PAIRE
		carte1 = new Carte(CarteCouleur.COEUR,CarteRang.SIX);
		carte2 = new Carte(CarteCouleur.TREFLE,CarteRang.AS);
		carte3 = new Carte(CarteCouleur.COEUR,CarteRang.QUATRE);
		carte4 = new Carte(CarteCouleur.COEUR,CarteRang.AS);
		carte5 = new Carte(CarteCouleur.CARREAU,CarteRang.DEUX);
		
		listeCartes = new ArrayList<Carte>();
		listeCartes.add(carte1);listeCartes.add(carte2);listeCartes.add(carte3);listeCartes.add(carte4);listeCartes.add(carte5);
		combinaison = new Combinaison(listeCartes);
		combiCalc.evaluerCombinaison(combinaison);
		assertTrue("paire", combinaison.getValeur() == CombinaisonValeur.PAIRE);
		
		// PLUS HAUTE CARTE
		carte1 = new Carte(CarteCouleur.CARREAU,CarteRang.SIX);
		carte2 = new Carte(CarteCouleur.TREFLE,CarteRang.NEUF);
		carte3 = new Carte(CarteCouleur.PIQUE,CarteRang.QUATRE);
		carte4 = new Carte(CarteCouleur.COEUR,CarteRang.AS);
		carte5 = new Carte(CarteCouleur.COEUR,CarteRang.DEUX);
		
		listeCartes = new ArrayList<Carte>();
		listeCartes.add(carte1);listeCartes.add(carte2);listeCartes.add(carte3);listeCartes.add(carte4);listeCartes.add(carte5);
		combinaison = new Combinaison(listeCartes);
		combiCalc.evaluerCombinaison(combinaison);
		assertTrue("plus haute carte", combinaison.getValeur() == CombinaisonValeur.PLUS_HAUTE_CARTE);
	}
}
