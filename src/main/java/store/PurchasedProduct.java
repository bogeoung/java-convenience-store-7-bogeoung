package store;

public class PurchasedProduct {

    private final String name;
    private final int price;
    private final int quantity;
    private final int bonusQuantity;

    public PurchasedProduct(String name, int price, int quantity, int bonusQuantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.bonusQuantity = bonusQuantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getBonusQuantity() {
        return bonusQuantity;
    }

    public int getTotalPrice() {
        return price * quantity;
    }

    public int getDiscountedPrice() {
        return bonusQuantity * price;
    }

    @Override
    public String toString() {
        return "PurchasedProduct{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", bonusQuantity=" + bonusQuantity +
                '}';
    }
}



