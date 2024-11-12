package store;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.Objects;

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

    public boolean hasSameName(String name) {
        return this.name.equals(name);
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

    public boolean isValidPromotionProduct() {
        if (promotion != null) {
            return promotion.checkValidatePromotionDate(DateTimes.now());
        }
        return false;
    }

    public boolean isNonPromotionProduct() {
        return promotion == null;
    }

    public void decreaseQuantity(final int quantity) {
        this.quantity -= quantity;
    }

    public String getPromotionName() {
        if (isValidPromotionProduct()) {
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
        if (isNonPromotionProduct() || !promotion.checkValidatePromotionDate(DateTimes.now())) {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return price == product.price && quantity == product.quantity && Objects.equals(name, product.name)
                && Objects.equals(promotion, product.promotion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, promotion, price, quantity);
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
