package lv.javaguru.java2.core;

import lv.javaguru.java2.database.GenericDAO;
import lv.javaguru.java2.domain.Generic;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class GenericFactoryImpl
       < T extends GenericDAO,
        R extends Generic> implements GenericFactory {
    @Autowired
    public T dao;

    public List<Generic> getAll() throws Exception  {
        return dao.getAll();
    }

    public Generic getObject(long id) throws Exception{
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
        validate(object, true);
        dao.create(object);
    }

    public void updateObject(Generic object) throws Exception {
        R result = (R)dao.getById(object.getId());
        if (result==null)
            throw new ObjectNotExistException();

        validate(object, false);
        dao.update(object);
    }

    public void deleteObject(long id) throws Exception {
        R result = (R)dao.getById(id);
        if (result==null)
            throw new ObjectNotExistException();

        dao.delete(id);
    }

    public abstract void validate(Generic object, boolean add) throws Exception;

    public abstract R getNewInstance();
}
