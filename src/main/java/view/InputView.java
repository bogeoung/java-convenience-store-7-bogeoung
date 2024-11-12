package view;

import camp.nextstep.edu.missionutils.Console;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class InputView {

    private static final String PURCHASE_PRODUCT_MESSAGE = "\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String MEMBERSHIP_COMMENT = "\n멤버십 할인을 받으시겠습니까? (Y/N)";
    private static final String CONTINUE_PURCHASE_COMMENT = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";
    private static final String INTENTION_NOT_APPLY_PROMOTION = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n";
    private static final String INTENTION_ADD_PROMOTION_PRODUCT = "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n";

    public static String getPurchaseProductAndQuantity() {
        System.out.println(PURCHASE_PRODUCT_MESSAGE);
        return readItem();
    }

    public static String getMembershipDiscountApply() {
        System.out.println(MEMBERSHIP_COMMENT);
        return readItem();
    }

    public static String continuePurchase() {
        System.out.println(CONTINUE_PURCHASE_COMMENT);
        return readItem();
    }

    public static String getIntentionNotApplyPromotion(String productName, int Quantity) {
        System.out.printf(INTENTION_NOT_APPLY_PROMOTION, productName, Quantity);
        return readItem();
    }

    public static String getIntentionAddPromotionProduct(String productName, int Quantity) {
        System.out.printf(INTENTION_ADD_PROMOTION_PRODUCT, productName, Quantity);
        return readItem();
    }

    public static String readItem() {
        return Console.readLine();
    }

    public static String getValueUntilValidated(Predicate<String> validate, String errorMessage,
                                                Supplier<String> supplier) {
        String value = supplier.get();
        while (validate.test(value)) {
            System.out.println(errorMessage);
            value = supplier.get();
        }
        return value;
    }
}