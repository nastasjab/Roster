package lv.javaguru.java2.database;


import lv.javaguru.java2.domain.ShiftPattern;

import java.util.List;

public interface ShiftPatternDAO {
    void create(ShiftPattern shift) throws DBException;

    ShiftPattern getById(long id) throws DBException;

    void delete(long id) throws DBException;

    void update(ShiftPattern shift) throws DBException;

    List<ShiftPattern> getAll() throws DBException;
}