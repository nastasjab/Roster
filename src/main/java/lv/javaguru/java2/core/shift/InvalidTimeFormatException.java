package lv.javaguru.java2.core.shift;


public class InvalidTimeFormatException extends Exception{

    public InvalidTimeFormatException(String msg) {
        super(String.format("Invalid %s time format! Must be HH:MM", msg));
    }

    public InvalidTimeFormatException() {
        super("Invalid time format! Must be HH:MM");
    }
}
