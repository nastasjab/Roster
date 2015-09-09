package lv.javaguru.java2.core.pattern;

import lv.javaguru.java2.core.GenericFactory;
import lv.javaguru.java2.domain.Generic;

public interface PatternShiftFactory extends GenericFactory {
    Generic getObject(long id, long patternId)  ;
    int getNextSequence (long patternId) ;
}
