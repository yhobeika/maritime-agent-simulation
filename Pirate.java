// HOBEIKA Youssef

// Un pirate cherche les coffres pour ramasser de l'or, et fuit les garde-côtes
public class Pirate extends Marin {
    // Quantité d'or accumulée par le pirate
    private int or;
    // Indique si le pirate a été arrêté par un garde-côte
    private boolean arrete;

    // Constructeur : un pirate démarre avec 0 or et libre
    public Pirate(String nom, Terrain terrain, int lig, int col) {
        super(nom, 2, terrain, lig, col); // un pirate a une vitesse de 2 cases
        this.or = 0;
        this.arrete = false;
    }

    public int getOr() { return or; }
    public boolean estArrete() { return arrete; }

    // Le garde-côte appelle cette méthode pour arrêter le pirate
    public void arreter() {
        this.arrete = true;
    }

    // le pirate cherche le coffre le plus proche et essaie de le ramasser ; sinon il bouge au hasard
    public void agir() {
        // Si déjà arrêté, le pirate ne fait plus rien
        if (arrete) {
            System.out.println(nom + " est arrêté, il ne bouge plus.");
            return;
        }

        // On cherche le coffre le plus proche sur le terrain
        Ressource cibleCoffre = null;
        double meilleureDist = Double.MAX_VALUE;
        // On parcourt toutes les ressources du terrain pour trouver le coffre le plus proche
        for (Ressource r : terrain.lesRessources()) {
            if (r.type.equals("Coffre")) {
                double d = distance(r.getLigne(), r.getColonne());
                if (d < meilleureDist) {
                    meilleureDist = d;
                    cibleCoffre = r;
                }
            }
        }

        // S'il n'y a aucun coffre, déplacement aléatoire
        if (cibleCoffre == null) {
            int[] cible = Utils.caseAleatoireProche(ligne, colonne, vitesse, terrain);
            seDeplacer(cible[0], cible[1]);
            System.out.println(nom + " erre vers (" + ligne + ", " + colonne + ")");
            return;
        }

        // Si le pirate est sur la case du coffre, il le ramasse
        if (ligne == cibleCoffre.getLigne() && colonne == cibleCoffre.getColonne()) {
            int gain = cibleCoffre.getQuantite();
            or += gain;
            // On retire le coffre du terrain
            terrain.viderCase(ligne, colonne);
            System.out.println(nom + " ramasse un coffre et gagne " + gain + " pièces d'or ! Total = " + or);
        } else {
            // Sinon, on se rapproche du coffre d'au plus 'vitesse' cases
            int nouvLig = ligne + Integer.signum(cibleCoffre.getLigne() - ligne) * Math.min(vitesse, Math.abs(cibleCoffre.getLigne() - ligne));
            int nouvCol = colonne + Integer.signum(cibleCoffre.getColonne() - colonne) * Math.min(vitesse, Math.abs(cibleCoffre.getColonne() - colonne));
            seDeplacer(nouvLig, nouvCol);
            System.out.println(nom + " se dirige vers le coffre, position = (" + ligne + ", " + colonne + ")");
        }
    }

    public String toString() {
        return nom + " [Pirate] en (" + ligne + ", " + colonne + "), or = " + or + (arrete ? " [ARRETE]" : "");
    }
}
