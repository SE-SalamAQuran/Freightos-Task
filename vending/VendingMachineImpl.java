package vending;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
    for (Note n : Note.values()) {
      noteInventory.put(n, 5);
    }
    for (Item i : Item.values()) {
      itemInventory.put(i, 5);
    }

  }

  public Item detectItem(String name) {
    Item i = Item.CHETOS;
    if (name.equals("C4")) {
      return i;
    } else if (name.equals("C3")) {
      i = Item.MARS;
    } else if (name.equals("C5")) {
      i = Item.PRINGLES;
    } else if (name.equals("C2")) {
      i = Item.DORITOS;
    } else if (name.equals("C1")) {
      i = Item.SNICKERS;
    }
    return i;
  }

  @Override
  public double selectItemAndGetPrice(Item item) {
    if (itemInventory.hasItem(item)) {
      currentItem = detectItem(item.getName());
      return currentItem.getPrice();
    }
    throw new SoldOutException("Sold Out, Please buy another item");
  }

  public Coin detectCoin(double amount) {
    Coin c = Coin.TEN;
    if (amount == 0.1) {
      return c;
    } else if (amount == 0.2) {
      c = Coin.TWENTY;
    } else if (amount == 0.5) {
      c = Coin.FIFTY;
    } else if (amount == 1) {
      c = Coin.DOLLAR;
    }
    return c;
  }

  public Note detectNote(double amount) {
    Note n = Note.TWENTY;
    if (amount == 20) {
      return n;
    } else if (amount == 50) {
      n = Note.FIFTY;
    }
    return n;
  }

  @Override
  public void insertCoin(Coin coin) {
    currentBalance = currentBalance + coin.getDenomination();
    coinInventory.add(detectCoin(coin.getDenomination()));
  }

  public double getCurrentBalance() {
    return currentBalance;
  }

  @Override
  public void insertNote(Note note) {
    currentBalance = currentBalance + note.getDenomination();
    noteInventory.add(detectNote(note.getDenomination()));
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

  public void printItems() {
    Map<Item, Integer> inv = itemInventory.getInv();
    for (Item i : inv.keySet()) {
      System.out.println(i + " Remaining: " + inv.get(i) + " Code: " + i.getName());

    }
  }

  public void printCoins() {
    Map<Coin, Integer> inv = coinInventory.getInv();
    for (Coin c : inv.keySet()) {
      System.out.println("Coins: " + c + " Remaining: " + inv.get(c));
    }
  }

  public void printStats() {
    System.out.println("Total Sales : " + totalSales);
    System.out.println("\nCurrent Item Inventory : \n");
    printItems();
    System.out.println("\nCurrent Cash Inventory : \n");
    printCoins();
  }

  public void printList(List<Coin> list) {
    double sumOfChange = 0;
    Iterator<Coin> it = list.iterator();
    while (it.hasNext()) {
      sumOfChange += it.next().getDenomination();
    }
    System.out.println(sumOfChange);

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

}
