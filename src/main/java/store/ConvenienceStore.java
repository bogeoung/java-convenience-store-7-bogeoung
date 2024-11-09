package store;

import java.util.List;
import java.util.Optional;

public class ConvenienceStore {

    private final List<Product> products;

    public ConvenienceStore(List<Product> products) {
        this.products = products;
    }

    public boolean checkEnoughQuantity(String productName, int quantity) {
        List<Product> products = this.products.stream()
                .filter(product -> product.getName().equals(productName))
                .toList();
        for (Product product : products) {
            quantity -= product.getQuantity();
        }
        return quantity <= 0;
    }

    public int getPromotionAppliedQuantity(String productName, int quantity) {
        Optional<Product> promotionProduct = getProductsWithName(productName).stream()
                .filter(Product::isPromotionProduct)
                .findAny();
        return promotionProduct.map(product -> product.sellablePromotionQuantity(quantity)).orElse(0);
    }

    public List<Product> getProductsWithName(String productName) {
        return products.stream()
                .filter(product -> product.getName().equals(productName))
                .toList();
    }

    public Receipt purchase(List<PurchaseInfo> purchaseInfos) {
        List<PurchasedProduct> purchasedProducts = purchaseInfos.stream().map(purchaseInfo -> {
            String productName = purchaseInfo.getProductName();
            int productQuantity = purchaseInfo.getProductQuantity();
            int bonusQuantity = 0;
            List<Product> products = this.getProductsWithName(productName);
            if (products.isEmpty()) {
                throw new IllegalArgumentException("구매하려는 이름의 상품이 없습니다.");
            }

            Optional<Product> promotionProduct = products.stream().filter(Product::isPromotionProduct).findAny();
            int promotionBonusQuantity = promotionProduct
                    .map(product -> product.getBonusQuantity(productQuantity)).orElse(0);

            bonusQuantity += promotionBonusQuantity;

            int prevProductQuantity = purchaseInfo.getProductQuantity();

            Optional<Product> nonPromotionProduct =
                    products.stream().filter(product -> !product.isPromotionProduct()).findAny();

            if (promotionProduct.isPresent()) {
                Product product = promotionProduct.get();
                int quantity = product.getQuantity();
                if (quantity >= prevProductQuantity) {
                    product.decreaseQuantity(prevProductQuantity);
                    prevProductQuantity = 0;
                }
                if (quantity < prevProductQuantity) {
                    product.decreaseQuantity(quantity);
                    prevProductQuantity -= quantity;
                }
            }

            if (nonPromotionProduct.isPresent()) {
                Product product = nonPromotionProduct.get();
                product.decreaseQuantity(prevProductQuantity);
            }

            return new PurchasedProduct(productName, products.getFirst().getPrice(), productQuantity, bonusQuantity);
        }).toList();
        return new Receipt(purchasedProducts);
    }
}