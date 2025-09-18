package repository;

import model.Compte;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Simple in-memory repository using HashMap for fast lookup.
 */
public class CompteRepository {
    private Map<String, Compte> comptes = new HashMap<>();

    public void save(Compte compte) {
        comptes.put(compte.getCode(), compte);
    }

    public Optional<Compte> findByCode(String code) {
        return Optional.ofNullable(comptes.get(code));
    }

    public List<Compte> findAll() {
        return new ArrayList<>(comptes.values());
    }

    public void delete(String code) {
        comptes.remove(code);
    }

    // Bonus: stream filtering example
    public List<Compte> findBySoldeLessThan(double threshold) {
        return comptes.values().stream().filter(c -> c.getSolde() < threshold).collect(Collectors.toList());
    }
}
