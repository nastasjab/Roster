package lv.javaguru.java2.servlet.mvc.data;


import lv.javaguru.java2.domain.Shift;
import lv.javaguru.java2.domain.PatternShift;

import java.util.ArrayList;
import java.util.List;

public class PatternShiftEditControllerData extends PatternShift {

    private List<Shift> allShifts;

    public List<Shift> getAllShifts() {
        if (allShifts == null)
            allShifts = new ArrayList<Shift>();

        return allShifts;
    }

    public void setAllShifts(List<Shift> allShifts) {
        this.allShifts = new ArrayList<Shift>(allShifts);
    }




}
