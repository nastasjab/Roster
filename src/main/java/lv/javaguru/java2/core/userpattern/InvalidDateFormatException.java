package lv.javaguru.java2.core.userpattern;

public class InvalidDateFormatException extends Exception {

    public InvalidDateFormatException(String msg) {
        super(String.format("Invalid %s date format!", msg));
    }

    public InvalidDateFormatException() {
        super("Invalid time format!");
    }
}
