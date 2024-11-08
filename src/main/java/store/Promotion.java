package store;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Promotion {

    private final String name;
    private final int purchaseQuantity;
    private final int bonusQuantity;
    private final Date startDay;
    private final Date endDay;

    public Promotion(String name, int purchaseQuantity, int bonusQuantity, Date startDay, Date endDay) {
        this.name = name;
        this.purchaseQuantity = purchaseQuantity;
        this.bonusQuantity = bonusQuantity;
        this.startDay = startDay;
        this.endDay = endDay;
    }

    public String getName() {
        return name;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public int getBonusQuantity() {
        return bonusQuantity;
    }

    public Date getStartDay() {
        return startDay;
    }

    public Date getEndDay() {
        return endDay;
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
