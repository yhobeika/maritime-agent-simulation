// HOBEIKA Youssef

// Ressource STATIQUE (sa quantité ne change pas toute seule)
// Une île bloque le passage : les agents ne peuvent pas marcher dessus
public class Ile extends Ressource {
    // Constructeur : la "quantité" représente la taille/solidité de l'île (constante)
    public Ile(int taille) {
        super("Ile", taille);
    }
}
