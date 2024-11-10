package view;

import java.util.List;
import store.ErrorMessage;
import store.Product;
import store.PurchasedProduct;
import store.Receipt;

public class OutputView {

    private static final String START_PURCHASE_COMMENT = "안녕하세요. W편의점입니다. \n현재 보유하고 있는 상품입니다.\n";
    private static final String PRODUCT_START_SIGN = "- ";
    private static final String PRODUCT_QUANTITY_UNIT = "%d개";
    private static final String PRODUCT_PRICE_UNIT = "%,d원";
    private static final String PRODUCT_QUANTITY_ZERO = "재고 없음";
    private static final String RECEIPT_START_MESSAGE = "==============W 편의점================\n";
    private static final String RECEIPT_ITEM_MESSAGE = "%-18s%-9s%-6s%s";
    private static final String RECEIPT_BONUS_MESSAGE = "=============증\t\t\t정===============%s";
    private static final String RECEIPT_TOTAL_MESSAGE = "====================================\n";
    private static final String RECEIPT_TOTAL_AMOUNT = "총구매액";
    private static final String RECEIPT_PROMOTION_DISCOUNT = "행사할인";
    private static final String RECEIPT_MEMBERSHIP_DISCOUNT = "멤버십할인";
    private static final String RECEIPT_PAY_MONEY = "내실돈";
    private static final String FORMATED_STRING_PRODUCT = "%-18s%-9d%-6s%s";
    private static final String FORMATED_STRING_BONUS_PRODUCT = "%-18s%-15d%s";
    private static final String FORMATED_STRING_LAST_PRODUCT = "%-27s%-6s%s";
    private static final String NUMBER_FORMAT = " %,d";
    private static final String MINUS_NUMBER_FORMAT = "-%,d";


    public static void printStartPurchase(List<Product> products) {
        System.out.println(START_PURCHASE_COMMENT);
        printProducts(products);
    }

    public static void printProducts(List<Product> products) {
        for (Product product : products) {
            System.out.println(printCurrentProducts(product));
        }
    }

    public static void printTotalReceipt(Receipt receipt) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(RECEIPT_START_MESSAGE);
        printReceiptProductPart(stringBuffer, receipt.getPurchasedProducts());
        printReceiptBonusProducts(stringBuffer, receipt.getPromotionAppliedProducts());
        printReceiptTotalAmount(stringBuffer, receipt.getTotalCount(), receipt.getTotalAmount());
        printTotalDiscountAmount(stringBuffer, receipt.getTotalDiscountedAmount());
        printTotalMembershipDiscountAmount(stringBuffer, receipt.getMemberShipDiscountedAmount());
        printTotalAmountToPay(stringBuffer, receipt.getTotalAmount() - receipt.getTotalDiscountedAmount()
                - receipt.getMemberShipDiscountedAmount());
        System.out.println(stringBuffer.toString());
    }

    private static void printReceiptProductPart(StringBuffer stringBuffer, List<PurchasedProduct> purchasedProducts) {
        stringBuffer.append(String.format(RECEIPT_ITEM_MESSAGE, "상품명", "수량", "금액", System.lineSeparator()));
        for (PurchasedProduct purchasedProduct : purchasedProducts) {
            String formattedPurchasedPrice = String.format(NUMBER_FORMAT, purchasedProduct.getTotalPrice());
            stringBuffer.append(String.format(FORMATED_STRING_PRODUCT, purchasedProduct.getName(),
                    purchasedProduct.getQuantity(), formattedPurchasedPrice, System.lineSeparator()));
        }
    }

    private static void printReceiptBonusProducts(StringBuffer stringBuffer,
                                                  List<PurchasedProduct> promotionAppliedProducts) {
        stringBuffer.append(String.format(RECEIPT_BONUS_MESSAGE, System.lineSeparator()));
        for (PurchasedProduct purchasedProduct : promotionAppliedProducts) {
            stringBuffer.append(String.format(FORMATED_STRING_BONUS_PRODUCT, purchasedProduct.getName(),
                    purchasedProduct.getBonusQuantity(), System.lineSeparator()));
        }
    }

    private static void printReceiptTotalAmount(StringBuffer stringBuffer, int totalCount, int totalAmount) {
        stringBuffer.append(RECEIPT_TOTAL_MESSAGE);
        String formattedTotalAmount = String.format(NUMBER_FORMAT, totalAmount);
        stringBuffer.append(String.format(FORMATED_STRING_PRODUCT, RECEIPT_TOTAL_AMOUNT, totalCount,
                formattedTotalAmount,
                System.lineSeparator()));
    }

    private static void printTotalDiscountAmount(StringBuffer stringBuffer, int totalDiscountedAmount) {
        String formattedTotalDiscountAmount = String.format(MINUS_NUMBER_FORMAT, totalDiscountedAmount);
        stringBuffer.append(
                String.format(FORMATED_STRING_LAST_PRODUCT, RECEIPT_PROMOTION_DISCOUNT, formattedTotalDiscountAmount,
                        System.lineSeparator()));
    }

    private static void printTotalMembershipDiscountAmount(StringBuffer stringBuffer,
                                                           long memberShipDiscountedAmount) {
        String formattedMemberShipDiscountedAmount = String.format(MINUS_NUMBER_FORMAT, memberShipDiscountedAmount);
        stringBuffer.append(
                String.format(FORMATED_STRING_LAST_PRODUCT, RECEIPT_MEMBERSHIP_DISCOUNT,
                        formattedMemberShipDiscountedAmount,
                        System.lineSeparator()));
    }

    private static void printTotalAmountToPay(StringBuffer stringBuffer, long totalAmount) {
        String formattedTotalAmount = String.format(NUMBER_FORMAT, totalAmount);
        stringBuffer.append(
                String.format(FORMATED_STRING_LAST_PRODUCT, RECEIPT_PAY_MONEY, formattedTotalAmount,
                        System.lineSeparator()));
    }

    public static void printProductNotExistMessage() {
        System.out.println(ErrorMessage.NOT_SELLING_PRODUCT.getMessage());
    }

    public static void printNotEnoughQuantityMessage() {
        System.out.println(ErrorMessage.NOT_ENOUGH_QUANTITY.getMessage());
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
