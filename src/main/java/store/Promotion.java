package store;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Promotion {

    private final String name;
    private final int purchaseQuantity;
    private final int bonusQuantity;
    private final LocalDateTime startDay;
    private final LocalDateTime endDay;

    public Promotion(String name, int purchaseQuantity, int bonusQuantity, LocalDateTime startDay,
                     LocalDateTime endDay) {
        this.name = name;
        this.purchaseQuantity = purchaseQuantity;
        this.bonusQuantity = bonusQuantity;
        this.startDay = startDay;
        this.endDay = endDay;
    }

    public String getName() {
        return name;
    }

    public int getAppliableQuantity(int quantity) {
        int bundleSize = purchaseQuantity + bonusQuantity;
        if (quantity % bundleSize == purchaseQuantity) {
            return (quantity / bundleSize + 1) * bundleSize;
        }
        return (quantity / bundleSize) * bundleSize;
    }

    public int getBonusQuantity(int quantity) {
        if (quantity < purchaseQuantity + bonusQuantity) {
            return 0;
        }
        return quantity / (purchaseQuantity + bonusQuantity);
    }

    public boolean checkValidatePromotionDate(LocalDateTime curDate) {
        return curDate.isAfter(startDay) && curDate.isBefore(endDay);
    }

    public int getBundleSize() {
        return this.bonusQuantity + this.purchaseQuantity;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-M-d");
        return "Promotion{" +
                "name='" + name + '\'' +
                ", purchaseQuantity=" + purchaseQuantity +
                ", bonusQuantity=" + bonusQuantity +
                ", startDay=" + dateFormat.format(startDay) +
                ", endDay=" + dateFormat.format(endDay) +
                '}';
    }
}
