package store;

public enum ErrorMessage {
    INCORRECT_PRODUCT_AND_QUANTITY_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NOT_SELLING_PRODUCT("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    NOT_ENOUGH_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INPUT_FORMAT_IS_WRONG("잘못된 입력입니다. 다시 입력해 주세요.\n"),
    MD_FILE_DATE_DATA_IS_WRONG("MD파일의 날짜 정보가 올바르지 않습니다."),
    MD_FILE_DATA_IS_WRONG_FORMAT("MD파일의 형식이 올바르지 않습니다.");

    private final String content;

    ErrorMessage(String content) {
        this.content = content;
    }

    public String getMessage() {
        return "[ERROR] " + content;
    }
}
