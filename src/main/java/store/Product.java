package store;

import camp.nextstep.edu.missionutils.DateTimes;

public class Product {

    private final String name;
    private final Promotion promotion;
    private final int price;
    private int quantity;

    public Product(String name, int price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isPromotionProduct() {
        if (promotion != null) {
            return promotion.checkValidatePromotionDate(DateTimes.now());
        }
        return false;
    }

    public void decreaseQuantity(final int quantity) {
        this.quantity -= quantity;
    }

    public String getPromotionName() {
        if (isPromotionProduct()) {
            return promotion.getName();
        }
        return "";
    }

    public int getBonusQuantity(int quantity) {
        if (this.promotion == null) {
            return 0;
        }
        if (quantity > this.quantity) {
            return this.promotion.getBonusQuantity(this.quantity);
        }
        return this.promotion.getBonusQuantity(quantity);
    }

    public int sellablePromotionQuantity(int quantity) {
        if (!isPromotionProduct() || !promotion.checkValidatePromotionDate(DateTimes.now())) {
            return 0;
        }
        if (this.quantity < promotion.getBundleSize()) {
            return 0;
        }
        if (this.quantity >= quantity) {
            return promotion.getAppliableQuantity(quantity);
        }
        return promotion.getAppliableQuantity(this.quantity);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", promotion=" + promotion +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
