package vending;

public enum Item {
    SNICKERS("Snickers", 2.5), DORITOS("Doritos", 3.5), MARS("Mars", 2.5), CHETOS("Chetos", 4.0),
    PRINGLES("Pringles", 4.5);

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