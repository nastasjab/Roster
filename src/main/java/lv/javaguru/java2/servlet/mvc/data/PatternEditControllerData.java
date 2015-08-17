package lv.javaguru.java2.servlet.mvc.data;


import lv.javaguru.java2.domain.Pattern;
import lv.javaguru.java2.domain.PatternShift;

import java.util.ArrayList;
import java.util.List;

public class PatternEditControllerData extends Pattern {

    private List<PatternShift> patternShifts;

    public List<PatternShift> getPatternShifts() {
        if (patternShifts == null)
            patternShifts = new ArrayList<PatternShift>();

        return patternShifts;
    }

    public void setPatternShifts(List<PatternShift> patternShifts) {

        this.patternShifts = new ArrayList<PatternShift>(patternShifts);
    }




}
