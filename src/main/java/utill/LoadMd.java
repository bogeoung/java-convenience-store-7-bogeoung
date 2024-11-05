package utill;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.Product;

public class LoadMd {

    public static List<Product> loadProductsFromFile(String fileName) {
        List<Product> products = new ArrayList<Product>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));) {
            String line;
            while ((line = reader.readLine()) != null) {
                products.add(enrollProduct(line));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    private static Product enrollProduct(String line) {
        Product product = null;
        String[] parts = line.split(",");
        if (!parts[0].equals("name") && LoadMdValidate.isValid(parts)) {
            product = new Product(parts[0].strip(), Integer.parseInt(parts[1].strip()),
                    Integer.parseInt(parts[2].strip()), parts[3].strip());
        }
        return product;
    }
}
