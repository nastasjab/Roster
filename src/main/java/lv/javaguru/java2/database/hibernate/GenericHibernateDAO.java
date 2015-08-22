package lv.javaguru.java2.database.hibernate;


import lv.javaguru.java2.database.DBException;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericHibernateDAO<T> {

    private Class<T> persistentClass;

    public GenericHibernateDAO() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Autowired
    SessionFactory sessionFactory;



    @Transactional
    public void create(T obj)  throws JDBCException {
        sessionFactory.getCurrentSession().save(obj);
    }

    @Transactional
    public T getById(long id)  throws JDBCException {
        return (T)sessionFactory.getCurrentSession().get(persistentClass, id);
    }

    @Transactional
    public void delete(long id)   throws JDBCException{
        Session session = sessionFactory.getCurrentSession();
        T obj = (T) session.get(persistentClass, id);
        session.delete(obj);
    }

    @Transactional
    public void update(T obj)  throws JDBCException {
        sessionFactory.getCurrentSession().update(obj);
    }

    @Transactional
    public List<T> getAll()  throws JDBCException {
        return sessionFactory.getCurrentSession().createCriteria(persistentClass).list();
    }

    // TODO for Andrew
    /*
    @Transactional
    public boolean isEmpty() throws DBException {
        return sessionFactory.getCurrentSession().createCriteria(persistentClass).add(Restrictions.isEmpty(???)).list() == 0;
*/
}
