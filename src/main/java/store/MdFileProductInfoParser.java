package store;

import java.util.List;
import java.util.Optional;

public class MdFileProductInfoParser {

    private static final String PRODUCT_INFO_DELIMITER = ",";

    public static List<Product> parseAll(List<String> productInfos, Promotions promotions) {
        return productInfos.stream().map(content -> parseToProduct(content, promotions)).toList();
    }

    private static Product parseToProduct(String productInfo, Promotions promotions) {
        String[] splitInfos = productInfo.split(PRODUCT_INFO_DELIMITER);
        if (splitInfos.length != 4) {
            throw new RuntimeException();
        }
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
}
