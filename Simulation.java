// HOBEIKA Youssef

import java.util.ArrayList;

// Organise tout le déroulement de la simulation
// Elle contient un terrain, une ArrayList d'agents et une ArrayList de ressources
// Implémentée en SINGLETON (une seule simulation existe à la fois)
public class Simulation {
    // INSTANCE unique de la classe (Singleton)
    private static Simulation instance = null;

    // Le terrain de la simulation
    private Terrain terrain;
    // ArrayList des agents (pirates + garde-côtes)
    private ArrayList<Agent> agents;
    // ArrayList des ressources (coffres + îles + courants)
    private ArrayList<Ressource> ressources;
    // Liste des pirates (utile pour les garde-côtes qui doivent les connaître)
    private ArrayList<Pirate> pirates;
    // Numéro de l'étape courante
    private int etape;

    // getInstance()
    private Simulation(int nbLignes, int nbColonnes, int nbCoffres, int nbIles, int nbCourants, int nbPirates, int nbGardes) {
        this.terrain = new Terrain(nbLignes, nbColonnes);
        this.agents = new ArrayList<Agent>();
        this.ressources = new ArrayList<Ressource>();
        this.pirates = new ArrayList<Pirate>();
        this.etape = 0;

        // (a) initialisation : on place les ressources sur le terrain
        initialiserRessources(nbCoffres, nbIles, nbCourants);
        // On crée les agents (pirates puis garde-côtes)
        initialiserAgents(nbPirates, nbGardes);
    }

    // Méthode statique pour récupérer (ou créer) l'instance unique
    public static Simulation getInstance(int nbLignes, int nbColonnes, int nbCoffres, int nbIles, int nbCourants, int nbPirates, int nbGardes) {
        if (instance == null) {
            instance = new Simulation(nbLignes, nbColonnes, nbCoffres, nbIles, nbCourants, nbPirates, nbGardes);
        }
        return instance;
    }

    // Méthode pour réinitialiser l'instance (utile si on veut lancer plusieurs simulations à la suite)
    public static void reset() {
        instance = null;
    }

    // Place aléatoirement les ressources sur le terrain
    private void initialiserRessources(int nbCoffres, int nbIles, int nbCourants) {
        // Placement des coffres (durée de vie = 8 étapes, quantité d'or aléatoire entre 5 et 25)
        for (int i = 0; i < nbCoffres; i++) {
            placerRessource(new Coffre(5 + Utils.ALEA.nextInt(21), 8));
        }
        // Placement des îles (taille = 1, statique)
        for (int i = 0; i < nbIles; i++) {
            placerRessource(new Ile(1));
        }
        // Placement des courants marins (force initiale aléatoire entre 3 et 7)
        for (int i = 0; i < nbCourants; i++) {
            placerRessource(new CourantMarin(3 + Utils.ALEA.nextInt(5)));
        }
    }

    // Essaie de placer une ressource sur une case vide aléatoire, si occupée on cherche une autre
    private void placerRessource(Ressource r) {
        for (int essai = 0; essai < 50; essai++) {
            int[] pos = Utils.caseAleatoire(terrain);
            if (terrain.caseEstVide(pos[0], pos[1])) {
                if (terrain.setCase(pos[0], pos[1], r)) {
                    ressources.add(r);
                    return;
                }
            }
        }
    }

    // Crée les agents et les place aléatoirement
    private void initialiserAgents(int nbPirates, int nbGardes) {
        // On crée d'abord les pirates
        for (int i = 0; i < nbPirates; i++) {
            int[] pos = positionLibrePourAgent();
            Pirate p = new Pirate("Pirate" + (i + 1), terrain, pos[0], pos[1]);
            pirates.add(p);
            agents.add(p);
        }
        // Puis les garde-côtes (ils ont besoin de la liste des pirates)
        for (int i = 0; i < nbGardes; i++) {
            int[] pos = positionLibrePourAgent();
            GardeCote g = new GardeCote("Garde" + (i + 1), terrain, pos[0], pos[1], pirates);
            agents.add(g);
        }
    }

