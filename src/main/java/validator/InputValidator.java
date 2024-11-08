package validator;

import java.util.List;
import parser.InputParser;
import store.Application;
import store.ErrorMessage;
import store.Product;

public class InputValidator {

    public static void validateProductNumber(List<String> purchaseItems) {
        for (String purchaseItem : purchaseItems) {
            String curPurchaseItemQuantity = InputParser.getProductNumber(purchaseItem);
            String curPurchaseItemName = InputParser.getProductName(purchaseItem);
            if (!validateNumber(curPurchaseItemQuantity)) {
                System.out.println(ErrorMessage.INPUT_FORMAT_IS_WRONG.getErrorMessage());
            }
            validateQuantity(curPurchaseItemName, curPurchaseItemQuantity);
        }
    }

    public static void validateProductName(List<String> purchaseItems) {
        List<String> productNames = Application.products.stream().map(Product::getName).toList();
        for (String purchaseItem : purchaseItems) {
            String curPurchaseItemName = InputParser.getProductName(purchaseItem);
            if (!productNames.contains(curPurchaseItemName)) {
                System.out.println(ErrorMessage.NOT_SELLING_PRODUCT);
            }
        }
    }

    public static boolean validateNumber(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean validateQuantity(String inputName, String inputQuantity) {
        int number = Integer.parseInt(inputQuantity);
        if (number == 0) {
            System.out.println(ErrorMessage.INPUT_FORMAT_IS_WRONG.getErrorMessage());
            return false;
        }
        if (number > getProductNumber(inputName)) {
            System.out.println(ErrorMessage.NOT_ENOUGH_QUANTITY.getErrorMessage());
            return false;
        }
        return true;
    }

    private static int getProductNumber(String input) {
        int countNumber = 0;
        for (Product product : Application.products) {
            if (product.getName().equals(input)) {
                countNumber += product.getQuantity();
            }
        }
        return countNumber;
    }

}
