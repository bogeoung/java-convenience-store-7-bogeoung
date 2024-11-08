package parser;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import store.MdFileProductInfoParser;

public class InputParser {

    public static boolean validateInputFormat(String input) {
        return Pattern.matches("^\\[\\s*\\S+\\s*-\\s*\\d+\\s*\\]$", input);
    }

    public static List<String> splitInput(String input) {
        return Arrays.stream(input.split(MdFileProductInfoParser.PRODUCT_INFO_DELIMITER, -1))
                .map(String::strip).toList();
    }

    public static String getProductNumber(String input) {
        String productNumber = input.split("-")[1];
        productNumber = productNumber.replaceAll("[^0-9]", "");
        return productNumber.strip();
    }

    public static String getProductName(String input) {
        String productName = input.split("-")[0];
        productName = productName.replaceAll("\\[", "");
        return productName.strip();
    }
}
