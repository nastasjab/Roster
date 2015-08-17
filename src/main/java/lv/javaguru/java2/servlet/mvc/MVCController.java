package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;

import javax.servlet.http.HttpServletRequest;

public interface MVCController {

    MVCModel processRequest (HttpServletRequest req);
}
