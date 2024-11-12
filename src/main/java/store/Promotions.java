package store;

import java.util.List;
import java.util.Optional;

public class Promotions {

    private final List<Promotion> promotions;

    public Promotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public Optional<Promotion> getPromotionWithName(String name) {
        return promotions.stream().filter(promotion -> promotion.getName().equals(name))
                .findFirst();
    }
}
