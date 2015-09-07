package lv.javaguru.java2.domain.shift;

public class ShiftBuilder extends Shift{

    private ShiftBuilder() {}

    public static ShiftBuilder createShift() {
        return new ShiftBuilder();
    }

    public Shift build() {
        Shift shift = new Shift();
        shift.setId(id);
        shift.setName(name);
        shift.setShiftStarts(shiftStarts);
        shift.setShiftEnds(shiftEnds);
        return shift;
    }

    public ShiftBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ShiftBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ShiftBuilder withShiftStarts(String shiftStarts) {
        this.shiftStarts = shiftStarts;
        return this;
    }

    public ShiftBuilder withShiftEnds(String shiftEnds) {
        this.shiftEnds = shiftEnds;
        return this;
    }

}
