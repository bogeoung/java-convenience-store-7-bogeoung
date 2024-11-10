package store;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class MdFilePromotionInfoParser {

    private static final String PRODUCT_INFO_DELIMITER = ",";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String TIME_FORMAT = "T00:00:00";

    public static List<Promotion> parseAll(List<String> promotionInfos) {
        return promotionInfos.stream().map(MdFilePromotionInfoParser::parseToPromotion).toList();
    }

    public static LocalDateTime parseToLocalDateTime(String input) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
            return LocalDateTime.parse(input + TIME_FORMAT, formatter);
        } catch (DateTimeParseException e) {
            throw new RuntimeException(ErrorMessage.MD_FILE_DATE_DATA_IS_WRONG.getMessage());
        }
    }

    private static Promotion parseToPromotion(String promotionInfo) {
        String[] splitInfos = promotionInfo.split(PRODUCT_INFO_DELIMITER);
        if (splitInfos.length != 5) {
            throw new RuntimeException();
        }
        String name = splitInfos[0].strip();
        int purchaseQuantity = Integer.parseInt(splitInfos[1].strip());
        int bonusQuantity = Integer.parseInt(splitInfos[2].strip());
        List<LocalDateTime> dateTimes = ParsePromotionDateTime(splitInfos);
        return new Promotion(name, purchaseQuantity, bonusQuantity, dateTimes.getFirst(), dateTimes.getLast());
    }

    private static List<LocalDateTime> ParsePromotionDateTime(String[] splitInfos) {
        try {
            LocalDateTime startDay = parseToLocalDateTime(splitInfos[3].strip());
            LocalDateTime endDay = parseToLocalDateTime(splitInfos[4].strip());
            if (endDay.isBefore(startDay)) {
                throw new RuntimeException(ErrorMessage.MD_FILE_DATE_DATA_IS_WRONG.getMessage());
            }
            return List.of(startDay, endDay);
        } catch (NumberFormatException e) {
            throw new RuntimeException(ErrorMessage.MD_FILE_DATA_IS_WRONG_FORMAT.getMessage());
        }
    }
}
