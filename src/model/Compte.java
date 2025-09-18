package model;

import util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Compte {
    protected String code; // CPT-12345
    protected double solde;
    protected List<Operation> listeOperations = new ArrayList<>();

    public Compte(String code, double initialSolde) {
        if (!ValidationUtil.isValidCompteCode(code)) {
            throw new IllegalArgumentException("Code de compte invalide. Format attendu: CPT-12345");
        }
        this.code = code;
        this.solde = initialSolde;
    }

    public String getCode() {
        return code;
    }

    public double getSolde() {
        return solde;
    }

    public List<Operation> getListeOperations() {
        return listeOperations;
    }

    public void addOperation(Operation op) {
        listeOperations.add(op);
    }

    // deposit
    public void verser(double montant, String source) {
        if (montant <= 0) throw new IllegalArgumentException("Montant doit être positif");
        this.solde += montant;
        Versement v = new Versement(montant, source);
        addOperation(v);
    }

    // abstract withdraw
    public abstract void retirer(double montant, String destination) throws IllegalArgumentException;

    // interest for account (0 for current account)
    public abstract double calculerInteret();

    public abstract void afficherDetails();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Compte compte = (Compte) o;
        return Objects.equals(code, compte.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}