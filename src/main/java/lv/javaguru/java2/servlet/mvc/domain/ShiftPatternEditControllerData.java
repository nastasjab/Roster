package lv.javaguru.java2.servlet.mvc.domain;


import lv.javaguru.java2.domain.Shift;
import lv.javaguru.java2.domain.ShiftPattern;

import java.util.ArrayList;
import java.util.List;

public class ShiftPatternEditControllerData extends ShiftPattern {

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
