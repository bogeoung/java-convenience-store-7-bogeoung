package store;

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
        return promotion != null;
    }

    public void selled(final int quantity) {
        this.quantity -= quantity;
    }

    public Promotion getPromotion() {
        return promotion;
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
