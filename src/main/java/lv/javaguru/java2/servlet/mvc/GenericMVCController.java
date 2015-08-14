package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.Generic;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericMVCController <T extends DAO, R extends Generic> {

    @Autowired
    private T dao;

    public MVCModel processRequest(HttpServletRequest req) {

        List<R> list = new ArrayList<R>();

        try {
            list = dao.getAll();
        } catch (DBException e) {
            e.printStackTrace();
        }

        return new MVCModel(list, "/shifts.jsp");
    }

    protected abstract String getListPageAddress();
}
