package logic;

import java.util.ArrayList;

public abstract class Compte {
    protected String code;
    protected double solde;
    protected ArrayList<Operation> listeOperations;

    public Compte(String code) {
        this.code = code;
        this.solde = 0.0;
        this.listeOperations = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public double getSolde() {
        return solde;
    }

    public ArrayList<Operation> getListeOperations() {
        return listeOperations;
    }

    public void verser(double montant, String source) {
        solde += montant;
        listeOperations.add(new Versement(montant, source));
    }

    public abstract boolean retirer(double montant, String destination);
    public abstract double calculerInteret();
    public abstract void afficherDetails();
}