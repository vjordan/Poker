import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Tour {
	private final static int VALEUR_PETITE_BLINDE = 10;
	private final static int VALEUR_GROSSE_BLINDE = 20;
	
	private static int nbTours;
	private Table table;
	private int nbJoueursTour;
	private ArrayList<Joueur> listeJoueursTour;
	private ArrayList<Carte> paquetCartes;
	private Joueur donneur, petiteBlind, grosseBlind;
	private Scanner sc = new Scanner(System.in);
	private Scanner sc2 = new Scanner(System.in);
	
	public Tour(ArrayList<Joueur> listeJoueursPartie, ArrayList<Joueur> listeJoueursBase) {
		table = new Table();
		listeJoueursTour = new ArrayList<Joueur>();
		paquetCartes = new ArrayList<Carte>();
		
		nbTours++;		
		for (int i=0; i<listeJoueursPartie.size(); i++) {
			listeJoueursTour.add(listeJoueursPartie.get(i));
		}
		nbJoueursTour = listeJoueursTour.size();
		tourEntete();

		donneur(listeJoueursBase);
		petiteBlind();
		grosseBlind();
		affichageDebutTour();
		
		constitutionPaquetCartes();
		melangePaquetCartes();
		distributionCartes();
		
		preFlop(listeJoueursPartie);
	}

	public void tourEntete() {
		System.out.println("---------------------------");
		System.out.println("TOUR " + nbTours);
		System.out.println("---------------------------");
		System.out.println();
	}
	
	public void donneur(ArrayList<Joueur> listeJoueursBase) {
		Joueur donneurBase = null;
		int i = 0;
		
		for (Joueur joueur : listeJoueursBase) {
			if (joueur.isEstDonneur() == true)
				donneurBase = joueur;
		}
		int posDonneurBase = listeJoueursBase.indexOf(donneurBase);
		int posDonneur = listeJoueursTour.indexOf(donneurBase);
		while (posDonneur == -1) {
			i++;
			if ((posDonneurBase+i) < listeJoueursBase.size())
				posDonneur = listeJoueursTour.indexOf(listeJoueursBase.get(posDonneurBase+i));
			else
				posDonneur = listeJoueursTour.indexOf(listeJoueursBase.get(posDonneurBase+i-listeJoueursBase.size()));
		}
		
		donneur = listeJoueursTour.get(posDonneur);
		
		donneur.setEstDonneur(false);
		if (listeJoueursBase.indexOf(donneur)+1 < listeJoueursBase.size())
			listeJoueursBase.get(listeJoueursBase.indexOf(donneur)+1).setEstDonneur(true);
		else
			listeJoueursBase.get(listeJoueursBase.indexOf(donneur)+1-listeJoueursBase.size()).setEstDonneur(true);
		
		System.out.println("Donneur : " + donneur.getNom());
	}
	
	public void petiteBlind() {
		if (nbJoueursTour > 2) {
			int posDonneur = listeJoueursTour.indexOf(donneur);
			int posPetiteBlind = posDonneur+1;
			if (posPetiteBlind > nbJoueursTour-1)
				posPetiteBlind -= nbJoueursTour;
			petiteBlind = listeJoueursTour.get(posPetiteBlind);
		}
		else
			petiteBlind = donneur;
		
		System.out.println("Petite blind : " + petiteBlind.getNom());
		if (VALEUR_PETITE_BLINDE < petiteBlind.getCompte())
			petiteBlind.setCompte(petiteBlind.getCompte()-VALEUR_PETITE_BLINDE);
		else
			petiteBlind.setCompte(0);
	}
	
	public void grosseBlind() {
		int posGrosseBlind;
		
		if (nbJoueursTour > 2) {
			int posPetiteBlind = listeJoueursTour.indexOf(petiteBlind);
			posGrosseBlind = posPetiteBlind+1;
			if (posGrosseBlind > nbJoueursTour-1)
				posGrosseBlind -= nbJoueursTour;
		}
		else {
			int posDonneur = listeJoueursTour.indexOf(donneur);
			if ((posDonneur+1) < nbJoueursTour)
				posGrosseBlind = posDonneur+1;
			else
				posGrosseBlind = posDonneur-1;
		}
		
		grosseBlind = listeJoueursTour.get(posGrosseBlind);
		
		System.out.println("Grosse blind : " + grosseBlind.getNom());
		if (VALEUR_GROSSE_BLINDE < grosseBlind.getCompte())
			grosseBlind.setCompte(grosseBlind.getCompte()-VALEUR_GROSSE_BLINDE);
		else
			grosseBlind.setCompte(0);
		
		System.out.println();
	}
	
	public void affichageDebutTour() {
		for (Joueur joueur : listeJoueursTour)
			System.out.println(joueur.toString());
		
		table.setPot(table.getPot()+VALEUR_PETITE_BLINDE+VALEUR_GROSSE_BLINDE);
		System.out.println();
		System.out.println(table.toString());
		System.out.println();
	}
	
	public void constitutionPaquetCartes() {
		for (CarteCouleur couleur : CarteCouleur.values())
			for (CarteValeur valeur : CarteValeur.values())
				paquetCartes.add(new Carte(couleur,valeur));
	}
	
	public void melangePaquetCartes() {
		long seed = System.nanoTime();
		
		Collections.shuffle(paquetCartes, new Random(seed));
	}
	
	public void distributionCartes() {
		Joueur joueur;
		
		for (int i=0; i<nbJoueursTour;i++) {
			joueur = listeJoueursTour.get(i);
			joueur.main(paquetCartes);
		}
		System.out.println();
	}
	
	public void preFlop(ArrayList<Joueur> listeJoueursPartie) {
		String repChoix;
		int indicePos, repRelance, tapis;
		
		boolean finPreFlop = false;
		boolean finPreFlopBis = false;
		int miseSup = VALEUR_GROSSE_BLINDE;
		int nbToursPreFlop = 0;
		
		ArrayList<Joueur> listeJoueursPreFlop = new ArrayList<Joueur>();
		
		reinitialisationMises();
		for (Joueur joueur : listeJoueursTour)
			joueur.setMiseTour(0);
		petiteBlind.setMise(VALEUR_PETITE_BLINDE);
		grosseBlind.setMise(VALEUR_GROSSE_BLINDE);
		
		int posGrosseBlind = listeJoueursTour.indexOf(grosseBlind);
		for (int i=1; i<nbJoueursTour+1; i++) {
			indicePos = posGrosseBlind+i;
			if (indicePos < nbJoueursTour)
				listeJoueursPreFlop.add(listeJoueursTour.get(indicePos));
			else
				listeJoueursPreFlop.add(listeJoueursTour.get(indicePos-nbJoueursTour));
		}
		
		do {
			nbToursPreFlop++;
			ArrayList<Joueur> joueursSupprimes = new ArrayList<Joueur>();
			
			for (Joueur joueur : listeJoueursPreFlop) {
				if ((finPreFlop == false) && (nbJoueursTour > 1) && (finPreFlopBis == false) && (joueur.getCompte()>0)) {
					do {
						if (joueur.getCompte() > miseSup-joueur.getMise()) {
							if (miseSup-joueur.getMise() != 0)
								System.out.println(joueur.getNom() + " : se coucher(1), suivre avec " + (miseSup-joueur.getMise()) + " (2), relancer(3), tapis(4) ?");
							else
								System.out.println(joueur.getNom() + " : se coucher(1), check(2), miser(3), tapis(4) ?");
						}
						else {
							if (miseSup-joueur.getMise() != 0)
								System.out.println(joueur.getNom() + " : se coucher(1), suivre avec " + joueur.getCompte() + " (2), relancer(3), tapis(4) ?");
							else
								System.out.println(joueur.getNom() + " : se coucher(1), check(2), miser(3), tapis(4) ?");
						}
						System.out.println("Tapis = " + joueur.getCompte() + " | Carte 1 : " + joueur.getMain()[0].toString() + " / Carte 2 : " + joueur.getMain()[1].toString());
						repChoix = sc.nextLine();
					} while ((!(repChoix.equals("1")))&&(!(repChoix.equals("2")))&&(!(repChoix.equals("3")))&&(!(repChoix.equals("4"))));
					switch (repChoix) {
					case "1" :
						joueursSupprimes.add(joueur);
						majListeJoueursTour(joueur);
						break;
					case "2" :
						if (joueur.getCompte() > miseSup-joueur.getMise()) {
							joueur.setCompte(joueur.getCompte()-(miseSup-joueur.getMise()));
							table.setPot(table.getPot()+(miseSup-joueur.getMise()));
							joueur.setMise(miseSup);
						}
						else {
							System.out.println("Impossible de suivre ! Vous êtes à tapis.");
							tapis = joueur.getCompte();
							joueur.setCompte(0);
							table.setPot(table.getPot()+tapis);
							joueur.setMise(joueur.getMise()+tapis);
						}
						break;
					case "3" :
						if (miseSup < (joueur.getCompte()-(miseSup-joueur.getMise()))) {
							do {
								if (miseSup-joueur.getMise() == 0)
									System.out.println("Combien voulez-vous miser ? (entre " + miseSup + " et " + (joueur.getCompte()-(miseSup-joueur.getMise())) + ")");
								else
									System.out.println("De combien voulez-vous relancer ? (entre " + miseSup + " et " + (joueur.getCompte()-(miseSup-joueur.getMise())) + ")");
								repRelance = sc2.nextInt();
							} while ((repRelance < miseSup) || (repRelance > (joueur.getCompte()-(miseSup-joueur.getMise()))));
							joueur.setCompte(joueur.getCompte()-(miseSup-joueur.getMise())-repRelance);
							table.setPot(table.getPot()+(miseSup-joueur.getMise())+repRelance);
							miseSup = miseSup + repRelance;
							joueur.setMise(miseSup);
						}
						else {
							if (miseSup-joueur.getMise() <= joueur.getCompte()) {
								System.out.println("Impossible de relancer ! Vous avez suivi.");
								joueur.setCompte(joueur.getCompte()-(miseSup-joueur.getMise()));
								table.setPot(table.getPot()+(miseSup-joueur.getMise()));
								joueur.setMise(miseSup);
							}
							else {
								System.out.println("Impossible de relancer ! Vous êtes à tapis.");
								tapis = joueur.getCompte();
								joueur.setCompte(0);
								table.setPot(table.getPot()+tapis);
								joueur.setMise(joueur.getMise()+tapis);
							}
						}
						break;
					case "4" :
						tapis = joueur.getCompte();					
						if (miseSup < (tapis-(miseSup-joueur.getMise()))) {
							joueur.setCompte(0);
							table.setPot(table.getPot()+tapis);
							miseSup = miseSup + (tapis-(miseSup-joueur.getMise()));
							joueur.setMise(miseSup);
						}
						else {
							if (tapis > miseSup-joueur.getMise()) {
								joueur.setCompte(0);
								table.setPot(table.getPot()+tapis);
								miseSup = miseSup + (tapis-miseSup);
								joueur.setMise(miseSup);
							}
							else {
								joueur.setCompte(0);
								table.setPot(table.getPot()+tapis);
								joueur.setMise(joueur.getMise()+tapis);
							}
						}	
						break;
					}
					if (nbToursPreFlop > 1) {
						finPreFlop = finTourMise(miseSup);
						finPreFlopBis = finTourMiseBis(miseSup);	
					}
					System.out.println();
					System.out.println(joueur.toString());
					System.out.println(table.toString());
					System.out.println();
				}
			}
			
			listeJoueursPreFlop.removeAll(joueursSupprimes);
			if (nbToursPreFlop == 1) {
				finPreFlop = finTourMise(miseSup);
				finPreFlopBis = finTourMiseBis(miseSup);
			}	
		} while ((finPreFlop == false) && (nbJoueursTour > 1) && (finPreFlopBis == false));
		
		majMiseJoueursTour();
		
		if (nbJoueursTour > 1) {
			afficherInfosTour();
			flop(listeJoueursPartie);
		}
		else
			finTour(listeJoueursPartie);
	}
	
	public void flop(ArrayList<Joueur> listeJoueursPartie) {
		table.flop(paquetCartes);
		
		deroulementTourMise(listeJoueursPartie);
		
		if (nbJoueursTour > 1) {
			afficherInfosTour();
			turn(listeJoueursPartie);
		}
		else
			finTour(listeJoueursPartie);
	}


	public void turn(ArrayList<Joueur> listeJoueursPartie) {
		table.turn(paquetCartes);	
		
		deroulementTourMise(listeJoueursPartie);
		
		if (nbJoueursTour > 1) {
			afficherInfosTour();
			river(listeJoueursPartie);
		}
		else
			finTour(listeJoueursPartie);
	}

	public void river(ArrayList<Joueur> listeJoueursPartie) {
		table.river(paquetCartes);
		
		deroulementTourMise(listeJoueursPartie);
		
		afficherInfosTour();
		finTour(listeJoueursPartie);
	}
	
	public void deroulementTourMise(ArrayList<Joueur> listeJoueursPartie) {
		String repChoix;
		int indicePos, repRelance, posPetiteBlindPartie, tapis;
		
		boolean finTourMise = false;
		boolean finTourMiseBis = false;
		int miseSup = 0;
		int nbToursMise = 0;
		int j = 0;
		int nbJoueursParle = 0;
		
		ArrayList<Joueur> listeJoueursTourMise = new ArrayList<Joueur>();
		
		reinitialisationMises();
		
		int posPetiteBlind = listeJoueursTour.indexOf(petiteBlind);
		while (posPetiteBlind == -1) {
			j++;
			posPetiteBlindPartie = listeJoueursPartie.indexOf(petiteBlind);
			if ((posPetiteBlindPartie+j) < listeJoueursPartie.size())
				posPetiteBlind = listeJoueursTour.indexOf(listeJoueursPartie.get(posPetiteBlindPartie+j));
			else
				posPetiteBlind = listeJoueursTour.indexOf(listeJoueursPartie.get(posPetiteBlindPartie+j-listeJoueursPartie.size()));
		}
		for (int i=0; i<nbJoueursTour; i++) {
			indicePos = posPetiteBlind+i;
			if (indicePos < nbJoueursTour)
				listeJoueursTourMise.add(listeJoueursTour.get(indicePos));
			else
				listeJoueursTourMise.add(listeJoueursTour.get(indicePos-nbJoueursTour));
		}
		
		do {
			nbToursMise++;
			ArrayList<Joueur> joueursSupprimes = new ArrayList<Joueur>();
			
			finTourMiseBis = finTourMiseBis(miseSup);
			
			for (Joueur joueur : listeJoueursTourMise) {
				nbJoueursParle++;
				if ((finTourMise == false) && (nbJoueursTour > 1) && (finTourMiseBis == false) && (joueur.getCompte()>0)) {
					if (nbJoueursParle > 1) {
						do {
							if (joueur.getCompte() > miseSup-joueur.getMise()) {
								if (miseSup-joueur.getMise() != 0)
									System.out.println(joueur.getNom() + " : se coucher(1), suivre avec " + (miseSup-joueur.getMise()) + " (2), relancer(3), tapis(4) ?");
								else
									System.out.println(joueur.getNom() + " : se coucher(1), check(2), miser(3), tapis(4) ?");
							}
							else {
								if (miseSup-joueur.getMise() != 0)
									System.out.println(joueur.getNom() + " : se coucher(1), suivre avec " + joueur.getCompte() + " (2), relancer(3), tapis(4) ?");
								else
									System.out.println(joueur.getNom() + " : se coucher(1), check(2), miser(3), tapis(4) ?");
							}
							System.out.println("Tapis = " + joueur.getCompte() + " | Carte 1 : " + joueur.getMain()[0].toString() + " / Carte 2 : " + joueur.getMain()[1].toString());
							repChoix = sc.nextLine();
						} while ((!(repChoix.equals("1")))&&(!(repChoix.equals("2")))&&(!(repChoix.equals("3")))&&(!(repChoix.equals("4"))));
					}
					else {
						do {
							System.out.println(joueur.getNom() + " : check(2), miser(3), tapis(4) ?");
							System.out.println("Tapis = " + joueur.getCompte() + " | Carte 1 : " + joueur.getMain()[0].toString() + " / Carte 2 : " + joueur.getMain()[1].toString());
							repChoix = sc.nextLine();
						} while ((!(repChoix.equals("2")))&&(!(repChoix.equals("3")))&&(!(repChoix.equals("4"))));
					}
					switch (repChoix) {
					case "1" :
						joueursSupprimes.add(joueur);
						majListeJoueursTour(joueur);
						break;
					case "2" :
						if (joueur.getCompte() > miseSup-joueur.getMise()) {
							joueur.setCompte(joueur.getCompte()-(miseSup-joueur.getMise()));
							table.setPot(table.getPot()+(miseSup-joueur.getMise()));
							joueur.setMise(miseSup);
						}
						else {
							System.out.println("Impossible de suivre ! Vous êtes à tapis.");
							tapis = joueur.getCompte();
							joueur.setCompte(0);
							table.setPot(table.getPot()+tapis);
							joueur.setMise(joueur.getMise()+tapis);
						}
						break;
					case "3" :
						if (miseSup < (joueur.getCompte()-(miseSup-joueur.getMise()))) {
							if (miseSup > 0) {
								do {
									if (miseSup-joueur.getMise() == 0)
										System.out.println("Combien voulez-vous miser ? (entre " + miseSup + " et " + (joueur.getCompte()-(miseSup-joueur.getMise())) + ")");
									else
										System.out.println("De combien voulez-vous relancer ? (entre " + miseSup + " et " + (joueur.getCompte()-(miseSup-joueur.getMise())) + ")");
									repRelance = sc2.nextInt();
								} while ((repRelance < miseSup) || (repRelance > (joueur.getCompte()-(miseSup-joueur.getMise()))));
							}
							else {
								do {
									System.out.println("combien voulez-vous miser ? (entre " + VALEUR_GROSSE_BLINDE + " et " + (joueur.getCompte()-(miseSup-joueur.getMise())) + ")");
									repRelance = sc2.nextInt();
								} while ((repRelance < VALEUR_GROSSE_BLINDE) || (repRelance > (joueur.getCompte()-(miseSup-joueur.getMise()))));
							}
							joueur.setCompte(joueur.getCompte()-(miseSup-joueur.getMise())-repRelance);
							table.setPot(table.getPot()+(miseSup-joueur.getMise())+repRelance);
							miseSup = miseSup + repRelance;
							joueur.setMise(miseSup);
						}
						else {
							if (miseSup-joueur.getMise() <= joueur.getCompte()) {
								System.out.println("Impossible de relancer ! Vous avez suivi.");
								joueur.setCompte(joueur.getCompte()-(miseSup-joueur.getMise()));
								table.setPot(table.getPot()+(miseSup-joueur.getMise()));
								joueur.setMise(miseSup);
							}
							else {
								System.out.println("Impossible de relancer ! Vous êtes à tapis.");
								tapis = joueur.getCompte();
								joueur.setCompte(0);
								table.setPot(table.getPot()+tapis);
								joueur.setMise(joueur.getMise()+tapis);
							}
						}
						break;
					case "4" :
						tapis = joueur.getCompte();					
						if (miseSup < (tapis-(miseSup-joueur.getMise()))) {
							joueur.setCompte(0);
							table.setPot(table.getPot()+tapis);
							miseSup = miseSup + (tapis-(miseSup-joueur.getMise()));
							joueur.setMise(miseSup);
						}
						else {
							if (tapis > miseSup-joueur.getMise()) {
								joueur.setCompte(0);
								table.setPot(table.getPot()+tapis);
								miseSup = miseSup + (tapis-miseSup);
								joueur.setMise(miseSup);
							}
							else {
								joueur.setCompte(0);
								table.setPot(table.getPot()+tapis);
								joueur.setMise(joueur.getMise()+tapis);
							}
						}	
						break;
					}
					if (nbToursMise > 1) {
						finTourMise = finTourMise(miseSup);
						finTourMiseBis = finTourMiseBis(miseSup);
					}
					System.out.println();
					System.out.println(joueur.toString());
					System.out.println(table.toString());
					System.out.println();
				}
			}
			
			listeJoueursTourMise.removeAll(joueursSupprimes);
			if (nbToursMise == 1) {
				finTourMise = finTourMise(miseSup);
				finTourMiseBis = finTourMiseBis(miseSup);
			}
		} while ((finTourMise == false) && (nbJoueursTour > 1) && (finTourMiseBis == false));
		
		majMiseJoueursTour();
	}
	
	public boolean finTourMise(int miseSup) {
		for (Joueur joueur : listeJoueursTour) {
			if (joueur.getMise() != miseSup) {
				if (joueur.getCompte() != 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean finTourMiseBis(int miseSup) {
		int nbJoueursTapis = 0;
		int nbJoueursPasJoue = 0;
		
		for (Joueur joueur : listeJoueursTour) {
			if (joueur.getCompte() == 0)
				nbJoueursTapis++;
			else {
				if (joueur.getMise() < miseSup)
					nbJoueursPasJoue++;
			}
		}
		if ((nbJoueursTapis >= (nbJoueursTour-1)) && (nbJoueursPasJoue == 0))
			return true;
		return false;
	}
	
	public void majListeJoueursTour(Joueur joueur) {
		listeJoueursTour.remove(listeJoueursTour.indexOf(joueur));
		nbJoueursTour--;
	}
	
	public void reinitialisationMises() {
		for (Joueur joueur : listeJoueursTour)
			joueur.setMise(0);
	}
	
	public void majMiseJoueursTour() {
		for (Joueur joueur : listeJoueursTour)
			joueur.setMiseTour(joueur.getMiseTour() + joueur.getMise());
	}
	
	public void afficherInfosTour() {
		for (Joueur joueur : listeJoueursTour)
			System.out.println(joueur.toString());
		System.out.println(table.toString());
		for (Joueur joueur : listeJoueursTour)
			joueur.affichageMain();
		System.out.println();
	}

	public static void setNbTours(int nbTours) {
		Tour.nbTours = nbTours;
	}
	
	public void finTour(ArrayList<Joueur> listeJoueursPartie) {
		int miseMax = 0;
		
		if (nbJoueursTour > 1) {
			CombinaisonVainqueur meilleureCombinaison = new CombinaisonVainqueur(listeJoueursTour, table);
			Joueur vainqueur = meilleureCombinaison.getVainqueur();
			//Joueur vainqueur = listeJoueursTour.get(0);
			System.out.println("Vainqueur du tour : " + vainqueur.getNom());
			System.out.println();
			
			for (Joueur joueur : listeJoueursTour) {
				if (joueur.getMiseTour() > miseMax)
					miseMax = joueur.getMiseTour();
			}		
			if (vainqueur.getMiseTour() == miseMax)
				vainqueur.setCompte(vainqueur.getCompte()+table.getPot());
			else {
				int gain = listeJoueursTour.size()*vainqueur.getMiseTour();
				int potReste = table.getPot()-gain;
				int quotientPotReste = potReste / (listeJoueursTour.size()-1);
				int moduloPotReste = potReste - (quotientPotReste*(listeJoueursTour.size()-1));
				
				vainqueur.setCompte(vainqueur.getCompte()+gain+moduloPotReste);
				listeJoueursTour.remove(listeJoueursTour.indexOf(vainqueur));
				for (Joueur joueur : listeJoueursTour)
					joueur.setCompte(joueur.getCompte()+quotientPotReste);
			}
			
			for (Joueur joueur : listeJoueursTour) {
				if (joueur.getCompte() == 0)
					listeJoueursPartie.remove(listeJoueursPartie.indexOf(joueur));
			}
		}
		else
			listeJoueursTour.get(0).setCompte(listeJoueursTour.get(0).getCompte()+table.getPot());
	}
}
