package vending;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class VendingMachineImpl implements VendingMachine {
  private Inventory<Coin> coinInventory = new Inventory<>();
  private Inventory<Note> noteInventory = new Inventory<>();

  private Inventory<Item> itemInventory = new Inventory<>();
  private double totalSales;
  private Item currentItem;
  private double currentBalance;

  public VendingMachineImpl() {
    initialize();
  }

  private void initialize() {
    // initialize machine with 5 coins of each denomination
    // and 5 of each Item

    for (Coin c : Coin.values()) {
      coinInventory.put(c, 5);
    }
    for (Item i : Item.values()) {
      itemInventory.put(i, 5);
    }

  }

  @Override
  public double selectItemAndGetPrice(Item item) {
    if (itemInventory.hasItem(item)) {
      currentItem = item;
      return currentItem.getPrice();
    }
    throw new SoldOutException("Sold Out, Please buy another item");
  }

  @Override
  public void insertCoin(Coin coin) {
    currentBalance = currentBalance + coin.getDenomination();
    coinInventory.add(coin);
  }

  @Override
  public void insertNote(Note note) {
    currentBalance = currentBalance + note.getDenomination();
    noteInventory.add(note);
  }

  @Override
  public List<Coin> refund() {
    List<Coin> refund = getChange(currentBalance);
    updatecoinInventory(refund);
    currentBalance = 0;
    currentItem = null;
    return refund;
  }

  private Item collectItem() throws NotSufficientChangeException, NotPaidFullException {
    if (isFullPaid()) {
      if (hasSufficientChange()) {
        itemInventory.deduct(currentItem);
        return currentItem;
      }
      throw new NotSufficientChangeException("Not Sufficient change in Inventory");

    }
    double remainingBalance = currentItem.getPrice() - currentBalance;
    throw new NotPaidFullException("Price not full paid, remaining : ", remainingBalance);
  }

  @Override
  public Bucket<Item, List<Coin>> collectItemAndChange() {
    Item item = collectItem();
    totalSales = totalSales + currentItem.getPrice();

    List<Coin> change = collectChange();

    return new Bucket<Item, List<Coin>>(item, change);
  }

  @Override
  public void reset() {
    coinInventory.clear();
    itemInventory.clear();
    totalSales = 0;
    currentItem = null;
    currentBalance = 0;

  }

  @Override
  public void insertCard(Card card) {
    currentBalance += card.getBalance();
  }

  private boolean hasSufficientChangeForAmount(double amount) {
    boolean hasChange = true;
    try {
      getChange(amount);
    } catch (NotSufficientChangeException nsce) {
      hasChange = false;
    }

    return hasChange;
  }

  private List<Coin> collectChange() {
    double changeAmount = currentBalance - currentItem.getPrice();
    List<Coin> change = getChange(changeAmount);
    updatecoinInventory(change);
    currentBalance = 0;
    currentItem = null;
    return change;
  }

  private boolean isFullPaid() {
    if (currentBalance >= currentItem.getPrice()) {
      return true;
    }
    return false;
  }

  private boolean hasSufficientChange() {
    return hasSufficientChangeForAmount(currentBalance - currentItem.getPrice());
  }

  public void printStats() {
    System.out.println("Total Sales : " + totalSales);
    System.out.println("Current Item Inventory : " + itemInventory);
    System.out.println("Current Cash Inventory : " + coinInventory);
  }

  private List<Coin> getChange(double amount)
      throws ClassCastException, NullPointerException, NotSufficientChangeException {
    List<Coin> changes = Collections.emptyList();

    if (amount > 0) {
      changes = new ArrayList<Coin>();
      double balance = amount;
      while (balance > 0) {
        if (balance >= Coin.DOLLAR.getDenomination() && coinInventory.hasItem(Coin.DOLLAR)) {
          changes.add(Coin.DOLLAR);
          balance = balance - Coin.DOLLAR.getDenomination();

        } else if (balance >= Coin.FIFTY.getDenomination() && coinInventory.hasItem(Coin.FIFTY)) {
          changes.add(Coin.FIFTY);
          balance = balance - Coin.FIFTY.getDenomination();

        } else if (balance >= Coin.TWENTY.getDenomination() && coinInventory.hasItem(Coin.TWENTY)) {
          changes.add(Coin.TWENTY);
          balance = balance - Coin.TWENTY.getDenomination();

        } else if (balance >= Coin.TEN.getDenomination() && coinInventory.hasItem(Coin.TEN)) {
          changes.add(Coin.TEN);
          balance = balance - Coin.TEN.getDenomination();

        } else {
          throw new NotSufficientChangeException("NotSufficientChange, Please try another product");
        }
      }
    }

    return changes;
  }

  private void updatecoinInventory(List<Coin> change) {
    for (Coin c : change) {
      coinInventory.deduct(c);
    }
  }

  public double getTotalSales() {
    return totalSales;
  }

}
