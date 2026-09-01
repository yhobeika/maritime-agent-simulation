// HOBEIKA Youssef

// Classe abstraite intermédiaire : un Marin est un Agent qui a un nom et une vitesse
// Niveau 2 dans la hiérarchie (Agent -> Marin -> Pirate/GardeCote)
public abstract class Marin extends Agent {
    protected String nom;
    protected int vitesse; // distance maximale parcourable en 1 étape

    // Constructeur : super doit être la PREMIÈRE instruction du constructeur
    public Marin(String nom, int vitesse, Terrain terrain, int lig, int col) {
        super(terrain, lig, col); // appel du constructeur parent en premier
        this.nom = nom;
        this.vitesse = vitesse;
    }

    public String getNom() { return nom; }
    public int getVitesse() { return vitesse; }

    public String toString() {
        return nom + " en (" + ligne + ", " + colonne + ")";
    }
}
