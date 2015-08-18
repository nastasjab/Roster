package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.GenericDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.servlet.mvc.data.MessageContents;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericMVCController <T extends GenericDAO, R extends Generic> {

    @Autowired
    private T dao;

    public MVCModel processRequest(HttpServletRequest req) {

        List<R> list;

        try {
            list = dao.getAll();
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
}
