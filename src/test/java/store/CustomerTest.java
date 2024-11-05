package store;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class CustomerTest {
    @Test
    void 입력받은_구매_상품명과_수량을_저장한다(){
        Map<String, Integer> items = new HashMap<>();
        items.put("콜라", 1);
        items.put("사이다",2);

        Customer customer = new Customer(items);

        assertEquals(customer.getItemName(), items.keySet().stream().toList());
        assertEquals(customer.getItemCount("콜라"),1);
        assertEquals(customer.getItemCount("사이다"),2);
    }
}
