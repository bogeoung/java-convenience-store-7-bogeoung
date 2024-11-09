package store;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class MdFilePromotionInfoParser {

    private static final String PRODUCT_INFO_DELIMITER = ",";

    public static List<Promotion> parseAll(List<String> promotionInfos) {
        return promotionInfos.stream().map(MdFilePromotionInfoParser::parseToProduct).toList();
    }

    public static LocalDateTime parseToLocalDateTime(String input) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime parsedDate = LocalDateTime.parse(input + "T00:00:00", formatter);
            return parsedDate;
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
        return null;
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
            LocalDateTime startDay = parseToLocalDateTime(splitInfos[3].strip());
            LocalDateTime endDay = parseToLocalDateTime(splitInfos[4].strip());
            if (endDay.isBefore(startDay)) {
                throw new RuntimeException();
            }
            return new Promotion(name, purchaseQuantity, bonusQuantity, startDay, endDay);
        } catch (NumberFormatException e) {
            throw new RuntimeException();
        }
    }
}
