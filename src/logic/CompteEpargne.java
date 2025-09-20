package logic;

public class CompteEpargne extends Compte {
    private double tauxInteret;

    public CompteEpargne(String code, double tauxInteret) {
        super(code);
        this.tauxInteret = tauxInteret;
    }

    public double getTauxInteret() {
        return tauxInteret;
    }

    @Override
    public boolean retirer(double montant, String destination) {
        if (solde >= montant) {
            solde -= montant;
            listeOperations.add(new Retrait(montant, destination));
            return true;
        }
        return false;
    }

    @Override
    public double calculerInteret() {
        return solde * tauxInteret;
    }

    @Override
    public void afficherDetails() {
        System.out.println("=== COMPTE EPARGNE ===");
        System.out.println("Code: " + code);
        System.out.println("Solde: " + solde + " MAD");
        System.out.println("Taux d interet: " + (tauxInteret * 100) + "%");
        System.out.println("Interets: " + calculerInteret() + " MAD");
        System.out.println("Nombre d operations: " + listeOperations.size());
    }
}