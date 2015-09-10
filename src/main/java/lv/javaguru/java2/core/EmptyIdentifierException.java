package lv.javaguru.java2.core;

public class EmptyIdentifierException extends Exception{
    public EmptyIdentifierException() {
        super("Empty object id!");
    }

    public EmptyIdentifierException(String objectName) {
        super(String.format("Empty %s id!", objectName));
    }
}
