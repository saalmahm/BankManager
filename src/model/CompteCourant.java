package model;

public class CompteCourant extends Compte {
    private double decouvert; // allowed overdraft (positive number)

    public CompteCourant(String code, double initialSolde, double decouvert) {
        super(code, initialSolde);
        if (decouvert < 0) throw new IllegalArgumentException("Découvert must be >= 0");
        this.decouvert = decouvert;
    }

    public double getDecouvert() {
        return decouvert;
    }
    // float x = 3.14f
    // double y = 3.14
    //  x = y;
    //  y = x; // error

    @Override
    public void retirer(double montant, String destination) {
        if (montant <= 0) throw new IllegalArgumentException("Montant doit être positif");
        double newSolde = solde - montant;
        if (newSolde < -decouvert) {
            throw new IllegalArgumentException("Retrait refusé : dépasse le découvert autorisé.");
        }
        solde = newSolde;
        Retrait r = new Retrait(montant, destination);
        addOperation(r);
    }

    @Override
    public double calculerInteret() {
        return 0.0;
    }

    @Override
    public void afficherDetails() {
        System.out.println("Compte Courant - code: " + code + " | solde: " + solde + " | découvert: " + decouvert);
    }
}
