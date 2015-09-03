package lv.javaguru.java2.core.userpattern;

/**
 * Created by Boss on 03.09.2015.
 */
public class UserPatternOverlapException extends Exception{
    public UserPatternOverlapException() {
        super("Patterns overlap!");
    }

    public UserPatternOverlapException(String message) {
        super(String.format("Pattern '%s' overlaps with another!", message));
    }

    public UserPatternOverlapException(String pattern1, String pattern2) {
        super(String.format("Pattern '%s' overlaps with pattern '%s'!", pattern1, pattern2));
    }
}
