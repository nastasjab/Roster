package lv.javaguru.java2.domain.roster;

import lv.javaguru.java2.domain.shift.Shift;

import java.sql.Date;

public class SingleShiftBuilder extends SingleShift {

    private SingleShiftBuilder() {}

    public static SingleShiftBuilder createSingleShift() {
        return new SingleShiftBuilder();
    }

    public SingleShift build() {

        SingleShift singleShift = new SingleShift();
        singleShift.setId(id);
        singleShift.setShift(shift);
        singleShift.setDate(date);
        singleShift.setUserId(userId);
        return singleShift;
    }

    public SingleShiftBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public SingleShiftBuilder withShift(Shift shift) {
        this.shift = shift;
        return this;
    }

    public SingleShiftBuilder withDate(Date date) {
        this.date = date;
        return this;
    }

    public SingleShiftBuilder withUserId(long userId) {
        this.userId = userId;
        return this;
    }
}
