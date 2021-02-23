package vending;

public enum Coin {
    TEN(0.1), TWENTY(0.2), FIFTY(0.5), DOLLAR(1.0);

    private double denomination;

    private Coin(double denomination) {
        this.denomination = denomination;
    }

    public double getDenomination() {
        return denomination;
    }

}