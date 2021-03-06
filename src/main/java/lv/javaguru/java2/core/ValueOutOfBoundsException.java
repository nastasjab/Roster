package lv.javaguru.java2.core;

public class ValueOutOfBoundsException extends Exception{
    public ValueOutOfBoundsException() {
        super("Value is out of bounds!");
    }

    public ValueOutOfBoundsException(String message) {
        super(String.format("'%s' is out of bounds!", message));
    }

    public ValueOutOfBoundsException(String message, String bounds) {
        super(String.format("'%s' is out of bounds! '%s'", message, bounds));
    }
}
