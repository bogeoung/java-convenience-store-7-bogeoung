package store;

public enum ErrorMessage {
    INCORRECT_PRODUCT_AND_QUANTITY_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NOT_SELLING_PRODUCT("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    NOT_ENOUGH_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INPUT_FORMAT_IS_WRONG("잘못된 입력입니다. 다시 입력해 주세요.\n");

    private final String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return "[ERROR] " + errorMessage;
    }
}
