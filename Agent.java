// HOBEIKA Youssef

// Classe abstraite qui représente un agent générique sur le terrain
// Les agents ne sont PAS mémorisés sur le terrain, mais ils connaissent le terrain sur lequel ils évoluent
public abstract class Agent implements Deplacement {
    // Le terrain sur lequel l'agent évolue (donné à la création)
    protected Terrain terrain;
    // Position de l'agent : numéro de ligne et numéro de colonne
    protected int ligne;
    protected int colonne;

    public Agent(Terrain terrain, int lig, int col) {
        this.terrain = terrain;
        this.ligne = lig;
        this.colonne = col;
    }

    // Distance euclidienne entre l'agent et la case (lig, col)
    public double distance(int lig, int col) {
        int dLig = this.ligne - lig;
        int dCol = this.colonne - col;
        return Math.sqrt(dLig * dLig + dCol * dCol);
    }

    // Déplace l'agent à la position (lig, col) si elle est valide sur le terrain
    public void seDeplacer(int lig, int col) {
        if (terrain.sontValides(lig, col)) {
            this.ligne = lig;
            this.colonne = col;
        }
    }

    // Méthode abstraite : chaque agent agit différemment (pirate, garde-côte...)
    public abstract void agir();

    public int getLigne() { return ligne; }
    public int getColonne() { return colonne; }
    public Terrain getTerrain() { return terrain; }

    public String toString() {
        return "Agent en (" + ligne + ", " + colonne + ")";
    }
}
