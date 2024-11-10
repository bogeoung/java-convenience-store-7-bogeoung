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
            ErrorMessage.INPUT_FORMAT_IS_WRONG.getErrorMessage();

    public static void main(String[] args) {
        List<Product> products = loadProductInfos();
        ConvenienceStore convenienceStore = new ConvenienceStore(products);
        while (true) {
            OutputView.printStartPurchase(products);
            Customer customer = getPurchaseProducts(convenienceStore, products);
            purchase(convenienceStore, customer);
            String membershipDiscountApply = InputView.getValueUntilValidated(YES_OR_NO_INPUT_VALIDATE,
                    YES_OR_NO_INPUT_ERROR_MESSAGE,
                    InputView::getMembershipDiscountApply);
            List<PurchaseInfo> purchaseInfos = customer.getItemNames().stream()
                    .map(productName -> new PurchaseInfo(productName, customer.getItemCount(productName))).toList();
            Receipt receipt = convenienceStore.purchase(purchaseInfos, membershipDiscountApply);
            OutputView.printTotalReceipt(receipt, membershipDiscountApply);
            String continuous = InputView.getValueUntilValidated(YES_OR_NO_INPUT_VALIDATE,
                    YES_OR_NO_INPUT_ERROR_MESSAGE,
                    InputView::continuePurchase);
            if (continuous.equals("N")) {
                break;
            }
        }

    }

    private static Customer getPurchaseProducts(ConvenienceStore convenienceStore, List<Product> products) {
        Customer customer = new Customer();
        boolean isValidated = false;
        while (!isValidated) {
            String purchaseProductAndQuantity = InputView.getPurchaseProductAndQuantity();
            List<String> purchaseProducts = InputParser.splitInput(purchaseProductAndQuantity, PRODUCT_INFO_DELIMITER);
            for (String purchaseProduct : purchaseProducts) {
                try {
                    isValidated = true;
                    String productName = InputParser.getProductName(purchaseProduct);
                    int productQuantity = InputParser.getProductQuantity(purchaseProduct);
                    if (!isValidProductName(products, productName)) {
                        OutputView.printProductNotExistMessage();
                        isValidated = false;
                        break;
                    }
                    if (!isValidProductQuantity(convenienceStore, productName, productQuantity)) {
                        OutputView.printNotEnoughQuantityMessage();
                        isValidated = false;
                        break;
                    }
                    customer.purchase(productName, productQuantity);
                } catch (IllegalArgumentException | NullPointerException e) {
                    System.out.println(ErrorMessage.INPUT_FORMAT_IS_WRONG.getErrorMessage());
                    isValidated = false;
                    break;
                }
            }
        }
        return customer;
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
                String add = InputView.getValueUntilValidated(
                        YES_OR_NO_INPUT_VALIDATE,
                        YES_OR_NO_INPUT_ERROR_MESSAGE,
                        () -> InputView.getIntentionAddPromotionProduct(purchaseItemName,
                                promotionAppliedQuantity - quantity));
                if (add.equals("Y")) {
                    customer.purchase(purchaseItemName, promotionAppliedQuantity - quantity);
                }
            }
            if (0 < promotionAppliedQuantity && promotionAppliedQuantity < quantity) {
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
    }
}