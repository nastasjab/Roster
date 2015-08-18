package lv.javaguru.java2.servlet.mvc.data;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    List<MenuItem> menu;

    public Menu() {
        menu = new ArrayList<MenuItem>();
    }

    public Menu(List<MenuItem> menu) {
        this.menu = menu;
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItem> menu) {
        this.menu = menu;
    }

}
