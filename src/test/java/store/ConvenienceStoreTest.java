package store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static store.MdFilePromotionInfoParser.parseToLocalDateTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ConvenienceStoreTest {

    private static final List<Product> products = new ArrayList<>();
    private static final LocalDateTime startDate = parseToLocalDateTime("2024-01-01");
    private static final LocalDateTime endDate = parseToLocalDateTime("2024-01-02");
    private static final Promotion promotion = new Promotion("탄산2+1", 2, 1, startDate, endDate);
    private static ConvenienceStore convenienceStore;

    @BeforeAll
    static void setUp() {
        products.add(new Product("콜라", 1000, 10, promotion));
        products.add(new Product("콜라", 1000, 10, null));
        products.add(new Product("사이다", 1000, 8, promotion));
        products.add(new Product("사이다", 1000, 7, null));
        convenienceStore = new ConvenienceStore(products);
    }


    @ParameterizedTest
    @CsvSource({"'콜라', 100", "'사이다', 1000"})
    void 들어온_상품의_개수가_충분하지_못하면_false를_반환한다(String productName, int quantity) {
        assertFalse(convenienceStore.checkEnoughQuantity(productName, quantity));
    }

    @ParameterizedTest
    @CsvSource({"'콜라', 10", "'사이다', 5"})
    void 들어온_상품의_개수가_충분하면_true를_반환한다(String productName, int quantity) {
        assertTrue(convenienceStore.checkEnoughQuantity(productName, quantity));
    }

    @ParameterizedTest
    @ValueSource(strings = {"콜라"})
    void 들어온_상품명이_products에_존재하면_해당되는_product만_반환한다(String productName) {
        List<Product> answerProducts = new ArrayList<>();
        answerProducts.add(new Product("콜라", 1000, 10, promotion));
        answerProducts.add(new Product("콜라", 1000, 10, null));

        assertEquals(answerProducts, convenienceStore.getProductsWithName(productName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"콜라젤리"})
    void 들어온_상품명이_products에_존재하지_않으면_빈리스트를_반환한다(String productName) {
        List<Product> answerProducts = new ArrayList<>();
        assertEquals(answerProducts, convenienceStore.getProductsWithName(productName));
    }


    @Test
    void 들어온_상품명의_Non_promotion_제품만_반환한다() {
        String productName = "콜라";
        Optional<Product> answerProducts = Optional.of(new Product(productName, 1000, 10, null));
        assertEquals(answerProducts, convenienceStore.getNonPromotionProductWithName(productName));
    }

}
