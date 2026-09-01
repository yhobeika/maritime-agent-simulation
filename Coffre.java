// HOBEIKA Youssef

// Ressource qui contient une quantité d'or et qui évolue dans le temps (peut disparaître si elle n'est pas ramassée)
public class Coffre extends Ressource {
    // Compte le nombre d'étapes depuis la création du coffre
    private int ageEtapes;
    // Durée de vie max d'un coffre avant qu'il disparait
    private int dureeVie;

    public Coffre(int quantiteOr, int dureeVie) {
        super("Coffre", quantiteOr); 
        this.ageEtapes = 0;
        this.dureeVie = dureeVie;
    }

    public Coffre(Coffre autre) {
        super("Coffre", autre.getQuantite());
        this.ageEtapes = autre.ageEtapes;
        this.dureeVie = autre.dureeVie;
    }

    public int getAgeEtapes() { return ageEtapes; }
    public int getDureeVie() { return dureeVie; }

    // Vieillir d'une étape : true si le coffre a expiré
    public boolean vieillir() {
        ageEtapes++;
        return ageEtapes >= dureeVie;
    }
}
