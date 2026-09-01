// HOBEIKA Youssef

// Interface qui décrit ce que doit savoir faire toute entité capable de se déplacer
public interface Deplacement {
    // Déplace l'entité vers la case avec les coordonnées (lig, col)
    void seDeplacer(int lig, int col);

    // Calcule la distance euclidienne entre l'entité et la case (lig, col)
    double distance(int lig, int col);
}
