package store;

import java.util.Date;
import java.util.List;
import utill.ProductInfoValidator;

public class MdFilePromotionInfoParser {

    private static final String PRODUCT_INFO_DELIMITER = ",";

    public static List<Promotion> parseAll(List<String> promotionInfos) {
        return promotionInfos.stream().map(MdFilePromotionInfoParser::parseToProduct).toList();
    }

    private static Promotion parseToProduct(String promotionInfo) {
        String[] splitInfos = promotionInfo.split(PRODUCT_INFO_DELIMITER);
        if (splitInfos.length != 5) {
            throw new RuntimeException();
        }
        try {
            String name = splitInfos[0].strip();
            int purchaseQuantity = Integer.parseInt(splitInfos[1].strip());
            int bonusQuantity = Integer.parseInt(splitInfos[2].strip());
            Date startDay = ProductInfoValidator.parseToDate(splitInfos[3].strip());
            Date endDay = ProductInfoValidator.parseToDate(splitInfos[4].strip());
            if (endDay.before(startDay)) {
                throw new RuntimeException();
            }
            return new Promotion(name, purchaseQuantity, bonusQuantity, startDay, endDay);
        } catch (NumberFormatException e) {
            throw new RuntimeException();
        }
    }
}
