package lv.javaguru.java2.core;

import lv.javaguru.java2.domain.Generic;

import java.util.List;

public interface GenericService {

    Generic getObject(long id);
    void addObject(Generic object) throws Exception;
    void updateObject(Generic object) throws Exception;
    void deleteObject(long id) ;
    List<Generic> getAll() ;
}
