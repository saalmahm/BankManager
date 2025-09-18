package service;

import model.*;
import repository.CompteRepository;

import java.util.List;
import java.util.Optional;

public class BankService {
    private CompteRepository repo = new CompteRepository();

    public void createCompteCourant(String code, double initSolde, double decouvert) {
        CompteCourant c = new CompteCourant(code, initSolde, decouvert);
        repo.save(c);
    }

    public void createCompteEpargne(String code, double initSolde, double taux) {
        CompteEpargne c = new CompteEpargne(code, initSolde, taux);
        repo.save(c);
    }

    public void verser(String code, double montant, String source) {
        Compte c = getCompteOrThrow(code);
        c.verser(montant, source);
    }

    public void retirer(String code, double montant, String destination) {
        Compte c = getCompteOrThrow(code);
        c.retirer(montant, destination);
    }

    public void virement(String fromCode, String toCode, double montant) {
        Compte from = getCompteOrThrow(fromCode);
        Compte to = getCompteOrThrow(toCode);
        // Use retire then verser
        from.retirer(montant, "Virement vers " + toCode);
        to.verser(montant, "Virement depuis " + fromCode);
    }

    public double consulterSolde(String code) {
        return getCompteOrThrow(code).getSolde();
    }

    public List<Operation> listerOperations(String code) {
        return getCompteOrThrow(code).getListeOperations();
    }

    public void afficherTousComptes() {
        List<Compte> all = repo.findAll();
        for (Compte c : all) {
            c.afficherDetails();
        }
    }

    private Compte getCompteOrThrow(String code) {
        Optional<Compte> o = repo.findByCode(code);
        if (!o.isPresent()) {
            throw new IllegalArgumentException("Compte introuvable: " + code);
        }
        return o.get();
    }
}
