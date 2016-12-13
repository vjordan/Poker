package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Tour {
	/**
	 * montant de la petite blinde
	 */
	private final static int VALEUR_PETITE_BLINDE = 10;
	/**
	 * montant de la grosse blinde
	 */
	private final static int VALEUR_GROSSE_BLINDE = 20;
	
	/**
	 * nombre de tours jou�s durant une partie
	 */
	private static int nbTours;
	/**
	 * table pour le tour
	 */
	private Table table = new Table();
	/**
	 * nombre de joueurs pr�sents pendant le tour
	 */
	private int nbJoueursTour;
	/**
	 * liste des joueurs du tour
	 */
	private ArrayList<Joueur> listeJoueursTour = new ArrayList<Joueur>();
	/**
	 * paquet de cartes
	 */
	private ArrayList<Carte> paquetCartes = new ArrayList<Carte>();
	/**
	 * joueur qui occupe le r�le de donneur pour le tour
	 */
	private Joueur donneur;
	/**
	 * joueur qui verse la petite blind au d�but du tour
	 */
	private Joueur petiteBlind;
	/**
	 * joueur qui verse la grosse blinde au d�but du tour
	 */
	private Joueur grosseBlind;
	/**
	 * scanner pour lire les entr�es clavier ( cha�nes de caract�re )
	 */
	private Scanner sc = new Scanner(System.in);
	/**
	 * scanner pour lire les entr�es clavier ( nombres entiers )
	 */
	private Scanner sc2 = new Scanner(System.in);
	
	/**
	 * D�roulement d'un tour
	 * @param listeJoueursPartie : liste des joueurs toujours pr�sents dans la partie, et donc qui n'ont pas �t� �limin�s
	 * @param listeJoueursBase : liste initiale des joueurs au d�but de la partie
	 */
	public Tour(ArrayList<Joueur> listeJoueursPartie, ArrayList<Joueur> listeJoueursBase) {
		nbTours++;
		table.setPot(table.getPot()+VALEUR_PETITE_BLINDE+VALEUR_GROSSE_BLINDE);
		
		for (int i=0; i<listeJoueursPartie.size(); i++) {
			listeJoueursTour.add(listeJoueursPartie.get(i));
		}
		nbJoueursTour = listeJoueursTour.size();
		
		tourEntete();

		donneur(listeJoueursBase);
		petiteBlind();
		grosseBlind();
		
		constitutionPaquetCartes();
		melangePaquetCartes();
		distributionCartes();
		
		afficherInfosTour();	
		preFlop(listeJoueursPartie);
	}

	/**
	 * Affichage du nombre de tours d�j� jou�. Permet de distinguer chaque tour
	 */
	public void tourEntete() {
		System.out.println("---------------------------");
		System.out.println("TOUR " + nbTours);
		System.out.println("---------------------------");
		System.out.println();
	}
	
	/**
	 * Attribution du donneur
	 * @param listeJoueursBase : liste initiale des joueurs de la partie
	 */
	public void donneur(ArrayList<Joueur> listeJoueursBase) {
		Joueur donneurBase = null;
		int i = 0;
		
		// on r�cup�re le joueur qui doit �tre le donneur du tour dans la liste de base
		for (Joueur joueur : listeJoueursBase) {
			if (joueur.isEstDonneur() == true) {
				donneurBase = joueur;
				joueur.setEstDonneur(false);
				break;
			}
		}
		int posDonneurBase = listeJoueursBase.indexOf(donneurBase);
		int posDonneur = listeJoueursTour.indexOf(donneurBase);
		// si le joueur qui devait �tre donneur n'est plus pr�sent dans la partie, on prend le suivant de la liste de base et on regarde s'il est toujours en jeu
		// si oui, alors on a trouv�, sinon on continue de prendre le suivant et ainsi de suite
		while (posDonneur == -1) {
			i++;
			if ((posDonneurBase+i) < listeJoueursBase.size())
				posDonneur = listeJoueursTour.indexOf(listeJoueursBase.get(posDonneurBase+i));
			else
				posDonneur = listeJoueursTour.indexOf(listeJoueursBase.get(posDonneurBase+i-listeJoueursBase.size()));
		}
		
		donneur = listeJoueursTour.get(posDonneur);
		
		// on pr�voit qui sera le donneur au prochain tour
		if (listeJoueursBase.indexOf(donneur)+1 < listeJoueursBase.size())
			listeJoueursBase.get(listeJoueursBase.indexOf(donneur)+1).setEstDonneur(true);
		else
			listeJoueursBase.get(listeJoueursBase.indexOf(donneur)+1-listeJoueursBase.size()).setEstDonneur(true);
		
		System.out.println("Donneur : " + donneur.getNom());
	}
	
	/**
	 * Attribution de la petite blind
	 */
	public void petiteBlind() {
		// s'il y a plus de 2 joueurs dans la partie, la petite blind est le joueur qui suit le donneur
		if (nbJoueursTour > 2) {
			int posDonneur = listeJoueursTour.indexOf(donneur);
			int posPetiteBlind = posDonneur+1;
			if (posPetiteBlind > nbJoueursTour-1)
				posPetiteBlind -= nbJoueursTour;
			petiteBlind = listeJoueursTour.get(posPetiteBlind);
		}
		// si on n'a plus que 2 joueurs, la petite blind est le donneur
		else
			petiteBlind = donneur;
		
		System.out.println("Petite blind : " + petiteBlind.getNom());
		if (VALEUR_PETITE_BLINDE < petiteBlind.getCompte())
			petiteBlind.setCompte(petiteBlind.getCompte()-VALEUR_PETITE_BLINDE);
		// on g�re le cas o� le joueur n'aurait pas assez de jetons pour payer la petite blind
		else {
			table.setPot(table.getPot()-(VALEUR_PETITE_BLINDE - petiteBlind.getCompte()));
			petiteBlind.setCompte(0);
		}
	}
	
	/**
	 * Attribution de la grosseBlind
	 */
	public void grosseBlind() {
		int posGrosseBlind;
		
		// s'il y a plus de 2 joueurs dans la partie, la grosse blind est le joueur qui suit la petite blind
		if (nbJoueursTour > 2) {
			int posPetiteBlind = listeJoueursTour.indexOf(petiteBlind);
			posGrosseBlind = posPetiteBlind+1;
			if (posGrosseBlind > nbJoueursTour-1)
				posGrosseBlind -= nbJoueursTour;
		}
		// s'il n'y a que 2 joueurs, la grosse blind est celui qui n'est pas le donneur
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
		// on g�re le cas o� le joueur ne pourrait pas payer la grosse blinde
		else {
			table.setPot(table.getPot()-(VALEUR_GROSSE_BLINDE - grosseBlind.getCompte()));
			grosseBlind.setCompte(0);
		}
	}
	
	/**
	 * Affiche les informations utiles pendant le tour ( tapis des joueurs, pot de la table, main des joueurs )
	 */
	public void afficherInfosTour() {
		for (Joueur joueur : listeJoueursTour)
			System.out.println(joueur.toString());
		System.out.println();
		System.out.println(table.toString());
		System.out.println();
		for (Joueur joueur : listeJoueursTour)
			joueur.affichageMain();
		System.out.println();
	}
	
	/**
	 * Constitution du paquet de cartes en cr�ant chaque carte, gr�ce aux �num�rations
	 */
	public void constitutionPaquetCartes() {
		for (CarteCouleur couleur : CarteCouleur.values())
			for (CarteRang rang : CarteRang.values())
				paquetCartes.add(new Carte(couleur,rang));
	}
	
	/**
	 * M�lange du paquet de cartes
	 */
	public void melangePaquetCartes() {
		long seed = System.nanoTime();
		
		// on m�lange le paquet de cartes al�atoirement
		Collections.shuffle(paquetCartes, new Random(seed));
	}
	
	/**
	 * Distribution des mains aux joueurs ( 2 cartes chacun )
	 */
	public void distributionCartes() {
		for (Joueur joueur : listeJoueursTour)
			joueur.main(paquetCartes);
		System.out.println();
	}
	
	/**
	 * D�roulement du pr�flop
	 * @param listeJoueursPartie : liste des joueurs encore pr�sents dans la partie
	 */
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
			joueur.setMiseTotale(0);
		petiteBlind.setMise(VALEUR_PETITE_BLINDE);
		grosseBlind.setMise(VALEUR_GROSSE_BLINDE);
		
		//on constitue la liste des joueurs du pr� flop, afin de respecter l'ordre de parole de chacun
		// ( pour le pr�flop, c'est celui � gauche de la grosse blind qui parle en premier )
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
				if ((finPreFlop == false) && (finPreFlopBis == false) && (nbJoueursTour > 1) && (joueur.getCompte()>0)) {
					// les messages affich�s au joueur s'adaptent selon sa situation dans le tour
					do {
						// si le joueur a assez pour suivre
						if (joueur.getCompte() > miseSup-joueur.getMise()) {
							//si la mise du joueur n'est pas �gal � la mise sup�rieure
							if (miseSup-joueur.getMise() != 0)
								System.out.println(joueur.getNom() + " : se coucher(1), suivre avec " + (miseSup-joueur.getMise()) + " (2), relancer(3), tapis(4) ?");
							//si la mise du joueur est d�j� �gal � la mise sup�rieure
							else
								System.out.println(joueur.getNom() + " : se coucher(1), check(2), miser(3), tapis(4) ?");
						}
						// si le joueur n'a pas assez pour suivre
						else
							System.out.println(joueur.getNom() + " : se coucher(1), suivre avec " + joueur.getCompte() + " (2), relancer(3), tapis(4) ?");
						System.out.println("Tapis = " + joueur.getCompte() + " | Carte 1 : " + joueur.getMain()[0].toString() + " / Carte 2 : " + joueur.getMain()[1].toString());
						repChoix = sc.nextLine();
					} while ((!(repChoix.equals("1")))&&(!(repChoix.equals("2")))&&(!(repChoix.equals("3")))&&(!(repChoix.equals("4"))));
					switch (repChoix) {
					// si le joueur se couche
					case "1" :
						joueursSupprimes.add(joueur);
						majListeJoueursTour(joueur);
						break;
					// si le joueur suit ( ou check )
					case "2" :
						// si le joueur a assez pour suivre
						if (joueur.getCompte() > miseSup-joueur.getMise()) {
							joueur.setCompte(joueur.getCompte()-(miseSup-joueur.getMise()));
							table.setPot(table.getPot()+(miseSup-joueur.getMise()));
							joueur.setMise(miseSup);
						}
						// si le joueur n'a pas assez pour suivre
						else {
							System.out.println("Impossible de suivre ! Vous �tes � tapis.");
							tapis = joueur.getCompte();
							joueur.setCompte(0);
							table.setPot(table.getPot()+tapis);
							joueur.setMise(joueur.getMise()+tapis);
						}
						break;
					// si le joueur relance ( ou mise s'il a d�j� suivi la plus haute mise )
					case "3" :
						// si le joueur peut relancer d'au moins le double de la mise pr�c�dente
						if (miseSup < (joueur.getCompte()-(miseSup-joueur.getMise()))) {
							do {
								// si le joueur avait d�j� suivi la mise pr�c�dente, il peut miser
								if (miseSup-joueur.getMise() == 0)
									System.out.println("Combien voulez-vous miser ? (entre " + miseSup + " et " + (joueur.getCompte()-(miseSup-joueur.getMise())) + ")");
								// si le joueur n'a pas encore suivi, il peut relancer
								else
									System.out.println("De combien voulez-vous relancer ? (entre " + miseSup + " et " + (joueur.getCompte()-(miseSup-joueur.getMise())) + ")");
								repRelance = sc2.nextInt();
							} while ((repRelance < miseSup) || (repRelance > (joueur.getCompte()-(miseSup-joueur.getMise()))));
							joueur.setCompte(joueur.getCompte()-(miseSup-joueur.getMise())-repRelance);
							table.setPot(table.getPot()+(miseSup-joueur.getMise())+repRelance);
							miseSup = miseSup + repRelance;
							joueur.setMise(miseSup);
						}
						// si le joueur ne peut pas relancer d'au moins le double de la mise pr�c�dente
						else {
							// si le joueur a quand m�me assez pour suivre
							if (miseSup-joueur.getMise() <= joueur.getCompte()) {
								System.out.println("Impossible de relancer ! Vous avez suivi.");
								joueur.setCompte(joueur.getCompte()-(miseSup-joueur.getMise()));
								table.setPot(table.getPot()+(miseSup-joueur.getMise()));
								joueur.setMise(miseSup);
							}
							// si le joueur ne peut m�me pas suivre
							else {
								System.out.println("Impossible de relancer ! Vous �tes � tapis.");
								tapis = joueur.getCompte();
								joueur.setCompte(0);
								table.setPot(table.getPot()+tapis);
								joueur.setMise(joueur.getMise()+tapis);
							}
						}
						break;
					// si le joueur d�cide de mettre tapis
					case "4" :
						tapis = joueur.getCompte();
						// si son tapis correspond � une relance
						if (miseSup < (tapis-(miseSup-joueur.getMise()))) {
							joueur.setCompte(0);
							table.setPot(table.getPot()+tapis);
							miseSup = miseSup + (tapis-(miseSup-joueur.getMise()));
							joueur.setMise(miseSup);
						}
						// si son tapis ne correspond pas v�ritablement � une relance (x2)
						else {
							// si suivi <= tapis <= relance
							if (tapis > miseSup-joueur.getMise()) {
								joueur.setCompte(0);
								table.setPot(table.getPot()+tapis);
								miseSup = miseSup + (tapis-miseSup);
								joueur.setMise(miseSup);
							}
							// si le tapis ne correspond pas � un suivi
							else {
								joueur.setCompte(0);
								table.setPot(table.getPot()+tapis);
								joueur.setMise(joueur.getMise()+tapis);
							}
						}	
						break;
					}
					// d�s que tout le monde a parl� au moins une fois ( un tour de table a �t� effectu� )
					// on v�rifie apr�s que chaque joueur ait parl� si on doit continuer le pr�flop
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
			// � la fin du premier tour de table, on regarde si on doit continuer le pr�flop
			if (nbToursPreFlop == 1) {
				finPreFlop = finTourMise(miseSup);
				finPreFlopBis = finTourMiseBis(miseSup);
			}	
		} while ((finPreFlop == false) && (finPreFlopBis == false) && (nbJoueursTour > 1));
		
		majMiseJoueursTour();
		
		// si plus d'un joueur sont encore pr�sents apr�s le pr�flop, on passe au flop
		if (nbJoueursTour > 1) {
			afficherInfosTour();
			flop(listeJoueursPartie);
		}
		// sinon on termine le tour
		else
			finTour(listeJoueursPartie);
	}
	
	/**
	 * D�roulement du tour de mises "flop"
	 * @param listeJoueursPartie : liste des joueurs encore pr�sents dans la partie
	 */
	public void flop(ArrayList<Joueur> listeJoueursPartie) {
		table.flop(paquetCartes);		
		deroulementTourMise(listeJoueursPartie);
		
		//s'il n'y a plus qu'un joueur, on va directement � la fin du tour, sinon on continue � la turn
		if (nbJoueursTour > 1) {
			afficherInfosTour();
			turn(listeJoueursPartie);
		}
		else
			finTour(listeJoueursPartie);
	}

	/**
	 * D�roulement du tour de mises "turn"
	 * @param listeJoueursPartie : liste des joueurs encore pr�sents dans la partie
	 */
	public void turn(ArrayList<Joueur> listeJoueursPartie) {
		table.turn(paquetCartes);		
		deroulementTourMise(listeJoueursPartie);
		
		//s'il n'y a plus qu'un joueur, on va directement � la fin du tour, sinon on continue � la river
		if (nbJoueursTour > 1) {
			afficherInfosTour();
			river(listeJoueursPartie);
		}
		else
			finTour(listeJoueursPartie);
	}
	
	/**
	 * D�roulement du tour de mises "river"
	 * @param listeJoueursPartie : liste des joueurs encore pr�sents dans la partie
	 */
	public void river(ArrayList<Joueur> listeJoueursPartie) {
		table.river(paquetCartes);	
		deroulementTourMise(listeJoueursPartie);
		
		afficherInfosTour();
		finTour(listeJoueursPartie);
	}
	
	/**
	 * D�roulement des tours de mises "flop", "turn", "river"
	 * @param listeJoueursPartie : liste des joueurs encore pr�sents dans la partie
	 */
	public void deroulementTourMise(ArrayList<Joueur> listeJoueursPartie) {
		String repChoix;
		int indicePos, repRelance, posPetiteBlindPartie, tapis;
		
		boolean finTourMise = false;
		boolean finTourMiseBis = false;
		int miseSup = 0;
		int nbToursMise = 0;
		int incr = 0;
		int nbJoueursParle = 0;
		
		ArrayList<Joueur> listeJoueursTourMise = new ArrayList<Joueur>();
		
		reinitialisationMises();
		
		// on red�finit la position de la petite blind si n�cessaire ( si la petite blind s'est couch� durant un tour de mises ant�rieure par exemple )
		int posPetiteBlind = listeJoueursTour.indexOf(petiteBlind);
		while (posPetiteBlind == -1) {
			incr++;
			posPetiteBlindPartie = listeJoueursPartie.indexOf(petiteBlind);
			if ((posPetiteBlindPartie+incr) < listeJoueursPartie.size())
				posPetiteBlind = listeJoueursTour.indexOf(listeJoueursPartie.get(posPetiteBlindPartie+incr));
			else
				posPetiteBlind = listeJoueursTour.indexOf(listeJoueursPartie.get(posPetiteBlindPartie+incr-listeJoueursPartie.size()));
		}
		//on constitue la liste des joueurs du tour de mises, afin de respecter l'ordre de parole de chacun
		// ( pour flop, turn et river, c'est celui � gauche du donneur qui parle en premier )
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
				// les messages affich�s au joueur s'adaptent selon sa situation dans le tour
				if ((finTourMise == false) && (finTourMiseBis == false) && (nbJoueursTour > 1) && (joueur.getCompte()>0)) {
					// si le joueur n'est pas le premier � parler pour le tour
					if (nbJoueursParle > 1) {
						do {
							// si le joueur a assez pour suivre
							if (joueur.getCompte() > miseSup-joueur.getMise()) {
								//si la mise du joueur n'est pas �gal � la mise sup�rieure
								if (miseSup-joueur.getMise() != 0)
									System.out.println(joueur.getNom() + " : se coucher(1), suivre avec " + (miseSup-joueur.getMise()) + " (2), relancer(3), tapis(4) ?");
								//si la mise du joueur est d�j� �gal � la mise sup�rieure
								else
									System.out.println(joueur.getNom() + " : se coucher(1), check(2), miser(3), tapis(4) ?");
							}
							// si le joueur n'a pas assez pour suivre
							else
								System.out.println(joueur.getNom() + " : se coucher(1), suivre avec " + joueur.getCompte() + " (2), relancer(3), tapis(4) ?");
							System.out.println("Tapis = " + joueur.getCompte() + " | Carte 1 : " + joueur.getMain()[0].toString() + " / Carte 2 : " + joueur.getMain()[1].toString());
							repChoix = sc.nextLine();
						} while ((!(repChoix.equals("1")))&&(!(repChoix.equals("2")))&&(!(repChoix.equals("3")))&&(!(repChoix.equals("4"))));
					}
					// si le joueur est le premier � parler pour le tour, il ne peut pas se coucher
					else {
						do {
							System.out.println(joueur.getNom() + " : check(2), miser(3), tapis(4) ?");
							System.out.println("Tapis = " + joueur.getCompte() + " | Carte 1 : " + joueur.getMain()[0].toString() + " / Carte 2 : " + joueur.getMain()[1].toString());
							repChoix = sc.nextLine();
						} while ((!(repChoix.equals("2")))&&(!(repChoix.equals("3")))&&(!(repChoix.equals("4"))));
					}
					switch (repChoix) {
					// si le joueur se couche
					case "1" :
						joueursSupprimes.add(joueur);
						majListeJoueursTour(joueur);
						break;
					// si le joueur suit ( ou check )
					case "2" :
						// si le joueur a assez pour suivre
						if (joueur.getCompte() > miseSup-joueur.getMise()) {
							joueur.setCompte(joueur.getCompte()-(miseSup-joueur.getMise()));
							table.setPot(table.getPot()+(miseSup-joueur.getMise()));
							joueur.setMise(miseSup);
						}
						// si le joueur n'a pas assez pour suivre
						else {
							System.out.println("Impossible de suivre ! Vous �tes � tapis.");
							tapis = joueur.getCompte();
							joueur.setCompte(0);
							table.setPot(table.getPot()+tapis);
							joueur.setMise(joueur.getMise()+tapis);
						}
						break;
					// si le joueur relance ( ou mise s'il a d�j� suivi la plus haute mise )
					case "3" :
						// si le joueur peut relancer d'au moins le double de la mise pr�c�dente
						if (miseSup < (joueur.getCompte()-(miseSup-joueur.getMise()))) {
							// si la mise maximale est sup�rieure � z�ro, le joueur va relancer ou miser
							if (miseSup > 0) {
								do {
									// si le joueur avait d�j� suivi la mise pr�c�dente, il peut miser
									if (miseSup-joueur.getMise() == 0)
										System.out.println("Combien voulez-vous miser ? (entre " + miseSup + " et " + (joueur.getCompte()-(miseSup-joueur.getMise())) + ")");
									// si le joueur n'a pas encore suivi, il peut relancer
									else
										System.out.println("De combien voulez-vous relancer ? (entre " + miseSup + " et " + (joueur.getCompte()-(miseSup-joueur.getMise())) + ")");
									repRelance = sc2.nextInt();
								} while ((repRelance < miseSup) || (repRelance > (joueur.getCompte()-(miseSup-joueur.getMise()))));
							}
							//si la mise maximale vaut z�ro, le joueur ne peut que miser
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
						// si le joueur ne peut pas relancer d'au moins le double de la mise pr�c�dente
						else {
							// si le joueur a quand m�me assez pour suivre
							if (miseSup-joueur.getMise() <= joueur.getCompte()) {
								System.out.println("Impossible de relancer ! Vous avez suivi.");
								joueur.setCompte(joueur.getCompte()-(miseSup-joueur.getMise()));
								table.setPot(table.getPot()+(miseSup-joueur.getMise()));
								joueur.setMise(miseSup);
							}
							// si le joueur ne peut m�me pas suivre
							else {
								System.out.println("Impossible de relancer ! Vous �tes � tapis.");
								tapis = joueur.getCompte();
								joueur.setCompte(0);
								table.setPot(table.getPot()+tapis);
								joueur.setMise(joueur.getMise()+tapis);
							}
						}
						break;
					// si le joueur d�cide de mettre tapis
					case "4" :
						tapis = joueur.getCompte();
						// si son tapis correspond � une relance
						if (miseSup < (tapis-(miseSup-joueur.getMise()))) {
							joueur.setCompte(0);
							table.setPot(table.getPot()+tapis);
							miseSup = miseSup + (tapis-(miseSup-joueur.getMise()));
							joueur.setMise(miseSup);
						}
						// si son tapis ne correspond pas v�ritablement � une relance (x2)
						else {
							// si suivi <= tapis <= relance
							if (tapis > miseSup-joueur.getMise()) {
								joueur.setCompte(0);
								table.setPot(table.getPot()+tapis);
								miseSup = miseSup + (tapis-miseSup);
								joueur.setMise(miseSup);
							}
							// si le tapis ne correspond pas � un suivi
							else {
								joueur.setCompte(0);
								table.setPot(table.getPot()+tapis);
								joueur.setMise(joueur.getMise()+tapis);
							}
						}	
						break;
					}
					// d�s que tout le monde a parl� au moins une fois ( un tour de table a �t� effectu� )
					// on v�rifie apr�s que chaque joueur ait parl� si on doit continuer le tour de mises
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
			// � la fin du premier tour de table, on regarde si on doit continuer le tour de mises
			if (nbToursMise == 1) {
				finTourMise = finTourMise(miseSup);
				finTourMiseBis = finTourMiseBis(miseSup);
			}
		} while ((finTourMise == false) && (finTourMiseBis == false) && (nbJoueursTour > 1));
		
		majMiseJoueursTour();
	}
	
	/**
	 * Indique la fin d'un tour de mise si chaque joueur a suivi la plus haute mise
	 * @param miseSup : meilleure mise du tour de mises
	 * @return true si tous les joueurs ont suivi la plus haute mise, donc le tour de mises peut s'arr�ter
	 */
	public boolean finTourMise(int miseSup) {
		// pour chaque joueur, on v�rifie s'il a suivi la meilleure mise du tour de mises en question
		// si ce n'est pas le cas, on regarde si ce n'est pas parce qu'il est � tapis, et donc qu'il ne pouvait pas suivre
		for (Joueur joueur : listeJoueursTour) {
			if (joueur.getMise() != miseSup) {
				if (joueur.getCompte() != 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Indique la fin d'un tour de mise s'il y a moins de 2 joueurs encore en jeu
	 * @param miseSup : meilleure mise du tour de mises
	 * @return true s'il n'y a plus que 0 ou 1 joueur encore pr�sent au tour de mises
	 */
	public boolean finTourMiseBis(int miseSup) {
		int nbJoueursTapis = 0;
		int nbJoueursPasJoue = 0;
		
		// pour chaque joueur, on v�rifie s'il est � tapis ou s'il n'a pas encore parl�
		for (Joueur joueur : listeJoueursTour) {
			if (joueur.getCompte() == 0)
				nbJoueursTapis++;
			else {
				if (joueur.getMise() < miseSup)
					nbJoueursPasJoue++;
			}
		}
		
		//si tout le monde est � tapis ( sauf au plus 1 joueur ) et si tout le monde a parl� pendant le tour
		// alors on peut arr�ter le tour
		if ((nbJoueursTapis >= (nbJoueursTour-1)) && (nbJoueursPasJoue == 0))
			return true;
		return false;
	}
	
	/**
	 * mise � jour de la liste des joueurs encore pr�sents pendant le tour ( suppression d'un joueur qui se couche )
	 * @param joueur : joueur supprim� de la liste des joueurs du tour
	 */
	public void majListeJoueursTour(Joueur joueur) {
		listeJoueursTour.remove(listeJoueursTour.indexOf(joueur));
		nbJoueursTour--;
	}
	
	/**
	 * r�initialisation des mises � chaque tour de mises
	 */
	public void reinitialisationMises() {
		for (Joueur joueur : listeJoueursTour)
			joueur.setMise(0);
	}
	
	/**
	 * mise � jour de la mise totale d'un joueur pendant un tour ( addition des mises plac�es � chaque tour de mises )
	 */
	public void majMiseJoueursTour() {
		for (Joueur joueur : listeJoueursTour)
			joueur.setMiseTotale(joueur.getMiseTotale() + joueur.getMise());
	}
	
	/**
	 * Gestion de la fin du tour
	 * @param listeJoueursPartie : liste des joueurs encore pr�sents dans la partie
	 */
	public void finTour(ArrayList<Joueur> listeJoueursPartie) {
		int miseMax = 0;
		
		System.out.println("Cartes table :");
		table.affichageCartesTable();
		System.out.println();
		
		// si plusieurs joueurs sont encore pr�sents apr�s le dernier tour de mises ( river )
		if (nbJoueursTour > 1) {
			CombinaisonCalculateur meilleureCombinaison = new CombinaisonCalculateur(listeJoueursTour, table);
			ArrayList<Joueur> listeVainqueurs = meilleureCombinaison.getVainqueur();
			int nbVainqueurs = listeVainqueurs.size();
			
			// on d�finit la mise totale maximale pour le tour ( chaque joueur devrait l'avoir mis�, sauf dans le cas o� il �tait � tapis et qu'il ne pouvait pas suivre )
			for (Joueur joueur : listeJoueursTour) {
				if (joueur.getMiseTotale() > miseMax)
					miseMax = joueur.getMiseTotale();
			}
			
			// si on a un unique vainqueur
			if (nbVainqueurs == 1) {
				Joueur vainqueur = listeVainqueurs.get(0);
				// on v�rifie qu'il a mis� le montant de la mise maximale
				if (vainqueur.getMiseTotale() == miseMax)
					vainqueur.setCompte(vainqueur.getCompte()+table.getPot());
				// si ce n'est pas le cas, le reste du pot non allou� au vainqueur est partag� entre les joueurs perdants
				else {
					int gain = listeJoueursTour.size()*vainqueur.getMiseTotale();
					int potReste = table.getPot()-gain;
					int quotientPotReste = potReste / (listeJoueursTour.size()-1);
					int moduloPotReste = potReste - (quotientPotReste*(listeJoueursTour.size()-1));
					
					vainqueur.setCompte(vainqueur.getCompte()+gain+moduloPotReste);
					listeJoueursTour.remove(listeJoueursTour.indexOf(vainqueur));
					nbJoueursTour--;
					for (Joueur joueur : listeJoueursTour)
						joueur.setCompte(joueur.getCompte()+quotientPotReste);
				}
			}
			// si on a plusieurs vainqueurs, on divise le pot de mani�re � ce que chacun ait la m�me part
			else {
				int gainVainqueur = table.getPot()/nbVainqueurs;
				int potReste = table.getPot() - gainVainqueur*nbVainqueurs;
				for (Joueur vainqueur : listeVainqueurs) {
					vainqueur.setCompte(vainqueur.getCompte()+gainVainqueur);
					listeJoueursTour.remove(listeJoueursTour.indexOf(vainqueur));
					nbJoueursTour--;
				}
				listeVainqueurs.get(0).setCompte(listeVainqueurs.get(0).getCompte()+potReste);
			}
			
			// pour les joueurs ayant perdu le tour qui �tait � tapis, ils sont �limin�s de la partie
			for (Joueur joueur : listeJoueursTour) {
				if (joueur.getCompte() == 0)
					listeJoueursPartie.remove(listeJoueursPartie.indexOf(joueur));
			}
		}
		// si un seul joueur est pr�sent en fin de tour, on lui alloue directement le montant du pot de la table
		else
			listeJoueursTour.get(0).setCompte(listeJoueursTour.get(0).getCompte()+table.getPot());
	}
	
	/**
	 * setter pour "nbTours"
	 * @param nbTours : nombre de tours pour la partie
	 */
	public static void setNbTours(int nbTours) {
		Tour.nbTours = nbTours;
	}
}
