package lv.javaguru.java2.core;

public class EmptyObjectNameException extends  Exception {
    public EmptyObjectNameException() {
        super("Empty object name!");
    }

    public EmptyObjectNameException(String message) {
        super(String.format("Empty %s name!", message));
    }
}
