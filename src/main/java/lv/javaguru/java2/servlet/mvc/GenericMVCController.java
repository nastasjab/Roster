package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.core.GenericFactory;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.servlet.mvc.data.MessageContents;
import org.hibernate.JDBCException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class GenericMVCController {
    public MVCModel processRequest(HttpServletRequest req)  {

        List<Generic> list;

        try {
            list = getService().getAll();
        } catch (JDBCException e){
            return new MVCModel(
                    new MessageContents(
                            e.getSQLException().getMessage(),
                            e.getSQLException().getMessage(),
                            getListPageAddress(),
                            "Back"), "/message.jsp");
        }

        catch (Exception e){
            return new MVCModel(
                    new MessageContents(
                            e.getMessage(),
                            e.getMessage(),
                            getListPageAddress(),
                            "Back"), "/message.jsp");
        }

        return new MVCModel(list, getListPageAddress());
    }

    protected abstract String getListPageAddress();
    protected abstract GenericFactory getService();
}
