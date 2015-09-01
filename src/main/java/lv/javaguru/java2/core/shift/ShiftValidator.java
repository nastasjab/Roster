package lv.javaguru.java2.core.shift;

public interface ShiftValidator {
    void validateStartTime(String startTime) throws Exception ;
    void validateEndTime(String endTime) throws Exception;
    void validateName(String name, long id, boolean add) throws Exception;
}
