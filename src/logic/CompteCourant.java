package logic;

public class CompteCourant extends Compte {
    private double decouvert;

    public CompteCourant(String code, double decouvert) {
        super(code);
        this.decouvert = decouvert;
    }

    public double getDecouvert() {
        return decouvert;
    }

    @Override
    public boolean retirer(double montant, String destination) {
        if (solde - montant >= -decouvert) {
            solde -= montant;
            listeOperations.add(new Retrait(montant, destination));
            return true;
        }
        return false;
    }

    @Override
    public double calculerInteret() {
        return 0.0;
    }

    @Override
    public void afficherDetails() {
        System.out.println("=== COMPTE COURANT ===");
        System.out.println("Code: " + code);
        System.out.println("Solde: " + solde + " MAD");
        System.out.println("Decouvert autorise: " + decouvert + " MAD");
        System.out.println("Nombre d operations: " + listeOperations.size());
    }
}