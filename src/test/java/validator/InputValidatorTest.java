package validator;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Date;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.Application;
import store.Product;
import store.Promotion;

public class InputValidatorTest {

    @BeforeAll
    static void setUp() {
        Date dateStart = new Date(2023, 01, 01);
        Date dateEnd = new Date(2023, 02, 01);
        Application.products = new ArrayList<>();
        Promotion promotionSample = new Promotion("MD추천상품", 1, 1, dateStart, dateEnd);
        Application.products.add(new Product("사이다", 1000, 10, null));
        Application.products.add(new Product("사이다", 1000, 10, promotionSample));

    }

    @ParameterizedTest
    @ValueSource(strings = {"[사이다-사이다]", "[사이다--1]"})
    void 구한_판매상품의_개수가_숫자가_아니라면_false가_리턴된다(String candidate) {
        assertFalse(InputValidator.validateNumber(candidate));
    }

    @ParameterizedTest
    @CsvSource({"사이다, 0", "사이다, 10000"})
    void 구한_판매상품의_개수가_0이거나_재고가부족하면_false가_리턴된다(String candidateName, String candidateQuantity) {
        assertFalse(InputValidator.validateQuantity(candidateName, candidateQuantity));
    }

}
