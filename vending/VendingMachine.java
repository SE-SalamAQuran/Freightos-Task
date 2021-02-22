package vending;

import java.util.List;

public interface VendingMachine {
    public double selectItemAndGetPrice(Item item);

    public void insertCoin(Coin coin);

    public void insertNote(Note note);

    public void insertCard(Card card);

    public List<Coin> refund();

    public Bucket<Item, List<Coin>> collectItemAndChange();

    public void reset();
}
