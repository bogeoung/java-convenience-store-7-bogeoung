package view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public static final String PURCHASE_PRODUCT_MESSAGE = "\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";

    public String startPurchase() {
        System.out.println(PURCHASE_PRODUCT_MESSAGE);
        return readItem();
    }

    public String readItem() {
        return Console.readLine();
    }
}