package store;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MdFileProductInfoParser {

    public static final String PRODUCT_INFO_DELIMITER = ",";

    public static List<Product> parseAll(List<String> productInfos, Promotions promotions) {
        List<Product> products = new ArrayList<>();
        for (String productInfo : productInfos) {
            Product product = parseToProduct(productInfo, promotions);
            products.add(product);
            checkLastProductHavePromotion(products);
        }
        return products;
    }

    private static Product parseToProduct(String productInfo, Promotions promotions) {
        String[] splitInfos = productInfo.split(PRODUCT_INFO_DELIMITER);
        if (splitInfos.length != 4) {
            throw new RuntimeException();
        }
        return parseProductDetail(splitInfos, promotions);
    }

    private static Product parseProductDetail(String[] splitInfos, Promotions promotions) {
        try {
            String name = splitInfos[0].strip();
            int price = Integer.parseInt(splitInfos[1].strip());
            int quantity = Integer.parseInt(splitInfos[2].strip());
            Optional<Promotion> promotionNullable = promotions.getPromotionWithName(splitInfos[3].strip());
            return promotionNullable.map(promotion -> new Product(name, price, quantity, promotion))
                    .orElseGet(() -> new Product(name, price, quantity, null));
        } catch (NumberFormatException e) {
            throw new RuntimeException();
        }
    }


    private static void checkLastProductHavePromotion(List<Product> products) {
        if (products.size() < 2) {
            return;
        }
        Product secondLastAddedProduct = products.get(products.size() - 2);
        Product lastAddedProduct = products.getLast();
        if (!secondLastAddedProduct.getName().equals(lastAddedProduct.getName())) {
            if (secondLastAddedProduct.isPromotionProduct()) {
                addDefaultProduct(products, secondLastAddedProduct);
            }
        }
    }

    private static void addDefaultProduct(List<Product> products, Product product) {
        products.add(products.size() - 1, new Product(product.getName(),
                product.getPrice(), 0, null));
    }
}
