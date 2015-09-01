package lv.javaguru.java2.core;


public class ObjectNotExistException extends  Exception{
    public ObjectNotExistException() {
        super("Object with given identifier doesn't exist!");
    }

    public ObjectNotExistException(String message) {
        super(String.format("Object '%s' doesn't exist!", message));
    }
}
