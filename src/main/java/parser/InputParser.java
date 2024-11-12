package parser;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class InputParser {

    public static boolean validateInputFormat(String input) {
        return Pattern.matches("^\\[\\s*\\S+\\s*-\\s*\\d+\\s*\\]$", input);
    }

    public static List<String> splitInput(String input, String delimiter) {
        return Arrays.stream(input.split(delimiter, -1))
                .map(String::strip).toList();
    }

    public static int getProductQuantity(String input) throws IllegalArgumentException {
        String[] splitInfo = input.split("-");
        if (splitInfo.length < 2) {
            throw new IllegalArgumentException();
        }
        String inputproductNumber = splitInfo[1];
        inputproductNumber = inputproductNumber.replaceAll("[^0-9]", "");
        return Integer.parseInt(inputproductNumber.strip());
    }

    public static String getProductName(String input) throws IllegalArgumentException {
        String[] splitInfo = input.split("-");
        if (splitInfo.length < 1) {
            throw new IllegalArgumentException();
        }
        String productName = input.split("-")[0];
        productName = productName.replaceAll("[^가-힣]", "");
        return productName.strip();
    }
}
