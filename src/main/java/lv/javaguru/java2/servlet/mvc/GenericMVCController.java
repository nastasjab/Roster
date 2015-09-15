package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.core.GenericFactory;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.servlet.mvc.data.MessageContents;
import org.hibernate.JDBCException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public abstract class GenericMVCController {

    protected abstract GenericFactory getService();
    protected abstract String getObjectName();
    protected abstract String getListPageAddress(HttpServletRequest req) throws Exception;
    protected abstract String getListPageAddressJSP(HttpServletRequest req);
    protected abstract String getEditPageAddressJSP(HttpServletRequest req);
    protected abstract Generic fillParameters(HttpServletRequest req) throws Exception;

    public ModelAndView processRequest(HttpServletRequest req)  {
        PageAction action = getPageAction(req);
        try{
            switch (action){
                case ADD : return addObject(req);
                case UPDATE : return updateObject(req);
                case DELETE : return deleteObject(req);
                case LIST_ONE: return listObject(req);
                case LIST_ALL:
                default:   return listAllObjects(req);
            }
        } catch (JDBCException e){
            return new ModelAndView("/message.jsp", "model",
                    new MessageContents(
                            e.getSQLException().getMessage(),
                            e.getSQLException().getMessage(),
                            "javascript:history.back()",
                            "Back"));
        } catch (Exception e){
            return new ModelAndView("/message.jsp", "model",
                    new MessageContents(
                            e.getMessage(),
                            e.getMessage(),
                            "javascript:history.back()",
                            "Back"));
        }
    }

    protected ModelAndView listObject(HttpServletRequest req) throws Exception {
        return new ModelAndView(getEditPageAddressJSP(req), "model", getService().getObject(getId(req)));
    }

    public ModelAndView listAllObjects(HttpServletRequest req) throws Exception{
        return new ModelAndView(getListPageAddressJSP(req), "model", getService().getAll());
    }

    protected ModelAndView addObject(HttpServletRequest req) throws Exception {
        getService().addObject(fillParameters(req));

        return actionCompletedMessage(req, "added", true);
    }

    protected ModelAndView updateObject(HttpServletRequest req) throws Exception {
        Generic object = fillParameters(req);
        object.setId(getId(req));
        getService().updateObject(object);

        return actionCompletedMessage(req, "updated", false);
    }

    protected ModelAndView deleteObject(HttpServletRequest req) throws Exception {
        getService().deleteObject(getId(req));

        return actionCompletedMessage(req, "deleted", false);
    }

    protected ModelAndView actionCompletedMessage(HttpServletRequest req, String actionName, boolean isNew) throws Exception {
        return new ModelAndView("/message.jsp", "model",
                new MessageContents(
                        (isNew ? "New " : "") + getObjectName() + " " + actionName,
                        (isNew ? "New " : "") + getObjectName() + " " + actionName,
                        getListPageAddress(req),
                        "Back to " + getObjectName() + "s List"));
    }

    protected long getId(HttpServletRequest req) {
        try {
            return Long.decode(req.getParameter("id"));
        } catch (Exception e) {
            return 0;
        }
    }

    private PageAction getPageAction(HttpServletRequest req){
        if (req.getParameter("act_add") != null) return PageAction.ADD;
        if (req.getParameter("act_update") != null) return PageAction.UPDATE;
        if (req.getParameter("act_delete") != null) return PageAction.DELETE;
        if (req.getParameter("id") != null) return PageAction.LIST_ONE;
        return PageAction.LIST_ALL;
    }

}
