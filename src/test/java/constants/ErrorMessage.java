package constants;

public enum ErrorMessage {
    MISSING_USERNAME("Username is required"),
    MISSING_PASSWORD("Enter Password");
    private final String value;

    ErrorMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
