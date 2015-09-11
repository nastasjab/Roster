package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.core.GenericFactory;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.servlet.mvc.data.MessageContents;
import org.hibernate.JDBCException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class GenericSpringMVCController {
    public ModelAndView processRequest(HttpServletRequest req)  {

        List<Generic> list=null;

        try {
            list = getService().getAll();
        } catch (JDBCException e){
            new ModelAndView( "/message.jsp", "model", new MessageContents( e.getSQLException().getMessage(),
                    e.getSQLException().getMessage(), getListPageAddress(), "Back"));
        }

        catch (Exception e){
            new ModelAndView( "/message.jsp", "model", new MessageContents( e.getMessage(), e.getMessage(),
                    getListPageAddress(), "Back"));
        }

        return new ModelAndView(getListPageAddress(), "model", list);
    }

    protected abstract String getListPageAddress();
    protected abstract GenericFactory getService();
}
