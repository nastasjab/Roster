package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.Pattern;

import java.util.List;

public interface PatternDAO {
    void create(Pattern shift) throws DBException;

    Pattern getById(long id) throws DBException;

    void delete(long id) throws DBException;

    void update(Pattern shift) throws DBException;

    List<Pattern> getAll() throws DBException;
}
