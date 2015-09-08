package lv.javaguru.java2.core.user;

public class InvalidEmailException extends Exception{
    public InvalidEmailException() {
        super("Invalid Email Address");
    }
}
