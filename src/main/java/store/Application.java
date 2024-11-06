package store;

import java.io.IOException;
import java.util.List;
import utill.FileReader;

public class Application {

    public final static String productsMdDirectory = "src/main/resources/products.md";
    public final static String promotionsMdDirectory = "src/main/resources/promotions.md";

    public static void main(String[] args) {
        try {
            List<String> promotionInfos = FileReader.parseMdFile(promotionsMdDirectory);
            promotionInfos.removeFirst();
            List<String> productInfos = FileReader.parseMdFile(productsMdDirectory);
            productInfos.removeFirst();
            Promotions promotions = new Promotions(MdFilePromotionInfoParser.parseAll(promotionInfos));
            List<Product> products = MdFileProductInfoParser.parseAll(productInfos, promotions);

            products.forEach(System.out::println);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
