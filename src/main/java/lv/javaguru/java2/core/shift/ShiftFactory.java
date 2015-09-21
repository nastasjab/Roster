package lv.javaguru.java2.core.shift;

import lv.javaguru.java2.core.GenericFactory;
import lv.javaguru.java2.domain.shift.Shift;

import java.util.List;

public interface ShiftFactory extends GenericFactory {
    public List<Shift> getWorkingShifts();
}
