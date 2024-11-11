package store;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductTest {

    private Product nonPromotionProduct;
    private Product promotionProduct;

    @BeforeEach
    void setUp() {
        LocalDateTime startDate = LocalDateTime.of(2024, 11, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 11, 30, 0, 0);
        Promotion promotion = new Promotion("탄산2+1", 2, 1, startDate, endDate);

        nonPromotionProduct = new Product("콜라", 1000, 10, null);
        promotionProduct = new Product("콜라", 1000, 10, promotion);

    }

    @Test
    void 입력받은_문자열과_같은이름이라면_참을_반환한다() {
        String name = "콜라";
        Assertions.assertThat(nonPromotionProduct.hasSameName(name)).isTrue();
    }

    @Test
    void 입력받은_문자열과_같은이름이라면_거짓을_반환한다() {
        String name = "사이다";
        Assertions.assertThat(nonPromotionProduct.hasSameName(name)).isFalse();
    }

    @Test
    void 현재_객체의_프로모션정보가_없다면_참을_반환한다() {
        Assertions.assertThat(nonPromotionProduct.isNonPromotionProduct()).isTrue();
        Assertions.assertThat(promotionProduct.isNonPromotionProduct()).isFalse();
    }

    @Test
    void 현재_객체의_프로모션정보가_있고_기한내라면_참을_반환한다() {
        Assertions.assertThat(promotionProduct.isValidPromotionProduct()).isTrue();
    }

    @Test
    void 입력된_수량만큼_수량을_감소시킨다() {
        int answerQuantity = 9;

        nonPromotionProduct.decreaseQuantity(1);
        Assertions.assertThat(nonPromotionProduct.getQuantity()).isEqualTo(answerQuantity);
    }

    @Test
    void 프로모션_정보가_없으면_빈문자열을_반환한다() {
        Assertions.assertThat(nonPromotionProduct.getPromotionName()).isEqualTo("");
    }
}
