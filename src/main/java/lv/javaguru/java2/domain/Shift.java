package lv.javaguru.java2.domain;

public class Shift extends Generic{

    private String name = "";
    private String shiftStarts = "";
    private String shiftEnds = "";

    public Shift() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShiftStarts() {
        return shiftStarts;
    }

    public void setShiftStarts(String shiftStarts) {
        this.shiftStarts = shiftStarts;
    }

    public String getShiftEnds() {
        return shiftEnds;
    }

    public void setShiftEnds(String shiftEnds) {
        this.shiftEnds = shiftEnds;
    }
}
