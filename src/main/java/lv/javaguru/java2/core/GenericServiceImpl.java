package lv.javaguru.java2.core;

import lv.javaguru.java2.database.GenericDAO;
import lv.javaguru.java2.domain.Generic;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class GenericServiceImpl
       < T extends GenericDAO,
        R extends Generic> implements  GenericService {
    @Autowired
    private T dao;

    public List<Generic> getAll()  {
        return dao.getAll();
    }

    public Generic getObject(long id) {
        R result=null;
        try {
            result = (R)dao.getById(id);
        } catch (NullPointerException e) {
            result = getNewInstance();
        }

        if (result==null)
            result = getNewInstance();

        return result;
    }

    public void addObject(Generic object) throws Exception {
        validate(object);
        dao.create(object);
    }

    public void updateObject(Generic object) throws Exception {
        validate(object);
        dao.update(object);
    }

    public void deleteObject(long id)  {
        dao.delete(id);
    }

    protected abstract void validate(Generic object) throws Exception;

    public abstract R getNewInstance();
}
