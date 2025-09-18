package model;

public class CompteEpargne extends Compte {
    private double tauxInteret; // e.g., 0.03 for 3%

    public CompteEpargne(String code, double initialSolde, double tauxInteret) {
        super(code, initialSolde);
        if (tauxInteret < 0) throw new IllegalArgumentException("Taux d'intérêt doit être >= 0");
        this.tauxInteret = tauxInteret;
    }

    public double getTauxInteret() {
        return tauxInteret;
    }

    @Override
    public void retirer(double montant, String destination) {
        if (montant <= 0) throw new IllegalArgumentException("Montant doit être positif");
        if (solde < montant) {
            throw new IllegalArgumentException("Retrait refusé : solde insuffisant.");
        }
        solde -= montant;
        Retrait r = new Retrait(montant, destination);
        addOperation(r);
    }

    @Override
    public double calculerInteret() {
        return solde * tauxInteret;
    }

    @Override
    public void afficherDetails() {
        System.out.println("Compte Epargne - code: " + code + " | solde: " + solde + " | taux: " + tauxInteret);
    }
}
