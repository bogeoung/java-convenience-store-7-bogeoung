package view;

import java.util.List;
import store.ErrorMessage;
import store.Product;

public class OutputView {

    private static final String START_PURCHASE_COMMENT = "안녕하세요. W편의점입니다. \n현재 보유하고 있는 상품입니다.\n";
    private static final String PRODUCT_START_SIGN = "- ";
    private static final String PRODUCT_QUANTITY_UNIT = "%d개";
    private static final String PRODUCT_PRICE_UNIT = "%,d원";
    private static final String PRODUCT_QUANTITY_ZERO = "재고 없음";
    private static final String RECEIPT_START_MESSAGE = "==============W 편의점================\n";
    private static final String RECEIPT_ITEM_MESSAGE = "상품명\t\t수량\t금액";
    private static final String RECEIPT_BONUS_MESSAGE = "=============증\t정===============\n";
    private static final String RECEIPT_TOTAL_MESSAGE = "====================================\n";
    private static final String RECEIPT_TOTAL_AMOUNT = "총구매액";
    private static final String RECEIPT_PROMOTION_DISCOUNT = "행사할인";
    private static final String RECEIPT_MEMBERSHIP_DISCOUNT = "멤버십할인";
    private static final String RECEIPT_PAY_MONEY = "내실돈";

    public static void printStartPurchase(List<Product> products) {
        System.out.println(START_PURCHASE_COMMENT);
        printProducts(products);
    }

    public static void printProducts(List<Product> products) {
        for (Product product : products) {
            System.out.println(printCurrentProducts(product));
        }
    }

    public static void printReceipt(List<Product> products) {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(RECEIPT_START_MESSAGE).append(RECEIPT_START_MESSAGE);
        for (Product product : products) {
            stringBuffer.append(product.getName()).append("\t").append("상품 판매개수 추가 예정");
        }
        stringBuffer.append(RECEIPT_BONUS_MESSAGE);
        stringBuffer.append(RECEIPT_TOTAL_MESSAGE);
        stringBuffer.append(RECEIPT_PROMOTION_DISCOUNT);
        stringBuffer.append(RECEIPT_MEMBERSHIP_DISCOUNT);
        stringBuffer.append(RECEIPT_PAY_MONEY);
        System.out.println(stringBuffer.toString());
    }

    public static void printProductNotExistMessage() {
        System.out.println(ErrorMessage.NOT_SELLING_PRODUCT.getErrorMessage());
    }

    public static void printNotEnoughQuantityMessage() {
        System.out.println(ErrorMessage.NOT_ENOUGH_QUANTITY.getErrorMessage());
    }

    private static String printCurrentProducts(Product product) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(PRODUCT_START_SIGN).append(product.getName());
        stringBuffer.append(" ").append(getPrice(product));
        stringBuffer.append(" ").append(getQuantity(product));
        stringBuffer.append(" ").append(product.getPromotionName());
        return stringBuffer.toString();
    }

    private static String getPrice(Product product) {
        return String.format(PRODUCT_PRICE_UNIT, product.getPrice());
    }

    private static String getQuantity(Product product) {
        int quantity = product.getQuantity();
        if (quantity == 0) {
            return PRODUCT_QUANTITY_ZERO;
        }
        return String.format(PRODUCT_QUANTITY_UNIT, quantity);
    }
}
