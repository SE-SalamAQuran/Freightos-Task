package vending;

public enum Item {
    SNICKERS("C1", 2.5), DORITOS("C2", 3.5), MARS("C3", 2.5), CHETOS("C4", 4.0), PRINGLES("C5", 4.5);

    private String name;
    private double price;

    private Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}