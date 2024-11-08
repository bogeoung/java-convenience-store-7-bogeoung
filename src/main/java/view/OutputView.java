package view;

import java.util.List;
import store.Product;
import store.Promotion;

public class OutputView {

    private static final String START_PURCHASE_COMMENT = "안녕하세요. W편의점입니다. \n현재 보유하고 있는 상품입니다.\n";
    public static final String PRODUCT_START_SIGN = "- ";
    public static final String PRODUCT_QUANTITY_UNIT = "%d개";
    public static final String PRODUCT_PRICE_UNIT = "%,d원";
    public static final String PRODUCT_QUANTITY_ZERO = "재고 없음";

    public void printStartPurchase(List<Product> products) {
        System.out.println(START_PURCHASE_COMMENT);
        printProducts(products);
    }

    public void printProducts(List<Product> products) {
        for (Product product : products) {
            System.out.println(printCurrentProducts(product));
        }
    }

    private String printCurrentProducts(Product product) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(PRODUCT_START_SIGN).append(product.getName());
        stringBuffer.append(" ").append(getPrice(product));
        stringBuffer.append(" ").append(getQuantity(product));
        stringBuffer.append(" ").append(getPromotionName(product));
        return stringBuffer.toString();
    }

    private String getPrice(Product product) {
        return String.format(PRODUCT_PRICE_UNIT, product.getPrice());
    }

    private String getQuantity(Product product) {
        int quantity = product.getQuantity();
        if (quantity == 0) {
            return PRODUCT_QUANTITY_ZERO;
        }
        return String.format(PRODUCT_QUANTITY_UNIT, quantity);
    }

    private String getPromotionName(Product product) {
        Promotion promotion = product.getPromotion();
        if (promotion == null) {
            return "";
        }
        return promotion.getName();
    }
}
