package lv.javaguru.java2.core;

public class ObjectExistException extends Exception {
    public ObjectExistException() {
        super("Object with given identifier already exist!");
    }

    public ObjectExistException(String message) {
        super(String.format("Object '%s' already exist!", message));
    }
}
