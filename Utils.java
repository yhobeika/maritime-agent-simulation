// HOBEIKA Youssef

// Ne contient QUE des attributs et méthodes statiques 
public class Utils {
    // Générateur aléatoire partagé par tout le projet
    public static final java.util.Random ALEA = new java.util.Random();

    // Constructeur privé pour empêcher l'instanciation
    private Utils() {}

    // Vérifie qu'une position (lig, col) est dans le terrain, sinon lève une exception
    public static void verifierPosition(Terrain t, int lig, int col) throws HorsTerrainException {
        if (!t.sontValides(lig, col)) {
            throw new HorsTerrainException(lig, col);
        }
    }

    // Renvoie une case voisine aléatoire (à au plus 'rayon' cases) qui reste dans le terrain
    public static int[] caseAleatoireProche(int lig, int col, int rayon, Terrain t) {
        // On essaie 10 fois max pour trouver une case valide, sinon on reste sur place
        for (int i = 0; i < 10; i++) {
            int dLig = ALEA.nextInt(2 * rayon + 1) - rayon;
            int dCol = ALEA.nextInt(2 * rayon + 1) - rayon;
            int nLig = lig + dLig;
            int nCol = col + dCol;
            if (t.sontValides(nLig, nCol)) {
                return new int[] { nLig, nCol };
            }
        }
        // Si rien trouvé, on reste sur place
        return new int[] { lig, col };
    }

    // Renvoie une case totalement aléatoire dans le terrain (utile pour placer ressources/agents)
    // Attention : Terrain utilise des coordonnées qui commencent à 1 (1 <= lig <= nbLignes)
    public static int[] caseAleatoire(Terrain t) {
        int lig = 1 + ALEA.nextInt(t.nbLignes);
        int col = 1 + ALEA.nextInt(t.nbColonnes);
        return new int[] { lig, col };
    }
}
