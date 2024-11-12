package store;

import java.util.List;
import parser.InputParser;
import view.InputView;
import view.OutputView;

public class Station {

    public final static String PRODUCT_INFO_DELIMITER = ",";

    private final ConvenienceStore convenienceStore;
    private final List<Product> products;
    private Customer customer;

    public Station(ConvenienceStore convenienceStore, List<Product> products) {
        this.convenienceStore = convenienceStore;
        this.products = products;
    }

    public Customer getPurchaseProducts() {
        customer = new Customer();
        boolean isValidated = false;
        while (!isValidated) {
            String purchaseProductAndQuantity = InputView.getPurchaseProductAndQuantity();
            List<String> purchaseProducts = InputParser.splitInput(purchaseProductAndQuantity, PRODUCT_INFO_DELIMITER);
            for (String purchaseProduct : purchaseProducts) {
                isValidated = purchaseWhenValidate(purchaseProduct);
            }
        }
        return customer;
    }

    private boolean purchaseWhenValidate(String product) {
        try {
            if (!validatePurchase(InputParser.getProductName(product), InputParser.getProductQuantity(product))) {
                return false;
            }
            customer.purchase(InputParser.getProductName(product), InputParser.getProductQuantity(product));
        } catch (IllegalArgumentException e) {
            System.out.println(ErrorMessage.INPUT_FORMAT_IS_WRONG.getMessage());
            return false;
        }
        return true;
    }

    private boolean validatePurchase(String productName, int productQuantity) {
        if (!isValidProductName(products, productName)) {
            OutputView.printProductNotExistMessage();
            return false;
        }
        if (!isValidProductQuantity(convenienceStore, productName, productQuantity)) {
            OutputView.printNotEnoughQuantityMessage();
            return false;
        }
        return true;
    }

    private boolean isValidProductName(List<Product> products, String productName) {
        return products.stream().anyMatch(product -> product.getName().equals(productName));
    }

    private boolean isValidProductQuantity(ConvenienceStore convenienceStore,
                                           String productName,
                                           int productQuantity) {
        return convenienceStore.checkEnoughQuantity(productName, productQuantity);
    }

    public void purchase() {
        for (String purchaseItemName : customer.getItemNames()) {
            int quantity = customer.getItemCount(purchaseItemName);
            int promotionAppliedQuantity = convenienceStore.getPromotionAppliedQuantity(purchaseItemName, quantity);
            if (promotionAppliedQuantity > quantity) {
                purchasePromotionQuantityIsEnough(purchaseItemName, promotionAppliedQuantity, quantity);
            }
            if (0 < promotionAppliedQuantity && promotionAppliedQuantity < quantity) {
                purchasePromotionQuantityIsNotEnough(purchaseItemName, promotionAppliedQuantity, quantity);
            }
        }
    }

    private void purchasePromotionQuantityIsEnough(String purchaseItemName,
                                                   int promotionAppliedQuantity,
                                                   int quantity) {
        String add = InputView.getValueUntilValidated(
                Application.YES_OR_NO_INPUT_VALIDATE,
                Application.YES_OR_NO_INPUT_ERROR_MESSAGE,
                () -> InputView.getIntentionAddPromotionProduct(purchaseItemName,
                        promotionAppliedQuantity - quantity));
        if (add.equals("Y")) {
            customer.purchase(purchaseItemName, promotionAppliedQuantity - quantity);
        }
    }

    private void purchasePromotionQuantityIsNotEnough(String purchaseItemName,
                                                      int promotionAppliedQuantity, int quantity) {
        String apply = InputView.getValueUntilValidated(
                Application.YES_OR_NO_INPUT_VALIDATE,
                Application.YES_OR_NO_INPUT_ERROR_MESSAGE,
                () -> InputView.getIntentionNotApplyPromotion(purchaseItemName,
                        quantity - promotionAppliedQuantity));
        if (apply.equals("N")) {
            customer.cancelPurchase(purchaseItemName, quantity - promotionAppliedQuantity);
        }
    }
}
