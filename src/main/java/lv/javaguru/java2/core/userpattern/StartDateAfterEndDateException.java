package lv.javaguru.java2.core.userpattern;

public class StartDateAfterEndDateException extends Exception{
    public StartDateAfterEndDateException() {
        super("Start day can not be after end day!");
    }
}
