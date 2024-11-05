package store;

import java.util.List;
import java.util.Map;

public class Customer {

    private final Map<String, Integer> items;

    public Customer(Map<String, Integer> itemsInput) {
        this.items = itemsInput;
    }

    public List<String> getItemName() {
        return items.keySet().stream().toList();
    }

    public int getItemCount(String itemName) {
        return items.get(itemName);
    }
}
