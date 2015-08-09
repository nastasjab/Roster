package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Roster extends Generic {

    Date from;
    Date till;

    List<User> users = new ArrayList<User>();


}
