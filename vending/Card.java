package vending;

public class Card {
    private int serial;
    private int cvv;
    private String brand; // Visa or mastercard...etc
    private double balance;

    // Constructor
    public Card(int serial, int cvv) {
        this.serial = serial;
        this.cvv = cvv;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCvv() {
        return cvv;
    }

    public String getBrand() {
        return brand;
    }

    public int getSerial() {
        return serial;
    }

    public double getBalance() {
        return balance;
    }
}
