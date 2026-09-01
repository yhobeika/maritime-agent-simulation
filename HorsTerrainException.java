// HOBEIKA Youssef

// Exception lancée quand on essaie d'aller sur une case en dehors du terrain
public class HorsTerrainException extends Exception {
    // Constructeur : on passe la position fautive pour avoir un message clair
    public HorsTerrainException(int lig, int col) {
        super("Position hors terrain : (" + lig + ", " + col + ")");
    }
}
