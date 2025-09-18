package model;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Operation {
    protected UUID numero;
    protected LocalDateTime date;
    protected double montant;

    public Operation(double montant) {
        this.numero = UUID.randomUUID();
        this.date = LocalDateTime.now();
        this.montant = montant;
    }

    public UUID getNumero() { return numero; }
    public LocalDateTime getDate() { return date; }
    public double getMontant() { return montant; }

    public abstract String getType();

    @Override
    public String toString() {
        return "[" + getType() + "] id=" + numero + " date=" + date + " montant=" + montant;
    }
}
