package model;

public class Versement extends Operation {
    private String source;

    public Versement(double montant, String source) {
        super(montant);
        this.source = source;
    }

    public String getSource() { return source; }

    @Override
    public String getType() { return "VERSEMENT"; }

    @Override
    public String toString() {
        return super.toString() + " source=" + source;
    }
}
