package model;

public class Retrait extends Operation {
    private String destination;

    public Retrait(double montant, String destination) {
        super(montant);
        this.destination = destination;
    }

    public String getDestination() { return destination; }

    @Override
    public String getType() { return "RETRAIT"; }

    @Override
    public String toString() {
        return super.toString() + " destination=" + destination;
    }
}
