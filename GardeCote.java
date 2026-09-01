// HOBEIKA Youssef

// Un garde-côte patrouille la mer et arrête les pirates qu'il croise
public class GardeCote extends Marin {
    // Nombre de pirates arrêtés par ce garde-côte (statistique)
    private int nbArrestations;
    // Liste des pirates à surveiller (le garde-côte la connaît dès sa création)
    private java.util.ArrayList<Pirate> pirates;

    public GardeCote(String nom, Terrain terrain, int lig, int col, java.util.ArrayList<Pirate> pirates) {
        super(nom, 3, terrain, lig, col);
        this.nbArrestations = 0;
        this.pirates = pirates;
    }

    public int getNbArrestations() { return nbArrestations; }

    // Le garde-côte cherche le pirate libre le plus proche et se rapproche pour l'arrêter
    public void agir() {
        // On cherche le pirate non encore arrêté le plus proche
        Pirate cible = null;
        double meilleureDist = Double.MAX_VALUE;
        for (Pirate p : pirates) {
            if (!p.estArrete()) {
                double d = distance(p.getLigne(), p.getColonne());
                if (d < meilleureDist) {
                    meilleureDist = d;
                    cible = p;
                }
            }
        }

        // Si tous les pirates sont déjà arrêtés, le garde-côte reste
        if (cible == null) {
            int[] alea = Utils.caseAleatoireProche(ligne, colonne, vitesse, terrain);
            seDeplacer(alea[0], alea[1]);
            System.out.println(nom + " patrouille en (" + ligne + ", " + colonne + ")");
            return;
        }

        // Si le garde-côte est sur la même case que le pirate, il l'arrête
        if (ligne == cible.getLigne() && colonne == cible.getColonne()) {
            cible.arreter();
            nbArrestations++;
            System.out.println(nom + " arrête " + cible.getNom() + " ! (total arrestations = " + nbArrestations + ")");
        } else {
            // Sinon, il se rapproche du pirate au max de sa vitesse
            int nouvLig = ligne + Integer.signum(cible.getLigne() - ligne) * Math.min(vitesse, Math.abs(cible.getLigne() - ligne));
            int nouvCol = colonne + Integer.signum(cible.getColonne() - colonne) * Math.min(vitesse, Math.abs(cible.getColonne() - colonne));
            seDeplacer(nouvLig, nouvCol);
            System.out.println(nom + " poursuit " + cible.getNom() + ", position = (" + ligne + ", " + colonne + ")");
        }
    }

    public String toString() {
        return nom + " [GardeCote] en (" + ligne + ", " + colonne + "), arrestations = " + nbArrestations;
    }
}
