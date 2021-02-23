package vending;

import java.util.HashMap;
import java.util.Map;

public class Inventory<T> {
    private Map<T, Integer> inv = new HashMap<T, Integer>();

    public int getQuantity(T item) {
        Integer value = inv.get(item);
        return value == null ? 0 : value;
    }

    public void add(T item) {
        int count = inv.get(item);
        inv.put(item, count + 1);
    }

    public void deduct(T item) {
        if (hasItem(item)) {
            int count = inv.get(item);
            inv.put(item, count - 1);
        }
    }

    public boolean hasItem(T item) {
        return getQuantity(item) > 0;
    }

    public void clear() {
        inv.clear();
    }

    public void put(T item, int quantity) {
        inv.put(item, quantity);
    }

    public Map<T, Integer> getInv() {
        return inv;
    }
}
