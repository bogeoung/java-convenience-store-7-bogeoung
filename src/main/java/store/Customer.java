package store;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Customer {

    private final Map<String, Integer> items = new LinkedHashMap<>();

    public void purchase(String productName, int quantity) {
        if (items.containsKey(productName)) {
            items.put(productName, items.get(productName) + quantity);
            return;
        }
        items.put(productName, quantity);
    }

    public void cancelPurchase(String productName, int quantity) {
        if (items.containsKey(productName)) {
            if (items.get(productName) - quantity < 0) {
                items.remove(productName);
                return;
            }
            items.put(productName, items.get(productName) - quantity);
        }
    }

    public int getItemCount(String itemName) {
        return items.get(itemName);
    }

    public List<String> getItemNames() {
        return items.keySet().stream().toList();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "items=" + items +
                '}';
    }
}
