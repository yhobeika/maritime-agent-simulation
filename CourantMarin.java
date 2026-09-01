// HOBEIKA Youssef

// Ressource ÉVOLUTIVE (sa force diminue toute seule au fil du temps)
// Un courant marin peut perturber un agent qui passe dessus
public class CourantMarin extends Ressource {
    public CourantMarin(int force) {
        super("Courant", force);
    }

    // Le courant s'affaiblit au fil du temps : retourne true si le courant a disparu
    public boolean affaiblir() {
        int nouv = getQuantite() - 1;
        setQuantite(nouv);
        return nouv <= 0;
    }
}
