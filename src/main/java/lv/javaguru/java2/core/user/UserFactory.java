package lv.javaguru.java2.core.user;

import lv.javaguru.java2.core.GenericFactory;
import lv.javaguru.java2.domain.user.User;

import java.util.List;

public interface UserFactory extends GenericFactory {

    List<User> getAllSorted()  throws Exception;

    User getByLogin(String login);
}
