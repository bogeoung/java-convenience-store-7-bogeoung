package store;

import java.util.List;

public class Receipt {

    public static final double MEMBERSHIP_DISCOUNT_RATE = 0.3;
    public static final long MAX_MEMBERSHIP_DISCOUNT_PRICE = 8000L;

    private final List<PurchasedProduct> purchasedProducts;

    public Receipt(List<PurchasedProduct> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    public List<PurchasedProduct> getPurchasedProducts() {
        return purchasedProducts;
    }

    public List<PurchasedProduct> getPromotionAppliedProducts() {
        return purchasedProducts.stream().filter(product -> product.getBonusQuantity() > 0).toList();
    }

    public int getTotalCount() {
        return purchasedProducts.stream().mapToInt(product -> product.getQuantity() - product.getBonusQuantity()).sum();
    }

    public int getTotalAmount() {
        return purchasedProducts.stream().mapToInt(PurchasedProduct::getTotalPrice).sum();
    }

    public int getTotalDiscountedAmount() {
        return purchasedProducts.stream().mapToInt(PurchasedProduct::getDiscountedPrice).sum();
    }

    public long getMemberShipDiscountedAmount() {
        int amountToApply = purchasedProducts.stream().filter(product -> product.getBonusQuantity() == 0)
                .mapToInt(PurchasedProduct::getTotalPrice).sum();
        long discountedAmount = Math.round(amountToApply * MEMBERSHIP_DISCOUNT_RATE);
        return Math.min(MAX_MEMBERSHIP_DISCOUNT_PRICE, discountedAmount);
    }
}