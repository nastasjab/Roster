package lv.javaguru.java2.core;

public class EmptyIdentifierException extends  Exception {
    public EmptyIdentifierException() {
        super("Empty object name!");
    }

    public EmptyIdentifierException(String message) {
        super(String.format("Empty %s name!", message));
    }
}
