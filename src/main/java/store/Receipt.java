package store;

import java.util.List;

public class Receipt {

    public static final double MEMBERSHIP_DISCOUNT_RATE = 0.3;
    public static final long MAX_MEMBERSHIP_DISCOUNT_PRICE = 8000L;

    private final List<PurchasedProduct> purchasedProducts;
    private final String membershipDiscountApply;

    public Receipt(List<PurchasedProduct> purchasedProducts, String membershipDiscountApply) {
        this.purchasedProducts = purchasedProducts;
        this.membershipDiscountApply = membershipDiscountApply;
    }

    public List<PurchasedProduct> getPurchasedProducts() {
        return purchasedProducts;
    }

    public List<PurchasedProduct> getPromotionAppliedProducts() {
        return purchasedProducts.stream().filter(product -> product.getBonusQuantity() > 0).toList();
    }

    public int getTotalCount() {
        return purchasedProducts.stream().mapToInt(PurchasedProduct::getQuantity).sum();
    }

    public int getTotalAmount() {
        return purchasedProducts.stream().mapToInt(PurchasedProduct::getTotalPrice).sum();
    }

    public int getTotalDiscountedAmount() {
        return purchasedProducts.stream().mapToInt(PurchasedProduct::getDiscountedPrice).sum();
    }

    public long getMemberShipDiscountedAmount() {
        if (membershipDiscountApply.equals("N")) {
            return 0;
        }
        int amountToApply = purchasedProducts.stream().filter(product -> product.getBonusQuantity() == 0)
                .mapToInt(PurchasedProduct::getTotalPrice).sum();
        long discountedAmount = Math.round(amountToApply * MEMBERSHIP_DISCOUNT_RATE);
        return Math.min(MAX_MEMBERSHIP_DISCOUNT_PRICE, discountedAmount);
    }
}