    // Cherche une position valide qui n'est pas une île (pour pas spawner sur un obstacle)
    private int[] positionLibrePourAgent() {
        for (int essai = 0; essai < 50; essai++) {
            int[] pos = Utils.caseAleatoire(terrain);
            Ressource r = terrain.getCase(pos[0], pos[1]);
            // Une île bloque, mais un coffre ou un courant ne bloquent pas
            if (r == null || !r.type.equals("Ile")) {
                return pos;
            }
        }
        return new int[] { 0, 0 };
    }

    // (b) Réalise une étape de la simulation
    public void etapeSimulation() {
        etape++;
        System.out.println("\n========== Etape " + etape + " ==========");

        // Chaque agent réalise une action (avec gestion d'exception)
        for (Agent a : agents) {
            try {
                // On vérifie que l'agent est sur une position valide AVANT d'agir
                Utils.verifierPosition(terrain, a.getLigne(), a.getColonne());
                a.agir();
            } catch (HorsTerrainException e) {
                // Si l'agent est hors terrain, on logge l'erreur et on continue
                System.out.println("[Erreur] " + e.getMessage());
            }
        }

        // Mise à jour de chaque case du terrain : courants s'affaiblissent, coffres vieillissent
        majTerrain();

        // Affichage du terrain à la fin de l'étape
        terrain.afficher(6);
    }

    // Mise à jour des ressources évolutives à la fin de l'étape
    private void majTerrain() {
        // On utilise un ArrayList temporaire car on ne peut pas modifier 'ressources' pendant qu'on l'itère
        ArrayList<Ressource> aSupprimer = new ArrayList<Ressource>();
        for (Ressource r : ressources) {
            // Si une ressource a été retirée du terrain par un agent (ramassée), getLigne() == -1
            if (r.getLigne() == -1) {
                aSupprimer.add(r);
                continue;
            }
            if (r instanceof Coffre) {
                Coffre c = (Coffre) r;
                if (c.vieillir()) {
                    System.out.println("Un coffre disparaît en (" + c.getLigne() + ", " + c.getColonne() + ")");
                    terrain.viderCase(c.getLigne(), c.getColonne());
                    aSupprimer.add(c);
                }
            } else if (r instanceof CourantMarin) {
                CourantMarin co = (CourantMarin) r;
                if (co.affaiblir()) {
                    System.out.println("Un courant disparaît en (" + co.getLigne() + ", " + co.getColonne() + ")");
                    terrain.viderCase(co.getLigne(), co.getColonne());
                    aSupprimer.add(co);
                }
            }
        }
        ressources.removeAll(aSupprimer);
    }

    // (c) Lance la simulation jusqu'à un nombre max d'étapes ou jusqu'à ce que tous les pirates soient arrêtés
    public void lancer(int nbEtapesMax) {
        System.out.println(">>> Début de simulation : " + nbEtapesMax + " étapes max");
        System.out.println(terrain);
        terrain.afficher(6);

        for (int i = 0; i < nbEtapesMax; i++) {
            etapeSimulation();
            // Condition d'arrêt : tous les pirates sont arrêtés
            if (tousArretes()) {
                System.out.println("\n>>> Tous les pirates ont été arrêtés à l'étape " + etape);
                break;
            }
        }
        afficherStats();
    }

    // Vérifie si tous les pirates ont été arrêtés
    private boolean tousArretes() {
        for (Pirate p : pirates) {
            if (!p.estArrete()) return false;
        }
        return true;
    }

    // Affiche les statistiques finales
    public void afficherStats() {
        System.out.println("\n========== Statistiques finales ==========");
        int orTotal = 0;
        for (Pirate p : pirates) {
            System.out.println(p);
            orTotal += p.getOr();
        }
        for (Agent a : agents) {
            if (a instanceof GardeCote) {
                System.out.println(a);
            }
        }
        System.out.println("Or total amassé par les pirates : " + orTotal);
        System.out.println("Etapes réalisées : " + etape);
    }

    // Accesseurs (utiles pour TestSimulation)
    public Terrain getTerrain() { return terrain; }
    public ArrayList<Agent> getAgents() { return agents; }
    public ArrayList<Ressource> getRessources() { return ressources; }
}
