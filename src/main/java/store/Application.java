package store;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import utill.FileReader;
import view.InputView;
import view.OutputView;

public class Application {

    public final static String productsMdDirectory = "src/main/resources/products.md";
    public final static String promotionsMdDirectory = "src/main/resources/promotions.md";
    public final static Predicate<String> YES_OR_NO_INPUT_VALIDATE = (value) -> !List.of("Y", "N").contains(value);
    public final static String YES_OR_NO_INPUT_ERROR_MESSAGE =
            ErrorMessage.INPUT_FORMAT_IS_WRONG.getMessage();

    public static void main(String[] args) {
        List<Product> products = loadProductInfos();
        ConvenienceStore convenienceStore = new ConvenienceStore(products);
        Station station = new Station(convenienceStore, products);
        do {
            OutputView.printStartPurchase(products);
            Customer customer = station.getPurchaseProducts();
            station.purchase();
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

}
