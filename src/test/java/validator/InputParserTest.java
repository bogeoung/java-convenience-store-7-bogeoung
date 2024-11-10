package validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import parser.InputParser;

public class InputParserTest {

    @ParameterizedTest(name = "{0}가 입력된경우")
    @ValueSource(strings = {"[사이다 2]", "사이다-2", "[사이다.2], [사이다-2],[감자칩", "[사이다]"})
    void 입력된_구매상품이_입력형식에_맞지않을경우_false가_반환된다(String candidate) {
        assertFalse(InputParser.validateInputFormat(candidate));
    }

    @ParameterizedTest(name = "{0}가 입력된경우")
    @ValueSource(strings = {"[사이다-2]", "[안파는항목-1]"})
    void 입력된_구매상품이_입력형식에_맞는경우_True가_반환된다(String candidate) {
        assertTrue(InputParser.validateInputFormat(candidate));
        assertDoesNotThrow(() -> InputParser.validateInputFormat(candidate));
    }

    @Test
    void 입력된_상품이_여러개라면_콤마를_기준으로_분리한다() {
        String input = "[사이다-1], [콜라-1]";
        List<String> products = new ArrayList<>(List.of("[사이다-1]", "[콜라-1]"));
        assertEquals(products, InputParser.splitInput(input, ","));
    }

    @Test
    void 입력에서_상품개수에_해당하는_문자열만_리턴한다() {
        String input = "[사이다-2]";
        assertEquals(2, InputParser.getProductQuantity(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"[사이다-2]", "[   사이다-2]"})
    void 입력에서_상품명에_해당하는_문자열만_리턴한다(String candidate) {
        assertEquals("사이다", InputParser.getProductName(candidate));
    }
}
