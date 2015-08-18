package lv.javaguru.java2.servlet.mvc.controller;

import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.MVCModel;
import lv.javaguru.java2.servlet.mvc.data.Menu;
import lv.javaguru.java2.servlet.mvc.data.MenuItem;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class MainMenuController implements MVCController {

    public MVCModel processRequest(HttpServletRequest req) {

        Menu menu = new Menu();

        menu.getMenu().add(new MenuItem("/roster/users", "Users"));
        menu.getMenu().add(new MenuItem("/roster/shifts", "Shifts"));
        menu.getMenu().add(new MenuItem("/roster/patterns", "Patterns"));
        menu.getMenu().add(new MenuItem("/roster/roster", "Roster"));

        return new MVCModel(menu, "/mainMenu.jsp");
    }
}
