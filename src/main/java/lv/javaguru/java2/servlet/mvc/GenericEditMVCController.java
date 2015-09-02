package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.GenericDAO;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.servlet.mvc.data.MessageContents;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public abstract class GenericEditMVCController<T extends GenericDAO, R extends Generic> {

    @Autowired
    private T dao;

    public MVCModel processRequest(HttpServletRequest req)  {
        PageAction action = getPageAction(req);
        try{
            switch (action){
                case ADD : return addObject(req);
                case UPDATE : return updateObject(req);
                case DELETE : return deleteObject(req);
                case LIST :
                default:   return listObject(req);
            }
        }

        catch (JDBCException e){
            return new MVCModel(
                    new MessageContents(
                            e.getSQLException().getMessage(),
                            e.getSQLException().getMessage(),
                            "javascript:history.back()",
                            "Back"), "/message.jsp");
        }
        catch (Exception e){
            return new MVCModel(
                    new MessageContents(
                            e.getMessage(),
                            e.getMessage(),
                            "javascript:history.back()",
                            "Back"), "/message.jsp");
        }
    }

    protected abstract String getObjectName();
    protected abstract String getEditPageAddressJSP();
    protected abstract String getListPageAddress(HttpServletRequest req);
    protected abstract R getNewInstance();
    protected abstract void fillParameters(HttpServletRequest req, R object) throws Exception;

    protected MVCModel listObject(HttpServletRequest req) throws Exception {
        R result = null;
        try {
            long id = getId(req);
            result = (R)dao.getById(id);
        } catch (NullPointerException e) {
            result = getNewInstance();
        }

        return new MVCModel(result, getEditPageAddressJSP());
    }

    protected MVCModel addObject(HttpServletRequest req) throws Exception {
        R object = getNewInstance();
        fillParameters(req, object);

        dao.create(object);

        return new MVCModel(
                new MessageContents(
                        "New "+getObjectName()+" created",
                        "New "+getObjectName()+" created",
                        getListPageAddress(req),
                        "Back to "+getObjectName()+"s List"), "/message.jsp");
    }

    protected MVCModel updateObject(HttpServletRequest req) throws Exception {
        R object = getNewInstance();
        object.setId(getId(req));
        fillParameters(req, object);

        dao.update(object);

        return new MVCModel(
                new MessageContents(
                        getObjectName()+" updated",
                        getObjectName()+" updated",
                        getListPageAddress(req),
                        "Back to "+getObjectName()+"s List"), "/message.jsp");
    }

    protected MVCModel deleteObject(HttpServletRequest req) throws Exception {
        deleteChildObjects(req);
        dao.delete(getId(req));

        return new MVCModel(
                new MessageContents(
                        getObjectName()+" deleted",
                        getObjectName()+" deleted",
                        getListPageAddress(req),
                        "Back to "+getObjectName()+"s List"), "/message.jsp");
    }

    protected void deleteChildObjects(HttpServletRequest req) throws Exception {
        // override if additional cascade delete is required
    }

    protected long getId(HttpServletRequest req) throws NullPointerException {
        long result = 0;
        try {
            result = Long.decode(req.getParameter("id"));
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return result;
    }

    private PageAction getPageAction(HttpServletRequest req){
        if (req.getParameter("act_add") != null) return PageAction.ADD;
        if (req.getParameter("act_update") != null) return PageAction.UPDATE;
        if (req.getParameter("act_delete") != null) return PageAction.DELETE;
        return PageAction.LIST;
    }
}
