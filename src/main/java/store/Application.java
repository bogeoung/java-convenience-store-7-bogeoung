package store;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import parser.InputParser;
import utill.FileReader;
import view.InputView;
import view.OutputView;

public class Application {

    public final static String productsMdDirectory = "src/main/resources/products.md";
    public final static String promotionsMdDirectory = "src/main/resources/promotions.md";
    public final static String PRODUCT_INFO_DELIMITER = ",";
    public final static Predicate<String> YES_OR_NO_INPUT_VALIDATE = (value) -> !List.of("Y", "N").contains(value);
    public final static String YES_OR_NO_INPUT_ERROR_MESSAGE =
            ErrorMessage.INPUT_FORMAT_IS_WRONG.getMessage();

    public static void main(String[] args) {
        List<Product> products = loadProductInfos();
        ConvenienceStore convenienceStore = new ConvenienceStore(products);
        do {
            OutputView.printStartPurchase(products);
            Customer customer = getPurchaseProducts(convenienceStore, products);
            purchase(convenienceStore, customer);
            Receipt receipt = convenienceStore.sellProducts(getPurchaseInfos(customer), getMembershipDiscountApply());
            OutputView.printTotalReceipt(receipt);
        } while (!getContinueIntention().equals("N"));
    }

    private static List<Product> loadProductInfos() {
        try {
            List<String> promotionInfos = FileReader.parseMdFile(promotionsMdDirectory);
            promotionInfos.removeFirst();
            List<String> productInfos = FileReader.parseMdFile(productsMdDirectory);
            productInfos.removeFirst();
            Promotions promotions = new Promotions(MdFilePromotionInfoParser.parseAll(promotionInfos));
            return MdFileProductInfoParser.parseAll(productInfos, promotions);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Customer getPurchaseProducts(ConvenienceStore convenienceStore, List<Product> products) {
        Customer customer = new Customer();
        boolean isValidated = false;
        while (!isValidated) {
            String purchaseProductAndQuantity = InputView.getPurchaseProductAndQuantity();
            List<String> purchaseProducts = InputParser.splitInput(purchaseProductAndQuantity, PRODUCT_INFO_DELIMITER);
            for (String purchaseProduct : purchaseProducts) {
                isValidated = purchaseWhenValidate(convenienceStore, products, purchaseProduct, customer);
            }
        }
        return customer;
    }

    private static List<PurchaseInfo> getPurchaseInfos(Customer customer) {
        return customer.getItemNames().stream()
                .map(productName -> new PurchaseInfo(productName, customer.getItemCount(productName))).toList();
    }

    private static String getMembershipDiscountApply() {
        return InputView.getValueUntilValidated(YES_OR_NO_INPUT_VALIDATE,
                YES_OR_NO_INPUT_ERROR_MESSAGE,
                InputView::getMembershipDiscountApply);
    }

    private static String getContinueIntention() {
        return InputView.getValueUntilValidated(YES_OR_NO_INPUT_VALIDATE,
                YES_OR_NO_INPUT_ERROR_MESSAGE,
                InputView::continuePurchase);
    }

    private static boolean purchaseWhenValidate(ConvenienceStore convenienceStore, List<Product> products,
                                                String product, Customer customer) {
        try {
            if (!validatePurchaseProduct(products, InputParser.getProductName(product), convenienceStore,
                    InputParser.getProductQuantity(product))) {
                return false;
            }
            customer.purchase(InputParser.getProductName(product), InputParser.getProductQuantity(product));
        } catch (IllegalArgumentException e) {
            System.out.println(ErrorMessage.INPUT_FORMAT_IS_WRONG.getMessage());
            return false;
        }
        return true;
    }

    private static boolean validatePurchaseProduct(List<Product> products, String productName,
                                                   ConvenienceStore convenienceStore, int productQuantity) {
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

    private static boolean isValidProductName(List<Product> products, String productName) {
        return products.stream().anyMatch(product -> product.getName().equals(productName));
    }

    private static boolean isValidProductQuantity(ConvenienceStore convenienceStore,
                                                  String productName,
                                                  int productQuantity) {
        return convenienceStore.checkEnoughQuantity(productName, productQuantity);
    }

    private static void purchase(ConvenienceStore convenienceStore, Customer customer) {
        for (String purchaseItemName : customer.getItemName()) {
            int quantity = customer.getItemCount(purchaseItemName);
            int promotionAppliedQuantity = convenienceStore.getPromotionAppliedQuantity(purchaseItemName, quantity);
            if (promotionAppliedQuantity > quantity) {
                purchasePromotionQuantityIsEnough(customer, purchaseItemName, promotionAppliedQuantity, quantity);
            }
            if (0 < promotionAppliedQuantity && promotionAppliedQuantity < quantity) {
                purchasePromotionQuantityIsNotEnough(customer, purchaseItemName, promotionAppliedQuantity, quantity);
            }
        }
    }

    private static void purchasePromotionQuantityIsEnough(Customer customer, String purchaseItemName,
                                                          int promotionAppliedQuantity,
                                                          int quantity) {
        String add = InputView.getValueUntilValidated(
                YES_OR_NO_INPUT_VALIDATE,
                YES_OR_NO_INPUT_ERROR_MESSAGE,
                () -> InputView.getIntentionAddPromotionProduct(purchaseItemName,
                        promotionAppliedQuantity - quantity));
        if (add.equals("Y")) {
            customer.purchase(purchaseItemName, promotionAppliedQuantity - quantity);
        }
    }

    private static void purchasePromotionQuantityIsNotEnough(Customer customer, String purchaseItemName,
                                                             int promotionAppliedQuantity, int quantity) {
        String apply = InputView.getValueUntilValidated(
                YES_OR_NO_INPUT_VALIDATE,
                YES_OR_NO_INPUT_ERROR_MESSAGE,
                () -> InputView.getIntentionNotApplyPromotion(purchaseItemName,
                        quantity - promotionAppliedQuantity));
        if (apply.equals("N")) {
            customer.cancelPurchase(purchaseItemName, quantity - promotionAppliedQuantity);
        }
    }

}
