package lv.javaguru.java2.servlet.mvc.data;


import lv.javaguru.java2.domain.Pattern;
import lv.javaguru.java2.domain.ShiftPattern;

import java.util.ArrayList;
import java.util.List;

public class PatternEditControllerData extends Pattern {

    private List<ShiftPattern> shiftPatterns;

    public List<ShiftPattern> getShiftPatterns() {
        if (shiftPatterns == null)
            shiftPatterns = new ArrayList<ShiftPattern>();

        return shiftPatterns;
    }

    public void setShiftPatterns(List<ShiftPattern> shiftPatterns) {

        this.shiftPatterns = new ArrayList<ShiftPattern>(shiftPatterns);
    }




}
