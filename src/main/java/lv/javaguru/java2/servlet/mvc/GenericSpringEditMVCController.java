package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.core.GenericFactory;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.servlet.mvc.data.MessageContents;
import org.hibernate.JDBCException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public abstract class GenericSpringEditMVCController {

    public ModelAndView processRequest(HttpServletRequest req)  {
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
            return new ModelAndView("/message.jsp", "model",
                    new MessageContents(e.getSQLException().getMessage(),e.getSQLException().getMessage(),
                            "javascript:history.back()","Back"));
        }
        catch (Exception e){
            return new ModelAndView("/message.jsp", "model",
                    new MessageContents(e.getMessage(),e.getMessage(),"javascript:history.back()","Back"));
        }
    }

    protected abstract GenericFactory getService();
    protected abstract String getObjectName();
    protected abstract String getEditPageAddressJSP();
    protected abstract String getListPageAddress(HttpServletRequest req);
    protected abstract Generic fillParameters(HttpServletRequest req) throws Exception;

    protected ModelAndView listObject(HttpServletRequest req) throws Exception {
        return new ModelAndView(getEditPageAddressJSP(), "model", getService().getObject(getId(req)));
    }

    protected ModelAndView addObject(HttpServletRequest req) throws Exception {
        getService().addObject(fillParameters(req));

        return new ModelAndView("/message.jsp", "model",
                new MessageContents(
                        "New "+getObjectName()+" created",
                        "New "+getObjectName()+" created",
                        getListPageAddress(req),
                        "Back to "+getObjectName()+"s List"));
    }

    protected ModelAndView updateObject(HttpServletRequest req) throws Exception {
        Generic object = fillParameters(req);
        object.setId(getId(req));
        getService().updateObject(object);

        return new ModelAndView("/message.jsp", "model",
                new MessageContents(
                        getObjectName()+" updated",
                        getObjectName()+" updated",
                        getListPageAddress(req),
                        "Back to "+getObjectName()+"s List"));
    }

    protected ModelAndView deleteObject(HttpServletRequest req) throws Exception {
        getService().deleteObject(getId(req));

        return new ModelAndView("/message.jsp", "model",
                new MessageContents(
                        getObjectName()+" deleted",
                        getObjectName()+" deleted",
                        getListPageAddress(req),
                        "Back to "+getObjectName()+"s List"));
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
