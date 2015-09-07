package lv.javaguru.java2.core.roster;

public class ShiftAlreadyInRosterException extends Exception {

    public ShiftAlreadyInRosterException() {
        super("Such shift already in roster!");
    }
}
