package validator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.ErrorMessage;

public class InputValidatorTest {

    @ParameterizedTest(name = "{0}가 입력된경우")
    @ValueSource(strings = {"[사이다 2]", "사이다-2", "[사이다.2], [사이다-2],[감자칩", "[사이다]"})
    void 입력된_구매상품이_입력형식에_맞지않을경우_예외문구가_출력된다(String candidate) {
        assertThatThrownBy(() -> InputValidator.validateInputFormat(candidate))
                .hasMessage(ErrorMessage.INPUT_FORMAT_IS_WRONG.getErrorMessage());
    }

    @ParameterizedTest(name = "{0}가 입력된경우")
    @ValueSource(strings = {"[사이다-2]", "[사이다-2],[감자칩-1]", "[안파는항목-1]"})
    void 입력된_구매상품이_입력형식에_맞는경우_예외문구가_출력되지_않는다(String candidate) {
        assertDoesNotThrow(() -> InputValidator.validateInputFormat(candidate));
    }

    @ParameterizedTest
    @ValueSource(strings = {"[사이다-0]", "[사이다- -1]"})
    void 입력된_구매상품의_개수가_양수가_아닌경우_예외문구가_출력된다(String candidate) {
        assertThatThrownBy(() -> InputValidator.validateQuantity(candidate)).hasMessage(
                ErrorMessage.NOT_ENOUGH_QUANTITY.getErrorMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"[사이다-1]", "[사이다-100]"})
    void 입력된_구매상품의_개수가_양수가_아닌경우_예외문구가_출력된다(String candidate) {
        assertDoesNotThrow(() -> InputValidator.validateQuantity(candidate));
    }

}
