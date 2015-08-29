package lv.javaguru.java2.core.roster;

import lv.javaguru.java2.domain.Roster;
import lv.javaguru.java2.domain.User;

import java.sql.Date;
import java.util.List;

public interface RosterService {
    Roster getRoster(Date from, Date till);
    Roster getRoster(Date from, Date till, List<User> forUsers);
}
