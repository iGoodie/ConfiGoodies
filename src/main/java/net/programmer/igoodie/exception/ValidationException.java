package net.programmer.igoodie.exception;

public class ValidationException extends RuntimeException {

    protected String propertyName;
    protected String reason;

    public ValidationException(String propertyName) {
        this(propertyName, "Unknown reason");
    }

    public ValidationException(String propertyName, String reason) {
        this.propertyName = propertyName;
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return String.format("Invalid %s -> %s", propertyName, reason);
    }

}
