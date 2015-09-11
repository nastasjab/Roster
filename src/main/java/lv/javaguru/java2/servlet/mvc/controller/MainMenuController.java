package lv.javaguru.java2.servlet.mvc.controller;

import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.MVCModel;
import lv.javaguru.java2.servlet.mvc.data.Menu;
import lv.javaguru.java2.servlet.mvc.data.MenuItem;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainMenuController {

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView processRequest(HttpServletRequest req) {

        Menu menu = new Menu();

        menu.getMenu().add(new MenuItem("/roster/users", "Users"));
        menu.getMenu().add(new MenuItem("/roster/shifts", "Shifts"));
        menu.getMenu().add(new MenuItem("/roster/patterns", "Patterns"));
        menu.getMenu().add(new MenuItem("/roster/roster", "Roster"));

        return new ModelAndView("/mainMenu.jsp", "model", menu);
    }
}
