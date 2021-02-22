package vending;

public enum Note {
    TWENTY(20), FIFTY(50);

    private double denomination;

    private Note(double denomination) {
        this.denomination = denomination;
    }

    public double getDenomination() {
        return denomination;
    }

}
