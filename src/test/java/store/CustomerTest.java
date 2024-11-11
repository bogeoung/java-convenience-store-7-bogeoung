package store;

import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    private Map<String, Integer> items = new HashMap<>();
    private static Customer customer;

    @BeforeAll
    static void setUp() {
        customer = new Customer();
        customer.purchase("콜라", 1);
        customer.purchase("콜라", 1);
        customer.purchase("사이다", 1);
    }

    @Test
    void 상품명과_수량이_입력되면_구매할_품목에_저장한다() {
        Map<String, Integer> answerItems = new HashMap<>();
        answerItems.put("콜라", 2);
        answerItems.put("사이다", 1);

        Assertions.assertThat(answerItems.keySet().stream().toList())
                .containsAll(customer.getItemNames());
        Assertions.assertThat(answerItems.get("콜라")).isEqualTo(customer.getItemCount("콜라"));
    }

    @Test
    void 상품명과_수량이_입력되면_구매할_품목에서_수량을_줄인다() {
        Map<String, Integer> answerItems = new HashMap<>();
        answerItems.put("콜라", 0);

        customer.cancelPurchase("콜라", 2);
        Assertions.assertThat(answerItems.get("콜라")).isEqualTo(customer.getItemCount("콜라"));
    }
}
