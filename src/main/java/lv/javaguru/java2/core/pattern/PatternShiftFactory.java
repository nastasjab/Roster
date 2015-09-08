package lv.javaguru.java2.core.pattern;

import lv.javaguru.java2.core.GenericService;
import lv.javaguru.java2.domain.Generic;

public interface PatternShiftFactory extends GenericService {
    Generic getObject(long id, long patternId)  ;
    int getNextSequence (long patternId) ;
}
