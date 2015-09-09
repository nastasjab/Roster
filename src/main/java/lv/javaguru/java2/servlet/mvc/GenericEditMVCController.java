package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.core.GenericFactory;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.servlet.mvc.data.MessageContents;
import org.hibernate.JDBCException;

import javax.servlet.http.HttpServletRequest;

public abstract class GenericEditMVCController {

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

    protected abstract GenericFactory getService();
    protected abstract String getObjectName();
    protected abstract String getEditPageAddressJSP();
    protected abstract String getListPageAddress(HttpServletRequest req);
    protected abstract Generic fillParameters(HttpServletRequest req) throws Exception;

    protected MVCModel listObject(HttpServletRequest req) throws Exception {
        return new MVCModel(getService().getObject(getId(req)), getEditPageAddressJSP());
    }

    protected MVCModel addObject(HttpServletRequest req) throws Exception {
        getService().addObject(fillParameters(req));

        return new MVCModel(
                new MessageContents(
                        "New "+getObjectName()+" created",
                        "New "+getObjectName()+" created",
                        getListPageAddress(req),
                        "Back to "+getObjectName()+"s List"), "/message.jsp");
    }

    protected MVCModel updateObject(HttpServletRequest req) throws Exception {
        Generic object = fillParameters(req);
        object.setId(getId(req));
        getService().updateObject(object);

        return new MVCModel(
                new MessageContents(
                        getObjectName()+" updated",
                        getObjectName()+" updated",
                        getListPageAddress(req),
                        "Back to "+getObjectName()+"s List"), "/message.jsp");
    }

    protected MVCModel deleteObject(HttpServletRequest req) throws Exception {
        getService().deleteObject(getId(req));

        return new MVCModel(
                new MessageContents(
                        getObjectName()+" deleted",
                        getObjectName()+" deleted",
                        getListPageAddress(req),
                        "Back to "+getObjectName()+"s List"), "/message.jsp");
    }

    protected long getId(HttpServletRequest req)  {
        long result = 0;
        try {
            result = Long.decode(req.getParameter("id"));
        } catch (NumberFormatException e){
            e.printStackTrace();
        }catch (NullPointerException e){
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
