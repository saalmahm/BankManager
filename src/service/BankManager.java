package service;

import logic.*;
import java.util.HashMap;

public class BankManager {
    private HashMap<String, Compte> comptes;

    public BankManager() {
        this.comptes = new HashMap<>();
    }

    // Creer un compte courant
    public boolean creerCompteCourant(String code, double decouvert) {
        if (comptes.containsKey(code)) {
            return false; // Compte existe deja
        }
        comptes.put(code, new CompteCourant(code, decouvert));
        return true;
    }

    // Creer un compte épargne
    public boolean creerCompteEpargne(String code, double tauxInteret) {
        if (comptes.containsKey(code)) {
            return false; // Compte existe deja
        }
        comptes.put(code, new CompteEpargne(code, tauxInteret));
        return true;
    }

    // Trouver un compte
    public Compte trouverCompte(String code) {
        return comptes.get(code);
    }

    // Faire un versement
    public boolean faireVersement(String code, double montant, String source) {
        Compte compte = comptes.get(code);
        if (compte == null || montant <= 0) {
            return false;
        }
        compte.verser(montant, source);
        return true;
    }

    // Faire un retrait
    public boolean faireRetrait(String code, double montant, String destination) {
        Compte compte = comptes.get(code);
        if (compte == null || montant <= 0) {
            return false;
        }
        return compte.retirer(montant, destination);
    }

    // Faire un virement
    public boolean faireVirement(String codeSource, String codeDestination, double montant) {
        Compte compteSource = comptes.get(codeSource);
        Compte compteDestination = comptes.get(codeDestination);

        if (compteSource == null || compteDestination == null || montant <= 0) {
            return false;
        }

        // Essayer de retirer du compte source
        if (compteSource.retirer(montant, "Virement vers " + codeDestination)) {
            // Verser au compte destination
            compteDestination.verser(montant, "Virement de " + codeSource);
            return true;
        }
        return false;
    }
}