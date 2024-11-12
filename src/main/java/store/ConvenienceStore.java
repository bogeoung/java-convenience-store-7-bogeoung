package store;

import java.util.List;
import java.util.Optional;

public class ConvenienceStore {

    private final List<Product> products;

    public ConvenienceStore(List<Product> products) {
        this.products = products;
    }

    public boolean checkEnoughQuantity(String productName, int quantity) {
        List<Product> products = getProductsWithName(productName);
        for (Product product : products) {
            quantity -= product.getQuantity();
        }
        return quantity <= 0;
    }

    public List<Product> getProductsWithName(String productName) {
        return products.stream()
                .filter(product -> product.hasSameName(productName))
                .toList();
    }

    public Receipt sellProducts(List<PurchaseInfo> purchaseInfos, String membershipDiscountApply) {
        List<PurchasedProduct> purchasedProducts = purchaseInfos.stream().map(purchaseInfo -> {
            Optional<Product> product = getNonPromotionProductWithName(purchaseInfo.getProductName());
            int price = product.map(Product::getPrice)
                    .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NOT_SELLING_PRODUCT.getMessage()));
            int bonusQuantity = getBonusQuantity(purchaseInfo);
            decreaseQuantity(purchaseInfo);
            return new PurchasedProduct(purchaseInfo, price, bonusQuantity);
        }).toList();

        return new Receipt(purchasedProducts, membershipDiscountApply);
    }

    public Optional<Product> getNonPromotionProductWithName(String productName) {
        return products.stream()
                .filter(product -> product.hasSameName(productName))
                .filter(Product::isNonPromotionProduct)
                .findAny();
    }

    private int getBonusQuantity(PurchaseInfo purchaseInfo) {
        String productName = purchaseInfo.getProductName();
        int productQuantity = purchaseInfo.getProductQuantity();
        if (getProductsWithName(productName).isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.NOT_SELLING_PRODUCT.getMessage());
        }
        Optional<Product> promotionProduct = getPromotionProductWithName(productName);
        return promotionProduct.map(product -> product.getBonusQuantity(productQuantity)).orElse(0);
    }

    public Optional<Product> getPromotionProductWithName(String productName) {
        return products.stream()
                .filter(product -> product.hasSameName(productName))
                .filter(Product::isValidPromotionProduct)
                .findAny();
    }

    private void decreaseQuantity(PurchaseInfo purchaseInfo) {
        String productName = purchaseInfo.getProductName();
        int purchaseQuantity = purchaseInfo.getProductQuantity();
        Optional<Product> promotionProduct = getPromotionProductWithName(productName);
        Optional<Product> nonPromotionProduct = getNonPromotionProductWithName(productName);
        if (promotionProduct.isPresent()) {
            purchaseQuantity = decreasePromotionProductQuantityAndReturnLeft(promotionProduct.get(), purchaseQuantity);
        }
        if (nonPromotionProduct.isPresent()) {
            nonPromotionProduct.get().decreaseQuantity(purchaseQuantity);
        }
    }

    private int decreasePromotionProductQuantityAndReturnLeft(Product product, int purchasedQuantity) {
        int quantity = product.getQuantity();
        if (quantity >= purchasedQuantity) {
            product.decreaseQuantity(purchasedQuantity);
            return 0;
        }
        product.decreaseQuantity(quantity);
        return purchasedQuantity - quantity;
    }

    public int getPromotionAppliedQuantity(String productName, int quantity) {
        Optional<Product> promotionProduct = getProductsWithName(productName).stream()
                .filter(Product::isValidPromotionProduct)
                .findAny();
        return promotionProduct.map(product -> product.sellablePromotionQuantity(quantity)).orElse(0);
    }
}